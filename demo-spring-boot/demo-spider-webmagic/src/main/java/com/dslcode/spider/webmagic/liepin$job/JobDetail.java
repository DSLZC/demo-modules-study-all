package com.dslcode.spider.webmagic.liepin$job;

import lombok.Data;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * Created by dongsilin on 2017/5/31.
 */
@Data
@TargetUrl("https://www.liepin.com/job/\\d+.shtml")
@HelpUrl("https://www.liepin.com/zhaopin/?pubTime=&ckid=3cbc7169c51c9360&fromSearchBtn=2&compkind=&isAnalysis=&init=-1&searchType=1&dqs=280020&industryType=&jobKind=&sortFlag=15&degradeFlag=0&industries=&salary=&compscale=&clean_condition=&key=Java&headckid=7bc27c3defa814af&curPage=*")
public class JobDetail {

    @ExtractByUrl
    private String url;

    @ExtractBy("//div[@class='title-info']/h1/text()")
    private String title;
    @ExtractBy("//div[@class='title-info']/h3/a/text()")
    private String company;
    @ExtractBy("//p[@class='job-item-title']/text()")
    private String salary;
    /** 条件,资格 */
    @ExtractBy("//div[@class='job-qualifications']/span/text()")
    private List<String> qualifications;
    /** 福利 */
    @ExtractBy("//div[@class='tag-list']/span/text()")
    private List<String> benefits;

    @ExtractBy("//div[@class='content content-word'][1]/html()")
    private String description;

    @ExtractBy("//div[@class='job-item main-message noborder']/div/html()")
    private String companyDescription;

}
