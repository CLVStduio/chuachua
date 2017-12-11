package cn.clvstudio.game.chuachua.service;

import java.util.List;

import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author Darnell
 *
 */
public interface IMessageService {

	/**
	 * 单发消息
	 * @param session 收信人
	 * @param message 信息
	 * @return
	 */
	public <T> Boolean sendMessageToUser(WebSocketSession session,WebSocketMessage<T> message);
	/**
	 * 单发消息
	 * @param session 收信人
	 * @param receiptType 回执类型
	 * @param receiptStatus 回执状态
	 * @param obj 信息
	 * @return
	 */
	public Boolean sendMessageToUser(WebSocketSession session, String receiptType, String receiptStatus, Object obj);
	/** 双发消息
	 * @param sessionA 收信人A
	 * @param sessionB 收信人B
	 * @param receiptType 回执类型
	 * @param receiptStatus 回执状态
	 * @param obj 信息
	 * @return
	 */
	public <T> Boolean sendMessageToRoom(WebSocketSession sessionA, WebSocketSession sessionB, String receiptType,
			String receiptStatus, Object obj);
	/**
	 * 群发消息
	 * @param sessionList 收信人列表
	 * @param message 信息
	 */
	public <T> void sendMessageToAllUser(List<WebSocketSession> sessionList,WebSocketMessage<T> message);
	 
}
