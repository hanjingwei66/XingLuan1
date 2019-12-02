package com.shuojie.service.sensorService;

public interface Observer {
    public void update(int jizhongqid,int jiedianid,int time,byte[] sensorData,int snr);
    public void remove();
}
