package com.senye.storesenye.service;

import com.senye.storesenye.entity.User;


public interface IUserService {

    /**
     * 用户注册
     * @param user 用户数据
     */
    void reg(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户数据
     */
    User login(String username, String password);

    /**
     * 根据用户id更改密码
     * @param uid 用户id
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid,String username,String oldPassword,String newPassword);


    /**
     * 根据用户的uid查询用户的信息，返回给个人信息修改页面
     * （当客户点击个人修改信息页面，用户的相关信息就要显该页面展示）
     * @param uid 用户的id
     * @return 返回用户的信息，用于个人信息页面的展示
     */
    User getByUid(Integer uid);

    /**
     * 根据用户的uid修改用户的信息
     * @param uid 用户的id
     * @param username 当前登录的用户名
     * @param user 用户的新数据
     */
    void changeInfo(Integer uid,String username,User user);
}
