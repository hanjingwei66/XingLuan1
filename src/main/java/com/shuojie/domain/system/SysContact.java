package com.shuojie.domain.system;

import lombok.Data;

@Data
public class SysContact {

    private Integer sysContactId;//邮件id
    private Long id;//用户id
    private String sysContactTitle;//标题
    private String sysContactText;//文本信息
    private String sysContactStatus;//0未读 1已读
    private String deleteFlag;//0未删除 1已删除
    private Integer sendPersonId;//发件人id
    private String sendPerson;//发件人
    private  String startTime ;// 日期
    private  String endTime;
}
