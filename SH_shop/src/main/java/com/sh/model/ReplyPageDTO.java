package com.sh.model;

import java.util.List;

import lombok.Data;

@Data
public class ReplyPageDTO {

	List<ReplyDTO> list;
	
	PageDTO pageInfo;

}
