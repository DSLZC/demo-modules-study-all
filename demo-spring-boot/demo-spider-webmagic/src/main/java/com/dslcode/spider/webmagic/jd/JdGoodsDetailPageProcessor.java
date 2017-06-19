package com.dslcode.spider.webmagic.jd;

import com.dslcode.core.json.JsonUtil;
import com.dslcode.core.string.StringUtil;
import com.dslcode.core.util.NullUtil;
import com.dslcode.spider.webmagic.jd.db.JdGoods;
import com.dslcode.spider.webmagic.jd.pojo.JDGoodsPrice;
import com.dslcode.spider.webmagic.jd.pojo.JDGoodsPrices;
import com.dslcode.spider.webmagic.jd.util.ScriptResolveUtil;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.net.URL;
import java.util.*;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Slf4j
@Component
public class JdGoodsDetailPageProcessor implements PageProcessor {

    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36").setRetryTimes(2);

    public static final int STOCK_STATE = 33;
    public static final String DEFAULT_AREA = "737,794,798";
    public static final String GOODS_DETAIL_PAGE_URL = "https://item.jd.com/{skuId}.html";
    public static final String STOCK_URL = "http://c0.3.cn/stocks?type=getstocks&skuIds={skuid}&area="+ DEFAULT_AREA.replaceAll(",", "_");
    public static final String PRICE_URL = "http://p.3.cn/prices/get?type=1&skuid=J_{skuid}&area={area}";
    public static final String GOODS_DESCRIPT_URL = "http://cd.jd.com/description/channel?skuId={skuid}&mainSkuId={mainSkuId}&cdn=2";

