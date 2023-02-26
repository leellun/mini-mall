package com.newland.security.handler;

import com.alibaba.fastjson2.JSONObject;
import com.newland.mall.enumeration.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证节点
 * @author leell
 */
@Slf4j
public class LoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public LoginUrlAuthenticationEntryPoint() {
    }

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,PUT,POST,DELETE");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(ResultCode.SUCCESS.getCode());
        PrintWriter out = response.getWriter();
        JSONObject json=new JSONObject();
        json.put("code", ResultCode.UN_LOGIN.getCode());
        json.put("message",ResultCode.UN_LOGIN.getDesc());
        out.write(JSONObject.toJSONString(json));
        out.flush();
        out.close();
    }

}
