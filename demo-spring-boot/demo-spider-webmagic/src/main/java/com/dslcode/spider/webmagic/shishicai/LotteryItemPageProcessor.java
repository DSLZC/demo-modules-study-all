package com.dslcode.spider.webmagic.shishicai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Slf4j
@Component
public class LotteryItemPageProcessor implements PageProcessor {

    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36").setRetryTimes(2);

    @Override
    public void process(Page page) {

        String url = page.getUrl().get();
        page.putField("url", url);
        page.putField("date", url.substring(url.indexOf("_")+1, url.length()));
        List<Selectable> lotteryNodes = page.getHtml().xpath("//div[@class='history-tab']/table/tbody/tr[1]/td/table/tbody/tr").nodes();
        List<List<String>> lotteryItems =  lotteryNodes.stream().map(node -> node.xpath("//td/tidyText()").all()).collect(Collectors.toList());
        page.putField("lotteryItems", lotteryItems);

        // 从页面发现后续的url地址来抓取
        //page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return this.site;
    }

}
