package com.shuojie.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
public class User {

    @TableId
    private Long id;

    private String mobile;//手机号

    private String number;//版本

    private String command;//用于前台指令标识

    @TableField(exist = false)
    private String oldPassword;//前台传过来的密码
    private String password;

    private Integer firmId;//企业id

    private String username;//姓名

    private String idNumber;//身份证号

    private String position;//职位

    private String sex;//性别

    @TableField(exist = false)
    private String yzm;//验证码

    private UserFirm userFirm;//企业关联表

    private Integer  loginFlag ; //登陆标识

    //private Integer firmUserId;//企业用户id
}