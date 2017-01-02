package com.data.jpa;

import com.data.jpa.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongsilin on 2017/1/2.
 */
@Slf4j
public class SystemUserTest extends DemoJpaApplicationTests {

    @Autowired
    private SystemUserService systemUserService;

    @Test
    @Transactional
    public void all(){
        log.debug("allUsers={}", this.systemUserService.findAll());
    }
}
