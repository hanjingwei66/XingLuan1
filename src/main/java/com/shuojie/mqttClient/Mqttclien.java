package com.shuojie.mqttClient;


import com.alibaba.fastjson.JSONObject;
import com.shuojie.dao.sensorMappers.SensorMapper;
import com.shuojie.domain.sensorModle.SensorTitle;
import com.shuojie.domain.sensorModle.ZullProperty;
import com.shuojie.nettyService.Handler.SensorHandler;
import com.shuojie.nettyService.Handler.TextWebSocketFrameHandler;
import com.shuojie.serverImpl.sensorServiceImpl.SensorData;
import com.shuojie.service.sensorService.SensorAsyncService;
import com.shuojie.service.sensorService.SensorProperty;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.vo.Result;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    private static final Map<Integer, SensorData> sensorMap = new ConcurrentHashMap<>();
    public String mqttTopic="123";
    Result result=null;
    SensorData sensorData = new SensorData();

    public  SensorData getPoint(){
        return this.sensorData;
    }
//    @PostConstruct
    public void start() throws Exception {
        SensorHandler sensorHandler = (SensorHandler) SpringUtil.getBean("SensorHandler");
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
            Map hashMap = new HashMap<>();
            sampleClient.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    String theMsg = MessageFormat.format("{0} is arrived for topic {1}.", new String(message.getPayload()), topic);
//                    redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + topic,new String(message.getPayload()));
//                    redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + );
//                    System.out.println(theMsg);
//                    System.out.println("getload"+new String(message.getPayload()));
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
                        sensorMap.put(wrap.getInt(6),sensorData);
                        sensorHandler.checkSensor(sensorMap);
                        sensorData.notifyObservers();

//                    log.info("getload"+new String(message.getPayload())+"id"+message.getId()+message.getQos());

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
