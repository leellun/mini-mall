package com.newland.mybatis.mapper;

import java.util.List;

/**
 * base类型
 * @param <T>
 */
public interface BaseMapper<T> {
    int deleteByPrimaryKey(Long id);

    int insert(T row);

    int insertSelective(T row);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKey(T row);
}
