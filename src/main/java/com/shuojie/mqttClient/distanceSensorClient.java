//package com.shuojie.mqttClient;
//
//
//import com.shuojie.dao.sensorMappers.DistanceSensorMapper;
//import com.shuojie.domain.sensorModle.DistanceSensor;
//import com.shuojie.nettyService.Handler.TextWebSocketFrameHandler;
//import com.shuojie.service.RedisService;
//import com.shuojie.utils.snowFlake.SnowFlake;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelId;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.paho.client.mqttv3.*;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.text.MessageFormat;
//
//@Slf4j
//@Component
//public class distanceSensorClient  {
//    @Autowired
//    private RedisService redisService;
//    @Autowired
//    private TextWebSocketFrameHandler textWebSocketFrameHandler;
//    @Resource
//    private DistanceSensorMapper distanceSensorMapper;
//    //    @Autowired
////    private TextWebSocketFrameHandler text;
//    @Value("${redis.key.prefix.authCode}")
//    private String REDIS_KEY_PREFIX_AUTH_CODE;
//    //过期时间60秒
//    @Value("${redis.key.expire.authCode}")
//    private Long AUTH_CODE_EXPIRE_SECONDS;
//
//    @PostConstruct
//    public void start() throws Exception {
//        String broker = "tcp://localhost:1883";
//        String clientId = "JavaSample1";
//        //Use the memory persistence
//        MemoryPersistence persistence = new MemoryPersistence();
//
//        try {
//            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
//            MqttConnectOptions connOpts = new MqttConnectOptions();
//            connOpts.setCleanSession(true);
//            System.out.println("Connecting to broker:" + broker);
//            sampleClient.connect(connOpts);
//            System.out.println("Connected");
//
//
//            String topic = "api/distanceSensor";
//            System.out.println("Subscribe to topic:" + topic);
//            sampleClient.subscribe(topic);
//            sampleClient.setCallback(new MqttCallback() {
//                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    String theMsg = MessageFormat.format("{0} is arrived for topic {1}.", new String(message.getPayload()), topic);
////                    redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + topic,new String(message.getPayload()));
////                    redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + );
//                    System.out.println("getload"+new String(message.getPayload()));
//                    log.info("getload"+new String(message.getPayload())+"id"+message.getId()+message.getQos());
////            ConcurrentMap<Object, Channel> serverChannels = textWebSocketFrameHandler.getServerChannels();
////            Channel s = serverChannels.get("s");
////            s.writeAndFlush(new TextWebSocketFrame(theMsg));
//
//                    ChannelGroup channels= textWebSocketFrameHandler.channels;
////                    Channel incoming = ctx.channel();
////                    channels.add(ctx.channel());
////                    textWebSocketFrameHandler.
////                    if(){
////
////                    }
//                    DistanceSensor sensor =new DistanceSensor();
//                    sensor.setStartTime();
//                    for (Channel channel : channels) {
//                        ChannelId id = channel.id();
//                        Channel channel1 = channels.find(id);
//                        channel.writeAndFlush(new TextWebSocketFrame( new String(message.getPayload())));
//                    }
//
//                    sensor.setSensorId( SnowFlake.nextId());
//                    sensor.setDistance(12.122);
//                    sensor.setPower(99);
//                    sensor.setSensorType(0);
//                    sensor.setSignal(1);
//                    sensor.setEndTime();
//                    distanceSensorMapper.insert(sensor);
//
//                }
//
//                public void deliveryComplete(IMqttDeliveryToken token) {
//                }
//
//                public void connectionLost(Throwable throwable) {
//                }
//            });
//
//
//            String content = "服务器发布信息";
//            int qos = 2;
//            System.out.println("Publishing message:" + content);
//            MqttMessage message = new MqttMessage(content.getBytes());
//            message.setQos(qos);
//            sampleClient.publish(topic, message);
//            System.out.println("Message published");
//
//        } catch (MqttException me) {
//            System.out.println("reason" + me.getReasonCode());
//            System.out.println("msg" + me.getMessage());
//            System.out.println("loc" + me.getLocalizedMessage());
//            System.out.println("cause" + me.getCause());
//            System.out.println("excep" + me);
//            me.printStackTrace();
//        }
//    }
//}
