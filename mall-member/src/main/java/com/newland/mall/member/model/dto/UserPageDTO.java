package com.newland.mall.member.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * 用户查找分页
 * Author: leell
 * Date: 2023/2/23 23:16:12
 */
@Data
public class UserPageDTO extends PageEntity {
    private String loginName;
}
