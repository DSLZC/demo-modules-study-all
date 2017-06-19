package com.dslcode.spider.webmagic.jd;

import com.dslcode.core.util.NullUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Slf4j
@Component
public class JdGoodsListPageProcessor implements PageProcessor {

    @Autowired
    private JdGoodsDetailPageProcessor jdGoodsDetailPageProcessor;
    @Autowired
    private JdGoodsDetailPipeline jdGoodsDetailPipeline;

    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36").setRetryTimes(2);

    public static final String DEFAULT_AREA = "737,794,798";
    public static final String GOODS_DETAIL_PAGE_URL = "https://item.jd.com/{skuid}.html";
    public static final String LIST_URL = "https://list.jd.com/list.html?cat=9987,653,655&page={page}&sort=sort_rank_asc&trans=1&JL=6_0_0&ms=5";

    @Override
    public void process(Page page) {
        Selectable url = page.getUrl();
        Html html = page.getHtml();

        Request[] goodsPageRequests = html.xpath("//div[@id='plist']/ul/li").nodes().parallelStream()
                .map(node -> {
                    String skuId = node.$(".p-scroll .ps-item:eq(0) a img", "data-sku").get();
                    Request request = new Request(GOODS_DETAIL_PAGE_URL.replace("{skuid}", skuId));
                    request.putExtra("skuId", skuId);
                    return request;
                }).toArray(Request[]::new);

        if (NullUtil.isNotNull(goodsPageRequests)) {
            Spider
                    .create(jdGoodsDetailPageProcessor)
                    .addRequest(goodsPageRequests)
                    .addPipeline(jdGoodsDetailPipeline)
                    .thread(5)
                    .run();
        }

        // 循环分页抓取
        List<String> pageNums = html.xpath("//div[@class='p-wrap']/span/a/text()").regex("\\d+").all();
        List<String> listUrls = pageNums.stream().distinct().filter(num -> Integer.parseInt(num) > 0).parallel().map(num -> LIST_URL.replace("{page}", num)).collect(Collectors.toList());
        page.addTargetRequests(listUrls);

    }

    @Override
    public Site getSite() {
        return this.site;
    }

}
