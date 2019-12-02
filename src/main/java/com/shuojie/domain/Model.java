package com.shuojie.domain;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Model {

    private Integer mid;//模型id

    private String modelName;//模型名称

    private Double moLongitude;//模型经度

    private Double moLatitude;//模型纬度

    private String direction;//方向

}