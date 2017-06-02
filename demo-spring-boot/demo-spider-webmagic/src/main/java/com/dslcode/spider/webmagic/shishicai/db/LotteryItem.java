package com.dslcode.spider.webmagic.shishicai.db;

import com.dslcode.core.date.DateUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by dongsilin on 2017/6/1.
 */
@Data
@Entity
@Table(name = "shishicai_lottery")
public class LotteryItem {

    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    private String qiHao;

    private  String lotteryNumber;

    private String shiWei;

    private String geWei;

    private String last3;

    public LotteryItem() {
    }

    public LotteryItem(String date, String qiHao, String lotteryNumber, String shiWei, String geWei, String last3) {
        this.date = DateUtil.parseToDate(date, DateUtil.yyyyMMdd);
        this.qiHao = qiHao;
        this.lotteryNumber = lotteryNumber;
        this.shiWei = shiWei;
        this.geWei = geWei;
        this.last3 = last3;
    }

}
