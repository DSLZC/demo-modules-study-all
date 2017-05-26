package com.dslcode.core.document.excel.create;

import com.dslcode.core.document.excel.ExcelType;
import com.dslcode.core.document.excel.SheetCell;
import com.dslcode.core.document.excel.SheetStyle;
import com.dslcode.core.util.NullUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * Created by dongsilin on 2017/5/24.
 * EXCEL 生成处理工具类
 */
public final class ExcelCreateUtil {

    private static final SheetCell headIndexCell = new SheetCell("index", "序号");
    private static final int defaultHeadHeigth = 20;
    private static final int defaultBodyHeigth = 18;

    /**
     * 根据数据创建excel工作簿
     * @param excelType excel类型，2003版或2007版
     * @param headers excel表头数据，SheetCell：一个单元格数据，SheetCell[]：一行数据，List<SheetCell[]>：多行表头数据
     * @param bodys excel内容数据，SheetCell：一个单元格数据，List<SheetCell>：一行数据，List<List<SheetCell>>：多行内容数据
     * @param sheetName excel中sheet的名称
     * @param headHeigth 表头行的高度
     * @param bodyHeight 内容行的高度
     * @param isShowIndex 是否在第一列显示序号
     * @return 新创建的Excel工作簿
     * @throws Exception
     */
    public static Workbook createSheet(ExcelType excelType, List<SheetCell[]> headers, List<List<SheetCell>> bodys, String sheetName, int headHeigth, int bodyHeight, boolean isShowIndex) throws Exception{

        // 1. 创建新的Excel 工作簿
        Workbook workbook = excelType.equals(ExcelType.$2003)? new HSSFWorkbook() : new XSSFWorkbook();

        // 2. 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
        Sheet sheet = NullUtil.isNull(sheetName)? workbook.createSheet() : workbook.createSheet(sheetName);

        // 3. 创建默认的excel字体样式
        SheetStyle defaultStyle = SheetStyle.defaultStyle(workbook);

        //4. 创建表/标题（头）
        createHeader(sheet, defaultStyle, headers, headHeigth, isShowIndex);

        // 5. 创建内容
        createBody(sheet, defaultStyle, headers, bodys, bodyHeight, isShowIndex);

        return workbook;
    }

    /**
     * 根据数据创建excel工作簿，行高默认20和18
     * @param excelType excel类型，2003版或2007版
     * @param headers excel表头数据，SheetCell：一个单元格数据，SheetCell[]：一行数据，List<SheetCell[]>：多行表头数据
     * @param bodys excel内容数据，SheetCell：一个单元格数据，List<SheetCell>：一行数据，List<List<SheetCell>>：多行内容数据
     * @param sheetName excel中sheet的名称
     * @param isShowIndex 是否在第一列显示序号
     * @return 新创建的Excel工作簿
     * @throws Exception
     */
    public static Workbook createSheet(ExcelType excelType, List<SheetCell[]> headers, List<List<SheetCell>> bodys, String sheetName, boolean isShowIndex) throws Exception{
        return createSheet(excelType, headers, bodys, sheetName, defaultHeadHeigth, defaultBodyHeigth, isShowIndex);
    }

    /**
     * 创建表头行
     * @param sheet
     * @param defaultStyle
     * @param headers
     * @param headHeigth
     * @param isShowIndex
     */
    private static void createHeader(Sheet sheet, SheetStyle defaultStyle, List<SheetCell[]> headers, int headHeigth, boolean isShowIndex) throws Exception {
        int headerRows = headers.size(); //表头总行数
        int rowNum = 0; //行数
        for(SheetCell[] sheetCells : headers) {
            Row row = sheet.createRow(rowNum++);// 创建一行
            row.setHeightInPoints(headHeigth);  //设置行高
            int colNum = 0; // 列数
            // 最后一行首列表头设置序号名称
            if(isShowIndex && rowNum == headerRows){
                setCellValue(row.createCell(colNum++),headIndexCell , defaultStyle.getHeaderStyle());
            }
            for(SheetCell sheetCell : sheetCells) {
                // 获取一个单元格，并设置value和style
                setCellValue(row.createCell(colNum++), sheetCell, defaultStyle.getHeaderStyle());
            }
        }
    }

    /**
     * 创建内容
     * @param sheet
     * @param defaultStyle
     * @param headers
     * @param bodys
     * @param bodyHeight
     * @param isShowIndex
     */
    private static void createBody(Sheet sheet, SheetStyle defaultStyle, List<SheetCell[]> headers, List<List<SheetCell>> bodys, int bodyHeight, boolean isShowIndex) throws Exception {
        int rowNum = headers.size(); // 行数，从header的size开始
        for(List<SheetCell> sheetCells : bodys) {
            Row row = sheet.createRow(rowNum++);// 创建一行
            row.setHeightInPoints(bodyHeight);  // 设置行高
            int colNum = 0; // 列数
            for(SheetCell sheetCell : sheetCells) {
                // 设置序号的值
                if(isShowIndex && colNum == 0){
                    setIndexValue(row.createCell(colNum++), rowNum - headers.size(), defaultStyle.getBodyStyle());
                }
                // 获取一个单元格，并设置value和style
                setCellValue(row.createCell(colNum++), sheetCell, defaultStyle.getBodyStyle());
            }
        }
        // 自动调整列宽
        int colNum = headers.get(headers.size() - 1).length;
        for(int i = 0; i < colNum; i++) sheet.autoSizeColumn(i);
    }

    /**
     * 设置一个单元格的value和style
     * @param cell
     * @param sheetCell
     * @param defaultStyle
     */
    private static void setCellValue(Cell cell, SheetCell sheetCell, CellStyle defaultStyle) throws Exception {
        // 设置内容
        Object value = sheetCell.getValue();
        if(value instanceof String) cell.setCellValue((String) value);
        else if(value instanceof Number) cell.setCellValue(((Number) value).doubleValue());
        else throw new Exception("cell value 只允许 NUMBER 和 STRING 类型");

        // 设置style
        if (null != sheetCell.getCellStyle()) cell.setCellStyle(sheetCell.getCellStyle());
        else cell.setCellStyle(defaultStyle);
    }

    /**
     * 设置第一列序号单元格的value和style
     * @param cell
     * @param value
     * @param style
     */
    private static void setIndexValue(Cell cell, int value, CellStyle style){
        // 设置内容
        cell.setCellValue(value);
        // 设置style
        cell.setCellStyle(style);
    }

}
