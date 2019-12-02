package com.shuojie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuojie.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> selectUser();

    //注册
    //void register(User user);

    //登录
    User toLogin(User user);

    //忘记密码
    //根据手机号查询
    User getUserByMobile(User user);
    void updateUserPassworld(User newUser);
    List getMobileList();


    //个人信息
   // User findUserById(User user);

    //修改密码
    User xiugaiGetUserByid(User user);
    void xiugaiUserPassworld(User newUser);

    void updateStuse(User user);
    void updateReset(String telephone);

    Integer selectBytelphone(String telephone);

/*    //联系我们
    //根据用户id 查询企业信息
    List<UserFirm> getUserFirm(Integer id);*/

}