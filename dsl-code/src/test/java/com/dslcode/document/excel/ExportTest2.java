package com.dslcode.document.excel;

import com.dslcode.core.document.excel.ExcelType;
import com.dslcode.core.document.excel.SheetCell;
import com.dslcode.core.document.excel.create.ExcelCreateUtil;
import com.dslcode.document.excel.bean.CreditInfoBean;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongsilin on 2017/5/24.
 */
public class ExportTest2 {

    public static void main(String[] xxx) throws Exception {

        List<SheetCell[]> headers = Arrays.<SheetCell[]>asList(new SheetCell[]{
                new SheetCell("companyName", "供应商名称"),
                new SheetCell("billType", "票据类型"),
                new SheetCell("billNumber", "票据号"),
                new SheetCell("isBuyerIquidation", "买方是否参与"),
                new SheetCell("buyerBillAmount", "票据金额"),
                new SheetCell("buyerPayTime", "应付日期"),
                new SheetCell("overplusDays", "剩余天数"),
                new SheetCell("buyerBillStatus", "状态")
        });

        List<CreditInfoBean> creditInfoList = Arrays.asList(
                new CreditInfoBean(1, "11111111公司", "afsgd", "1646464646464646", "是", 56325.6, "2017-05-06", 52, "已成功"),
                new CreditInfoBean(2, "22222222公司", "khgsd", "846474645131314346", "是", 486.0, "2016-05-06", 4, "已激活"),
                new CreditInfoBean(3, "33333333公司", "bfdhfd", "25864454576523453", "否", 36995.7, "2017-01-06", 37, "已失效"),
                new CreditInfoBean(4, "44444444公司", "qewetedrg", "164646946348486684", "是", 1564.68, "2017-05-25", 24, "处理中")
        );
        List<List<SheetCell>> bodys = creditInfoList.stream().map(credit ->
            Arrays.asList(
                    new SheetCell("companyName", credit.getCompanyName()),
                    new SheetCell("billType", credit.getBillType()),
                    new SheetCell("billNumber", credit.getBillNumber()),
                    new SheetCell("isBuyerIquidation", credit.getIsBuyerIquidation()),
                    new SheetCell("buyerBillAmount", credit.getBuyerBillAmount()),
                    new SheetCell("buyerPayTime", credit.getBuyerPayTime()),
                    new SheetCell("overplusDays", credit.getOverplusDays()),
                    new SheetCell("buyerBillStatus", credit.getBuyerBillStatus())
            )).collect(Collectors.toList());

        Workbook workbook2003 = ExcelCreateUtil.createSheet(ExcelType.$2003, headers, bodys, "应付账款信息", 20, 18, true);
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(new FileOutputStream("D:\\demo2.xls"));
        workbook2003.write(bufferedOutPut);
        bufferedOutPut.close();

        Workbook workbook2007 = ExcelCreateUtil.createSheet(ExcelType.$2007, headers, bodys, "应付账款信息", 20, 18, true);
        bufferedOutPut = new BufferedOutputStream(new FileOutputStream("D:\\demo2.xlsx"));
        workbook2007.write(bufferedOutPut);
        bufferedOutPut.close();

    }
}
