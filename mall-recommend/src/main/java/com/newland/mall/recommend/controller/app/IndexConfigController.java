package com.newland.mall.recommend.controller.app;

import com.newland.mall.recommend.service.IndexConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  控制器
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
@RestController
@RequestMapping("/indexConfig")
@Tag(name = "", description = "")
public class IndexConfigController {
    @Autowired
    private IndexConfigService indexConfigService;
}