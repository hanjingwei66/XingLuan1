package com.shuojie.domain.system;

import lombok.Data;

@Data
public class PicturListR {
    private Long id;//子节点
    private Long parentId;//父节点
    private String pictureType;//
    private String pictureUrl;//图例地址
    private Integer pictureId;//图片的父id

}
