
/**
 * 常量库
 */
var Constants = {
	/**
	 * 玩家状态
	 */
	 PlayerStatus:{
		/** 玩家A标志 */
		 PLAYER_FLAG_A : 0,
		/** 玩家B标志 */
		 PLAYER_FLAG_B : 1,
	},
	/**
	 * 信息类型
	 */
	 ReceiptType:{
		/** 匹配回执 */
		 RECEIPT_TYPE_MATCH: "b",
		/** 游戏回执 */
		 RECEIPT_TYPE_GAME: "c",
		/** 结算回执 */
		 RECEIPT_TYPE_SETTLE: "d",
	},
	/** 来自玩家的固定信息
	 */
	 MessageForPlayer:{
		/** 玩家准备完毕 */
		 PLAYER_READY_OVER : "PRO",
	},
	/**
	 * 回执状态
	 */
	 ReceiptStatus:{
		/******** 游戏中的回执状态 *********/
		/** 房间准备完毕 */
		 ROOM_STATUS_SUCCESS : "rs",
		/** 游戏中球和时间信息 */
		 GAME_BALL_TIME : "bt",
		/** 清理A的砖块 */
		 CLEAR_BRICK_TO_A : "ca",
		/** 清理B的砖块 */
		 CLEAR_BRICK_TO_B : "cb",
		 MOVE_BAN : "mb",
		/******** 游戏中的回执状态 *********/
		
		/** 成功接收并处理 */
		 RECEIPT_STATUS_SUCCESS : "s",
		/** 失败 */
		 RECEIPT_STATUS_FALSE : "f",
	},
	
	/**
	 * 游戏的部分参数
	 */
	 GameParameter:{
		
		/** 玩家A获胜 */
		 GAME_WIN_A : 0,
		/** 玩家B获胜 */
		 GAME_WIN_B: 1,
		/** 平局 */
		 GAME_GRAW : 2,
	}
	
}
