package cn.clvstudio.game.chuachua.schedule;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;
import cn.clvstudio.game.chuachua.constants.Constants.PlayerStatus;
import cn.clvstudio.game.chuachua.constants.Constants.ReceiptStatus;
import cn.clvstudio.game.chuachua.constants.Constants.ReceiptType;
import cn.clvstudio.game.chuachua.constants.Constants.RoomStatus;
import cn.clvstudio.game.chuachua.model.game.Ball;
import cn.clvstudio.game.chuachua.model.game.Ban;
import cn.clvstudio.game.chuachua.model.game.Brick;
import cn.clvstudio.game.chuachua.model.game.Player;
import cn.clvstudio.game.chuachua.model.game.Time;
import cn.clvstudio.game.chuachua.model.game.Wall;
import cn.clvstudio.game.chuachua.model.message.MsgGameOfBallTime;
import cn.clvstudio.game.chuachua.model.message.MsgGameOfScoreCBrick;
import cn.clvstudio.game.chuachua.service.IMessageService;
import cn.clvstudio.game.chuachua.service.impl.MessageServiceImpl;

/**
 * 游戏房间
 * @author Darnell
 *
 */
public class GameRoom implements Runnable{
	private static final  Logger LOG = LoggerFactory.getLogger(GameRoom.class);
	
	private IMessageService messageService;

	/**房间号*/
	private String roomId;
	/**玩家A*/
	private Player playerA;
	/**玩家B*/
	private Player playerB;
	/**房间状态*/
	private String status;
	/**球*/
	private Ball ball;
	/**游戏时间*/
	private Time time;
	/**玩家A的板*/
	private Ban banToA;
	/**玩家B的板*/
	private Ban banToB;
	/**玩家A的砖*/
	private List<Brick> brickSToA ;
	/**玩家B的砖*/
	private List<Brick> brickSToB ;
	/**玩家A的分数 */
	private int scoreA;
	/**玩家B的分数*/
	private int scoreB;
	
	public GameRoom(Player playerA,Player playerB){
		messageService = new MessageServiceImpl();
		this.roomId =  UUID.randomUUID().toString();
		this.playerA = playerA;
		this.playerB = playerB;
		this.status = RoomStatus.ROOM_STATUS_READY;
		this.ball = new Ball(new Random().nextDouble()*30+30,new Random().nextInt(2)==0?-1:1);
		this.time = new Time(RoomStatus.ROOM_INIT_TIME);
		this.banToA = new Ban(GameParameter.BAN_TOP_A);
		this.banToB = new Ban(GameParameter.BAN_TOP_B);
		this.brickSToA = new Vector<Brick>();
		this.brickSToB = new Vector<Brick>();
		this.scoreA = 0;
		this.scoreB = 0;
		for(int i=0,k=0,j=0;j<4;i++,k++){
			int y = j*GameParameter.BRICK_HEIGHT;
			int yy = (int) (GameParameter.INTERFACE_HEIGHT - (j+1)*GameParameter.BRICK_HEIGHT);
				int x = i*GameParameter.BRICK_WIDTH;
				brickSToB.add(new Brick(k,x,y));
				brickSToA.add(new Brick(k,x,yy));
			if(i >= 8){
				i=-1;
				j++;
			}
		}
	}
	
