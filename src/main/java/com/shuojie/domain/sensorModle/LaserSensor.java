package com.shuojie.domain.sensorModle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sensor_laser")
public class LaserSensor  {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer jiedianid;//节点id
    private Integer time;//传感器时间
    private String  userTime;//系统当前时间
    private Integer Voltage1;//电压
    private Integer Voltage2;
    private Integer Voltage3;
    private Integer Voltage4;
    private Double xSupersonic;//激光x轴坐标
    private Double ySupersonic;//激光y轴坐标
    private Double Distance;//传感器距离（单位cm）
    private Double xAcceleration;
    private Double yAcceleration;
    private Double zAcceleration;
    private Double xAngularVelocity;
    private Double yAngularVelocity;
    private Double zAngularVelocity;
    private Double xAngular;
    private Double yAngular;
    private Double zAngular;
    @TableField(exist = false)
    private double[] Acceleration;//加速度xyz
    @TableField(exist = false)
    private double[] AngularVelocity;//角速度xyz
    @TableField(exist = false)
    private double[] Angular;     //角度
    private double hight;     //高度
}
