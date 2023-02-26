package com.newland.mall.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.security.utils.SecurityUtil;
import com.newland.mall.enumeration.BasicEnum;
import com.newland.mall.exception.BusinessException;
import com.newland.mall.model.LoginUser;
import com.newland.mall.system.entity.SysMenu;
import com.newland.mall.system.entity.SysRole;
import com.newland.mall.system.enums.MenuTypeEnum;
import com.newland.mall.system.enums.UserServiceErrorEnum;
import com.newland.mall.system.mapper.SysMenuMapper;
import com.newland.mall.system.mapper.SysRoleMapper;
import com.newland.mall.system.model.vo.MenuVo;
import com.newland.mall.system.service.SysMenuService;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.page.PageEntity;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class,noRollbackFor = BusinessException.class)
    public void addMenu(SysMenu sysMenu) {
        if (sysMenu.getPid() != null) {
            SysMenu parentMenu = baseMapper.selectByPrimaryKey(sysMenu.getPid());
            AssertUtil.notNull(parentMenu, UserServiceErrorEnum.MENU_PARENT_NOT_EXIST.getDesc());
        }
        sysMenu.setSubCount(0);
        baseMapper.insert(sysMenu);
        if (sysMenu.getPid() != null) {
            this.updateSubCount(sysMenu.getPid());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class,noRollbackFor = BusinessException.class)
    public void updateMenu(SysMenu sysMenu) {
        SysMenu dbDepartment = baseMapper.selectByPrimaryKey(sysMenu.getId());
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.MENU_NOT_EXIST.getDesc());
        baseMapper.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void deleteMenu(Set<Long> ids) {
        List<Long> pids = new ArrayList<>();
        ids.forEach(id -> {
            SysMenu sysMenu = baseMapper.selectByPrimaryKey(id);
            if (sysMenu != null) {
                pids.add(sysMenu.getPid());
            }
        });
        int count = baseMapper.deleteBatchIds(getSuperior(ids));
        AssertUtil.isTrue(count > 0, UserServiceErrorEnum.MENU_DELETE_FAIL.getDesc());
        pids.forEach(id -> {
            this.updateSubCount(id);
        });
    }

    private List<Long> getSuperior(Set<Long> ids) {
        List<Long> list = new ArrayList();
        List<SysMenu> menus = baseMapper.selectBatchIds(ids);
        while (menus.size() > 0) {
            List<Long> tempIds = new ArrayList<>();
            for (SysMenu menu : menus) {
                tempIds.add(menu.getId());
            }
            list.addAll(tempIds);
            menus = baseMapper.selectBatchByPids(tempIds);
        }
        return list;
    }

    @Override
    public List<String> getPermissions(Long userId) {
        List<SysRole> roles = sysRoleMapper.getRoleWithIdByUserId(userId, BasicEnum.YES.getCode());
        List<String> permissions = baseMapper.getPermissions(roles.stream().map(SysRole::getId).collect(Collectors.toList()));
        permissions.addAll(roles.stream().map(item -> "ROLE_" + item.getName()).collect(Collectors.toList()));
        return permissions;
    }

    @Override
    public List<SysMenu> getSubMenus(Long pid) {
        return baseMapper.selectSubMenuByPid(pid);
    }

    @Override
    public PageInfo<SysMenu> getMenuPage(PageEntity pageEntity) {
        return PageWrapper.page(pageEntity, () -> baseMapper.selectSubMenuByPid(null));
    }

    @Override
    public void updateMenuSort(Long id, Integer menuSort) {
        SysMenu dbMenu = baseMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(dbMenu, UserServiceErrorEnum.MENU_NOT_EXIST.getDesc());
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setMenuSort(menuSort);
        baseMapper.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    public void enableMenu(Long id, Integer enable) {
        SysMenu dbMenu = baseMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(dbMenu, UserServiceErrorEnum.MENU_NOT_EXIST.getDesc());
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setEnabled(enable);
        baseMapper.updateByPrimaryKeySelective(sysMenu);
    }

    /**
     * 更新子数目
     *
     * @param id id
     */
    private void updateSubCount(Long id) {
        int count = baseMapper.countDeptByPid(id);
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setSubCount(count);
        baseMapper.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    public MenuVo getMenu(Long id) {
        SysMenu dbMenu = baseMapper.selectByPrimaryKey(id);
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(dbMenu, menuVo);
        if (dbMenu.getPid() != null) {
            SysMenu parentMenu = baseMapper.selectByPrimaryKey(dbMenu.getPid());
            if (parentMenu != null) {
                menuVo.setParentName(parentMenu.getTitle());
            }
        }
        return menuVo;
    }

    @Override
    public List<SysMenu> getUserMenus() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        List<SysRole> roles = sysRoleMapper.getRoleWithIdByUserId(loginUser.getUserId(), BasicEnum.YES.getCode());
        List<SysMenu> menus = baseMapper.getMenuList(roles.stream().map(SysRole::getId).collect(Collectors.toList()));
        Map<Long, SysMenu> menuMap = menus.stream().collect(Collectors.toMap(SysMenu::getId, Function.identity()));
        List<Long> pids = menuMap.values().stream().filter(item -> item.getPid() != null && menuMap.get(item.getPid()) == null).map(SysMenu::getPid).distinct().collect(Collectors.toList());
        menus = menus.stream().filter(item -> item.getType().equals(MenuTypeEnum.MENU.getKey())).collect(Collectors.toList());
        while (pids.size() > 0) {
            List<SysMenu> list = baseMapper.selectBatchIds(pids);
            list.forEach(item -> {
                menuMap.put(item.getId(), item);
            });
            menus.addAll(list);
            pids = list.stream().filter(item -> item.getPid() != null && !menuMap.containsKey(item.getPid())).map(SysMenu::getPid).distinct().collect(Collectors.toList());
        }
        return menus;
    }

    @Override
    public List<String> getUserPermissions() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        List<SysRole> roles = sysRoleMapper.getRoleWithIdByUserId(loginUser.getUserId(), BasicEnum.YES.getCode());
        List<String> menus = baseMapper.getPermissions(roles.stream().map(SysRole::getId).collect(Collectors.toList()));
        return menus;
    }
}
