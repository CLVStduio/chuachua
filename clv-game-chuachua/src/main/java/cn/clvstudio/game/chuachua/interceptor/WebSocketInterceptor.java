package cn.clvstudio.game.chuachua.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import cn.clvstudio.game.chuachua.constants.Constants;
import cn.clvstudio.game.chuachua.model.game.Player;

/**
 * websocket拦截器
 * @author Darnell
 *
 */
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		/** 旧版本的浏览器或者三方工具利用了比较旧的非规范扩展“x-webkit-deflate-frame”,
		 * 而最新的浏览器发送的是websocket草案中规范的“permessage-deflate”，
		 * 所以在拦截器内强行修改websocket协议，
		 * 将部分浏览器不支持的 x-webkit-deflate-frame 
		 * 扩展修改成 permessage-deflate */  
		if(request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
			request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
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
		super.afterHandshake(request, response, wsHandler, exception);
	}

}