	public synchronized void run(){
		if(status.equals(RoomStatus.ROOM_STATUS_READY)){
			if(PlayerStatus.PLAYER_STATUS_GAME.equals(playerA.getStatus()) && PlayerStatus.PLAYER_STATUS_GAME.equals(playerB.getStatus()) ){
				this.status = RoomStatus.ROOM_STATUS_GAME;
				return;
			}
			if(PlayerStatus.PLAYER_STATUS_READY.equals(playerA.getStatus())){
				messageService.sendMessageToUser(playerA.getSession(), 
						ReceiptType.RECEIPT_TYPE_MATCH,ReceiptStatus.ROOM_STATUS_SUCCESS,PlayerStatus.PLAYER_FLAG_A );
			}
			if(PlayerStatus.PLAYER_STATUS_READY.equals(playerB.getStatus())){
				messageService.sendMessageToUser(playerB.getSession(), 
						ReceiptType.RECEIPT_TYPE_MATCH,ReceiptStatus.ROOM_STATUS_SUCCESS,PlayerStatus.PLAYER_FLAG_B );
				
			}
			return;
		}
		if(status.equals(RoomStatus.ROOM_STATUS_GAME)){
			LOG.debug("房间："+this.roomId+"，游戏中 球：x : " +ball.getCenterX()+" y :"+ball.getCenterY());
			if(ball.getCenterY() < GameParameter.MIDLINE){
				isCollision(PlayerStatus.PLAYER_FLAG_B, banToB, brickSToB, ReceiptStatus.CLEAR_BRICK_TO_B);
			}else{
				isCollision(PlayerStatus.PLAYER_FLAG_A, banToA, brickSToA, ReceiptStatus.CLEAR_BRICK_TO_A);
			}
			LOG.debug("房间："+this.roomId+"，碰撞检测全部完毕");
			ball.move();

			LOG.debug("房间："+this.roomId+"，球移动后：x : " +ball.getCenterX()+" y :"+ball.getCenterY());
			time.setTotalMS();
			LOG.debug("房间："+this.roomId+", 剩余时间："+time.toString());
			messageService.sendMessageToRoom(playerA.getSession(),playerB.getSession(), 
					ReceiptType.RECEIPT_TYPE_GAME,ReceiptStatus.GAME_BALL_TIME,new MsgGameOfBallTime(ball,time));
			LOG.debug("房间："+this.roomId+", 信息发送完毕");
			if(time.getTotalMS() <= 0 || brickSToB.size() == 0 || brickSToA.size() == 0){
				this.status = RoomStatus.ROOM_STATUS_SETTLE;
				playerA.setStatus(PlayerStatus.PLAYER_STATUS_SETTLE);
				playerB.setStatus(PlayerStatus.PLAYER_STATUS_SETTLE);
			}
			LOG.debug("房间："+this.roomId+"****************");
			return;
		}
		if(status.equals(RoomStatus.ROOM_STATUS_SETTLE)){
			if(!PlayerStatus.PLAYER_STATUS_SETTLE.equals(playerA.getStatus()) && !PlayerStatus.PLAYER_STATUS_SETTLE.equals(playerB.getStatus())){
				this.status = RoomStatus.ROOM_STATUS_OVER;
			}
			return;
		}
	}
	
	private synchronized void isCollision(int playerFlag,Ban ban,List<Brick> bricks,String receiptStatus){
		Wall.collision(this.ball);
		List<Brick> clearBrick = new Vector<Brick>(); 
		List<Integer> clearBrickId = new Vector<Integer>(); 
		ban.collision(this.ball);
		bricks.forEach(brick -> {
			if(brick.collision(this.ball)){
				LOG.debug("砖块被碰撞，id："+brick.getId());
				clearBrick.add(brick);
				if(playerFlag == PlayerStatus.PLAYER_FLAG_A){
					scoreB += GameParameter.BRICK_SCORE;
				}else{
					scoreA += GameParameter.BRICK_SCORE;
				}
			}
		});
		if(clearBrick.size() > 0){
			clearBrick.forEach(brick -> {
				clearBrickId.add(brick.getId());
				bricks.remove(brick);
			});
			messageService.sendMessageToRoom(playerA.getSession(),playerB.getSession(), 
					ReceiptType.RECEIPT_TYPE_GAME,receiptStatus,new MsgGameOfScoreCBrick(scoreA,scoreB,clearBrickId));
		}
	}
	
	public synchronized void moveBanToA(double disRatio){
		banToA.move(disRatio);
	}
	
	public synchronized void moveBanToB(double disRatio){
		banToB.move(disRatio);
	}

	public String getRoomId() {
		return roomId;
	}

	public String getStatus() {
		return status;
	}

	public Player getPlayerA() {
		return playerA;
	}

	public Player getPlayerB() {
		return playerB;
	}

	public int getScoreA() {
		return scoreA;
	}

	public int getScoreB() {
		return scoreB;
	}
	
}
