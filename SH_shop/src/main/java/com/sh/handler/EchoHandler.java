package com.sh.handler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.sh.model.MemberVO;

public class EchoHandler extends TextWebSocketHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
	
	// 로그인 한 전체
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1대1
	Map<String, WebSocketSession> userSessionMap = new HashMap<String, WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			System.out.println("afterConnectionEstablished : " + session );
			
			logger.info("Socket 연결");
			sessions.add(session); //접속된 유저 담기
			logger.info("session!!!!!!!!!! : " + session);
			
			String senderId = currentUserId(session);
			logger.info("senderId!!!!!!!!!!!! : "+ senderId);
			
			userSessionMap.put(senderId, session);
	}



/*
	private String getEmail(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		MemberVO loginUser = (MemberVO) httpSession.get("memDTO");
		
		if(loginUser == null) {
				String mid = session.getId();
				return mid;
		} else {
				String mid = loginUser.getMemberId();
				return mid;
		}
	}
*/


	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage: " + session + " : " + message );
	
//		String senderId = getId(session);
//		for (WebSocketSession webSocketSession : sessions) {
//			webSocketSession.sendMessage(new TextMessage(senderId + " : " + message.getPayload()));
//		}
		logger.info("ssesion!!!!!!"+currentUserId(session));
		
		String msg = message.getPayload();
		logger.info("msg="+msg);
		
		if(StringUtils.isNotEmpty(msg)) {
			logger.info("if문 들어옴?");
			String[] strs = msg.split(",");
			if(strs != null && strs.length == 4) {
				//protocol : cmd,댓글작성자,게시글작성자,bno   (ex:reply,user2,user1,글번호(ex:12)
				String cmd = strs[0];
				String replyWriter = strs[1]; //replyWriter  댓글작성자
				String boardWriter = strs[2]; //boardWriter 글 작성자
				//String receiverEmail = strs[3];
				String bno = strs[3];
				
				
				WebSocketSession replyWriterSession = userSessionMap.get(replyWriter);
				WebSocketSession boardWriterSession = userSessionMap.get(boardWriter);
				logger.info("boardWriterSession="+userSessionMap.get(boardWriter));
				logger.info("boardWirterSession"+boardWriterSession);
				
				//작성자가 로그인해서 있다면
				//WebSocketSession boardWriterSession = userSessionMap.get(boardWriter); 
				if ("reply".equals(cmd) && boardWriterSession != null) {
					TextMessage tmpMsg = new TextMessage(replyWriter+" 님이  "+bno +"  번 게시글에 댓글을 달았습니다!");
					boardWriterSession.sendMessage(tmpMsg);
				}
			}
		}
		
	}




	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionClosed:" + session + ":" + status);
	}


	/*
	private String getId(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		User loginUser = (User)httpSession.get(SessionNames.Login);
		return null;
	}
	 */
	
	private String currentUserId(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		MemberVO loginUser = (MemberVO) httpSession.get("member");
		
		if(loginUser == null) {
			return session.getId();
		} else {
			return loginUser.getMemberId();
		}
		
	}

}
