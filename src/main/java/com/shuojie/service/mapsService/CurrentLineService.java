package com.shuojie.service.mapsService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuojie.domain.maps.CurrentLine;
import com.shuojie.utils.vo.Result;

public interface CurrentLineService extends IService<CurrentLine> {

    Result insertCurrentLine(CurrentLine currentLine);
}
