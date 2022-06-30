package com.senye.storesenye.service.impl;

import com.senye.storesenye.entity.User;
import com.senye.storesenye.mapper.UserMapper;
import com.senye.storesenye.service.IUserService;
import com.senye.storesenye.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {

        // 根据参数user对象获取注册的用户名
        String username = user.getUsername();
        // 调用持久层的User findByUsername(String username)方法，根据用户名查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否不为null
        if(result != null){
            throw new UsernameDuplicateException("用户名已被占用！");

        }
        // 是：表示用户名已被占用，则抛出UsernameDuplicateException异常

        // 创建当前时间对象
         Date now = new Date();
        // 补全数据：加密后的密码
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(),salt);
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        // 补全数据：isDelete(0)
        user.setIsDelete(0);
        // 补全数据：4项日志属性
        user.setCreatedTime(now);
        user.setCreatedUser(username);
        user.setModifiedTime(now);
        user.setModifiedUser(username);

        Integer insert = userMapper.insert(user);
        if (insert != 1){
            throw new InsertException("添加用户数据出现未知错误，请联系系统管理员");
        }

        // 表示用户名没有被占用，则允许注册
        // 调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        // 判断受影响的行数是否不为1
        // 是：插入数据时出现某种错误，则抛出InsertException异常
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        // 检查查询结果是否为null
        // 是：抛出UserNotFoundException异常
        User result = userMapper.findByUid(uid);
        if (result == null){
            throw new UserNotFoundException("用户数据不存在");
        }

        // 检查查询结果中的isDelete是否为1
        // 是：抛出UserNotFoundException异常
        if (result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }

        // 从查询结果中取出盐值
        // 将参数oldPassword结合盐值加密，得到oldMd5Password
        // 判断查询结果中的password与oldMd5Password是否不一致
        // 是：抛出PasswordNotMatchException异常
        String salt = result.getSalt();
        String oldmd5Password = getMd5Password(oldPassword, salt);
        if (!oldmd5Password.equals(result.getPassword())){
            throw new PasswordNotMatchException("原密码错误");
        }

        // 将参数newPassword结合盐值加密，得到newMd5Password
        // 创建当前时间对象
        // 调用userMapper的updatePasswordByUid()更新密码，并获取返回值
        // 判断以上返回的受影响行数是否不为1
        // 是：抛了UpdateException异常
        String newmd5Password = getMd5Password(newPassword, salt);
        Date date = new Date();
        /*SimpleDateFormat simDate = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
        String formatDate = simDate.format(date);*/
        Integer row = userMapper.updatePasswordByUid(uid, newmd5Password, username, date);
        if (row != 1){
            throw new UpdateException("更改密码时，发生未知异常！请联系系统管理员！");
        }

    }

    @Override
    public User getByUid(Integer uid) {

        User result = userMapper.findByUid(uid);

        if (result == null){
            throw new UserNotFoundException("用户数据不存在！");
        }
        if (result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在！");
        }

        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());


        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {

        User result = userMapper.findByUid(uid);

        if (result == null){
            throw new UserNotFoundException("用户数据不存在！");
        }
        if (result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在！");
        }

       /*
       向参数user中补全数据,因为修改页面传过来的信息和Mapper层写的sql语句的参数不一致
       修改页面传过来的参数（电话邮箱性别），sql语句中有（用户id电话邮箱性别修改人修改时间）
       * */
       user.setUid(uid);
       user.setModifiedUser(username);
       user.setModifiedTime(new Date());

        Integer row = userMapper.updateInfoByUid(user);

        if (row != 1){
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员!");
        }


    }

    @Override
    public User login(String username, String password) {

        // 调用userMapper的findByUsername()方法，根据参数username查询用户数据
        User result = userMapper.findByUsername(username);

        // 判断查询结果是否为null
        // 是：抛出UserNotFoundException异常
        if (result == null){
            throw new UserNotFoundException("用户数据不存在的错误");
        }

        // 判断查询结果中的isDelete是否为1
        // 是：抛出UserNotFoundException异常
        if (result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在的错误");
        }

        // 从查询结果中获取盐值
        String salt = result.getSalt();
        String md5Password = getMd5Password(password, salt);
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        // 判断查询结果中的密码，与以上加密得到的密码是否不一致
        if (!md5Password.equals(result.getPassword())){
            throw new PasswordNotMatchException("密码验证失败的错误!");
        }
        // 是：抛出PasswordNotMatchException异常

        // 创建新的User对象
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        // 将查询结果中的uid、username、avatar封装到新的user对象中
        // 返回新的user对象

        return user;
    }

    /**
     * 执行密码加密
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
