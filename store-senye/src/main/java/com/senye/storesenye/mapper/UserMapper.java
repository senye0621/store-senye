package com.senye.storesenye.mapper;

import com.senye.storesenye.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


public interface UserMapper {

    /**
     * 向数据库中插入用户数据
     * @param user 需要插入的用户信息
     * @return  受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查找用户信息。客户注册前执行，查看数据库客户是否已经注册过。
     * @param username 根据用户的用户名查询
     * @return 返回查询的结果，如果没有匹配的用户信息，则返回null
     */
    User findByUsername(String username);

    /**
     * 根据用户的uid更改用户密码
     * @param uid 用户id
     * @param password 新密码
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户id查找用户的信息
     * @param uid 用户id
     * @return 匹配的用户数据，如果没有匹配的用户数据，则返回null
     */
    User findByUid(Integer uid);

    /**
     * 根据uid更新用户资料
     * @param user 封装了用户id和新个人资料的对象
     * @return 受影响的行数
     */
    Integer updateInfoByUid(User user);
}
