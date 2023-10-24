package com.sh.mapper;

import java.util.List;

import com.sh.model.AlarmDTO;
import com.sh.model.AlarmVO;

public interface AlarmMapper {

	void insertAlarm(String toId, String fromId, String postNo, String category);

	int alarmCount(String memberId);

	List<AlarmVO> alarmList(String memberId);

	void alarmClick(String memberId, int postNo);

	//알람 삽입2
	void insertAlarm(AlarmVO alarm);


}
