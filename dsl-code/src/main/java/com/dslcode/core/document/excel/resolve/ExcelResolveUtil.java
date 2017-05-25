package com.dslcode.core.document.excel.resolve;

import com.dslcode.core.document.excel.ExcelType;
import com.dslcode.core.file.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dongsilin on 2017/5/25.
 * EXCEL 解析处理工具类
 */
public final class ExcelResolveUtil {

    // 默认小数格式化类型
    private static final Format format = new DecimalFormat("#.###");

    /**
     * 解析excel输入流成list对象
     * @param inputStream excel输入流
     * @param fileName excel文件名称
     * @return 解析后的结果，String[]：一行的单元格value数组，List<String[]>：多行结果
     * @throws Exception
     */
    public static List<String[]> resolve2List(InputStream inputStream, String fileName) throws Exception {

        // 1. 获取Workbook
        Workbook workbook = getWorkbook(inputStream, fileName);
        if(workbook.getNumberOfSheets() == 0) throw new Exception("sheet数量为0，无法解析！");

        // 2. 解析Workbook
        int colNum;
        Row row;
        Cell cell;
        Sheet firstSheet = workbook.getSheetAt(0);
        List<String[]> result = new ArrayList<>(firstSheet.getLastRowNum() - firstSheet.getFirstRowNum());
        // 循环获取单元格的value
        Iterator<Row> rows = firstSheet.rowIterator();
        while (rows.hasNext()){
            colNum = 0;
            row = rows.next();
            if(null == row) continue;
            String[] values = new String[row.getLastCellNum() - row.getFirstCellNum()];
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()){
                cell = cells.next();
                values[colNum++] = getCellValue(cell);
            }
            result.add(values);
        }
        workbook.close();
        return result;
    }

    /**
     * 根据excel InputStream 获取Workbook
     * @param inputStream
     * @param fileName
     * @return
     * @throws Exception
     */
    private static Workbook getWorkbook(InputStream inputStream,String fileName) throws Exception{
        String suffix = FileUtil.suffix(fileName, true);
        if(suffix.equalsIgnoreCase(ExcelType.$2003.getSuffix())) return new HSSFWorkbook(inputStream);
        else if(fileName.equalsIgnoreCase(ExcelType.$2003.getSuffix())) return new XSSFWorkbook(inputStream);
        else throw new Exception("文件格式有误，不是excel文件！");
    }

    /**
     * 根据cell的类型获取cell的value
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell){
        if (null == cell) return "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING: return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC: {
                double number = cell.getNumericCellValue();
                if (number == (int) number) return String.valueOf((int) number);    //整数
                else return format.format(cell.getNumericCellValue());  // 小数
            }
            case Cell.CELL_TYPE_BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA: return cell.getCellFormula();  // 公式
            default: return "";
        }
    }

}
