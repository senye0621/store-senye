package com.senye.storesenye.controller;


import com.senye.storesenye.entity.User;
import com.senye.storesenye.service.IUserService;
import com.senye.storesenye.service.ex.InsertException;
import com.senye.storesenye.service.ex.UsernameDuplicateException;
import com.senye.storesenye.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){

            userService.reg(user);
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){

        User user = userService.login(username, password);

        //登录成功后，将uid和username存入到HttpSession中
        session.setAttribute("uid",user.getUid());
        session.setAttribute("username",user.getUsername());

        // 将以上返回值和状态码OK封装到响应结果中并返回
        return new JsonResult<User>(OK,user);

    }

    @RequestMapping("change_password")
    public JsonResult<Void> changPassword(String oldPassword,String newPassword,HttpSession session){

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);

        return new JsonResult<Void>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){

        Integer uid = getUidFromSession(session);
        User user = userService.getByUid(uid);

        return new JsonResult<User>(OK,user);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changInfoByUid(HttpSession session,User user){

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        userService.changeInfo(uid,username,user);

        return new JsonResult<Void>(OK);
    }
}
