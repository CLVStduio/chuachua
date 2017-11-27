package cn.clvstudio.game.chuachua.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import cn.clvstudio.game.chuachua.model.Player;

/**
 * 信息监听处理
 * @author Darnell
 *
 */
public interface IHandleService {
	/**
	 * 匹配
	 * @param myPlay
	 * @param allPlayer
	 * @param matchPlayer
	 * @param gamePlayer
	 */
	public void matchMessage(Player myPlay,ConcurrentHashMap<String, Player> allPlayer,List<Player> matchPlayer,ConcurrentHashMap<String,Player> gamePlayer);
	/**
	 * 游戏中
	 * @param myPlay
	 * @param gamePlayer
	 * @param msg
	 */
	public void gameMessage(Player myPlay,ConcurrentHashMap<String,Player> gamePlayer,String msg);
	/**结算*/
	public void settleMessage();
}
