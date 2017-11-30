package cn.clvstudio.game.chuachua.game;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import javax.annotation.Resource;

import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;
import cn.clvstudio.game.chuachua.constants.Constants.PlayerStatus;
import cn.clvstudio.game.chuachua.constants.Constants.ReceiptStatus;
import cn.clvstudio.game.chuachua.constants.Constants.ReceiptType;
import cn.clvstudio.game.chuachua.constants.Constants.RoomStatus;
import cn.clvstudio.game.chuachua.game.model.Ball;
import cn.clvstudio.game.chuachua.game.model.Ban;
import cn.clvstudio.game.chuachua.game.model.Brick;
import cn.clvstudio.game.chuachua.game.model.Time;
import cn.clvstudio.game.chuachua.game.model.Wall;
import cn.clvstudio.game.chuachua.model.MsgGameOfBallTime;
import cn.clvstudio.game.chuachua.model.MsgGameOfScoreCBrick;
import cn.clvstudio.game.chuachua.model.Player;
import cn.clvstudio.game.chuachua.service.IMessageService;

/**
 * 游戏
 * @author Darnell
 *
 */
public class Game implements Runnable{
	
	@Resource
	private IMessageService messageService;

	/**房间号*/
	private String roomId;
	/**玩家A*/
	private Player playerA;
	/**玩家B*/
	private Player playerB;
	/**房间状态（0：准备中；1：发球中；2：游戏中 ；3：结算中）*/
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
	
	public Game(Player playerA,Player playerB){
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
	
	public void run(){
		if(status.equals(RoomStatus.ROOM_STATUS_READY)){
			if(PlayerStatus.PLAYER_STATUS_GAME.equals(playerA.getStatus()) && PlayerStatus.PLAYER_STATUS_GAME.equals(playerB.getStatus()) ){
				this.status = RoomStatus.ROOM_STATUS_GAME;
				messageService.sendMessageToUser(playerB.getSession(), 
						ReceiptType.RECEIPT_TYPE_GAME,ReceiptStatus.ROOM_STATUS_SUCCESS,PlayerStatus.PLAYER_FLAG_B );
				messageService.sendMessageToUser(playerA.getSession(), 
						ReceiptType.RECEIPT_TYPE_GAME,ReceiptStatus.ROOM_STATUS_SUCCESS,PlayerStatus.PLAYER_FLAG_A );
			}
			return;
		}
		if(status.equals(RoomStatus.ROOM_STATUS_GAME)){
			Wall.collision(ball);
			if(ball.getCenterY() < GameParameter.MIDLINE){
				isCollision(PlayerStatus.PLAYER_FLAG_B, banToB, brickSToB, ReceiptStatus.CLEAR_BRICK_TO_B);
			}else{
				isCollision(PlayerStatus.PLAYER_FLAG_A, banToA, brickSToA, ReceiptStatus.CLEAR_BRICK_TO_A);
			}
			ball.move();
			time.setTotalMS();
			messageService.sendMessageToRoom(playerA.getSession(),playerB.getSession(), 
					ReceiptType.RECEIPT_TYPE_GAME,ReceiptStatus.RECEIPT_STATUS_SUCCESS,new MsgGameOfBallTime(ball,time));
			if(time.getTotalMS() <= 0 || brickSToB.size() == 0 || brickSToA.size() == 0){
				this.status = RoomStatus.ROOM_STATUS_SETTLE;
				playerA.setStatus(PlayerStatus.PLAYER_STATUS_SETTLE);
				playerB.setStatus(PlayerStatus.PLAYER_STATUS_SETTLE);
			}
			return;
		}
		if(status.equals(RoomStatus.ROOM_STATUS_SETTLE)){
			
			return;
		}
	}
	
	private void isCollision(int playerFlag,Ban ban,List<Brick> bricks,String receiptStatus){
		Wall.collision(this.ball);
		List<Integer> clearBrick = new Vector<Integer>(); 
		ban.collision(this.ball);
		bricks.forEach(brick -> {
			if(brick.collision(this.ball)){
				clearBrick.add(brick.getId());
				bricks.remove(brick);
			}
		});
		if(playerFlag == PlayerStatus.PLAYER_FLAG_A){
			scoreB += GameParameter.BRICK_SCORE;
		}else{
			scoreA += GameParameter.BRICK_SCORE;
		}
		if(clearBrick.size() > 0){
			messageService.sendMessageToRoom(playerA.getSession(),playerB.getSession(), 
					ReceiptType.RECEIPT_TYPE_GAME,receiptStatus,new MsgGameOfScoreCBrick(scoreA,scoreB,clearBrick));
		}
	}
	
	public void moveBanToA(double disRatio){
		banToA.move(disRatio);
	}
	
	public void moveBanToB(double disRatio){
		banToB.move(disRatio);
	}

	public String getRoomId() {
		return roomId;
	}
	
}
