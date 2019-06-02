package com.example.springwebproektna.config;

import com.example.springwebproektna.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    SecurityUtils utils;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker( "/chat");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }



    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    Authentication user =  utils.getUser(); // access authentication header(s)
                    accessor.setUser(user);
                }
                return message;
            }

            @Override
            public void postSend(Message<?> message, MessageChannel messageChannel, boolean b) {

            }

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel messageChannel, boolean b, @Nullable Exception e) {

            }

            @Override
            public boolean preReceive(MessageChannel messageChannel) {
                return false;
            }

            @Nullable
            @Override
            public Message<?> postReceive(Message<?> message, MessageChannel messageChannel) {
                return null;
            }

            @Override
            public void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel messageChannel, @Nullable Exception e) {

            }
        });
    }
}
