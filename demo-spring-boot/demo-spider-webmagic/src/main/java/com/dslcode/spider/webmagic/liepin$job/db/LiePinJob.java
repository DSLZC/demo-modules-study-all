package com.dslcode.spider.webmagic.liepin$job.db;

import com.dslcode.core.string.StringUtil;
import com.dslcode.spider.webmagic.liepin$job.JobDetail;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Data
@Entity
@Table(name = "liepin_job")
public class LiePinJob {

    @Id
    @GeneratedValue
    private Long id;

    private String url;
    private String title;
    private String company;
    private String salary;
    /** 条件,资格 */
    private String qualifications;
    /** 福利 */
    private String benefits;

    private String description;

    private String companyDescription;

    public LiePinJob() {
    }

    public LiePinJob(JobDetail job) {
        this.url = job.getUrl();
        this.title = job.getTitle();
        this.company = job.getCompany();
        this.salary = job.getSalary();
        this.qualifications = StringUtil.join(job.getQualifications(), ",");
        this.benefits = StringUtil.join(job.getBenefits(), ",");
        this.description = job.getDescription();
        this.companyDescription = job.getCompanyDescription();
    }

}
