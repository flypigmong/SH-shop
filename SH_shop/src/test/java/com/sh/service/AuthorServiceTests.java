package com.sh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sh.model.AuthorVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AuthorServiceTests {

	  /*AuthoreService 의존성 주입*/
    @Autowired
    private AuthorService service;
    
    /*
    @Test
    public void authorEnrollTest() throws Exception {
 
        AuthorVO author = new AuthorVO();
        
        author.setNationId("02");
        author.setAuthorName("foreign1");
        author.setAuthorIntro("Hi");
        
        System.out.println(author);
        
        service.authorEnroll(author);
        
    }
    */

	// 작가 상세 페이지
	@Test
	public void authorGetDetailTest() throws Exception{
		
		int authorId = 20;
		
		System.out.println("author....." + service.authorGetDetail(authorId));
	}
	
	
}
