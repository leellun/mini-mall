package com.newland.mall.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * cookie读写
 */
@Slf4j
public class CookieUtil {
    private final static String COOKIE_DOMAIN = "oursnail.cn";
    private final static String COOKIE_NAME = "snailmall_login_token";


    /**
     * 登陆的时候写入cookie
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");//设值在根目录
        ck.setHttpOnly(true);//不允许通过脚本访问cookie,避免脚本攻击
        ck.setMaxAge(60*60*24*365);//一年，-1表示永久,单位是秒，maxage不设置的话，cookie就不会写入硬盘，只会写在内存，只在当前页面有效
        log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck);
    }

    /**
     * 读取登陆的cookie
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck:cks){
                log.info("cookieName:{},cookieBValue:{}",ck.getName(),ck.getValue());
                if(COOKIE_NAME.equals(ck.getName())){
                    log.info("return cookieName:{},cookieBValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 注销的时候进行删除
     * @param request
     * @param response
     */
    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck:cks) {
                if(COOKIE_NAME.equals(ck.getName())){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//0表示消除此cookie
                    log.info("del cookieName:{},cookieBValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}
