package cn.clvstudio.game.chuachua.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;

import cn.clvstudio.game.chuachua.model.MessageReceipt;
import cn.clvstudio.game.chuachua.service.IMessageService;

/**
 * @author Darnell
 *
 */
@Service
public class MessageServiceImpl implements IMessageService {

	private static final  Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Override
	public <T> Boolean sendMessageToUser(WebSocketSession session,WebSocketMessage<T> message){
		if(session.isOpen()){
			try {
				session.sendMessage(message);
			} catch (IOException e) {
				LOG.error("单发消息异常-->信息内容：{} ，发给对象：{}，异常信息：{}", message.toString(),session.getId(),e);
				return false;
			}
			return true;
		}
		return false;
	}
	@Override
	public Boolean sendMessageToUser(WebSocketSession session, String receiptType, String receiptStatus, Object obj) {
		WebSocketMessage<String> message = null;
		if(session.isOpen()){
			try {
				message = new TextMessage(JSON.toJSONString(new MessageReceipt(receiptType,receiptStatus,obj)));
				session.sendMessage(message);
			} catch (IOException e) {
				LOG.error("单发消息异常-->信息内容：{} ，发给对象：{}，异常信息：{}", message.toString(),session.getId(),e);
				return false;
			}
			return true;
		}
		return false;
	}
	@Override
	public <T> Boolean sendMessageToRoom(WebSocketSession sessionA,WebSocketSession sessionB, String receiptType,String receiptStatus,Object obj) {
		WebSocketMessage<String> message = new TextMessage(JSON.toJSONString(new MessageReceipt(receiptType,receiptStatus,obj)));
		try {
			if(sessionA.isOpen()){
				sessionA.sendMessage(message);
			}
			if(sessionB.isOpen()){
				sessionB.sendMessage(message);
			}
			return true;
		} catch (IOException e) {
			LOG.error("双发消息异常-->信息内容：{} ，发给对象：{}，异常信息：{}", message.toString(),sessionA.getId(),e);
			return false;
		}
	}

	@Override
	public <T> void sendMessageToAllUser(List<WebSocketSession> sessionList,WebSocketMessage<T> message){
		sessionList.forEach(session -> {
			if(session.isOpen()){
				try {
					session.sendMessage(message);
				} catch (IOException e) {
					LOG.error("群发消息异常-->信息内容：{} ，发给对象：{}，异常信息：{}", message.toString(),session.getId(),e);
				}
			}
		});
	}
	

}
