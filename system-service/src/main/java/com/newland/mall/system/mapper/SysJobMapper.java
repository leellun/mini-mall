package com.newland.mall.system.mapper;

import com.newland.mall.system.entity.SysJob;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 岗位 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
public interface SysJobMapper extends BaseMapper<SysJob> {
    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteBatchIds(@Param("ids") Collection<Long> ids);

    /**
     * 查询列表
     * @param name
     * @param enabled
     * @return
     */
    List<SysJob> selectList(@Param("name") String name,@Param("enabled") Integer enabled);

    /**
     * 查询job
     * @param name
     * @return
     */
    SysJob selectJobByName(@Param("name") String name);
}
