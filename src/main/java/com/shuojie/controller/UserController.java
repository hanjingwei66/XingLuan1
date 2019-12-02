package com.shuojie.controller;


import com.shuojie.domain.User;
import com.shuojie.service.IUserService;
import com.shuojie.service.UserMerberService;
import com.shuojie.utils.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource(name = "userServiceImpl")
    private IUserService userServer;
    @Autowired
    private UserMerberService usermerberservice;

 @RequestMapping(value = "/findAll" ,method = RequestMethod.GET)
    public List<User> find(){
     return userServer.selectUser();
 }

    @RequestMapping("/find")
    public String findAll(){
        return "123";
    }

 @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public String Regist(@RequestBody String request){
     String pageUrl = "user/register";
     return pageUrl;
 }

 //注册
 @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@RequestBody User user){

     Result result =userServer.register(user);
     return result;
 }

  //登录
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public String index(){
     return "login";
    }
    @RequestMapping(value = "success",method = RequestMethod.POST)
    public String successs(){
     return "success";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody User user){
        Result result = userServer.toLogin(user);
        return result;
 }
 //发送短信
    @RequestMapping(value = "/sendMsg",method = RequestMethod.GET)
    public Result sendMsg(@RequestParam String telephone){
        Result result =usermerberservice.sendMsg(telephone);
        return result;
    }

    //忘记密码
    //根据手机号查询
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public Result updatePassword(@RequestBody User user){
        return  userServer.updateUserPassworld(user);
    }

    //修改密码
    @RequestMapping(value = "/xiugaiPassword",method = RequestMethod.POST)
    public Result xiugaiPassword(@RequestBody User user){
     return userServer.xiugaiUserPassworld(user);
    }

/*    @RequestMapping(value = "/getUserFirm",method = RequestMethod.POST)
    public List<UserFirm> getUserFirm(Integer id){
     return userServer.getUserFirm(id);
    }*/
@RequestMapping( "/getUserFirm")
public String getUserFirm(){
    return"123";
}
}
