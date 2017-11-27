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
	 * @param session
	 * @param message
	 * @return
	 */
	public <T> Boolean sendMessageToUser(WebSocketSession session,WebSocketMessage<T> message);
	public Boolean sendMessageToUser(WebSocketSession session, String receiptType, String receiptStatus, Object obj);
	/**
	 * 双发消息
	 * @param session
	 * @param message
	 * @return
	 */
	public <T> Boolean sendMessageToRoom(WebSocketSession sessionA, WebSocketSession sessionB, String receiptType,
			String receiptStatus, Object obj);
	/**
	 * 群发消息
	 * @param sessionList
	 * @param message
	 */
	public <T> void sendMessageToAllUser(List<WebSocketSession> sessionList,WebSocketMessage<T> message);
	 
}
