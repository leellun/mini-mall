package com.newland.mall.system.service;

import com.github.pagehelper.PageInfo;
import com.newland.mall.system.entity.SysJob;
import com.newland.mall.system.model.dto.JobQueryDTO;
import com.newland.mybatis.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 岗位 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
public interface SysJobService  extends IService<SysJob> {
    /**
     * 获取所有角色
     * @return
     */
    List<SysJob> getAllJobs();
    /**
     * 获取分页job
     * @param jobQueryDTO
     * @return
     */
    PageInfo<SysJob> getJobs(JobQueryDTO jobQueryDTO);

    /**
     * 根据角色id获取角色
     * @param id
     * @return
     */
    SysJob getJob(Long id);
    /**
     * 添加job
     * @param sysJob
     */
    void addJob(SysJob sysJob);

    /**
     * 修改job
     * @param sysJob
     */
    void updateJob(SysJob sysJob);

    /**
     * 删除job
     * @param ids job id列表
     */
    void deleteJob(Set<Long> ids);

    /**
     * 开启关闭
     * @param id
     */
    void enable(Long id,Integer enable);
}
