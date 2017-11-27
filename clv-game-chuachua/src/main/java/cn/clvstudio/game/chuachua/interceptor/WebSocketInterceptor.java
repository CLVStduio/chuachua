package cn.clvstudio.game.chuachua.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import cn.clvstudio.game.chuachua.constants.Constants;
import cn.clvstudio.game.chuachua.model.Player;

/**
 * websocket拦截器
 * @author Darnell
 *
 */
public class WebSocketInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		//将ServerHttpRequest转换成request请求相关的类，用来获取request域中的用户信息	
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpServletRequest httpRequest = servletRequest.getServletRequest();
			Player player = (Player) httpRequest.getAttribute(Constants.CURRENT_PLAYER);
			if(null != player){
				attributes.put(Constants.CURRENT_WEBSOCKET_PLAYER, player.getSession().getId());
				return true;
			}
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {

	}

}
