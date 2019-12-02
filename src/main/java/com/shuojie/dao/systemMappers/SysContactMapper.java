/*
 *
 * SysContactMapper.java
 *
 * @date 2019-09-06
 */
package com.shuojie.dao.systemMappers;

import com.shuojie.domain.system.SysContact;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysContactMapper {

    List<SysContact> selectById(Long id);
    void deleteById(Integer sysContactId);
    void updateStatus(Integer sysContactId);
}