/*
* Copyright @ 2021 bingye
* spring-boot-chatroom 下午5:00:22
* All right reserved.
*
*/
package com.bingye.chatroom.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.IdUtil;

/**
* @desc: spring-boot-chatroom
* @author: bingye
* @createTime: 2021年2月3日 下午5:00:22
* @history:
* @version: v1.0
*/
@RestController
@RequestMapping("/room")
public class RoomController {
	
	@GetMapping("/get/randomId")
	public String randomId() {
		return IdUtil.simpleUUID();
	}

}
