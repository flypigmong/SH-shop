package com.sh.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AlarmVO {
	private int alarmId;
	private String toId;
	private String fromId;
	private int postNo;
	private String category;
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
	private Date alarmDate;
	
	
	
	
}
