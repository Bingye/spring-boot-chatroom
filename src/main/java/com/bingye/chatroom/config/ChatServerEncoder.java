/*
* Copyright @ 2021 bingye
* spring-boot-chatroom 下午5:47:17
* All right reserved.
*
*/
package com.bingye.chatroom.config;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.alibaba.fastjson.JSONObject;
import com.bingye.chatroom.domain.dto.MessageDto;

/**
* @desc: spring-boot-chatroom
* @author: bingye
* @createTime: 2021年2月3日 下午5:47:17
* @history:
* @version: v1.0
*/
public class ChatServerEncoder implements Encoder.Text<MessageDto>{

	/*
	*(non-Javadoc)
	* @see javax.websocket.Encoder#init(javax.websocket.EndpointConfig)
	*/
	@Override
	public void init(EndpointConfig endpointConfig) {
		// TODO Auto-generated method stub
		
	}

	/*
	*(non-Javadoc)
	* @see javax.websocket.Encoder#destroy()
	*/
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/*
	*(non-Javadoc)
	* @see javax.websocket.Encoder.Text#encode(java.lang.Object)
	*/
	@Override
	public String encode(MessageDto object) throws EncodeException {
		return JSONObject.toJSONString(object);
	}

}
