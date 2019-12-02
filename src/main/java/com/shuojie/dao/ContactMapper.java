package com.shuojie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuojie.domain.Contact;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContactMapper extends BaseMapper<Contact> {
    //接收数据空间经纬点
    void insertContact(Contact contact);
}