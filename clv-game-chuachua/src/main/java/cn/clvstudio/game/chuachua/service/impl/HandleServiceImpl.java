package cn.clvstudio.game.chuachua.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import cn.clvstudio.game.chuachua.constants.Constants;
import cn.clvstudio.game.chuachua.constants.Constants.GameParameter;
import cn.clvstudio.game.chuachua.constants.Constants.MessageForPlayer;
import cn.clvstudio.game.chuachua.constants.Constants.PlayerStatus;
import cn.clvstudio.game.chuachua.constants.Constants.ReceiptStatus;
import cn.clvstudio.game.chuachua.constants.Constants.ReceiptType;
import cn.clvstudio.game.chuachua.model.game.Player;
import cn.clvstudio.game.chuachua.model.message.MsgSettle;
import cn.clvstudio.game.chuachua.schedule.GameRoom;
import cn.clvstudio.game.chuachua.schedule.TimingWork;
import cn.clvstudio.game.chuachua.service.IHandleService;
import cn.clvstudio.game.chuachua.service.IMessageService;

/**
 * @author Darnell
 *
 */
@Service
public class HandleServiceImpl implements IHandleService {

	@SuppressWarnings("unused")
	private static final  Logger LOG = LoggerFactory.getLogger(HandleServiceImpl.class);
	@Resource
	private IMessageService messageService;
	
	@Override
	public synchronized void matchMessage(Player myPlay,Map<String, Player> allPlayer,List<Player> matchPlayer,Map<String,GameRoom> playerToRoom) {
		WebSocketSession session = myPlay.getSession();
		if(playerToRoom.size() > Constants.MAX_GAME_ROOM_NUMS*2){
			messageService.sendMessageToUser(session,ReceiptType.RECEIPT_TYPE_MATCH,
							ReceiptStatus.RECEIPT_STATUS_FALSE,"当前游戏人数过多，请耐心等待");
			return;
		}
		if(matchPlayer.size()>0){
			if(null != matchPlayer.get(0)){
				String airId = matchPlayer.get(0).getSession().getId();
				Player airPlay = allPlayer.get(airId);
				if(null != airPlay && null !=myPlay && !airId.equals(myPlay.getPlayerId())){
					airPlay.setStatus(PlayerStatus.PLAYER_STATUS_READY);
					myPlay.setStatus(PlayerStatus.PLAYER_STATUS_READY);
					
					GameRoom game = new GameRoom(myPlay,airPlay);
					TimingWork.gameRoomRegistration(game);
					playerToRoom.put(airId, game);
					playerToRoom.put(session.getId(), game);
					
					matchPlayer.remove(0);
					if(PlayerStatus.PLAYER_STATUS_READY.equals(myPlay.getStatus())){
						messageService.sendMessageToUser(myPlay.getSession(), 
								ReceiptType.RECEIPT_TYPE_MATCH,ReceiptStatus.ROOM_STATUS_SUCCESS,PlayerStatus.PLAYER_FLAG_A );
					}
					if(PlayerStatus.PLAYER_STATUS_READY.equals(airPlay.getStatus())){
						messageService.sendMessageToUser(airPlay.getSession(), 
								ReceiptType.RECEIPT_TYPE_MATCH,ReceiptStatus.ROOM_STATUS_SUCCESS,PlayerStatus.PLAYER_FLAG_B );
						
					}
					return;
				}
				return;
			}
			matchPlayer.remove(0);
		}
		myPlay.setStatus(PlayerStatus.PLAYER_STATUS_MATCH);
		matchPlayer.add(myPlay);
		return;
	}

	@Override
	public void gameMessage(Player myPlay, Map<String,GameRoom> playerToRoom,String msg) {
		synchronized (myPlay) {
			if(MessageForPlayer.PLAYER_READY_OVER.equals(msg)){
				myPlay.setStatus(PlayerStatus.PLAYER_STATUS_GAME);
				return;
			}
		}
		GameRoom game = playerToRoom.get(myPlay.getPlayerId());
		int flag = game.getPlayerA().getPlayerId().equals(myPlay.getPlayerId()) ? PlayerStatus.PLAYER_FLAG_A : PlayerStatus.PLAYER_FLAG_B;
		if(flag == PlayerStatus.PLAYER_FLAG_A ){
			game.moveBanToA(Double.parseDouble(msg));
			messageService.sendMessageToUser(game.getPlayerB().getSession(), ReceiptType.RECEIPT_TYPE_GAME, ReceiptStatus.MOVE_BAN, msg);
			return;
		}
		game.moveBanToB(Double.parseDouble(msg));
		messageService.sendMessageToUser(game.getPlayerA().getSession(), ReceiptType.RECEIPT_TYPE_GAME, ReceiptStatus.MOVE_BAN, msg);
		return;
	}

	@Override
	public void settleMessage(Player play, Map<String,GameRoom> playerToRoom) {
		GameRoom game = playerToRoom.get(play.getPlayerId());
		MsgSettle msg = null;
		if(game.getScoreA() > game.getScoreB()){
			msg = new MsgSettle(GameParameter.GAME_WIN_A,game.getScoreA(),game.getScoreB());
		}else if(game.getScoreA() < game.getScoreB()){
			msg = new MsgSettle(GameParameter.GAME_WIN_B,game.getScoreA(),game.getScoreB());
		}else{
			msg = new MsgSettle(GameParameter.GAME_GRAW,game.getScoreA(),game.getScoreB());
		}
		messageService.sendMessageToUser(play.getSession(), ReceiptType.RECEIPT_TYPE_SETTLE, ReceiptStatus.RECEIPT_STATUS_SUCCESS, msg);
	}

}
