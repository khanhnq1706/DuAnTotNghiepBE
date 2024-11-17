package com.example.demo.config;

import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Value("${host.fe}")
    String hostFE;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // bật hỗ trợ SockJS để đảm bảo rằng các ứng dụng có thể hoạt động trên các
        // trình duyệt không hỗ trợ WebSocket.
        registry.addEndpoint("/ws/my-websocket-endpoint").setAllowedOrigins(hostFE).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue/");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
