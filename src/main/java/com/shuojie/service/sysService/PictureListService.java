package com.shuojie.service.sysService;

import com.shuojie.domain.system.PictureList;

import java.util.List;

public interface PictureListService {
    List<PictureList> selectList(PictureList picture);
}
