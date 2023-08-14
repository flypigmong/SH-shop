package com.sh.mapper;

import java.util.List;

import com.sh.model.AuthorVO;
import com.sh.model.Criteria;

public interface AuthorMapper {

    /* 작가 등록 */
    public void authorEnroll(AuthorVO author);
    
    /* 작가 목록 */
    public List<AuthorVO> authorGetList(Criteria cri);

}
