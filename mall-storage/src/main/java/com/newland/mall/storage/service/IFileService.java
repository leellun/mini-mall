package com.newland.mall.storage.service;

import com.newland.mall.storage.model.OssFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 * Author: leell
 * Date: 2023/1/12 20:33:47
 */
public interface IFileService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    OssFile uploadFile(MultipartFile file);
    /**
     * 上传图片
     * @param pic
     * @return
     */
    OssFile uploadPicture(MultipartFile pic);

    /**
     * 获取真实地址
     * @param objectName
     * @return
     */
    String getFileUrl(String objectName);

    /**
     * 删除文件
     * @param objectName 文件对象
     */
    void deleteFile(String objectName);
}
