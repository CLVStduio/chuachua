package cn.clvstudio.game.chuachua.service;

import java.util.List;
import java.util.Map;

import cn.clvstudio.game.chuachua.model.game.Player;
import cn.clvstudio.game.chuachua.schedule.GameRoom;

/**
 * 信息监听处理
 * @author Darnell
 *
 */
public interface IHandleService {
	/**
	 * 匹配
	 * @param myPlay 发起信息的玩家
	 * @param allPlayer 玩家列表
	 * @param matchPlayer 匹配列表
	 * @param playerToRoom 玩家对房间映射
	 */
	public void matchMessage(Player myPlay, Map<String, Player> allPlayer,List<Player> matchPlayer, Map<String,GameRoom> playerToRoom);
	/**
	 * 游戏中
	 * @param myPlay 发起信息的玩家
	 * @param playerToRoom 玩家对房间映射
	 * @param msg 信息
	 */
	public void gameMessage(Player myPlay, Map<String,GameRoom> playerToRoom,String msg);
	/**结算
	 * @param myPlay 发起信息的玩家
	 * @param playerToRoom 玩家对房间映射
	 */
	public void settleMessage(Player myPlay, Map<String,GameRoom> playerToRoom);
}
