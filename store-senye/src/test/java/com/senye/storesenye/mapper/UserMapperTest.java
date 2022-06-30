package com.senye.storesenye.mapper;

import com.senye.storesenye.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findByUsernameTest(){
        User user = userMapper.findByUsername("seny0001");
        System.out.println(user);
    }

    @Test
    public void insertTest(){
        User user = new User();
        user.setUsername("senye888");
        user.setPassword("123456");
        user.setPhone("06687761322");
        Integer insert = userMapper.insert(user);
        System.out.println(insert);

    }

    @Test
    public void findByUidtTest(){
        User user = userMapper.findByUid(28);
        System.out.println(user);

    }

    @Test
    public void updatePasswordByUidTest(){
        Integer senye111 = userMapper.updatePasswordByUid(28, "111111", "senye111", new Date());
        System.out.println(senye111);

    }

    @Test
    public void updateInfoByUidTest(){

        Date date = new Date();
        User user = new User();
        user.setUid(29);
        user.setPhone("15213464148");
        user.setEmail("912777954@qq.com");
        user.setGender(1);
        user.setModifiedUser("senye222");
        user.setModifiedTime(date);
        Integer row = userMapper.updateInfoByUid(user);
        System.out.println(row);
    }

}
