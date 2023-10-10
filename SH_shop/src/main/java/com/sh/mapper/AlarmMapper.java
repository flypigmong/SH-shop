package com.sh.mapper;

import java.util.List;

import com.sh.model.AlarmDTO;

public interface AlarmMapper {

	//알림 목록 조회
	public List<AlarmDTO> selectAlarm(String hsid);

	
	public int selectAlarmUchkCount(String memId);

}
