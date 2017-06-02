package com.dslcode.spider.webmagic.job51;

import com.dslcode.spider.webmagic.job51.db.Job51;
import com.dslcode.spider.webmagic.job51.db.Job51Service;
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
public class Job51Pipeline implements Pipeline {

    @Autowired
    private Job51Service job51Service;

    @Override
    public void process(ResultItems resultItems, Task task) {
        if(resultItems.getAll().size() == 0) return;
        Job51 job51 = new Job51(
                resultItems.get("url"),
                resultItems.get("title"),
                resultItems.get("area"),
                resultItems.get("salary"),
                resultItems.get("companyName"),
                resultItems.get("companyProperty"),
                resultItems.get("qualifications"),
                resultItems.get("benefits"),
                resultItems.get("jobRequire"),
                resultItems.get("contactType"),
                resultItems.get("companyInfo")
        );

        this.job51Service.save(job51);
    }

}
