package com.newland.mall.system.utils;

/**
 * 邮箱验证码
 * Author: leell
 * Date: 2023/1/14 10:34:31
 */
public class MailValidCode {
    public static String getContent(String code){
        return String.format("你好！你的注册验证码为：%s",code);
    }

    public static void main(String[] args) {
        System.out.println(MailValidCode.getContent(String.valueOf(2)));
    }
}
