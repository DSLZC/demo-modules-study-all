package com.dslcode.jpa;

import com.dslcode.core.property.PropertyUtil;
import com.dslcode.core.thread.ThreadPoolUtil;
import com.dslcode.jpa.elasticsearch.service.GoodsCommonService;
import com.dslcode.jpa.elasticsearch.type.GoodsCommon;
import com.dslcode.jpa.mysql.entity.GoodsCommonMysql;
import com.dslcode.jpa.mysql.service.GoodsCommonMysqlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dongsilin on 2017/7/29.
 */
@Slf4j
public class ElasticSearchTest extends DemoJpaElasticsearchApplicationTests {


    @Autowired
    private GoodsCommonMysqlService goodsCommonMysqlService;
    @Autowired
    private GoodsCommonService goodsCommonService;

    @Test
    public void insert() throws InterruptedException {
        this.goodsCommonService.deleteAll();

        int page = 0, size = 10;
        Page<GoodsCommonMysql> goodsCommonMysqlPagePage = this.goodsCommonMysqlService.findByPage(new PageRequest(page, size, new Sort(Sort.Direction.ASC, "id")));
        while (goodsCommonMysqlPagePage.hasNext()){
            List<GoodsCommon> goodsCommons = PropertyUtil.dozerListMapper(goodsCommonMysqlPagePage.getContent(), GoodsCommon.class);
            ThreadPoolUtil.run(() -> goodsCommonService.insert(goodsCommons));
            Thread.sleep(500);
            goodsCommonMysqlPagePage = this.goodsCommonMysqlService.findByPage(new PageRequest(++page, size, new Sort(Sort.Direction.ASC, "id")));
        }
        log.debug("page={}", page);
        ThreadPoolUtil.closePool();
    }

    @Test
    public void findAll(){
        Iterable<GoodsCommon> accounts = goodsCommonService.findAll();
        accounts.forEach(account -> log.debug("account={}", account));
    }

    @Test
    public void findByStoreId(){
        Page<GoodsCommon> goodsCommonPage = this.goodsCommonService.findByStoreId(20L, new PageRequest(0, 20, new Sort(Sort.Direction.DESC, "price")));
        log.debug("goodsCommonPage = {}", goodsCommonPage.getContent());
    }

    @Test
    public void count() {
        log.debug("goodsCommonMysql.count = {}", goodsCommonMysqlService.count());
        log.debug("goodsCommon.count = {}", goodsCommonService.count());
    }

    @Test
    public void findByName(){
        String name = "韩国 正品";

        Pageable page = new PageRequest(0, 20, new Sort(Sort.Direction.DESC, "price"));

        long start = System.currentTimeMillis();
        Page<GoodsCommonMysql> goodsCommonMysqlPage = goodsCommonMysqlService.findByName(name, page);
        log.debug("goodsCommonMysqlPage.useTime = {}", (System.currentTimeMillis() - start));
        log.debug("goodsCommonMysqlPage.getTotalElements={},goodsCommonMysqlPage.getTotalPages={} ",
                goodsCommonMysqlPage.getTotalElements(), goodsCommonMysqlPage.getTotalPages());

        start = System.currentTimeMillis();
        Page<GoodsCommon> goodsCommonPage = this.goodsCommonService.findByName(name, page);
        log.debug("goodsCommonPage.useTime = {}", (System.currentTimeMillis() - start));
        log.debug("goodsCommonPage.getTotalElements={},goodsCommonPage.getTotalPages={} ",
                goodsCommonPage.getTotalElements(), goodsCommonPage.getTotalPages());
        log.debug("goodsCommonPage = {}", goodsCommonPage.getContent());

    }

    @Test
    public void findGTPrice(){
        Page<GoodsCommon> goodsCommonPage = this.goodsCommonService.findGTPrice(new BigDecimal(2000), new PageRequest(0, 20, new Sort(Sort.Direction.DESC, "price")));
        log.debug("goodsCommonPage.getTotalElements={},goodsCommonPage.getTotalPages={} ",
                goodsCommonPage.getTotalElements(), goodsCommonPage.getTotalPages());
        log.debug("goodsCommonPage = {}", goodsCommonPage.getContent());
    }


}
