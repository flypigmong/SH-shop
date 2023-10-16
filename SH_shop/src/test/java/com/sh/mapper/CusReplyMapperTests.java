package com.sh.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sh.model.CusReplyDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root/root-context.xml")
public class CusReplyMapperTests {

	@Autowired
	private CusReplyMapper mapper;
	
	
	@Test
	public void CusReplyEnrollTest() {
		
		String id = "admin";
		int postNo = 3;
		String content = "댓글 테스트";
		
		CusReplyDTO dto = new CusReplyDTO();
		dto.setPostNo(postNo);
		dto.setMemberId(id);
		dto.setContent(content);
		
		mapper.cusEnrollReply(dto);
		
		
	}
}
