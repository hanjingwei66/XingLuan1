package com.shuojie.domain.maps;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MapPoints {

    private Integer mid;//数字空间地图点id

    private String pointsName;//数字空间地图点名称（String时间）

    private Double maLongitude;//数字空间地图点经度

    private Double maLatitude;//数字空间地图点纬度

}