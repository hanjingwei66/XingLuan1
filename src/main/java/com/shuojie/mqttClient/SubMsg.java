//package com.shuojie.mqttClient;
//
//import com.shuojie.dao.sensorMappers.SensorMapper;
//import com.shuojie.domain.sensorModle.ZullProperty;
//import com.shuojie.nettyService.Handler.TextWebSocketFrameHandler;
//import com.shuojie.service.sensorService.SensorAsyncService;
//import com.shuojie.service.sensorService.SensorProperty;
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.paho.client.mqttv3.*;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class SubMsg {
//
////	 private static String topic = "$share/testgroup/wyptest1";
////	 private static String topic = "$queue/wyptest1";
////	 private static String topic = "wyptest1";
//     private static int qos = 2;
//     private static String broker = "tcp://47.98.193.195:1883";
//     private static String userName = "suojie";
//     private static String passWord = "123456";
//
//    @Autowired
//    private static SensorProperty sensorProperty;
//    @Autowired
//    private SensorMapper sensorMapper;
//    //    @Autowired
////    private RedisService redisService;
//    @Autowired
//    private static TextWebSocketFrameHandler textWebSocketFrameHandler;
//    @Autowired
//    private SensorAsyncService asyncService;
////    @Resource
////    private DistanceSensorMapper distanceSensorMapper;
//
//    //    @Autowired
////    private TextWebSocketFrameHandler text;
//    @Value("${redis.key.prefix.authCode}")
//    private String REDIS_KEY_PREFIX_AUTH_CODE;
//    //过期时间60秒
//    @Value("${redis.key.expire.authCode}")
//    private Long AUTH_CODE_EXPIRE_SECONDS;
//
//    private static MqttClient connect(String clientId) throws MqttException{
//
//    	 MemoryPersistence persistence = new MemoryPersistence();
//    	 MqttConnectOptions connOpts = new MqttConnectOptions();
////    	 String[] uris = {"tcp://10.100.124.206:1883","tcp://10.100.124.206:1883"};
//    	 connOpts.setCleanSession(false);
//         connOpts.setUserName(userName);
//         connOpts.setPassword(passWord.toCharArray());
//         connOpts.setConnectionTimeout(10);
//         connOpts.setKeepAliveInterval(20);
////         connOpts.setServerURIs(uris);
////         connOpts.setWill(topic, "close".getBytes(), 2, true);
//         MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
//         mqttClient.connect(connOpts);
//    	 return mqttClient;
//     }
//
//     public static void sub(MqttClient mqttClient,String topic) throws MqttException{
//         int[] Qos  = {qos};
//         String[] topics = {topic};
//         mqttClient.subscribe(topics, Qos);
//     }
//
//
//    public static void runsub(String clientId, String topic) throws MqttException{
//    	MqttClient mqttClient = connect(clientId);
//        ZullProperty zullProperty=new ZullProperty();
//    	if(mqttClient != null){
//			sub(mqttClient,topic);
//    	}
//    	MqttMessageListener1 mqttMessageListener1=new MqttMessageListener1();
//        mqttClient.subscribe(topic,2, mqttMessageListener1);
//
//    }
//    public static void main(String[] args) throws MqttException{
//
//        runsub("client-id-1", "demo/test");//"$share/testgroupa/edge/server/private/+"
//    }
//}