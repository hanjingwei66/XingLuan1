package com.shuojie.domain.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Arrays;

@Data
public class PictureList {
    private Long id;//子节点
    private Long parentId;//父节点
    private String chanyeName;//产业名称(industry关键字)
    private String pictureUrl;//图例地址
    private Integer parentType;//给前端判断图例集选项标识 1
//    private String command;

}
