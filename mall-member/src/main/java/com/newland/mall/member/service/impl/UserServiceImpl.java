package com.newland.mall.member.service.impl;

import com.github.pagehelper.PageInfo;
import com.newland.mall.member.common.MemberServiceError;
import com.newland.mall.member.entity.User;
import com.newland.mall.member.mapper.UserMapper;
import com.newland.mall.member.model.dto.UserPageDTO;
import com.newland.mall.member.model.dto.UserUpdateDTO;
import com.newland.mall.member.model.vo.UserVO;
import com.newland.mall.member.service.UserService;
import com.newland.mall.model.MallUser;
import com.newland.mall.utils.AssertUtil;
import com.newland.mall.utils.Md5Util;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author leellun
 * @since 2023-02-23 19:43:27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public void register(String loginName, String password) {
        AssertUtil.isNull(baseMapper.getByLoginName(loginName), MemberServiceError.SAME_LOGIN_NAME_EXIST);
        User registerUser = new User();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        registerUser.setIntroduceSign("随新所欲，蜂富多彩");
        String passwordMD5 = Md5Util.encrypt(password);
        registerUser.setPasswordMd5(passwordMD5);
        baseMapper.insertSelective(registerUser);
    }

    @Override
    public MallUser login(String loginName, String passwordMD5) {
        User user = baseMapper.getByLoginNameAndPasswd(loginName, passwordMD5);
        AssertUtil.notNull(user, MemberServiceError.LOGIN_ERROR);
        AssertUtil.isNotTrue(user.getLockedFlag() == 1, MemberServiceError.LOGIN_USER_LOCKED_ERROR);
        MallUser mallUser = new MallUser();
        mallUser.setUserId(user.getUserId());
        mallUser.setLoginName(user.getLoginName());
        return mallUser;
    }

    @Override
    public void updateUserInfo(UserUpdateDTO mallUser, Long userId) {
        User user = baseMapper.selectByPrimaryKey(userId);
        AssertUtil.notNull(user, "用户不存在！");
        user.setNickName(mallUser.getNickName());
        //若密码为空字符，则表明用户不打算修改密码，使用原密码保存
        if (!Md5Util.encrypt("").equals(mallUser.getPasswordMd5())) {
            user.setPasswordMd5(mallUser.getPasswordMd5());
        }
        user.setIntroduceSign(mallUser.getIntroduceSign());
        int count = baseMapper.updateByPrimaryKeySelective(user);
        AssertUtil.isTrue(count > 0, "更新失败");
    }

    @Override
    public PageInfo<User> getUsersPage(UserPageDTO userPageDTO){
        return PageWrapper.page(userPageDTO,()->baseMapper.listUsers(userPageDTO));
    }

    @Override
    public void lockUsers(Long[] ids, int lockStatus) {
        AssertUtil.isTrue(ids.length>0,"锁定用户失败");
        baseMapper.updateLockUserBatch(ids,lockStatus);
    }

    @Override
    public UserVO getUserDetail(Long userId) {
        User user = baseMapper.selectByPrimaryKey(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}