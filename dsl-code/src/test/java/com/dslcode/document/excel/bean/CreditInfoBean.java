package com.dslcode.document.excel.bean;

import lombok.Data;

/**
 * Created by dongsilin on 2017/5/24.
 */
@Data
public class CreditInfoBean {
    private int id;
    private String companyName;
    private String billType;
    private String billNumber;
    private String isBuyerIquidation;
    private double buyerBillAmount;
    private String buyerPayTime;
    private int overplusDays;
    private String buyerBillStatus;

    public CreditInfoBean(int id, String companyName, String billType, String billNumber, String isBuyerIquidation, double buyerBillAmount, String buyerPayTime, int overplusDays, String buyerBillStatus) {
        this.id = id;
        this.companyName = companyName;
        this.billType = billType;
        this.billNumber = billNumber;
        this.isBuyerIquidation = isBuyerIquidation;
        this.buyerBillAmount = buyerBillAmount;
        this.buyerPayTime = buyerPayTime;
        this.overplusDays = overplusDays;
        this.buyerBillStatus = buyerBillStatus;
    }
}
