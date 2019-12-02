package com.shuojie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuojie.domain.User;
import com.shuojie.utils.vo.Result;

import java.util.List;

public interface IUserService extends IService<User> {
     List<User> selectUser();

     //注册
     Result register(User user);

     //登录
     Result toLogin(User user);

     //忘记密码
     //根据手机号查询
//     Result getUserByMobile(User user);
     //修改密码
     Result updateUserPassworld(User user);

     //修改密码
     Result xiugaiUserPassworld(User user);

//     Result updateStuse(User user);

/*     //联系我们
     //根据用户id 查询企业信息
     List<UserFirm> getUserFirm(Integer id);*/
}
