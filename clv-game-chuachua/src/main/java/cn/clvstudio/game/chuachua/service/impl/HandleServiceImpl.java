package cn.clvstudio.game.chuachua.service.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;

import cn.clvstudio.game.chuachua.constants.Constants;
import cn.clvstudio.game.chuachua.constants.Constants.PlayerStatus;
import cn.clvstudio.game.chuachua.constants.Constants.ReceiptStatus;
import cn.clvstudio.game.chuachua.constants.Constants.ReceiptType;
import cn.clvstudio.game.chuachua.model.MessageReceipt;
import cn.clvstudio.game.chuachua.model.Player;
import cn.clvstudio.game.chuachua.service.IHandleService;
import cn.clvstudio.game.chuachua.service.IMessageService;

/**
 * @author Darnell
 *
 */
@Service
public class HandleServiceImpl implements IHandleService {

	@Resource
	private IMessageService messageService;
	
	@Override
	public void matchMessage(Player myPlay,ConcurrentHashMap<String, Player> allPlayer,List<Player> matchPlayer,ConcurrentHashMap<String,Player> gamePlayer) {
		WebSocketSession session = myPlay.getSession();
		if(gamePlayer.size()>Constants.MAX_GAME_ROOM_NUMS){
			messageService.sendMessageToUser(session, new TextMessage(
					JSON.toJSONString(new MessageReceipt(ReceiptType.RECEIPT_TYPE_MATCH,
							ReceiptStatus.RECEIPT_STATUS_FALSE,"当前游戏人数过多，请耐心等待"))
				));
			return;
		}
		if(matchPlayer.size()>0){
			Player air = matchPlayer.get(0);
			if(null != air){
				String airId = air.getSession().getId();
				Player airPlay = allPlayer.get(airId);
				if(null != airPlay && null !=myPlay){
					messageService.sendMessageToUser(airPlay.getSession(), new TextMessage(
							JSON.toJSONString(new MessageReceipt(ReceiptType.RECEIPT_TYPE_MATCH,
									ReceiptStatus.RECEIPT_STATUS_SUCCESS,session.getId()))
						));
					messageService.sendMessageToUser(session, new TextMessage(
							JSON.toJSONString(new MessageReceipt(ReceiptType.RECEIPT_TYPE_MATCH,
									ReceiptStatus.RECEIPT_STATUS_SUCCESS,airId))
						));
					airPlay.setStatus(PlayerStatus.PLAYER_STATUS_GAME);
					myPlay.setStatus(PlayerStatus.PLAYER_STATUS_GAME);
					gamePlayer.put(airId, myPlay);
					gamePlayer.put(session.getId(), airPlay);
					matchPlayer.remove(0);
					return;
				}
			}
			matchPlayer.remove(0);
		}
		myPlay.setStatus(PlayerStatus.PLAYER_STATUS_MATCH);
		matchPlayer.add(myPlay);
		return;
	}

	@Override
	public void gameMessage(Player myPlay,ConcurrentHashMap<String,Player> gamePlayer,String msg) {
		Player air = gamePlayer.get(myPlay.getSession().getId());
		messageService.sendMessageToUser(air.getSession(), new TextMessage(
			JSON.toJSONString(new MessageReceipt(ReceiptType.RECEIPT_TYPE_GAME,ReceiptStatus.RECEIPT_STATUS_SUCCESS,msg))
		));
	}

	@Override
	public void settleMessage() {

	}

}
