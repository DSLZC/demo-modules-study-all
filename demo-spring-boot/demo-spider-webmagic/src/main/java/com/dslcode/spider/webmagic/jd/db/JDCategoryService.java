package com.dslcode.spider.webmagic.jd.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongsilin on 2017/6/9.
 */
@Slf4j
@Service
public class JDCategoryService {

    @Autowired
    private JDCategoryRepository jdCategoryRepository;

    @Transactional
    public JDCategory save(JDCategory jdCategory){
        return this.jdCategoryRepository.save(jdCategory);
    }

}
