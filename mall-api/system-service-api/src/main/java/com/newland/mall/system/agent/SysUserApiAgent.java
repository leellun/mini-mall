package com.newland.mall.system.agent;


import com.newland.mall.model.LoginUser;
import com.newland.mall.model.RestResponse;
import com.newland.mall.system.dto.LoginDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@FeignClient("system-service")
public interface SysUserApiAgent {

    @PostMapping(value = "/user/login")
    RestResponse<LoginUser> login(@RequestBody LoginDto loginDTO);

}

