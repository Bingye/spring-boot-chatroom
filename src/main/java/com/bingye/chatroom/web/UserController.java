/*
* Copyright @ 2021 bingye
* spring-boot-chatroom 下午5:00:22
* All right reserved.
*
*/
package com.bingye.chatroom.web;

import java.nio.charset.Charset;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bingye.chatroom.domain.dto.UserDto;

import cn.hutool.core.net.URLEncoder;

/**
* @desc: spring-boot-chatroom
* @author: bingye
* @createTime: 2021年2月3日 下午5:00:22
* @history:
* @version: v1.0
*/
@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/login")
	public String login(UserDto userDto,HttpSession session) {
		session.setAttribute("LOGIN_USER", userDto);
		return "/hall.html?username="+URLEncoder.createDefault().encode(userDto.getUsername(), Charset.forName("UTF-8"));
	}

}
