/*
* Copyright @ 2021 bingye
* spring-boot-chatroom 下午3:49:49
* All right reserved.
*
*/
package com.bingye.chatroom.domain.dto;

import java.io.Serializable;

import lombok.Data;

/**
* @desc: spring-boot-chatroom
* @author: bingye
* @createTime: 2021年2月3日 下午3:49:49
* @history:
* @version: v1.0
*/
@Data
public class UserDto implements Serializable{
	
	/**
	* TODO
	*/
	private static final long serialVersionUID = 4451342817773719277L;

	private String username;
	
	private String name;
	
	private String nickname;
	
	private String face;

}
