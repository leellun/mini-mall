package com.newland.security.handler;

import com.alibaba.fastjson2.JSONObject;
import com.newland.mall.enumeration.ResultCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * 异常处理
 * Author: leell
 * Date: 2023/2/26 00:18:30
 */
public class AccessExceptionFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        try{
            chain.doFilter(request,response);
//        }catch (Exception e){
//            if(e instanceof AccessDeniedException){
//                JSONObject json=new JSONObject();
//                json.put("code", ResultCode.UNAUTHORIZED.getCode());
//                json.put("message",ResultCode.UNAUTHORIZED.getDesc());
//                response.setCharacterEncoding("utf-8");
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                response.getWriter().write(JSONObject.toJSONString(json));
//            }else{
//                throw e;
//            }
//        }
    }
}
