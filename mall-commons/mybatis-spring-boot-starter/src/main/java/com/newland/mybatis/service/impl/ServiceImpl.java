package com.newland.mybatis.service.impl;

import com.newland.mybatis.mapper.BaseMapper;
import com.newland.mybatis.service.IService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.springframework.jdbc.object.BatchSqlUpdate.DEFAULT_BATCH_SIZE;

/**
 * Author: leell
 * Date: 2023/2/21 13:09:22
 */
@SuppressWarnings("unchecked")
public class ServiceImpl<M extends BaseMapper<T>, T> implements IService<T> {
    static int DEFAULT_BATCH_SIZE = 1000;
    @Autowired
    protected M baseMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public T getById(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    public boolean saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        M mapper = (M) session.getMapper(baseMapper.getClass());
        int i = 0;
        for (T entity : entityList) {
            i++;
            mapper.insertSelective(entity);
            if (i % batchSize == 0) {
                session.commit();
                session.clearCache();
            }
        }
        session.commit();
        session.clearCache();
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}
