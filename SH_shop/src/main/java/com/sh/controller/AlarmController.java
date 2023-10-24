package com.sh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sh.model.AlarmVO;
import com.sh.service.AlarmService;

@Controller
@RequestMapping(value="/board")
public class AlarmController {

	private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);
	
    @Autowired 
    private AlarmService alarmService;
	
    /*
    //알람
	@ResponseBody
	@PostMapping(value= "/insertAlarm")
	public int insertAlarm(String toId, String fromId, String category, String postNo) throws Exception {
		logger.info("알람insert"+category+"//"+fromId+toId+postNo+category);
	
		int alarm = 1;
		
		alarmService.insertAlarm(toId, fromId, postNo, category);
		
		return alarm;
	}
	*/
    
	//알람수
	@ResponseBody
	@GetMapping(value = "/alarmCount")
	public int alarmCount (String memberId) throws Exception{
		
		int alarm = alarmService.alarmCount(memberId);
		
		return alarm;
	}	
	
	
	//알람목록
	@ResponseBody
	@GetMapping(value = "/alarmList")
	public List<AlarmVO> alarmList(String memberId) throws Exception{
		System.out.println("controller::::::::::::::::::: alarmList");
		return alarmService.alarmList(memberId);
	}	
	
	
	//알람클릭
	@ResponseBody
	@PostMapping(value = "/alarmClick")
	public String alarmClick(String memberId, int postNo) throws Exception{
		logger.info("알람클릭");
		alarmService.alarmClick(memberId, postNo);
		
		return null;
	}
	
}
