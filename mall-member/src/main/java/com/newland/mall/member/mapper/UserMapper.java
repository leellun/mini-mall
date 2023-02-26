package com.newland.mall.member.mapper;

import com.newland.mall.member.entity.User;
import com.newland.mall.member.model.dto.UserPageDTO;
import com.newland.mall.model.MallUser;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Mapper 接口
 * @author leellun
 * @since 2023-02-23 19:43:27
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过登录名获取用户
     * @param loginName
     */
    User getByLoginName(@Param("loginName") String loginName);

    /**
     * 通过用户名和密码查找用户
     * @param loginName 用户名
     * @param password 密码
     */
    User getByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

    /**
     * 更新锁定用户
     * @param ids 用户id列表
     * @param lockStatus 状态
     */
    int updateLockUserBatch(@Param("ids") Long[] ids, @Param("lockStatus") int lockStatus);

    /**
     * @param userPageDTO
     * @return
     */
    List<User> listUsers(UserPageDTO userPageDTO);
}