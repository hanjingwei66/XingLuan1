package com.shuojie.serverImpl.sensorServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.sensorModle.LaserSensor;
import com.shuojie.domain.sensorModle.ZullProperty;
import com.shuojie.nettyService.Handler.TextWebSocketFrameHandler;
import com.shuojie.service.sensorService.Observer;
import com.shuojie.service.sensorService.SensorAsyncService;
import com.shuojie.service.sensorService.SensorProperty;
import com.shuojie.service.sensorService.SensorSubject;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.vo.Result;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 观察者类 激光传感器
 */
public class LaserSensorImpl implements Observer {
    @Value("${sensor.laserId}")
    private Integer laserId;
    private int jizhongqid;
    private int jiedianid;
    private int time;
    private byte[] LaserSensorData;
    private SensorSubject sensorData;
    public LaserSensorImpl(SensorSubject sensorData){
        this.sensorData=sensorData;
        sensorData.registerObserver(this);
    }

    LaserSensor LaserSensor=new LaserSensor();
    LaserSensor LaserSensorVo=new LaserSensor();
    Result result=null;
    @Override
    public void update(int jizhongqid, int jiedianid, int time, byte[] dataBytes,int snr) {
        if (jiedianid == 88682) {
            TextWebSocketFrameHandler textWebSocketFrameHandler = (TextWebSocketFrameHandler) SpringUtil.getBean("TextWebSocketFrameHandler");
            SensorProperty sensorProperty = (SensorProperty) SpringUtil.getBean("SensorProperty");
            SensorAsyncService asyncService = (SensorAsyncService) SpringUtil.getBean("AsyncService");
            this.jizhongqid = jizhongqid;
            this.jiedianid = jiedianid;
            this.time = time;
            this.LaserSensorData = dataBytes;
            if (dataBytes != null) {
                //电压
                Integer VD00 = sensorProperty.computeVoltage(dataBytes[0], dataBytes[1], dataBytes[2]);
                Integer VD01 = sensorProperty.computeVoltage(dataBytes[3], dataBytes[4], dataBytes[5]);
                Integer VD10 = sensorProperty.computeVoltage(dataBytes[6], dataBytes[7], dataBytes[8]);
                Integer VD11 = sensorProperty.computeVoltage(dataBytes[9], dataBytes[10], dataBytes[11]);
//                           //激光点的xy轴坐标
                Double Px = sensorProperty.computeSupersonic(dataBytes[12], dataBytes[13], dataBytes[14]);
                Double Py = sensorProperty.computeSupersonic(dataBytes[15], dataBytes[16], dataBytes[17]);
//                          //超声波测距
                Double Distance = sensorProperty.computeDistance(dataBytes[18], dataBytes[19]);

//                        Integer DataL = sensorProperty.computeTENAXIS(dataBytes[20], dataBytes[21]);
                //加速度xyz
                double[] AccelerationList = sensorProperty.computeAcceleration(dataBytes[20], dataBytes[21], dataBytes[22], dataBytes[23], dataBytes[24], dataBytes[25]);
////                       //角速度xyz
                double[] AngularVelocityList = sensorProperty.computeAngularVelocity(dataBytes[26], dataBytes[27], dataBytes[28], dataBytes[29], dataBytes[30], dataBytes[31]);
//                        //角度
                double[] AngularList = sensorProperty.computeAngular(dataBytes[32], dataBytes[33], dataBytes[34], dataBytes[35], dataBytes[36], dataBytes[37]);
//                        //高度
                double hight = sensorProperty.computeHight(dataBytes[38], dataBytes[39], dataBytes[40], dataBytes[41]);
////                        tt.setSensorData(dataBytes.toString());
                LaserSensor.setJiedianid(jiedianid);
                LaserSensor.setTime(time);
                LaserSensor.setVoltage1(VD00);
                LaserSensor.setVoltage2(VD01);
                LaserSensor.setVoltage3(VD10);
                LaserSensor.setVoltage4(VD11);
                LaserSensor.setXSupersonic(Px);
                LaserSensor.setYSupersonic(Py);
                LaserSensor.setDistance(Distance);
                LaserSensor.setAcceleration(AccelerationList);
                LaserSensor.setAngularVelocity(AngularVelocityList);
                LaserSensor.setAngular(AngularList);
                LaserSensor.setHight(hight);
                LaserSensor.setXAcceleration(AccelerationList[0]);
                LaserSensor.setYAcceleration(AccelerationList[1]);
                LaserSensor.setZAcceleration(AccelerationList[2]);
                LaserSensor.setXAngularVelocity(AngularVelocityList[0]);
                LaserSensor.setYAcceleration(AngularVelocityList[1]);
                LaserSensor.setZAcceleration(AngularVelocityList[2]);
                LaserSensor.setXAngular(AngularList[0]);
                LaserSensor.setYAngular(AngularList[1]);
                LaserSensor.setZAngular(AngularList[2]);

                LaserSensorVo.setJiedianid(jiedianid);
                LaserSensorVo.setTime(time);
                LaserSensorVo.setVoltage1(VD00);
                LaserSensorVo.setVoltage2(VD01);
                LaserSensorVo.setVoltage3(VD10);
                LaserSensorVo.setVoltage4(VD11);
                LaserSensorVo.setXSupersonic(Px);
                LaserSensorVo.setYSupersonic(Py);
                LaserSensorVo.setDistance(Distance);
                LaserSensorVo.setAcceleration(AccelerationList);
                LaserSensorVo.setAngularVelocity(AngularVelocityList);
                LaserSensorVo.setAngular(AngularList);
                LaserSensorVo.setHight(hight);
                Date date = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                LaserSensor.setUserTime(sf.format(date));
                asyncService.executeAsync(LaserSensor);//异步插入
                result = new Result(200, "SUCCESS", "sensor_laser", LaserSensorVo);
                String LaserSensor = JSONObject.toJSONString(result);
                textWebSocketFrameHandler.send(LaserSensor);
            }
        }
    }
    @Override
    public void remove(){
        sensorData.removeObserver(this);
    }
}
