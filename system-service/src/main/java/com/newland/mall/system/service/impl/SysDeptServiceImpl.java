package com.newland.mall.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.newland.mall.enumeration.BasicEnum;
import com.newland.mall.exception.BusinessException;
import com.newland.mall.system.entity.SysDept;
import com.newland.mall.system.enums.UserServiceErrorEnum;
import com.newland.mall.system.mapper.SysDeptMapper;
import com.newland.mall.system.model.dto.DeptQueryDTO;
import com.newland.mall.system.model.vo.SysDepartmentVo;
import com.newland.mall.system.service.SysDeptService;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import com.newland.mybatis.utils.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public SysDepartmentVo getDepartment(Long id) {
        SysDept dbDepartment = baseMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_NOT_EXIST.getDesc());
        SysDepartmentVo vo = new SysDepartmentVo();
        BeanUtils.copyProperties(dbDepartment, vo);
        if (dbDepartment.getPid() != null) {
            SysDept parent = baseMapper.selectByPrimaryKey(dbDepartment.getPid());
            if (parent != null) {
                vo.setParentName(parent.getName());
            }
        }
        return vo;
    }

    @Override
    public PageInfo<SysDept> getDepartments(DeptQueryDTO deptQueryDTO) {
        deptQueryDTO.setName(PageHelper.like(deptQueryDTO.getName()));
        return PageWrapper.page(deptQueryDTO.getPageNo(), deptQueryDTO.getPageSize(), () -> baseMapper.selectDepartments(deptQueryDTO, true));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void addDepartment(SysDept department) {
        if (department.getPid() != null) {
            SysDept parentDepartment = baseMapper.selectByPrimaryKey(department.getPid());
            AssertUtil.notNull(parentDepartment, UserServiceErrorEnum.DEPARTMENT_PARENT_NOT_EXIST.getDesc());
        }
        SysDept dbDepartment = baseMapper.selectDepartmentByNameAndPid(department.getName(), department.getPid());
        AssertUtil.isNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_EXIST.getDesc());
        department.setEnabled(BasicEnum.YES.getCode());
        department.setSubCount(0);
        baseMapper.insert(department);
        if (department.getPid() != null) {
            this.updateSubCount(department.getPid());
        }
    }

    @Override
    public void updateDepartment(SysDept department) {
        SysDept dbDepartment = baseMapper.selectByPrimaryKey(department.getId());
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_NOT_EXIST.getDesc());
        baseMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public void enableDepartment(Long id, Integer enable) {
        SysDept dbDepartment = baseMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_NOT_EXIST.getDesc());
        SysDept sysDepartment = new SysDept();
        sysDepartment.setId(id);
        sysDepartment.setEnabled(enable);
        baseMapper.updateByPrimaryKeySelective(sysDepartment);
    }

    @Override
    public void updateDeptSort(Long id, Integer deptSort) {
        SysDept dbDepartment = baseMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_NOT_EXIST.getDesc());
        SysDept sysDepartment = new SysDept();
        sysDepartment.setId(id);
        sysDepartment.setDeptSort(deptSort);
        baseMapper.updateByPrimaryKeySelective(sysDepartment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void deleteDepartment(Set<Long> ids) {
        List<Long> pids = new ArrayList<>();
        ids.forEach(id -> {
            SysDept dept = baseMapper.selectByPrimaryKey(id);
            if (dept != null) {
                pids.add(dept.getPid());
            }
        });
        int count = baseMapper.deleteBatchIds(ids);
        AssertUtil.isTrue(count > 0, UserServiceErrorEnum.DEPARTMENT_DELETE_FAIL.getDesc());
        pids.forEach(id -> {
            this.updateSubCount(id);
        });
    }

    @Override
    public List<SysDept> getDepartSearch(String name) {
        DeptQueryDTO deptQueryDTO = new DeptQueryDTO();
        deptQueryDTO.setName(PageHelper.like(name));
        List<SysDept> list = baseMapper.selectDepartments(deptQueryDTO, false);
        Map<Long, SysDept> map = new HashMap<>(10);
        List<SysDept> datas = new ArrayList<>();
        for (SysDept department : list) {
            if (department.getPid() == null || department.getPid() == 0) {
                datas.add(department);
                continue;
            } else {
                while (department.getPid() != null && department.getPid() != 0) {
                    List<SysDept> children = new ArrayList<>();
                    children.add(department);
                    if (map.containsKey(department.getPid())) {
                        SysDept parentDept = map.get(department.getPid());
                        parentDept.getChildren().add(department);
                        break;
                    } else {
                        department = baseMapper.selectByPrimaryKey(department.getPid());
                        department.setChildren(children);
                        map.put(department.getId(), department);
                    }
                }
            }
            boolean result = (department.getPid() == null || department.getPid() == 0) && map.get(department.getId()) == department;
            if (result) {
                datas.add(department);
            }
        }
        return datas;
    }

    @Override
    public List<SysDept> getSubDepts(Long pid) {
        return baseMapper.selectDepartmentsByPid(pid, BasicEnum.YES.getCode());
    }

    /**
     * 更新子数目
     *
     * @param pid 父id
     */
    private void updateSubCount(Long pid) {
        Integer count = baseMapper.countDeptByPid(pid);
        SysDept sysDepartment = new SysDept();
        sysDepartment.setId(pid);
        sysDepartment.setSubCount(count);
        baseMapper.updateByPrimaryKeySelective(sysDepartment);
    }
}
