package com.shuojie.service.sensorService;

import java.util.List;

public interface SensorProperty {
    Integer computeVoltage(byte a,byte b,byte c);//计算电压
    Double computeSupersonic(byte a,byte b,byte c);//计算激光xy坐标
    Double computeDistance(byte a,byte b);//计算距离
    double[] computeAcceleration(byte axl, byte axh, byte ayl, byte ayh, byte azl, byte azh);//计算加速度
    double[] computeAngularVelocity(byte wxl,byte wxh,byte wyl,byte wyh,byte wzl,byte wzh);//计算角速度
    double[] computeAngular(byte RollL , byte RollH ,byte PitchL , byte PitchH ,byte YawL, byte YawH);//计算角度
    double computeHight(byte h0,byte h1,byte h2,byte h3);//计算高度
}
