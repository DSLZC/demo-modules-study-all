package com.dslcode.spider.webmagic.jd;

import com.dslcode.spider.webmagic.jd.db.JdGoods;
import com.dslcode.spider.webmagic.jd.db.JdGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Slf4j
@Component
public class JdGoodsDetailPipeline implements Pipeline {

    @Autowired
    private JdGoodsService jdGoodsService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        JdGoods jdGoods = resultItems.get("jdGoods");
        jdGoodsService.save(jdGoods);
    }

}
