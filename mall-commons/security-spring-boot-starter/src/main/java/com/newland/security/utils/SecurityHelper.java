package com.newland.security.utils;

import com.newland.mall.model.MallUser;
import com.newland.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 安全辅助类
 * Author: leell
 * Date: 2023/2/23 22:29:22
 */
@Component
public class SecurityHelper {
    private ThreadLocal<String> authThreadLocal = new ThreadLocal<>();
    @Autowired(required = false)
    private RedisUtil redisUtil;

    public void setToken(String token) {
        authThreadLocal.set(token);
    }

    public MallUser getMallUser() {
        String token = authThreadLocal.get();
        if (token == null) {
            return null;
        }
        return redisUtil.get(token);
    }
}
