package com.dslcode.spider.webmagic.job51.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongsilin on 2017/6/2.
 */
@Slf4j
@Service
@Transactional
@EnableAsync
public class Job51Service {

    @Autowired
    private Job51Repository job51Repository;

    @Async
    public void save(Job51 job51){
        this.job51Repository.save(job51);
    }

}
