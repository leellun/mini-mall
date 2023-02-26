package com.newland.mybatis.utils;

/**
 * Author: leell
 * Date: 2023/2/21 18:36:13
 */
public class PageHelper {
    public static String like(String str) {
        if (str != null) {
            return "%" + str + "%";
        } else {
            return str;
        }
    }
}
