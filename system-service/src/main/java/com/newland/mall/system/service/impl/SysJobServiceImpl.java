package com.newland.mall.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.newland.mall.enumeration.BasicEnum;
import com.newland.mall.exception.BusinessException;
import com.newland.mall.system.entity.SysJob;
import com.newland.mall.system.enums.UserServiceErrorEnum;
import com.newland.mall.system.mapper.SysJobMapper;
import com.newland.mall.system.model.dto.JobQueryDTO;
import com.newland.mall.system.service.SysJobService;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import com.newland.mybatis.utils.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 岗位 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

    @Override
    public List<SysJob> getAllJobs() {
        return baseMapper.selectList(null, BasicEnum.YES.getCode());
    }

    @Override
    public PageInfo<SysJob> getJobs(JobQueryDTO jobQueryDTO) {
        return PageWrapper.page(jobQueryDTO, () -> baseMapper.selectList(PageHelper.like(jobQueryDTO.getName()), jobQueryDTO.getEnabled()));
    }

    @Override
    public SysJob getJob(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void addJob(SysJob sysJob) {
        SysJob dbJob = baseMapper.selectJobByName(sysJob.getName());
        AssertUtil.isNull(dbJob, UserServiceErrorEnum.JOB_EXIST.getDesc());
        sysJob.setEnabled(BasicEnum.YES.getCode());
        baseMapper.insert(sysJob);
    }

    @Override
    public void updateJob(SysJob sysJob) {
        SysJob dbJob = baseMapper.selectByPrimaryKey(sysJob.getId());
        AssertUtil.notNull(dbJob, UserServiceErrorEnum.JOB_NOT_EXIST.getDesc());
        baseMapper.updateByPrimaryKeySelective(sysJob);
    }

    @Override
    public void deleteJob(Set<Long> ids) {
        int count = baseMapper.deleteBatchIds(ids);
        AssertUtil.isTrue(count > 0, UserServiceErrorEnum.JOB_DELETE_FAIL.getDesc());
    }

    @Override
    public void enable(Long id, Integer enable) {
        SysJob dbJob = baseMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(dbJob, UserServiceErrorEnum.JOB_NOT_EXIST.getDesc());
        SysJob sysJob = new SysJob();
        sysJob.setId(id);
        sysJob.setEnabled(enable);
        baseMapper.updateByPrimaryKeySelective(sysJob);
    }
}
