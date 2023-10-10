package com.sh.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.sh.mapper.AlarmMapper;
import com.sh.model.AlarmDTO;
import com.sh.model.MemberVO;

@RequestMapping("/alarm")
public class EchoHandler1 extends TextWebSocketHandler{

	@Autowired
	private AlarmMapper alarmDao;
	
	public void setAlarmDao(AlarmMapper alarmDao) {
		this.alarmDao = alarmDao;
	}
	
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
	
	//로그인 한 인원 전체
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1:1로 할 경우
	private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	
	//클라이언트가 웹 소켓 생성
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("Socket 연결");
		//웹 소켓이 생성될 때마다 리스트에 넣어줌
		sessions.add(session); //리스트에 접속한 session들을 담음
	}
	
	//JS에서 메세지 받을 때.
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {// 메시지

		for(WebSocketSession single : sessions) {
			//세션아이디 
			String hsid = (String) single.getAttributes().get("member_id");
			
			//세션값이 같을때, 알람보낼 것이 있을 때만 전송 -> 로그인 한 사용자가 처음으로 알람 받는 순간임
			//해당 sendMsg에 DB정보 넣어서 체크 안된 알람 전부 전송하기
			
			if(single.getAttributes().get("member_id").equals(session.getAttributes().get("member_id"))) {				//체크 안된 알림들만 담아서 View
				List<AlarmDTO> dto = new ArrayList<>();
				dto = alarmDao.selectAlarm(hsid);
				for(AlarmDTO alarm : dto) {
					int idx = alarm.getIdx();
					String prefix = alarm.getPrefix();
					String code = alarm.getCode();
					if(code.equals("NewPost")) {
						code = "답변이 등록되었습니다.";
					}
					TextMessage sendMsg = new TextMessage("("+idx+")" + prefix + "에 " + code);
					single.sendMessage(sendMsg);
				}
			}
			
		}
	}

	
	
	
		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {//연결 해제
			logger.info("Socket 끊음");
			//웹 소켓이 종료될 때마다 리스트에서 뺀다.
			sessions.remove(session);
			userSessionsMap.remove(currentUserName(session),session);
		}
	
		private String currentUserName(WebSocketSession session) {
			Map<String, Object> httpSession = session.getAttributes();
			MemberVO loginUser = (MemberVO)httpSession.get("login");
			
			if(loginUser == null) {
				String mid = session.getId();
				return mid;
			}
			String mid = loginUser.getMemberId();
			return mid;
			
		}
	
}