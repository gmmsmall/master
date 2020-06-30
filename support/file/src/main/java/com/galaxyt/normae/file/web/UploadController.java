package com.galaxyt.normae.file.web;

import com.galaxyt.normae.core.annotation.NotWrapper;
import com.galaxyt.normae.core.exception.GlobalExceptionCode;
import com.galaxyt.normae.core.wrapper.GlobalResponseWrapper;
import com.galaxyt.normae.file.pojo.vo.FileVo;
import com.galaxyt.normae.file.service.FastDFSService;
import com.galaxyt.normae.security.provider.Authority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传
 * @author zhouqi
 * @date 2020/5/29 13:51
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/29 13:51     zhouqi          v1.0.0           Created
 *
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {


    @Autowired
    private FastDFSService fastDFSService;

    @Value("${fdfs.url-prefix}")
    private String urlPrefix;

    /**
     * 上传
     * 要求前端传递过来的 body 中文件的 key 必须为 file
     *
     * @param file
     * @return
     */
    @Authority(mark = "upload", name = "图片文件上传", isLogin = false)
    @NotWrapper
    @PostMapping
    public GlobalResponseWrapper upload(@RequestBody MultipartFile file) {

        String fullPath = this.fastDFSService.upload(file);

        if (fullPath == null) {
            return new GlobalResponseWrapper(GlobalExceptionCode.FILE_UPLOAD_FAIL);
        }

        FileVo fileVo = new FileVo();
        fileVo.setDomain(this.urlPrefix);
        fileVo.setPath(fullPath);

        return new GlobalResponseWrapper().data(fileVo);
    }


}
