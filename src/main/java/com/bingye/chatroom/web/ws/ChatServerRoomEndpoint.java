package com.bingye.chatroom.web.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
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
* @desc: 群聊 one-to-many
* @author: bingye
* @createTime: 2021年2月2日 下午1:24:30
* @history:
* @version: v1.0
*/
@Component
@ServerEndpoint(value="/websocket/{roomId}",configurator=GetHttpSessionConfigurator.class, encoders = {ChatServerEncoder.class})
@Slf4j
public class ChatServerRoomEndpoint {
	
	//房间号->组成员
	public static Map<String,List<ChatServerRoomEndpoint>> rooms = new ConcurrentHashMap<>();
	
	//websocket session
	private Session session;
	
	//http session
	private HttpSession httpSession;
	
	//房间ID
	public String roomId;
	
	@OnMessage
    public void onMessage(String message) {
    	UserDto user = (UserDto) httpSession.getAttribute("LOGIN_USER");
    	MessageDto messageDto = JSONObject.parseObject(message,MessageDto.class);
		//one to room
		if(StrUtil.equals(messageDto.getMsgType(), MessageType.TALK.getDm())) {
			//得到当前群的所有会话，也就是所有用户
	        List<ChatServerRoomEndpoint> room = rooms.get(roomId);
	        //遍历Session集合给每个会话发送文本消息
	        room.forEach(endpoint -> {
	            try {
	                endpoint.session.getBasicRemote().sendObject(new MessageDto(MessageType.TALK.getDm(), user.getUsername(), null, messageDto.getMessage(), DateUtil.now(),roomId));
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
		}
    }
	
    //建立连接调用的方法，房间成员加入
    @OnOpen
    public void onOpen(Session session,EndpointConfig config, @PathParam("roomId") String roomId) {
    	//获取httpSession
    	HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    	//websocket session
    	this.session = session;
    	//赋值httpSession
    	this.httpSession = httpSession;
    	//房间号
    	this.roomId = roomId;
    	//当前用户入座房间
    	UserDto user = (UserDto) httpSession.getAttribute("LOGIN_USER");
        //得到当前群的所有会话，也就是所有用户
        List<ChatServerRoomEndpoint> room = rooms.get(roomId);
        if (room == null) {
        	room = new ArrayList<>();
        	rooms.put(roomId,room);
        }
		//遍历Session集合给每个会话发送文本消息
        room.forEach(endpoint -> {
            try {
            	//通知其他人我已入席（不包括自己）
                endpoint.session.getBasicRemote().sendObject(new MessageDto(MessageType.ENTROOM.getDm(), user.getUsername(), null, "进入房间", DateUtil.now()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        room.add(this);
        log.info("连接建立");
        log.info("房间号: {}, 房间人数: {}", roomId, room.size());
    }
	
    //关闭连接调用的方法，群成员退出
    @OnClose
    public void onClose(Session session, @PathParam("roomId") String roomId) {
    	//当前用户入座房间
    	UserDto user = (UserDto) httpSession.getAttribute("LOGIN_USER");
    	//获取房间成员
    	List<ChatServerRoomEndpoint> room = rooms.get(roomId);
        //把自己删除
    	room.remove(this);
        //遍历Session集合给每个会话发送文本消息
        room.forEach(endpoint -> {
            try {
            	//通知其他人我离开房间
                endpoint.session.getBasicRemote().sendObject(new MessageDto(MessageType.LEVROOM.getDm(), user.getUsername(), null, "离开房间", DateUtil.now()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        log.info("连接关闭");
        log.info("房间号: {}, 房间人数: {}", roomId, room.size());
    }
    
    //传输消息错误调用的方法
    @OnError
    public void OnError(Throwable error) {
    	error.printStackTrace();
        log.info("连接出错：{}",error.getMessage());
    }
    
}
