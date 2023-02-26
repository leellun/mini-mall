package com.newland.mall.system.mapper;

import com.newland.mall.system.entity.SysDept;
import com.newland.mall.system.model.dto.DeptQueryDTO;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Repository
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 查询部门列表
     * @param dto
     * @return
     */
    List<SysDept> selectDepartments(@Param("dto") DeptQueryDTO dto, @Param("root") boolean root);

    /**
     * 获取部门
     * @param name
     * @param pid
     * @return
     */
    SysDept selectDepartmentByNameAndPid(@Param("name") String name, @Param("pid") Long pid);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteBatchIds(@Param("ids") Collection<Long> ids);

    /**
     * 获取子部门
     * @param pid
     * @return
     */
    List<SysDept> selectDepartmentsByPid(@Param("pid") Long pid, @Param("enabled") Integer enabled);

    /**
     * 子项目个数
     * @param pid
     * @return
     */
    Integer countDeptByPid(@Param("pid") Long pid);
}
