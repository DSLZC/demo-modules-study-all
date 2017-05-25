package com.dslcode.core.document.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by dongsilin on 2017/5/24.
 */
public final class SheetStyle {

    private CellStyle headerStyle;
    private CellStyle bodyStyle;


    /**
     * 默认style
     * @param workbook
     * @return
     */
    public static SheetStyle defaultStyle(Workbook workbook) {
        SheetStyle sheelStyle = new SheetStyle();
        // 表头style
        sheelStyle.headerStyle = defaultHeaderStyle(workbook.createCellStyle(), workbook.createFont());
        // 内容style
        sheelStyle.bodyStyle = defaultBodyStyle(workbook.createCellStyle(), workbook.createFont());
        return sheelStyle;
    }

    /**
     * 默认header style
     * @param cellStyle
     * @param font
     * @return
     */
    private static CellStyle defaultHeaderStyle(CellStyle cellStyle, Font font){
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("楷体");
        font.setFontHeightInPoints((short) 14);// 设置字体大小
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 水平居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //垂直居中
        cellStyle.setWrapText(true);   // 内容显示不下时自动换行
        // 背景颜色为淡蓝色
        //style1.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        //style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    /**
     * 默认body style
     * @param cellStyle
     * @param font
     * @return
     */
    private static CellStyle defaultBodyStyle(CellStyle cellStyle, Font font) {
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);// 设置字体大小
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 水平居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //垂直居中
        cellStyle.setWrapText(true);   // 内容显示不下时自动换行
        return cellStyle;
    }


    public CellStyle getHeaderStyle() {
        return headerStyle;
    }

    public void setHeaderStyle(CellStyle headerStyle) {
        this.headerStyle = headerStyle;
    }

    public CellStyle getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(CellStyle bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

}
