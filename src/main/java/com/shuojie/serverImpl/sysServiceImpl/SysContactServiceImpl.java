package com.shuojie.serverImpl.sysServiceImpl;

import com.shuojie.dao.systemMappers.SysContactMapper;
import com.shuojie.domain.system.SysContact;
import com.shuojie.service.sysService.SysContactService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class SysContactServiceImpl implements SysContactService {
    @Resource
    private SysContactMapper sysContactMapper;

    @Override
    public void insert(SysContact sysContact) {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    public List<SysContact> selectById(Long id){

        List<SysContact> list = sysContactMapper.selectById(id);

        return list;
    }

    @Override
    public void deleteById(Integer sysContactId) {
        sysContactMapper.deleteById(sysContactId);
    }

    @Override
    public void updateStatus(Integer sysContactId) {
        sysContactMapper.updateStatus(sysContactId);
    }


}
