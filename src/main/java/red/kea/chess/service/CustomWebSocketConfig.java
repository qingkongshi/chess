package red.kea.chess.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author： KeA
 * @date： 2021-12-16 17:28:13
 * @version: 1.0
 * @describe: websocket的配置类
 */
@Configuration
@EnableWebSocket
public class CustomWebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customWebSocketHandler(), "/customWebSocketHandler").addInterceptors(new CustomWebSocketInterceptor()).setAllowedOrigins("*");
//        registry.addHandler(customWebSocketHandler(), "/sockjs/customWebSocketHandler").addInterceptors(new CustomWebSocketInterceptor()).setAllowedOrigins("*").withSockJS();
    }


    @Bean
    public WebSocketHandler customWebSocketHandler() {
        return new CustomWebSocketHandler();
    }
}
