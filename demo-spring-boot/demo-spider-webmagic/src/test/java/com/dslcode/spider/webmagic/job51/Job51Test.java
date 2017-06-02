package com.dslcode.spider.webmagic.job51;

import com.dslcode.spider.webmagic.DemoSpiderWebmagicApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Spider;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Slf4j
public class Job51Test extends DemoSpiderWebmagicApplicationTests {

    @Autowired
    private Job51PageProcessor job51PageProcessor;
    @Autowired
    private Job51Pipeline job51Pipeline;

    @Test
    public void test() {
        log.debug(" =========================开始51job抓取Java数据===========================");

        Spider
                .create(job51PageProcessor)
                .addUrl("http://search.51job.com/list/090200,000000,0000,00,9,99,java,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=")
                .addPipeline(job51Pipeline)
                .thread(20)
                .run();
    }

}
