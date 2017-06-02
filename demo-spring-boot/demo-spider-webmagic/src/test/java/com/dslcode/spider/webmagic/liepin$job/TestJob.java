package com.dslcode.spider.webmagic.liepin$job;

import com.dslcode.spider.webmagic.DemoSpiderWebmagicApplicationTests;
import com.dslcode.spider.webmagic.liepin$job.db.LiePinJobService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;

/**
 * Created by dongsilin on 2017/5/31.
 */
@Slf4j
public class TestJob extends DemoSpiderWebmagicApplicationTests{

    @Autowired
    private LiePinJobService liePinJobService;

    @Test
    public void test() {
        log.debug(" =========================开始爬取猎聘JOB数据===========================");
        OOSpider.
                create(
                        Site.me().setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36"),
                        liePinJobService,
                        JobDetail.class
                )
                .addUrl("https://www.liepin.com/zhaopin/?pubTime=&ckid=3cbc7169c51c9360&fromSearchBtn=2&compkind=&isAnalysis=&init=-1&searchType=1&dqs=280020&industryType=&jobKind=&sortFlag=15&degradeFlag=0&industries=&salary=&compscale=&clean_condition=&key=Java&headckid=7bc27c3defa814af&curPage=0")
                .thread(40)
                .run();
    }

}
