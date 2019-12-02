package com.shuojie.mqttClient;


import com.alibaba.fastjson.JSONObject;
import com.shuojie.dao.sensorMappers.SensorMapper;
import com.shuojie.domain.sensorModle.SensorTitle;
import com.shuojie.domain.sensorModle.ZullProperty;
import com.shuojie.nettyService.Handler.TextWebSocketFrameHandler;
import com.shuojie.serverImpl.sensorServiceImpl.SensorData;
import com.shuojie.service.sensorService.SensorAsyncService;
import com.shuojie.service.sensorService.SensorProperty;
import com.shuojie.utils.vo.Result;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;

@Slf4j
@Component
public class Mqttclien  {
    @Autowired
    private SensorMapper sensorMapper;
//    @Autowired
//    private RedisService redisService;
    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;
    @Autowired
    private SensorAsyncService asyncService;
    @Autowired
    private SensorProperty sensorProperty;
//    @Resource
//    private DistanceSensorMapper distanceSensorMapper;

//    @Autowired
//    private TextWebSocketFrameHandler text;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    //过期时间60秒
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    public String mqttTopic="123";
    Result result=null;
    SensorData sensorData = new SensorData();
    public  SensorData getPoint(){
        return this.sensorData;
    }
//    @PostConstruct
    public void start() throws Exception {
        String broker = "tcp://47.98.193.195:1883";
        String clientId = "JavaSample12";
        //Use the memory persistence
        MemoryPersistence persistence = new MemoryPersistence();
        ZullProperty zullProperty=new ZullProperty();
        SensorTitle tt=new SensorTitle();
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName("suojie");
            connOpts.setPassword("123456".toCharArray());
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker:" + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            String topic = mqttTopic;
            System.out.println("Subscribe to topic:" + topic);
            sampleClient.subscribe(topic);
            sampleClient.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String theMsg = MessageFormat.format("{0} is arrived for topic {1}.", new String(message.getPayload()), topic);
//                    redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + topic,new String(message.getPayload()));
//                    redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + );
                    System.out.println(theMsg);
                    System.out.println("getload"+new String(message.getPayload()));
                    byte[] payload = message.getPayload();
//                    String s = EncodUtil.BinaryToHexString(payload);
//                    byte[] bytes = s.getBytes();
//                    ByteBuf wrap = Unpooled.wrappedBuffer(payload);
//                    ByteBufUtil.hexDump(payload);
                    ByteBuffer wrap = ByteBuffer.wrap(payload);

                    if(payload!=null&&payload.length>=27){
                        sensorData.setVersion(wrap.get(0)); //版本
                        sensorData.setComand(wrap.get(1));//2 命令字
                        sensorData.setJizhongqid(wrap.getInt(2));//6 集中器 ID
                        sensorData.setJiedianid(wrap.getInt(6));//节点 ID
                        sensorData.setDuanid(wrap.getShort(10));//短 ID
                        sensorData.setTongdao(wrap.get(12));//通道0x01-0x04
                        sensorData.setSnr(wrap.get(13));//SNR
                        sensorData.setRssi0(wrap.get(14));//RSSI[0]
                        sensorData.setRssi1(wrap.get(15));//12 RSSI[1]0x01:RSSI 为正数，0x00:RSSI 为负数
                        sensorData.setNc(wrap.get(16));//13 NC
                        sensorData.setNc1(wrap.get(17));//14 NC
                        sensorData.setTime(wrap.getInt(18));//15 时间戳
                        sensorData.setZdzxqk(wrap.get(22));//16 终端在线情况 0x01：掉线，0x00：在线
                        sensorData.setNum(wrap.getShort(23));//17 终端入网总数
                        sensorData.setSensorDataLength(wrap.getShort(25));// 18 数据长度
                        byte[] dataBytes2=null;
                        if(payload.length>=69) {
                            dataBytes2 = Arrays.copyOfRange(payload, 27, payload.length);
                        }
                        sensorData.setSensorData(dataBytes2);
                        sensorData.notifyObservers();
//                        byte[] dataBytes = new byte[wrap.getShort(25)];
//                        if(payload.length>=69) {
//                            byte[] dataBytes = Arrays.copyOfRange(payload, 27, payload.length);
//                            //电压
//                            Integer VD00 = sensorProperty.computeVoltage(dataBytes[0], dataBytes[1], dataBytes[2]);
//                            Integer VD01 = sensorProperty.computeVoltage(dataBytes[3], dataBytes[4], dataBytes[5]);
//                            Integer VD10 = sensorProperty.computeVoltage(dataBytes[6], dataBytes[7], dataBytes[8]);
//                            Integer VD11 = sensorProperty.computeVoltage(dataBytes[9], dataBytes[10], dataBytes[11]);
////                           //激光点的xy轴坐标
//                            Double Px = sensorProperty.computeSupersonic(dataBytes[12], dataBytes[13], dataBytes[14]);
//                            Double Py = sensorProperty.computeSupersonic(dataBytes[15], dataBytes[16], dataBytes[17]);
////                          //超声波测距
//                            Double Distance = sensorProperty.computeDistance(dataBytes[18], dataBytes[19]);
//
////                        Integer DataL = sensorProperty.computeTENAXIS(dataBytes[20], dataBytes[21]);
//                           //加速度xyz
//                        List AccelerationList = sensorProperty.computeAcceleration(dataBytes[20], dataBytes[21], dataBytes[22], dataBytes[23], dataBytes[24], dataBytes[25]);
//////                       //角速度xyz
//                        List AngularVelocityList = sensorProperty.computeAngularVelocity(dataBytes[26], dataBytes[27], dataBytes[28], dataBytes[29], dataBytes[30], dataBytes[31]);
////                        //角度
//                        List AngularList = sensorProperty.computeAngular(dataBytes[32], dataBytes[33], dataBytes[34], dataBytes[35], dataBytes[36], dataBytes[37]);
////                        //高度
//                            double hight= sensorProperty.computeHight(dataBytes[38], dataBytes[39], dataBytes[40], dataBytes[41]);
//////                        tt.setSensorData(dataBytes.toString());
//                            zullProperty.setJiedianid(wrap.getInt(6));
//                            zullProperty.setTime(tt.getTime());
//                            zullProperty.setVoltage1(VD00);
//                            zullProperty.setVoltage2(VD01);
//                            zullProperty.setVoltage3(VD10);
//                            zullProperty.setVoltage4(VD11);
//                            zullProperty.setXSupersonic(Px);
//                            zullProperty.setYSupersonic(Py);
//                            zullProperty.setDistance(Distance);
//                            zullProperty.setAcceleration(AccelerationList);
//                            zullProperty.setAngularVelocity(AngularVelocityList);
//                            zullProperty.setAngular(AngularList);
//                            zullProperty.setHight(hight);
//
//                          log.info("getload"+new String(message.getPayload())+"id"+message.getId()+message.getQos());
//
//                           result=new Result(200,"success","sensor_init",zullProperty);
//                            String jsonzullProperty = JSONObject.toJSONString(result);
//                           textWebSocketFrameHandler.send(jsonzullProperty);
//
//                            return;
//
//                        }
                    log.info("getload"+new String(message.getPayload())+"id"+message.getId()+message.getQos());

                  }


                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }

                public void connectionLost(Throwable throwable) {
                }
            });


        } catch (MqttException me) {
            System.out.println("reason" + me.getReasonCode());
            System.out.println("msg" + me.getMessage());
            System.out.println("loc" + me.getLocalizedMessage());
            System.out.println("cause" + me.getCause());
            System.out.println("excep" + me);
            me.printStackTrace();
        }
    }

}
