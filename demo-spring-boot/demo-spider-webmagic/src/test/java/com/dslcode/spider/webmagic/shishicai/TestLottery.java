package com.dslcode.spider.webmagic.shishicai;

import com.dslcode.core.date.DateUtil;
import com.dslcode.core.string.StringUtil;
import com.dslcode.spider.webmagic.DemoSpiderWebmagicApplicationTests;
import com.dslcode.spider.webmagic.shishicai.db.LotteryItemPipeline;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.util.stream.IntStream;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Slf4j
public class TestLottery extends DemoSpiderWebmagicApplicationTests {

    @Autowired
    private LotteryItemPageProcessor lotteryItemPageProcessor;
    @Autowired
    private LotteryItemPipeline lotteryItemPipeline;

    @org.junit.Test
    public void test() {
        log.debug(" =========================开始时时彩开奖数据===========================");

        LocalDate localDate = LocalDate.now();
        Request[] requests = IntStream.rangeClosed(1, 365).mapToObj(i -> {
            String date = localDate.minusDays(366 - i).toString(DateUtil.yyyyMMdd);
            return new Request(StringUtil.append2String("http://chart.cp.360.cn/kaijiang/kaijiang?lotId=255401&spanType=2&span=", date, "_", date)).setPriority(i);
        }).toArray(Request[]::new);

        Spider
                .create(lotteryItemPageProcessor)
                .addRequest(requests)
                .addPipeline(lotteryItemPipeline)
                .thread(10)
                .run();

    }

}
