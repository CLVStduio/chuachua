package cn.clvstudio.game.chuachua.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import cn.clvstudio.game.chuachua.interceptor.WebSocketInterceptor;

/**
 * websocke配置类
 * @author Darnell
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketPushHandler(),"/webSocketServer").addInterceptors(new WebSocketInterceptor());
        registry.addHandler(webSocketPushHandler(), "/sockjs/webSocketServer").addInterceptors(new WebSocketInterceptor())
                .withSockJS();		
	}
	@Bean	
    public WebSocketHandler webSocketPushHandler(){
        return new WebsocketHandler();
    }
}
