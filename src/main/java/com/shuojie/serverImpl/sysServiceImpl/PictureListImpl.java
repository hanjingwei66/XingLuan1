package com.shuojie.serverImpl.sysServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuojie.dao.systemMappers.PictureListMapper;
import com.shuojie.domain.system.PictureList;
import com.shuojie.service.sysService.PictureListService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PictureListImpl implements PictureListService {
    @Autowired
    private PictureListMapper pictureListMapper;

    @Override
    public List<PictureList> selectList(PictureList picture) {
        QueryWrapper<PictureList> queryWrapper = Wrappers.<PictureList>query();
        queryWrapper.isNull("parent_id");
        QueryWrapper<PictureList> queryWrapper1 = Wrappers.<PictureList>query();
        queryWrapper1.eq("parent_id",picture.getParentId());
        if (picture.getParentId() == null || picture.getParentId() == 0) {
            List<PictureList> pictureLists = pictureListMapper.selectList(queryWrapper);
            return pictureLists;
        }else {
            List<PictureList> pictureLists = pictureListMapper.selectList(queryWrapper1);
            return pictureLists;
        }


    }

}
