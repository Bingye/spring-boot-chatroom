package com.bingye.chatroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketStompConfig {

	@Bean
	public ServerEndpointExporter getServerEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
}
