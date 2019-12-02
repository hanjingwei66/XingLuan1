package com.shuojie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuojie.domain.Contact;
import com.shuojie.utils.vo.Result;

public interface ContactService extends IService<Contact> {

    //添加留言
    Result insertContact(Contact contact);
}
