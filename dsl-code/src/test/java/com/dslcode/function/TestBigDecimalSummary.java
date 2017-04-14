package com.dslcode.function;

import com.dslcode.core.collection.ListUtil;
import com.dslcode.core.function.bigdecimal.BigDecimalSummaryStatistics;
import com.dslcode.core.function.stream.CollectorsDefined;
import com.dslcode.core.number.NumberUtil;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongsilin on 2017/4/13.
 */
public class TestBigDecimalSummary {


    public static void main(String[] xxx){
        List<Goods> goodses = ListUtil.getList(
                new Goods(1, 2, new BigDecimal(5), new BigDecimal(0)),
                new Goods(2, 1, new BigDecimal(1.58), new BigDecimal(6.5)),
                new Goods(3, 3, new BigDecimal(15.27), new BigDecimal(10)),
                new Goods(4, 3, new BigDecimal(9.5), new BigDecimal(6.87)),
                new Goods(5, 3, new BigDecimal(53.19), new BigDecimal(2.5)),
                new Goods(6, 5, new BigDecimal(6.9), new BigDecimal(12.5))
        );

        BigDecimal sum = goodses.stream().map(goods ->
                goods.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add
                );

        System.out.println(NumberUtil.format(sum.multiply(new BigDecimal(100)), "#"));

        System.out.println("----------------------------------------BigDecimal reduce");


        BigDecimalSummaryStatistics summaryStatistics = goodses.stream().collect(CollectorsDefined.summarizing(goods -> goods.getPrice()));

        System.out.println(summaryStatistics.getSum().multiply(new BigDecimal(100)).intValue());

        System.out.println("---------------------------------------- BigDecimalSummaryStatistics");

        System.out.println(NumberUtil.defaultFormat(summaryStatistics.getSum()));
        System.out.println("----------------------------------------");

        DoubleSummaryStatistics doubleSummaryStatistics =  goodses.stream().collect(Collectors.summarizingDouble(goods -> goods.getPrice().doubleValue()));

        System.out.println(new BigDecimal(doubleSummaryStatistics.getSum()).multiply(new BigDecimal(100)).intValue());
    }


}

class Goods{

    private Integer id;
    private Integer num;
    private BigDecimal price;
    private BigDecimal shipFee;

    public Goods(Integer id, Integer num, BigDecimal price, BigDecimal shipFee) {
        this.id = id;
        this.num = num;
        this.price = price;
        this.shipFee = shipFee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }
}