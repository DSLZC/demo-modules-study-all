package com.dslcode.file.server.controller;

import com.dslcode.core.date.DateUtil;
import com.dslcode.core.file.FileUtil;
import com.dslcode.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by dongsilin on 2017/2/23.
 * 文件服务器操作主类
 * 详细操作见 user-guide.doc
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

    @Value("${file.directory.root}")
    private String rootDirectory;

    /**
     * 应用测试接口
     * @return
     */
    @GetMapping("/hello")
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
    @PostMapping("/upload/{appName}/{moduleName}/{typeName}")
    public String upload(
            @RequestParam(value = "filedata") MultipartFile filedata,
            @PathVariable(value = "appName") String appName,
            @PathVariable(value = "moduleName") String moduleName,
            @PathVariable(value = "typeName") String typeName){
        String uploadPath = StringUtil.append2String(rootDirectory, File.separator, appName, File.separator, moduleName, File.separator, typeName, File.separator);
        new File(uploadPath).mkdirs();
        try {
            String fileNewName = FileUtil.getRandomFileName(filedata.getOriginalFilename());
            String fileNewPath = StringUtil.append2String(uploadPath, fileNewName);
            FileUtil.copy(filedata.getInputStream(), fileNewPath, 2);
            return StringUtil.append2String(appName, "/", moduleName, "/", typeName, "/", fileNewName);
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
    @GetMapping("get/{appName}/{moduleName}/{typeName}/{fileName:.+}")
    public void get( @PathVariable(value = "appName") String appName,
                     @PathVariable String moduleName,
                     @PathVariable String typeName,
                     @PathVariable String fileName,
                     HttpServletResponse response){
        String filePath= StringUtil.append2String(rootDirectory, File.separator, appName, File.separator, moduleName, File.separator, typeName, File.separator, fileName);
        try {
            FileUtil.download(filePath, fileName, typeName, response);
            // TODO 文件访问量+1

        } catch (Exception e) {
            log.error("file={}, error={}", filePath, e.getMessage());
        }
    }

    /**
     * 删除文件，delete请求
     * @param appName 应用名
     * @param moduleName 模块名
     * @param typeName 类型名
     * @param fileName 文件名
     */
    @DeleteMapping("delete/{appName}/{moduleName}/{typeName}/{fileName:.+}")
    public void delete( @PathVariable(value = "appName") String appName,
                        @PathVariable String moduleName,
                        @PathVariable String typeName,
                        @PathVariable String fileName){
        String filePath= StringUtil.append2String(rootDirectory, File.separator, appName, File.separator, moduleName, File.separator, typeName, File.separator, fileName);
        new File(filePath).delete();
    }

}
