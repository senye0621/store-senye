package com.senye.storesenye.service;

import com.senye.storesenye.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void regTest() {

        User user = new User();
        user.setUsername("senye666");
        user.setPassword("123456");
        user.setPhone("06687761322");
        userService.reg(user);
        System.out.println("注册成功");
    }

    @Test
    public void loginTest(){

        User senye555 = userService.login("controller", "123456");
        System.out.println(senye555);
    }

    @Test
    public void changePasswordTest(){
        userService.changePassword(29,"senye222","123456","222222");

    }

    @Test
    public void getByUidTest(){
        User user = userService.getByUid(29);
        System.out.println(user);
    }

    @Test
    public void changeInfoTest(){
        User user = new User();
        user.setPhone("18813959389");
        user.setEmail("senye912777954@qq.com");
        user.setGender(0);
        userService.changeInfo(29,"senye222",user);
    }
}