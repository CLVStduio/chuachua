package cn.clvstudio.game.chuachua.game;

import java.util.UUID;

import cn.clvstudio.game.chuachua.constants.Constants.RoomStatus;
import cn.clvstudio.game.chuachua.game.model.Time;
import cn.clvstudio.game.chuachua.model.Player;

/**
 * 游戏房间
 * @author Darnell
 *
 */
public class GameRoom {
	/**房间号*/
	private String roomId;
	/**玩家A*/
	private Player playerA;
	/**玩家B*/
	private Player playerB;
	/**房间状态（0：准备中；1：发球中；2：游戏中 ；3：结算中）*/
	private String status;
	/**玩家A的分数 */
	private int scoreA;
	/**玩家B的分数*/
	private int scoreB;
	/**游戏时间*/
	private Time time;
	
	public GameRoom(Player playerA,Player playerB){
		this.roomId =  UUID.randomUUID().toString();
		this.playerA = playerA;
		this.playerB = playerB;
		this.status = RoomStatus.ROOM_STATUS_READY;
		this.scoreA = 0;
		this.scoreB = 0;
		this.time = new Time(RoomStatus.ROOM_INIT_TIME);
	}
	
}
