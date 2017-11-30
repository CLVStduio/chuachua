package cn.clvstudio.game.chuachua.model;

import org.springframework.web.socket.WebSocketSession;

/**
 * 玩家信息类
 * @author Darnell
 *
 */
public class Player {
	private String playerId;
	private String name;
	/**
	 * 状态（0：闲逛中；1：匹配中；2：游戏中；3：结算中;9:断线;）
	 */
	private String status;
	private WebSocketSession session;
	
	public Player(String playerId,String name){
		this.playerId = playerId;
		this.name = name;
	}
	
	public Player(WebSocketSession session,String status){
		this.session = session;
		this.status = status;
	}
	
	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	
}
