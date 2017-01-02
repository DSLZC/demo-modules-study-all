package com.plugins.web.fileupload;

import com.dslcode.core.file.FileUtil;
import com.dslcode.core.string.StringUtil;
import com.dslcode.web.response.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by dongsilin on 2016/12/25.
 */

@Slf4j
@Controller
@RequestMapping("/file")
public class FileConreoller {

    /**
     * 文件上传
     * @param filedata
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public WebResponse upload(@RequestParam("filedata") MultipartFile filedata){
        log.debug("file.name={}", filedata.getOriginalFilename());
        try {
            String fileNewName = FileUtil.getRandomFileName(filedata.getOriginalFilename());
            String fileNewPath = StringUtil.append2String("upload", File.separator, fileNewName);
            FileUtil.copy(filedata.getInputStream(), fileNewPath, 2);
            return WebResponse.buildSuccess("上传成功...", fileNewName);
        } catch (Exception e) {
            log.error("", e);
            return WebResponse.buildErrorMsg(e.getMessage());
        }
    }

    @RequestMapping("/hi")
    @ResponseBody
    public WebResponse hi(){
        return WebResponse.buildSuccessMsg("您好...");
    }

}
