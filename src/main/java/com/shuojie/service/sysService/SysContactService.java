package com.shuojie.service.sysService;

import com.shuojie.domain.system.SysContact;

import java.util.List;


public interface SysContactService {
    void insert(SysContact sysContact);
    void update();
    void delete();
    List <SysContact> selectById(Long id);
    void deleteById(Integer sysContactId);
    void updateStatus(Integer sysContactId);

}
