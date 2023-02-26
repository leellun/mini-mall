package com.newland.security.utils;

import com.newland.mall.model.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * 获取当前登录用户
     */
    public static LoginUser getLoginUser() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof LoginUser) {
            return (LoginUser) details;
        } else {
            return null;
        }
    }

}
