package com.newland.mybatis.service;

import java.util.Collection;


public interface IService<T> {
    /**
     * 通过id获取entity
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 批量插入
     * @param entityList 插入列表
     * @return
     */
    public boolean saveBatch(Collection<T> entityList);
}
