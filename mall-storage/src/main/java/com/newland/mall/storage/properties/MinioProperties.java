package com.newland.mall.storage.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: leell
 * Date: 2023/1/12 22:40:51
 */
@Data
@Component
public class MinioProperties {
    /**
     * minio地址+端口号
     */
    @Value("${minio.url}")
    private  String url;
    /**
     * minio用户名
     */
    @Value("${minio.accessKey}")
    private  String accessKey;
    /**
     * minio密码
     */
    @Value("${minio.secretKey}")
    private  String secretKey;
    /**
     * 文件桶的名称
     */
    @Value("${minio.bucketName}")
    private String bucketName;
}
