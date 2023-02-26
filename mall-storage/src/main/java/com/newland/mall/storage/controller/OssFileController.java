package com.newland.mall.storage.controller;

import com.newland.mall.model.RestResponse;
import com.newland.mall.storage.model.OssFile;
import com.newland.mall.storage.service.IFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件控制器
 * Author: leell
 * Date: 2023/1/12 20:33:11
 */
@Tag(name = "文件：文件上传下载")
@Log4j2
@RestController
@RequestMapping("/oss")
public class OssFileController {
    @Autowired
    private IFileService fileService;

    /**
     * 文件上传请求
     */
    @Operation(method = "文件上传")
    @PostMapping("/upload")
    public RestResponse<OssFile> upload(@RequestParam("file") MultipartFile file) {
        try {
            // 上传并返回访问地址
            OssFile ossFile = fileService.uploadFile(file);
            return RestResponse.ok(ossFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
        return RestResponse.error("上传文件失败");
    }

    /**
     * 文件上传请求
     */
    @Operation(method = "图片上传")
    @PostMapping("/uploadpic")
    public RestResponse<OssFile> uploadPicture(@RequestParam("pic") MultipartFile file) {
        try {
            // 上传并返回访问地址
            OssFile ossFile = fileService.uploadPicture(file);
            return RestResponse.ok(ossFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
        return RestResponse.error("上传文件失败");
    }

    @Operation(method = "文件地址查询")
    @Parameters({@Parameter(name = "objectName", description = "对象名", required = true)})
    @GetMapping("/object/{objectName}")
    public RestResponse<String> getRealUrl(@PathVariable("objectName") String objectName) {
        return RestResponse.ok(fileService.getFileUrl(objectName));
    }

    @Operation(method = "文件删除")
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public RestResponse delete(@RequestParam("objectName") String objectName) {
        fileService.deleteFile(objectName);
        return RestResponse.success("文件删除成功");
    }
}
