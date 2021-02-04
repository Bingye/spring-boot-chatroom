/*
* Copyright @ 2021 bingye
* spring-boot-chatroom 下午3:56:14
* All right reserved.
*
*/
package com.bingye.chatroom.base;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
* @desc: spring-boot-chatroom
* @author: bingye
* @createTime: 2021年2月3日 下午3:56:14
* @history:
* @version: v1.0
*/
@AllArgsConstructor
@NoArgsConstructor
public enum MessageType {
	
	ENTHALL("发起聊天","ENTHALL"),
	
	LEVHALL("离开大厅","LEVHALL"),
	
	ENTROOM("发起聊天","ENTROOM"),
	
	LEVROOM("离开房间","LEVROOM"),
	
	TALK("聊天","TALK"),
	
	SYS("系统","SYS");
	
	private String name;
	
	private String dm;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}
}
