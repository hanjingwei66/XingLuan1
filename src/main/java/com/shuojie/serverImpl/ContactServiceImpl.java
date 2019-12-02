package com.shuojie.serverImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuojie.dao.ContactMapper;
import com.shuojie.domain.Contact;
import com.shuojie.service.ContactService;
import com.shuojie.utils.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("contactServiceImpl")
public class ContactServiceImpl extends ServiceImpl<ContactMapper,Contact> implements ContactService {

    @Resource
    private ContactMapper contactMapper;

    private Result result;

    @Override
    public Result insertContact(Contact contact) {
        contactMapper.insert(contact);
        if (contact.getContactText() != null){
            result = new Result(200,"contactSuccess","api_insertContact");
        }else {
            result = new Result(201,"contactError","api_insertContact");
        }
        return result;
    }
}
