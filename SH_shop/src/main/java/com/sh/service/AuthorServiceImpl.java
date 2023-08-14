package com.sh.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.AuthorMapper;
import com.sh.model.AuthorVO;
import com.sh.model.Criteria;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	AuthorMapper authorMapper;
	
	private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);
	
	// 작가 등록
    @Override
    public void authorEnroll(AuthorVO author) throws Exception {
        
        authorMapper.authorEnroll(author);
        
    }
    
    // 작가 목록 
	@Override
	public List<AuthorVO> authorGetList(Criteria cri) throws Exception {
		
		log.info("(service)authorGetList()......."+ cri);
		
		return authorMapper.authorGetList(cri);
	}	
}
