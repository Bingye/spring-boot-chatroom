package com.bingye.chatroom.web.ws;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bingye.chatroom.base.MessageType;
import com.bingye.chatroom.config.GetHttpSessionConfigurator;
import com.bingye.chatroom.config.ChatServerEncoder;
import com.bingye.chatroom.domain.dto.MessageDto;
import com.bingye.chatroom.domain.dto.UserDto;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
* @desc: 大厅
* @author: bingye
* @createTime: 2021年2月2日 下午1:24:30
* @history:
* @version: v1.0
*/
@Slf4j
@Component
@ServerEndpoint(value="/websocket/hall",configurator=GetHttpSessionConfigurator.class , encoders = {ChatServerEncoder.class})
public class ChatServerHallEndpoint {
	
	//房间号->组成员
	public static Map<String,ChatServerHallEndpoint> hall = new ConcurrentHashMap<>();
	
	//websocket session
	private Session session;
	
	//http session
	private HttpSession httpSession;
	
	//处理消息
	@OnMessage
    public void onMessage(Session session,String message) {
    	//当前用户入座大厅
    	UserDto user = (UserDto) httpSession.getAttribute("LOGIN_USER");
		MessageDto messageDto = JSONObject.parseObject(message,MessageDto.class);
		//发起聊天通知
		if(StrUtil.equals(MessageType.ENTROOM.getDm(), messageDto.getMsgType())) {
			messageDto.getRoomUsernames().forEach(usrname -> {
                try {
					hall.get(usrname).session.getBasicRemote().sendObject(new MessageDto(MessageType.ENTROOM.getDm(), user.getUsername(), usrname, user.getUsername()+"邀请你进入房间", DateUtil.now(),messageDto.getRoomId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
    }
	
    //建立连接调用的方法，成员加入
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) {
    	//获取httpSession
    	HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    	//websocket session
    	this.session = session;
    	//赋值httpSession
    	this.httpSession = httpSession;
        //当前用户入座大厅
    	UserDto user = (UserDto) httpSession.getAttribute("LOGIN_USER");
    	//获取大厅所有账号
    	Set<String> usernames = hall.keySet();
		//告知其他用户进入大厅
    	usernames.forEach(usrname -> {
	        try {
	        	//初始化自己大厅的坐席（不包含我自己）
	        	this.session.getBasicRemote().sendObject(new MessageDto(MessageType.ENTHALL.getDm(), usrname, user.getUsername() , "进入大厅", DateUtil.now()));
	        	//通知其他人我已进入大厅（不包含我自己）
	        	hall.get(usrname).session.getBasicRemote().sendObject(new MessageDto(MessageType.ENTHALL.getDm(), user.getUsername(), usrname , "进入大厅", DateUtil.now()));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        });
    	
    	//塞入大厅队列
    	hall.put(user.getUsername(), this);
    	
        log.info("连接建立");
        log.info("大厅人数: {}", hall.size());
    }
	
    //关闭连接调用的方法，群成员退出
    @OnClose
    public void onClose(Session session) {
    	//当前用户入座大厅
    	UserDto user = (UserDto) httpSession.getAttribute("LOGIN_USER");
    	//当前会话移除大厅
    	hall.remove(user.getUsername());
    	//获取大厅所有账号
    	Set<String> usernames = hall.keySet();
		//遍历Session集合给每个会话发送文本消息
    	usernames.forEach(usrname -> {
            try {
                MessageDto messageDto = new MessageDto(MessageType.LEVHALL.getDm(), user.getUsername(), usrname , user.getUsername()+"离开大厅",DateUtil.now());
                hall.get(usrname).session.getBasicRemote().sendObject(messageDto);
            } catch (Exception e) {
                e.printStackTrace();
                hall.remove(usrname);
                log.info("移除已失效会话："+usrname);
            }
        });
        log.info("连接关闭");
        log.info("大厅人数: {}", hall.size());
    }
    
    //传输消息错误调用的方法
    @OnError
    public void OnError(Throwable error) {
    	error.printStackTrace();
        log.info("连接出错：{}",error.getMessage());
    }
    
}
