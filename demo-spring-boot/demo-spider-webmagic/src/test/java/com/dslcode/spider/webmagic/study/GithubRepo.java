package com.dslcode.spider.webmagic.study;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * HelpUrl/TargetUrl是一个非常有效的爬虫开发模式，TargetUrl是我们最终要抓取的URL，最终想要的数据都来自这里；而HelpUrl则是为了发现这个最终URL，我们需要访问的页面。几乎所有垂直爬虫的需求，都可以归结为对这两类URL的处理：

 对于博客页，HelpUrl是列表页，TargetUrl是文章页。
 对于论坛，HelpUrl是帖子列表，TargetUrl是帖子详情。
 对于电商网站，HelpUrl是分类列表，TargetUrl是商品详情。
 */
@TargetUrl("https://github.com/\\w+/\\w+")
@HelpUrl("https://github.com/\\w+")
public class GithubRepo {

    @ExtractBy(value = "//h1[@class='entry-title public']/strong/a/text()", notNull = true)
    private String name;

    @ExtractByUrl("https://github\\.com/(\\w+)/.*")
    private String author;

    @ExtractBy("//div[@id='readme']/tidyText()")
    private String readme;

    public static void main(String[] args) {
        OOSpider
                .create(
                        Site.me().setSleepTime(1000),
                        new ConsolePageModelPipeline(),
                        GithubRepo.class
                )
                .addUrl("https://github.com/code4craft")
                .thread(5)
                .run();
    }
}