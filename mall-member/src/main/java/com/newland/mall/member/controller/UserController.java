package com.newland.mall.member.controller;

import com.newland.mall.member.entity.User;
import com.newland.mall.member.model.dto.UserLoginDTO;
import com.newland.mall.member.model.dto.UserRegisterDTO;
import com.newland.mall.member.model.dto.UserUpdateDTO;
import com.newland.mall.member.model.vo.UserVO;
import com.newland.mall.member.service.UserService;
import com.newland.mall.model.MallUser;
import com.newland.mall.model.RestResponse;
import com.newland.security.utils.SecurityHelper;
import com.newland.security.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器
 *
 * @author leellun
 * @since 2023-02-23 19:43:27
 */
@RestController
@RequestMapping("/user")
@Tag(name = "", description = "")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityHelper securityHelper;

    @PostMapping("/login")
    @Operation(method = "登录接口", description = "返回token")
    public RestResponse<MallUser> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        MallUser user = userService.login(userLoginDTO.getLoginName(), userLoginDTO.getPasswordMd5());
        return RestResponse.success(user);
    }

    @PostMapping("/register")
    @Operation(method = "用户注册", description = "")
    public RestResponse register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO.getLoginName(), userRegisterDTO.getPassword());
        return RestResponse.success("注册成功");
    }

    @Operation(method = "修改用户信息", description = "")
    @Parameter(name = "userUpdateDTO", description = "用户信息")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update")
    public RestResponse updateInfo(@RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUserInfo(userUpdateDTO, securityHelper.getMallUser().getUserId());
        return RestResponse.success("修改成功");
    }

    @Operation(method = "获取用户信息", description = "")
    @GetMapping("/detail")
    @PreAuthorize("isAuthenticated()")
    public RestResponse<UserVO> getUserDetail() {
        UserVO userVO = userService.getUserDetail( securityHelper.getMallUser().getUserId());
        return RestResponse.success(userVO);
    }
}