    @Override
    public void process(Page page) {
        Selectable url = page.getUrl();
        Html html = page.getHtml();
        boolean detailPage = url.regex("https://item.jd.com/\\d+.html").match();
        // 详情页
        if (detailPage) {

            String skuId = (String) page.getRequest().getExtra("skuId");
            // 是否有库存，不能获取具体库存数量
            boolean hasStock = false;
            try {
                hasStock = hasStock(skuId);
            } catch (Exception e) {
                log.error("", e);
            }
            Map<String, Object> mainSkuIdAndCat = new HashMap();
            try {
                getMainSkuIdAndCat(html, skuId, mainSkuIdAndCat);
            } catch (ScriptException e) {
                log.error("", e);
                return;
            }

            Selectable productInfo = html.xpath("//div[@class='product-intro']");
            Selectable imgArea = productInfo.$(".preview-wrap");
            Selectable productArea = productInfo.$(".itemInfo-wrap");

            // 商品轮播图片
            List<String> imgs = imgArea.$("div.spec-items ul li img", "src").all();
            List<String> alts = imgArea.$("div.spec-items ul li img", "alt").all();
            // 商品主图
            String mainImg = imgs.get(0);
            // 标题
            String title = productArea.xpath("//div[@class='sku-name']/text()").get();

            // 商品属性
            Map<String, String> attrs = new HashMap();
            Set<String> allSkuIds = new HashSet<>();
            productArea.xpath("//div[@id='choose-attrs']/div[@class='li p-choose']").nodes().stream().forEach(node -> {
                String key = node.xpath("//div[@class='dt']/text()").get();
                String value = node.xpath("//div[@class='dd']/div[@class='selected']/@data-value").get();
                attrs.put(key, value);
                allSkuIds.addAll(node.xpath("//div[@class='dd']/div[@class='item']/@data-sku").all());
            });

            // 价格
            JDGoodsPrice goodsPrice = null;
            // 商品详情
            String goodsDescription = null;
            try {
                goodsPrice = getPrice(skuId, DEFAULT_AREA);
                goodsDescription = getGoodsDescription(skuId, (String) mainSkuIdAndCat.get("mainSkuId"));
            } catch (Exception e) {
                log.error("", e);
                return;
            }

            JdGoods jdGoods = new JdGoods(mainSkuIdAndCat.get("mainSkuId"), skuId, mainSkuIdAndCat.get("cat"), title, mainImg, imgs, attrs, goodsPrice, hasStock, goodsDescription);
            page.putField("jdGoods", jdGoods);

            allSkuIds.parallelStream().forEach(sku -> {
                Request request = new Request(GOODS_DETAIL_PAGE_URL.replace("{skuId}", sku));
                request.putExtra("skuId", sku);
                page.addTargetRequest(request);
            });
        }

    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public JDGoodsPrice getPrice(String skuId, String area) throws Exception {
        String url = PRICE_URL.replace("{skuid}", skuId).replace("{area}", area);

        String body = Jsoup.connect(url).timeout(5000).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36").ignoreContentType(Boolean.TRUE).execute().body();

        if(NullUtil.isNull(body)) throw new Exception("京东商品价格获取失败，skuId="+skuId);
        body = StringUtil.append2String("{\"prices\": ", body, "}");
        JDGoodsPrices jdGoodsPrices = JsonUtil.readJson(body, JDGoodsPrices.class);
        if(NullUtil.isNull(jdGoodsPrices) || jdGoodsPrices.getPrices().isEmpty()) throw new Exception("京东商品价格获取失败，skuId="+skuId);
        return jdGoodsPrices.getPrices().get(0);
    }

    public void getMainSkuIdAndCat(Html html, String skuId, Map<String, Object> map) throws ScriptException {
        Elements scripts = html.getDocument().head().getElementsByTag("script");
        String mainSkuId, desc;
        Collection<Object> cat;
        for (Element e : scripts){
            if (e.childNodeSize() > 0){
                String scriptCode = e.html();
                String pageConfigObj = scriptCode.substring(0, scriptCode.indexOf("};")+2) + "\nvar mainSkuId = pageConfig.product.mainSkuId, desc = pageConfig.product.desc, cat = pageConfig.product.cat;";
                ScriptEngine scriptEngine = ScriptResolveUtil.parse(pageConfigObj);
                mainSkuId = (String) scriptEngine.get("mainSkuId");
                if (NullUtil.isNull(mainSkuId)) {
                    desc = (String) scriptEngine.get("desc");
                    if (NullUtil.isNull(desc) || !desc.contains("mainSkuId=")) throw new NullPointerException("mainSkuId 获取失败，skuId="+skuId);
                    mainSkuId = desc.substring(desc.indexOf("mainSkuId=")+10, desc.lastIndexOf("&"));
                    if (NullUtil.isNull(mainSkuId)) throw new NullPointerException("mainSkuId 获取失败，skuId="+skuId);
                    log.debug("skuId={}，mainSkuId={}", skuId, mainSkuId);
                }
                ScriptObjectMirror scriptCat = (ScriptObjectMirror) scriptEngine.get("cat");
                cat = scriptCat.values();
                if (NullUtil.isNull(cat) || cat.isEmpty()) throw new NullPointerException("cat 获取失败，skuId="+skuId);
                map.put("mainSkuId", mainSkuId);
                map.put("cat", cat);
                return ;
            }
        }
        throw new NullPointerException("mainSkuId 和 cat 获取失败");
    }

    public String getGoodsDescription(String skuId, String mainSkuId) throws Exception {
        String url = GOODS_DESCRIPT_URL.replace("{skuid}", skuId).replace("{mainSkuId}", mainSkuId);

        String body = Jsoup.parse(new URL(url).openStream(), "GBK", url).body().html();
        if(NullUtil.isNull(body)) throw new Exception("京东商品详情获取失败，skuId="+skuId);
        body = body.substring(body.indexOf("\"content\":")+11, body.length()-2).replaceAll("\\\\&quot;", "");
        if(NullUtil.isNull(body)) throw new Exception("京东商品详情获取失败，skuId="+skuId);
        return body;
    }

    public boolean hasStock(String skuId) throws Exception {
        String url = STOCK_URL.replace("{skuid}", skuId);

        String body = Jsoup.parse(new URL(url).openStream(), "GBK", url).body().text();

        if(NullUtil.isNull(body)) throw new Exception("京东商品库存获取失败，skuId="+skuId);
        body = body.replace(StringUtil.append2String("{\"", skuId, "\":"), "").replace("}}", "}");
        Map<String, Object> map = JsonUtil.readJson(body, Map.class);
        if(null == map) throw new Exception("京东商品库存获取失败，skuId="+skuId);
        int stockState = (int) map.get("StockState");
        if (STOCK_STATE != stockState) throw new Exception("京东商品无库存，skuId="+skuId);
        String stockStateName = (String) map.get("StockStateName");
        if (stockStateName.contains("有") || stockStateName.contains("现")) return true;
        throw new Exception("京东商品无库存，skuId="+skuId);
    }


}
