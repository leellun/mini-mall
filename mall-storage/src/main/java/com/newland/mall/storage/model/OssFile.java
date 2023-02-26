package com.newland.mall.storage.model;

import lombok.Data;

/**
 * oss文件
 * Author: leell
 * Date: 2023/1/12 21:01:55
 */
@Data
public class OssFile {
    /**
     * objectName
     */
    private String objectName;
    /**
     * url地址
     */
    private String url;
}
