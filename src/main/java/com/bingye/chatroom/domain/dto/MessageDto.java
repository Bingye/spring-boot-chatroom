/*
* Copyright @ 2021 bingye
* spring-boot-chatroom 下午3:53:57
* All right reserved.
*
*/
package com.bingye.chatroom.domain.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @desc: spring-boot-chatroom
* @author: bingye
* @createTime: 2021年2月3日 下午3:53:57
* @history:
* @version: v1.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable{

	/**
	* TODO
	*/
	private static final long serialVersionUID = 5676111417760489740L;

	private String msgType;
	
	private String fromuser;
	
	private String touser;
	
	private String message;
	
	private String sendtime;
	
	private String roomId;
	
	private List<String> roomUsernames;
	
	
	/**
	* @param msgType
	* @param fromuser
	* @param touser
	* @param message
	* @param sendtime
	*/
	public MessageDto(String msgType, String fromuser, String touser, String message, String sendtime) {
		super();
		this.msgType = msgType;
		this.fromuser = fromuser;
		this.touser = touser;
		this.message = message;
		this.sendtime = sendtime;
	}

	/**
	* @param msgType
	* @param fromuser
	* @param touser
	* @param message
	* @param sendtime
	*/
	public MessageDto(String msgType, String fromuser, String touser, String message, String sendtime,String roomId) {
		super();
		this.msgType = msgType;
		this.fromuser = fromuser;
		this.touser = touser;
		this.message = message;
		this.sendtime = sendtime;
		this.roomId = roomId;
	}
	
	
}
