package cn.clvstudio.game.chuachua.websocket;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;

import cn.clvstudio.game.chuachua.constants.Constants;
import cn.clvstudio.game.chuachua.constants.Constants.PlayerStatus;
import cn.clvstudio.game.chuachua.constants.Constants.ReceiptType;
import cn.clvstudio.game.chuachua.constants.Constants.RoomStatus;
import cn.clvstudio.game.chuachua.model.game.Player;
import cn.clvstudio.game.chuachua.model.message.MessageGeneral;
import cn.clvstudio.game.chuachua.schedule.GameRoom;
import cn.clvstudio.game.chuachua.service.IHandleService;
import cn.clvstudio.game.chuachua.service.IMessageService;

/**
 * @author Darnell
 *
 */
public class WebsocketHandler implements WebSocketHandler {

	private static final  Logger LOG = LoggerFactory.getLogger(WebsocketHandler.class);
	
	/**玩家的id和玩家的信息*/
	private Map<String, Player> allPlayer = new ConcurrentHashMap<String,Player>();
	/**匹配中的玩家*/
	private List<Player> matchPlayer = new Vector<Player>();
	/** 游戏中的玩家对房间信息<自身的id，房间信息> */
	private Map<String,GameRoom> playerToRoom = new ConcurrentHashMap<String,GameRoom>();
	/** 断线玩家的id */
	private List<String> closePlayer = new Vector<String>();
	
	@Resource
	private IMessageService messageService;
	@Resource
	private IHandleService handleService;
	
	
	/** 
	 * 成功连接后自动调用
	 * @see org.springframework.web.socket.WebSocketHandler#afterConnectionEstablished(org.springframework.web.socket.WebSocketSession)
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOG.info("连接者id："+session.getId());
		if(!allPlayer.containsKey(session.getId())){
			Player player = new Player(session,PlayerStatus.PLAYER_STATUS_IDLE);
			allPlayer.put(session.getId(),player);
			if(closePlayer.size() > Constants.MAX_GAME_ROOM_NUMS){
				String id = closePlayer.get(0);
				allPlayer.remove(id);
				closePlayer.remove(0);
			}
			return;
		}
//		closePlayer.forEach(playerId -> {
//			if(playerId.equals(session.getId())){
//				if(playerToRoom.containsKey(playerId)){
//					allPlayer.get(playerId).setStatus(PlayerStatus.PLAYER_STATUS_GAME);
//					return;
//				}
//				allPlayer.get(playerId).setStatus(PlayerStatus.PLAYER_STATUS_IDLE);
//				return;
//			}
//		});
	}

	/** 
	 * 监听新的消息
	 * @see org.springframework.web.socket.WebSocketHandler#handleMessage(org.springframework.web.socket.WebSocketSession,
	 *  org.springframework.web.socket.WebSocketMessage)
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		LOG.info("监听："+message.getPayload());
		MessageGeneral msg = JSON.parseObject(message.getPayload().toString(),MessageGeneral.class);
		Player myPlay = allPlayer.get(session.getId());
		if(null != myPlay){
			if(ReceiptType.RECEIPT_TYPE_MATCH.equals(msg.getT())){
				handleService.matchMessage(myPlay, allPlayer, matchPlayer, playerToRoom);
			}
			if(ReceiptType.RECEIPT_TYPE_GAME.equals(msg.getT())){
				handleService.gameMessage(myPlay, playerToRoom,msg.getC());
			}
			if(ReceiptType.RECEIPT_TYPE_SETTLE.equals(msg.getT())){
				handleService.settleMessage(myPlay, playerToRoom);
			}
		}else {
			if(ReceiptType.RECEIPT_TYPE_MATCH.equals(msg.getT())){
				allPlayer.put(session.getId(), new Player(session,PlayerStatus.PLAYER_STATUS_MATCH));
				matchPlayer.add(new Player(session,PlayerStatus.PLAYER_STATUS_MATCH));
			}
			return;
		}
	}

	/** 
	 * 处理底层websocket消息传输中的异常情况
	 * @see org.springframework.web.socket.WebSocketHandler#handleTransportError(org.springframework.web.socket.WebSocketSession, java.lang.Throwable)
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		
	}

	/** 
	 * 传输关闭后、发送传输错误之后调用
	 * @see org.springframework.web.socket.WebSocketHandler#afterConnectionClosed(org.springframework.web.socket.WebSocketSession, org.springframework.web.socket.CloseStatus)
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		LOG.info("连接关闭-id："+session.getId()+"。》》 》  closeStatus:"+closeStatus.toString());
		Player player = allPlayer.get(session.getId());
		if(player != null){
			player.setStatus(PlayerStatus.PLAYER_STATUS_CLOSE);
			GameRoom game = playerToRoom.get(player.getPlayerId());
			if(game != null){
				if(RoomStatus.ROOM_STATUS_OVER.equals(game.getStatus())){
					playerToRoom.remove(player.getPlayerId(), game);
				}
			}
			matchPlayer.remove(allPlayer.get(session.getId()));
			allPlayer.remove(session.getId(), player);
		}
	}

	/**
	 *  WebSocketHandler是否处理部分消息。 
	 * 如果此标志设置为true并且底层的WebSocket服务器支持部分消息，
	 * 那么可能会将大型WebSocket消息或未知大小之一拆分，
	 * 并可能通过多个调用接收到handleMessage（WebSocketSession，WebSocketMessage）。 
	 * 标志org.springframework.web.socket.WebSocketMessage.isLast（）指示消息是否部分，
	 * 以及是否是最后一部分。
	 * @see org.springframework.web.socket.WebSocketHandler#supportsPartialMessages()
	 */
	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
