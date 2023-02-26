package com.newland.mall.storage.service.impl;

import com.newland.mall.exception.BusinessException;
import com.newland.mall.storage.model.OssFile;
import com.newland.mall.storage.service.IFileService;
import com.newland.mall.storage.utils.FileUtils;
import com.newland.mall.storage.utils.MinioClientUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Author: leell
 * Date: 2023/1/12 20:34:08
 */
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private MinioClientUtils minioClientUtils;

    @Override
    public OssFile uploadFile(MultipartFile file) {
        try {
            String objectFile = minioClientUtils.putObject(FileUtils.getGenerateDateName(file.getOriginalFilename()), file);
            OssFile ossFile = new OssFile();
            ossFile.setObjectName(objectFile);
            ossFile.setUrl(minioClientUtils.getObjectURL(objectFile));
            return ossFile;
        } catch (Exception e) {
            throw new BusinessException("文件上传失败");
        }
    }

    @Override
    public OssFile uploadPicture(MultipartFile pic) {
        try {
            String filename = pic.getOriginalFilename();
            File newFile = new File(FileUtils.getGenerateName(filename));
            InputStream is = pic.getInputStream();
            if (pic.getOriginalFilename().endsWith(".png")) {
                newFile = new File(newFile.getName().split("\\.")[0] + ".jpg");
                Thumbnails.of(is).scale(1f).toFile(newFile);
                is = new FileInputStream(newFile);
            }
            OssFile ossFile = new OssFile();
            if (newFile.exists()) {
                if ((1024 * 1024 * 0.1) <= pic.getSize() && pic.getSize() <= (1024 * 1024)) {
                    Thumbnails.of(is).scale(1f).outputQuality(0.3f).toFile(newFile);
                } else if ((1024 * 1024) < pic.getSize() && pic.getSize() <= (1024 * 1024 * 2)) {
                    Thumbnails.of(is).scale(1f).outputQuality(0.2f).toFile(newFile);
                } else if ((1024 * 1024 * 2) < pic.getSize()) {
                    Thumbnails.of(is).scale(1f).outputQuality(0.1f).toFile(newFile);
                }
                String objectFile = minioClientUtils.putObject(FileUtils.getGenerateDateName(newFile.getName()), filename, newFile);
                ossFile.setObjectName(objectFile);
                ossFile.setUrl(minioClientUtils.getObjectURL(objectFile));
                newFile.delete();
            } else {
                String objectFile = minioClientUtils.putObject(FileUtils.getGenerateDateName(pic.getOriginalFilename()), filename, pic);
                ossFile.setObjectName(objectFile);
                ossFile.setUrl(minioClientUtils.getObjectURL(objectFile));
            }
            return ossFile;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("文件上传失败");
        }
    }

    @Override
    public String getFileUrl(String objectName) {
        try {
            return minioClientUtils.getObjectURL(objectName);
        } catch (Exception e) {
            throw new BusinessException("文件解析失败");
        }
    }

    @Override
    public void deleteFile(String objectName) {
        try {
            minioClientUtils.removeObject(objectName);
        } catch (Exception e) {
            throw new BusinessException("文件删除失败");
        }
    }
}
