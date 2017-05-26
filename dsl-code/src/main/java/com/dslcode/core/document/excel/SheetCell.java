package com.dslcode.core.document.excel;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Created by dongsilin on 2017/5/24.
 * EXCEL cell对象
 */
public final class SheetCell {

    private String name;
    // value 只允许 NUMBER 和 STRING 类型
    private Object value;
    private CellStyle cellStyle;

    public SheetCell() {}

    public SheetCell(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public SheetCell(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }
}
