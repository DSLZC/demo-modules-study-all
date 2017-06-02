package com.dslcode.spider.webmagic.liepin$job.db;

import com.dslcode.spider.webmagic.liepin$job.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

@Service
@Transactional
public class LiePinJobService implements PageModelPipeline<JobDetail> {

    @Autowired
    private LiePinJobRepository liePinJobRepository;

    @Override
    public void process(JobDetail jobDetail, Task task) {
        liePinJobRepository.save(new LiePinJob(jobDetail));
    }

}