package com.newland.mall.system.service;

import com.github.pagehelper.PageInfo;
import com.newland.mall.system.entity.SysDept;
import com.newland.mall.system.model.dto.DeptQueryDTO;
import com.newland.mall.system.model.vo.SysDepartmentVo;
import com.newland.mybatis.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 获取部门
     *
     * @param id
     * @return
     */
    SysDepartmentVo getDepartment(Long id);
    /**
     * 获取部门列表
     *
     * @param deptQueryDTO
     * @return
     */
    PageInfo<SysDept> getDepartments(DeptQueryDTO deptQueryDTO);

    /**
     * 添加部门
     *
     * @param department
     */
    void addDepartment(SysDept department);

    /**
     * 更新部门
     *
     * @param department
     */

    void updateDepartment(SysDept department);
    /**
     * 更新状态
     * @param id id
     * @param enable 状态
     */
    void enableDepartment(Long id,Integer enable);

    /**
     * 排序更改
     * @param id 部门id
     * @param deptSort 排序
     */
    void updateDeptSort(Long id,Integer deptSort);
    /**
     * 删除部门
     *
     * @param ids
     */
    void deleteDepartment(Set<Long> ids);

    /**
     * 获取同级目录和上级目录
     *
     * @param name
     * @return
     */
    List<SysDept> getDepartSearch(String name);

    /**
     * 获取子部门
     * @param pid
     * @return
     */
    List<SysDept> getSubDepts(Long pid);
}
