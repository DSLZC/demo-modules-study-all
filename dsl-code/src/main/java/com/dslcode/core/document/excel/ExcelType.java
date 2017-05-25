package com.dslcode.core.document.excel;

/**
 * Created by dongsilin on 2017/5/25.
 */
public enum ExcelType {

    $2003(".xls"),
    $2007(".xlsx");

    private String suffix;

    ExcelType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
