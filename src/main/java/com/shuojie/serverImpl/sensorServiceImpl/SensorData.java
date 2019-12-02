package com.shuojie.serverImpl.sensorServiceImpl;

import com.shuojie.service.sensorService.Observer;
import com.shuojie.service.sensorService.SensorService;
import com.shuojie.service.sensorService.SensorSubject;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 *此类为传感器的被观察者类接受传感器的初始数据
 */

@Data
public class SensorData implements SensorSubject {
    private Long id;
    private byte version;//版本
    private byte comand;//2 命令字
    private int jizhongqid;//6 集中器 ID
    private int jiedianid;//节点 ID
    private short duanid;//短 ID
    private byte tongdao;//通道0x01-0x04
    private byte snr;//SNR
    private byte rssi0;//RSSI[0]
    private byte rssi1;//12 RSSI[1]0x01:RSSI 为正数，0x00:RSSI 为负数
    private byte nc;//13 NC
    private byte nc1;//14 NC
    private int time;//15 时间戳
    private byte zdzxqk;//16 终端在线情况 0x01：掉线，0x00：在线
    private short num;//17 终端入网总数
    private short sensorDataLength;// 18 数据长度
    private byte[] sensorData;//19 有效数据
    private ArrayList<Observer> observers;
    public SensorData(){
        observers=new ArrayList();
    }
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i=observers.indexOf(o);
        if(i>=0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer:observers) {
            observer.update(jizhongqid,jiedianid,time,sensorData,snr);
        }
    }

    @Override
    public void clearObserver() {
        observers.clear();
    }
}
