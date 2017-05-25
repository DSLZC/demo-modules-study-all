package com.dslcode.document.excel;

import com.dslcode.core.document.excel.resolve.ExcelResolveUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dongsilin on 2017/5/25.
 */
public class ImportTest {

    public static void main(String[] xxx) throws Exception {
        File file = new File("D:\\demo2.xls");
        InputStream inputStream = new FileInputStream(file);
        List<String[]> res =  ExcelResolveUtil.resolve2List(inputStream, file.getName());
        res.stream().map(r -> Arrays.stream(r).reduce("", (a, b) -> a+","+b)).forEach(System.out::println);
    }

}
