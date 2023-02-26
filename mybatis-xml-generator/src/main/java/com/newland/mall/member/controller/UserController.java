package com.newland.mall.member.controller;

import com.newland.mall.member.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  控制器
 * @author leellun
 * @since 2023-02-23 19:43:27
 */
@RestController
@RequestMapping("/user")
@Tag(name = "", description = "")
public class UserController {
    @Autowired
    private UserService userService;
}