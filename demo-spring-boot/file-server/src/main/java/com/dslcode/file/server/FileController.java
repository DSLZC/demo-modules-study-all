package com.dslcode.file.server;

import com.dslcode.core.date.DateUtil;
import com.dslcode.core.file.FileUtil;
import com.dslcode.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by dongsilin on 2017/2/23.
 * 文件服务器操作主类
 * 详细操作见 user-guide.doc
 */
@Slf4j
@RestController
@EnableScheduling
public class FileController {

    @Value("${file.directory.root}")
    private String rootDirectory;

    private static final String SEPARATOR = File.separator;

    private String yearMonth = DateUtil.nowStr(DateUtil.yyyyMM);

    /**
     * 每月初更新文件夹日期目录
     */
    @Scheduled(cron="0 0 0 1 * ?")
    public void updateYearMonth(){
        this.yearMonth = DateUtil.nowStr(DateUtil.yyyyMM);
    }

    /**
     * 应用测试接口
     * @return
     */
    @GetMapping("/hi")
    public String hello(){
        return "hello world:"+ DateUtil.nowStr(DateUtil.yyyyMMddHHmmss);
    }

    /**
     * 文件上传，post请求
     * @param filedata 文件
     * @param appName 应用名
     * @param moduleName 模块名
     * @param typeName 类型名
     * @return
     */
    @PostMapping("{appName}/{moduleName}/{typeName}")
    public String upload(
            @RequestParam(name = "filedata") MultipartFile filedata,
            @PathVariable(name = "appName") String appName,
            @PathVariable(name = "moduleName") String moduleName,
            @PathVariable(name = "typeName") String typeName){

        String uploadPath = StringUtil.append2String(
                rootDirectory,
                appName,
                SEPARATOR,
                moduleName,
                SEPARATOR,
                typeName,
                SEPARATOR,
                yearMonth,
                SEPARATOR
        );// /file_server/taobao/goods/image/201705/

        new File(uploadPath).mkdirs();  //文件夹不存在则创建
        try {
            String fileNewName = FileUtil.getRandomFileName(filedata.getOriginalFilename());
            String fileNewPath = StringUtil.append2String(uploadPath, fileNewName);
            FileUtil.copy(filedata.getInputStream(), fileNewPath, 2);

            return StringUtil.append2String("/", appName, "/", moduleName, "/", typeName, "/", yearMonth, "/", fileNewName);// /taobao/goods/image/201705/15464154131310kj.jpg
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 获取文件，get请求
     * @param appName 应用名
     * @param moduleName 模块名
     * @param typeName 类型名
     * @param fileName 文件名
     * @param response
     */
//    @GetMapping("{appName}/{moduleName}/{typeName}/{date}/{fileName:.+}")
//    public void get(@PathVariable(name = "appName") String appName,
//                    @PathVariable(name = "moduleName") String moduleName,
//                    @PathVariable(name = "typeName") String typeName,
//                    @PathVariable(name = "date") String date,
//                    @PathVariable(name = "fileName") String fileName,
//                    HttpServletResponse response){
//
//        String filePath= StringUtil.append2String(
//                rootDirectory,
//                SEPARATOR,
//                appName,
//                SEPARATOR,
//                moduleName,
//                SEPARATOR,
//                typeName,
//                SEPARATOR,
//                date,
//                SEPARATOR, fileName
//        );
//
//        try {
//            FileUtil.download(filePath, fileName, typeName, response);
//        } catch (Exception e) {
//            log.error("file={}, error={}", filePath, e.getMessage());
//        }
//    }

    /**
     * 删除文件，delete请求
     * @param appName 应用名
     * @param moduleName 模块名
     * @param typeName 类型名
     * @param fileName 文件名
     */
    @DeleteMapping("rm/{appName}/{moduleName}/{typeName}/{date}/{fileName:.+}")
    public void delete(@PathVariable(name = "appName") String appName,
                       @PathVariable(name = "moduleName") String moduleName,
                       @PathVariable(name = "typeName") String typeName,
                       @PathVariable(name = "date") String date,
                       @PathVariable(name = "fileName") String fileName){

        String filePath= StringUtil.append2String(
                rootDirectory,
                appName,
                SEPARATOR,
                moduleName,
                SEPARATOR,
                typeName,
                SEPARATOR,
                date,
                SEPARATOR, fileName
        );
        new File(filePath).delete();
    }

}
