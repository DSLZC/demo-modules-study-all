package com.dslcode.spider.webmagic.job51.db;

import com.dslcode.core.string.StringUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by dongsilin on 2017/6/1.
 */
//@Data
@Entity
@Table(name = "job_51")
public class Job51 {

    @Id
    @GeneratedValue
    private Long id;

    private String url;
    private String title;
    private String area;
    private String salary;
    private String companyName;
    private String companyProperty;
    /** 条件,资格 */
    private String qualifications;
    /** 福利 */
    private String benefits;

    private String jobRequire;

    private String contactType;

    private String companyInfo;

    public Job51() {
    }

    public Job51(String url, String title, String area, String salary, String companyName, String companyProperty,
                 List<String> qualifications, List<String> benefits, String jobRequire, String contactType, String companyInfo) {
        this.url = url;
        this.title = title;
        this.area = area;
        this.salary = salary;
        this.companyName = companyName;
        this.companyProperty = companyProperty;
        this.qualifications = StringUtil.join(qualifications, ",");
        this.benefits = StringUtil.join(benefits, ",");
        this.jobRequire = jobRequire;
        this.contactType = contactType;
        this.companyInfo = companyInfo;
    }
}
