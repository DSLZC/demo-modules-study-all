package com.dslcode.spider.webmagic.jd;

import com.dslcode.spider.webmagic.DemoSpiderWebmagicApplicationTests;
import com.dslcode.spider.webmagic.jd.db.JDCategory;
import com.dslcode.spider.webmagic.jd.db.JDCategoryService;
import com.dslcode.spider.webmagic.jd.util.ScriptResolveUtil;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import javax.script.ScriptEngine;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Slf4j
public class JdGoodsTest extends DemoSpiderWebmagicApplicationTests {

    @Autowired
    private JdGoodsListPageProcessor jdGoodsListPageProcessor;
    @Autowired
    private JDCategoryService jdCategoryService;

    @Autowired
    private JdGoodsDetailPageProcessor jdGoodsDetailPageProcessor;
    @Autowired
    private JdGoodsDetailPipeline jdGoodsDetailPipeline;

    @Test
    public void test() {
        log.debug(" =========================开始抓取京东手机商品数据===========================");

        Request request = new Request();
        request.setUrl("https://list.jd.com/list.html?cat=9987,653,655&page=1&sort=sort_rank_asc&trans=1&JL=6_0_0&ms=5");
        Spider
                .create(jdGoodsListPageProcessor)
                .addRequest(request)
                .thread(2)
                .run();

//        request.setUrl("https://item.jd.com/4586850.html");
//        request.putExtra("skuId", "4586850");
//        Spider
//                .create(jdGoodsDetailPageProcessor)
//                .addRequest(request)
//                .addPipeline(jdGoodsDetailPipeline)
//                .thread(1)
//                .run();
    }

    @Test
    public void getCategory() throws Exception {
        String url = "http://dc.3.cn/category/get";
        String categoryJson = Jsoup.parse(new URL(url).openStream(), "GBK", url).body().text();
        ScriptEngine scriptEngine = ScriptResolveUtil.parse("var c = " + categoryJson + ", categories = c.data;");
        Collection<Object> categories0 = ((ScriptObjectMirror) scriptEngine.get("categories")).values();

        List<JDCategory> allCategory3s = new ArrayList<>(200);
        List<List<JDCategory>> jdCategory0s = categories0.stream().map(c0 -> {
            Collection<Object> categories1 = ((ScriptObjectMirror) ((ScriptObjectMirror) c0).get("s")).values();
            List<JDCategory> jdCategory1s = categories1.stream().map(c1 -> {
                String c1n = (String) ((ScriptObjectMirror) c1).get("n");
                int i1 = c1n.indexOf("|");
                JDCategory jdCategory1 = new JDCategory(c1n.substring(i1+1, c1n.indexOf("||")), c1n.substring(0, i1));
//                log.debug("categories1={}", jdCategory1);
                Collection<Object> categories2 = ((ScriptObjectMirror) ((ScriptObjectMirror) c1).get("s")).values();
                jdCategory1.setChildrens(
                        categories2.stream().map(c2 -> {
                            String c2n = (String) ((ScriptObjectMirror) c2).get("n");
                            int i2 = c2n.indexOf("|");
                            JDCategory jdCategory2 = new JDCategory(c2n.substring(i2+1, c2n.indexOf("||")), c2n.substring(0, i2), jdCategory1.getId());
//                            log.debug("categories2={}", jdCategory2);
                            Collection<Object> categories3 = ((ScriptObjectMirror) ((ScriptObjectMirror) c2).get("s")).values();
                            jdCategory2.setChildrens(
                                    categories3.stream().map(c3 -> {
                                        JDCategory jdCategory3 = new JDCategory();
                                        jdCategory3.setParentId(jdCategory2.getId());
                                        String c3n = (String) ((ScriptObjectMirror) c3).get("n");

                                        String[] ssss = c3n.split("\\|\\|")[0].split("\\|");
                                        String url3 = ssss[0];
                                        String name3 = ssss[1];
                                        jdCategory3.setName(name3);
                                        if (url3.contains("jd")) {
                                            jdCategory3.setFirstPageUrl(url3);
                                            Arrays.stream(url3.split("&")).forEach(s -> {
                                                if (s.contains("cat=")) jdCategory3.setCat(s.split("=")[1]);
                                                if (s.contains("ev=")) jdCategory3.setEv(s.split("=")[1]);
                                            });
                                        } else jdCategory3.setCat(url3.replaceAll("-", ","));
//                                        log.debug("jdCategory3={}", jdCategory3);
                                        allCategory3s.add(jdCategory3);
                                        return jdCategory3;
                                    }).collect(Collectors.toList())
                            );
                            return jdCategory2;
                        }).collect(Collectors.toList())
                );
             return jdCategory1;
            }).collect(Collectors.toList());
            return jdCategory1s;
        }).collect(Collectors.toList());

//        log.debug("jdCategory0s={}", jdCategory0s);

        // 保存一级
        jdCategory0s.stream().forEach(c0 -> {
            c0.stream().forEach(c1 -> this.jdCategoryService.save(c1));
        });

        // 保存二级
        jdCategory0s.stream().forEach(c0 -> {
            c0.stream().forEach(c1 -> c1.getChildrens().stream().forEach(c2 -> {
                c2.setParentId(c1.getId());
                this.jdCategoryService.save(c2);
            }));
        });

        // 保存三级
        jdCategory0s.stream().forEach(c0 -> {
            c0.stream().forEach(c1 -> c1.getChildrens().stream().forEach(c2 -> c2.getChildrens().stream().forEach(c3 -> {
                c3.setParentId(c2.getId());
                this.jdCategoryService.save(c3);
            })));
        });

        log.debug("..............................");
    }


}
