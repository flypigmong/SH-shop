package com.sh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.AlarmMapper;
import com.sh.model.AlarmVO;

@Service
public class AlarmServiceImpl implements AlarmService{

	@Autowired
	private AlarmMapper alarmMapper;
	
	
	@Override
	public void insertAlarm(String toId, String fromId, String postNo, String category) throws Exception {
		alarmMapper.insertAlarm(toId,fromId,postNo,category);
		System.out.println("이게되냐???????????????????????????????????");
	}

	@Override
	public int alarmCount(String memberId) throws Exception {
		
		return alarmMapper.alarmCount(memberId);
	}

	@Override
	public List<AlarmVO> alarmList(String memberId) throws Exception {
		System.out.println("service::::::::::::::::::: alarmList");
		return alarmMapper.alarmList(memberId);
	}

	@Override
	public void alarmClick(String memberId, int postNo) throws Exception {
		System.out.println("service:::::::alarmClick");
		alarmMapper.alarmClick(memberId, postNo);
	}

	@Override
	public int insertAlarm(AlarmVO alarm) {
		alarmMapper.insertAlarm(alarm);
		System.out.println("알람서비스임플(저장)");
		return 1;
	}

}
