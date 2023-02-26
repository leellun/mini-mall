package com.newland.mall.storage.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文件工具
 * Author: leell
 * Date: 2023/2/4 12:17:42
 */
public class FileUtils {
    public static final String getGenerateDateName(String fileName) {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + "/" + getGenerateName(fileName);
    }

    public static final String getGenerateName(String fileName) {
        int index = fileName.lastIndexOf(".");
        return index > 0 ? (System.nanoTime() + fileName.substring(index)) : String.valueOf(System.nanoTime());
    }

}
