/*
* Copyright @ 2021 bingye
* spring-boot-chatroom 下午3:32:29
* All right reserved.
*
*/
package com.bingye.chatroom.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
* @desc: spring-boot-chatroom
* @author: bingye
* @createTime: 2021年2月3日 下午3:32:29
* @history:
* @version: v1.0
*/

public class GetHttpSessionConfigurator extends Configurator {
	
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession=(HttpSession) request.getHttpSession();
        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }

}
