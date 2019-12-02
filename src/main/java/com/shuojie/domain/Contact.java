package com.shuojie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Contact {
    @TableId(value = "contact_id",type = IdType.AUTO)
    private Integer contactId;

    private Long id;

    private String contactText;

}