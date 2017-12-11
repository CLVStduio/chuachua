package cn.clvstudio.game.chuachua.constants;

/**
 * 常量库
 * @author Darnell
 *
 */
public class Constants {
	/** 玩家信息 */
	public static final String CURRENT_PLAYER = "CURRENT_PLAYER";
	/** 存储WebsocketSession的key值 */
	public static final String CURRENT_WEBSOCKET_PLAYER = "CURRENT_WEBSOCKET_PLAYER";
	/**最大游戏房间数*/
	public static final Integer MAX_GAME_ROOM_NUMS = 5;
	/**
	 * 玩家状态
	 * @author Darnell
	 */
	public interface PlayerStatus {
		/** 玩家A标志 */
		public static final int PLAYER_FLAG_A = 0;
		/** 玩家B标志 */
		public static final int PLAYER_FLAG_B = 1;
		/** 空闲 */
		public static final String PLAYER_STATUS_IDLE = "0";
		/** 匹配中 */
		public static final String PLAYER_STATUS_MATCH = "1";
		/** 准备开始游戏 */
		public static final String PLAYER_STATUS_READY = "2";
		/** 游戏中 */
		public static final String PLAYER_STATUS_GAME = "3";
		/** 结算中 */
		public static final String PLAYER_STATUS_SETTLE= "4";
		/** 断线  */
		public static final String PLAYER_STATUS_CLOSE= "9";
		
	}
	/**
	 * 信息类型
	 * @author Darnell
	 */
	public interface ReceiptType {
		/** 匹配回执 */
		public static final String RECEIPT_TYPE_MATCH= "b";
		/** 游戏回执 */
		public static final String RECEIPT_TYPE_GAME= "c";
		/** 结算回执 */
		public static final String RECEIPT_TYPE_SETTLE= "d";
	}
	/** 来自玩家的固定信息
	 * @author Darnell
	 */
	public interface MessageForPlayer {
		/** 玩家准备完毕 */
		public static final String PLAYER_READY_OVER = "PRO";
	}
	/**
	 * 回执状态
	 * @author Darnell
	 */
	public interface ReceiptStatus {
		/******** 游戏中的回执状态 *********/
		/** 房间准备完毕 */
		public static final String ROOM_STATUS_SUCCESS = "rs";
		/** 游戏中球和时间信息 */
		public static final String GAME_BALL_TIME = "bt";
		/** 清理A的砖块 */
		public static final String CLEAR_BRICK_TO_A = "ca";
		/** 清理B的砖块 */
		public static final String CLEAR_BRICK_TO_B = "cb";
		public static final String MOVE_BAN = "mb";
		/******** 游戏中的回执状态 *********/
		
		/** 成功接收并处理 */
		public static final String RECEIPT_STATUS_SUCCESS = "s";
		/** 失败 */
		public static final String RECEIPT_STATUS_FALSE = "f";
	}
	/**
	 * 房间状态
	 * @author Darnell
	 */
	public interface RoomStatus {
		/** 准备中 */
		public static final String ROOM_STATUS_READY = "1";
		/** 游戏中 */
		public static final String ROOM_STATUS_GAME = "2";
		/** 结算中 */
		public static final String ROOM_STATUS_SETTLE= "3";
		/** 待销毁 */
		public static final String ROOM_STATUS_OVER = "4";
		/** 初始游戏时间 */
		public static final int ROOM_INIT_TIME= 180000;
	}
	/**
	 * 游戏的部分参数
	 * @author Darnell
	 */
	public interface GameParameter {
		/**最大的速度*/
		public static final double MAX_SEEP = 20;
		/**最小的速度*/
		public static final double MIN_SEEP = 5;
		
		/**界面的高*/
		public static final double INTERFACE_HEIGHT = 1600;
		/**界面的宽*/
		public static final double INTERACE_WIDTH = 900;
		/**中间线*/
		public static final double MIDLINE = 800;
		
		/**砖的高*/
		public static final int BRICK_HEIGHT = 60;
		/**砖的宽*/
		public static final int BRICK_WIDTH = 100;
		
		/**板的高*/
		public static final int BAN_HEIGHT = 48;
		/**板的宽*/
		public static final int BAN_WIDTH = 225;
		
		/**球的直径*/
		public static final double BALL_DIAMETER = 51.2;
		/**球的半径*/
		public static final double BALL_RADIUS = 25.6;
		
		/**玩家A板的y坐标*/
		public static final double BAN_TOP_A = 1100.8;
		/**玩家B板的y坐标*/
		public static final double BAN_TOP_B = 451.2;
		
		/**上面触碰 */
		public static final String COLLISION_UP ="CU";
		/**下面触碰*/
		public static final String COLLISION_DOWN ="CD";
		/**左面触碰*/
		public static final String COLLISION_LEFT = "CL";
		/**右面触碰*/
		public static final String COLLISION_RIGHT = "CR";
		/**角被碰到*/
		public static final String COLLISION_ANGLE = "CA";
		/**物体：板*/
		public static final String OBJ_BAN = "ban";
		/**物体：砖*/
		public static final String OBJ_BRICK = "brick";
		/**物体：墙*/
		public static final String OBJ_WALL = "wall";
		
		/** 游戏运行间隔毫秒数 */
		public static final long RUN_TIME= 10;
		/** 每个砖的得分 */
		public static final int BRICK_SCORE = 20;
		
		/** 玩家A获胜 */
		public static final int GAME_WIN_A = 0;
		/** 玩家B获胜 */
		public static final int GAME_WIN_B= 1;
		/** 平局 */
		public static final int GAME_GRAW = 2;
	}
	
	/** 监视者
	 * @author Darnell
	 */
	public interface Surveillancer{
		/** 启动延后时间 */
		public static final long initialDelay = 5;
		/** 行动运行时间 */
		public static final long delay = 5;
	}
}
