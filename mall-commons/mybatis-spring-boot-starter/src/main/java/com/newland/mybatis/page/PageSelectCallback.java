package com.newland.mybatis.page;

import java.util.List;

/**
 * select查询
 * Author: leell
 * Date: 2023/2/19 14:18:08
 */
public interface PageSelectCallback<T> {
    List<T> get();
}
