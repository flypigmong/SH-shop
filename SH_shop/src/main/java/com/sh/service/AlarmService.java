package com.sh.service;

import java.util.List;
import com.sh.model.AlarmVO;

public interface AlarmService {

	//알람 삽입
	public void insertAlarm(String toId, String fromId, String postNo, String category) throws Exception;

	//알람 개수 조회
	public int alarmCount(String memberId) throws Exception;

	//알람 목록 조회
	public List<AlarmVO> alarmList(String memberId) throws Exception;

	//알람 클릭
	void alarmClick(String memberId, int postNo) throws Exception;

	//알람 삽입 2
	public int insertAlarm(AlarmVO alarm) throws Exception;


}
