package com.sh.service;

import java.util.List;

import com.sh.model.AuthorVO;
import com.sh.model.Criteria;

public interface AuthorService {

    /* 작가 등록 */
    public void authorEnroll(AuthorVO author) throws Exception;
    
    /* 작가 목록 */
    public List<AuthorVO> authorGetList(Criteria cri) throws Exception;
}
