package com.dslcode.spider.webmagic.job51;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Slf4j
@Component
public class Job51PageProcessor implements PageProcessor {

    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36").setRetryTimes(2);


    @Override
    public void process(Page page) {
        Selectable url = page.getUrl();
        Html html = page.getHtml();
//        log.debug("size0={}", page.getHtml().xpath("//p[@class='t1']/span/a/text()").all().size());
//        log.debug("size1={}", page.getHtml().links().regex("http://jobs\\.51job\\.com/[\\w\\-]+/\\d+.html\\?s=\\d+&t=\\d+").all().size());
//        page.getHtml().links().regex("http://jobs\\.51job\\.com/[\\w\\-]+/\\d+.html\\?s=\\d+&t=\\d+").all().forEach(log::debug);
//        log.debug("\n");
//        page.getHtml().links().regex("http://search\\.51job\\.com/list/090200,000000,0000,00,9,99,java,2,\\d+.html\\.*").all().forEach(log::debug);
//        log.debug("\n");

        boolean isListPage = url.regex("http://search\\.51job\\.com/list/*").match();
        // 列表页
        if (isListPage){
            List<String> detailUrls = html.$("p.t1 span a", "href").all();
            List<String> listUrls = html.$("div.p_in ul li a", "href").all();
            page.addTargetRequests(detailUrls);
            page.addTargetRequests(listUrls);
        }else{
            // 详情页
            Selectable baseInfo = html.xpath("//div[@class='cn']");

            page.putField("url", url.get());
            page.putField("title", baseInfo.$("h1", "title").get());
            page.putField("area", baseInfo.xpath("//span[@class='lname']/text()").get());
            page.putField("salary", baseInfo.xpath("//strong/text()").get());
            page.putField("companyName", baseInfo.$("p.cname a", "title").get());
            page.putField("companyProperty", baseInfo.xpath("//p[@class='msg ltype']/text()").get());

            List<Selectable> jobBodyNodes = html.xpath("//div[@class='tBorderTop_box']").nodes();
            // 条件,资格
            page.putField("qualifications", jobBodyNodes.get(0).xpath("//div[@class='t1']/span[@class='sp4']/text()").all());
            // 福利
            page.putField("benefits", jobBodyNodes.get(0).xpath("//p[@class='t2']/span/text()").all());

            page.putField("jobRequire", jobBodyNodes.get(1).xpath("//div[@class='bmsg job_msg inbox']/html()").get());

            page.putField("contactType", jobBodyNodes.get(2).xpath("//div[@class='bmsg inbox']/p[@class='fp']/html()").get());

            page.putField("companyInfo", jobBodyNodes.get(3).xpath("//div[@class='tmsg inbox']/text()").get());

        }

    }

    @Override
    public Site getSite() {
        return this.site;
    }

}
