package com.shuojie.serverImpl.sensorServiceImpl;

import com.shuojie.service.sensorService.Observer;
import com.shuojie.service.sensorService.SensorSubject;

public class SensorBase implements Observer {
    private int jizhongqid;
    private int jiedianid;
    private int time;
    private byte[] LaserSensorData;
    private SensorSubject sensorData;
    public SensorBase(SensorSubject sensorData){
        this.sensorData=sensorData;
        sensorData.registerObserver(this);
    }
    @Override
    public void update(int jizhongqid, int jiedianid, int time, byte[] sensorData,int snr) {

    }

    @Override
    public void remove() {

    }
}
