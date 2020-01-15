package com.shuojie.nettyService.Handler;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuojie.dao.sensorMappers.DistanceSensorMapper;
import com.shuojie.domain.sensorModle.*;
import com.shuojie.domain.system.Session;
import com.shuojie.mqttClient.Mqttclien;
import com.shuojie.mqttClient.PubMsg;
//import com.shuojie.mqttClient.SubMsg;
import com.shuojie.serverImpl.sensorServiceImpl.DistanceSensorImpl;
import com.shuojie.serverImpl.sensorServiceImpl.LaserSensorImpl;
import com.shuojie.serverImpl.sensorServiceImpl.SensorData;
import com.shuojie.serverImpl.sensorServiceImpl.SensorEventImpl;
import com.shuojie.service.sensorService.LaserSensorService;
import com.shuojie.service.sensorService.Observer;
import com.shuojie.service.sensorService.SensorEventService;
import com.shuojie.service.sensorService.SensorService;
import com.shuojie.utils.nettyUtil.LoginCheckUtil;
import com.shuojie.utils.nettyUtil.SessionUtil;
import com.shuojie.utils.snowFlake.SnowFlake;
import com.shuojie.utils.vo.Result;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*传感器handler*/
@Component("SensorHandler")
@ChannelHandler.Sharable
public class SensorHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //    private static RedisService redisService;
//    static {
//        redisService = SpringUtil.getBean(RedisService.class);
//    }
    private boolean flag;
    //    @Autowired
//    private RedisService redisService;
    @Resource
    private DistanceSensorMapper distanceSensorMapper;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private LaserSensorService laserSensorService;
    @Autowired
    private SensorEventService sensorEventService;
    //订阅
    @Autowired
    private Mqttclien mqttclien;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    //过期时间60秒
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    DistanceSensorImpl distanceSensor;
    LaserSensorImpl laserSensor;
    SensorData sensorData;
    private Map<Integer, SensorData> sensorMap ;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        sensorData = mqttclien.getPoint();
//        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
//        String ip = inetSocketAddress.getHostName();
//        System.out.println("收到id为"+ctx.channel().id().asLongText()+"ip为"+ip+"发来的消息1："+msg.text());
        JSONObject json = JSONObject.parseObject(msg.text().toString());//json字符串转json对象
        String command = json.getString("command");
        ReferenceCountUtil.release(msg);
        switch (command) {
            case "sensor_check"://检测数量 假数据
                if(LoginCheckUtil.hasLogin(ctx.channel())){
                    List<BaseSensor> list = new ArrayList();
                    for(Map.Entry<Integer, SensorData> entry : sensorMap.entrySet()){
                        Integer mapKey = entry.getKey();
                        SensorData mapValue = entry.getValue();
                        BaseSensor s = new BaseSensor();
                        s.setSensorId(mapKey.longValue());
                        Integer power = 80 + ((int) (Math.random() * 20));
                        Integer sesorType=0;
                        s.setPower(power);
                        if(mapKey==88853){
                            sesorType=0;
                        }
                        if(mapKey==88682){
                            sesorType=1;
                        }
                        s.setSensorType(sesorType);
                        s.setSensorName("sensor"+mapKey);
                        s.setSignal(4);
                        s.setStatus(0);
                        list.add(s);
                    }


//                for (int i = 0; i < sensorMap.size(); i++) {
//                    Integer id = (int) (Math.random() * 100);
//                    Integer sesorType=0;
//                    if(i<3){
//                        sesorType=0;
//                    }else {
//                        sesorType=1;
//                    }
//                    //Integer sesorType = ((int) (Math.random() * 4));
//                    Integer power = 80 + ((int) (Math.random() * 20));
//                    Integer status = 0;
//                    Integer signal = 2 + (int) (Math.random() * 3);
//                    BaseSensor s = new BaseSensor();
//                    s.setSensorId(SnowFlake.nextId());
//                    s.setPower(power);
////                    s.setSensorName("Sesor" + id);
//                    s.setSensorType(sesorType);
//                    s.setStatus(status);
//                    s.setSignal(signal);
//                    s.setSensorName("sensor"+id);
//                    list.add(s);
//                }
                Map map = new HashMap();
                map.put("list", list);
                map.put("command", "sensor_check");
                String sesorList = JSONObject.toJSONString(map);
                ctx.writeAndFlush(new TextWebSocketFrame(sesorList));
//                System.out.println("buf.refCnt()" + buf.refCnt());
                }
                return;
            case "sensor_allow"://检测
                if (flag) {

                }
//                String redisauthcode= redisService.get("portal:authCode:"+"demo/topics");//REDIS_KEY_PREFIX_AUTH_CODE
//                ctx.channel().writeAndFlush(new TextWebSocketFrame(redisauthcode));
//                System.out.println("value"+redisauthcode);
                break;
            case "sensor_start":
//                REDIS_KEY_PREFIX_AUTH_CODE
//                redisService.set( "portal:authCode:"+"123", ctx);

//                new Timer("testTimer").schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        String rediscode= redisService.get("portal:authCode:"+"demo/topics");
//                        ctx.channel().writeAndFlush(new TextWebSocketFrame(rediscode));
                System.out.println("123");
//                    }
//                }, 100,100);

                break;

            case "sensor_init_distance":
                //网关ID+设备ID+数据长度+要发送的数据
              //  2595fcd0是网关ID，00015b6c是设备ID，07为数据长度，ff fe为识别码，没实际意义，后面五个数据分别代表
            //激光器是否使用（01开00关）+超声波测距是否使用（01开00关）+十轴是否使用（01开00关）+
                // 上传间隔时间（计算方式为=0a*100ms=1s）+放置方式（00设备水平放置，01设备垂直放置）。

                byte []bytes4={0x25,(byte) 0x95,(byte) 0xfc,(byte) 0xd0,
                        0x00,0x01,0x5b,0x15
                        ,0x07, (byte)0xff, (byte) 0xfe,
                        0x00,0x01,0x01,0x0a,0x00};

                PubMsg.publish(bytes4,"client-id-20","demo/test");
//                PubMsg.publish(bytes6,"client-id-0","demo/test");
                break ;
            case "sensor_init_laser":
                byte []bytes6={0x25,(byte) 0x95,(byte) 0xfc,(byte) 0xd0,
                        0x00,0x01,0x5a,0x6a
                        ,0x07, (byte)0xff, (byte) 0xfe,
                        0x01,0x00,0x01,0x0a,0x00};
                PubMsg.publish(bytes6,"client-id-23","demo/test");
                break;
                //测距传感器

            case "sensor_distance":
                LoginCheckUtil.Sensor(ctx.channel());//发送传感器数据
                this.distanceSensor=new DistanceSensorImpl(sensorData);

                break;
                //激光传感器
            case "sensor_laser":
                LoginCheckUtil.Sensor(ctx.channel());//发送传感器数据
                this.laserSensor=new LaserSensorImpl(sensorData);

                break;
                //移除测距传感器订阅
            case "sensor_distance_remove":
                 this.distanceSensor.remove();
                    break;
                //移除激光传感器订阅
            case "sensor_laser_remove":
                this.laserSensor.remove();
                break;
            case "sensor_clear":
                sensorData.clearObserver();
                break;
                //移除
            case "sensor_sub":
                mqttclien.mqttTopic="demo/test";
                mqttclien.start();
//              subMsg.runsub("client-id-0","demo/test1");
                break;
            case "sensor_remove_sub":
                mqttclien.mqttTopic="nosub";
                mqttclien.start();
//              subMsg.runsub("client-id-0","demo/test1");
                break;
            case "sensor_test":
                System.out.println("112");

                byte [] bytes5={0x01,0x03,0x25,(byte)0x95,(byte)0xFC,(byte)0xD0,0x00,0x01,0x5B
                        ,0x15,0x00,0x04,0x02,0x0D,0x15,0x00,
                        0x00,0x00,0x5D,(byte)0x9E,(byte)0xD3,
                        (byte)0x85,0x00,0x00,0x0A,0x00,0x2A,0x30,0x30,0x35,0x30,0x31,0x33,0x30,
                        0x30,0x35,0x30,0x31,0x32
                    ,0x33,0x31,0x39,0x32,0x36,0x31,0x00,0x00,(byte)0xD0,(byte)0xFF,(byte)0xF9,
                        0x07,(byte)0xD3,(byte)0xFF,0x00,0x00,0x00,0x00,0x00,0x00,0x02,0x00,
                        (byte)0xF6,(byte)0xFF,0x0B
                    ,(byte)0xFE,0x54,0x00,0x00,0x00};
                PubMsg.publish(bytes5,"client-id-8","demo/test");
                System.out.println("1");
                break;
            case "sensor_test1":
                System.out.println("112");

                byte [] bytes7={0x01,0x03,0x25,(byte)0x95,(byte)0xFC,(byte)0xD0,0x00,0x01,0x5a
                        ,0x6a,0x00,0x04,0x02,0x0D,0x15,0x00,
                        0x00,0x00,0x5D,(byte)0x9E,(byte)0xD3,
                        (byte)0x85,0x00,0x00,0x0A,0x00,0x2A,0x30,0x30,0x35,0x30,0x31,0x33,0x30,
                        0x30,0x35,0x30,0x31,0x32
                        ,0x33,0x31,0x39,0x32,0x36,0x31,0x00,0x00,(byte)0xD0,(byte)0xFF,(byte)0xF9,
                        0x07,(byte)0xD3,(byte)0xFF,0x00,0x00,0x00,0x00,0x00,0x00,0x02,0x00,
                        (byte)0xF6,(byte)0xFF,0x0B
                        ,(byte)0xFE,0x54,0x00,0x00,0x00};
                PubMsg.publish(bytes7,"client-id-9","demo/test");
                System.out.println("1");
                break;
                //传感器开始录制和结束录制记录
            case "sensor_event_insert":
                //{"command":"sensor_event_insert","userId":"1","startTime":"1","endTime":"1","sensorEventName":"1"}
                SensorEvent sensorEvent=new SensorEvent();
                Long userId = Long.parseLong(json.getString("userId"));
                String startTime = json.getString("startTime");
                String endTime = json.getString("endTime");
                String sensorEventName = json.getString("sensorEventName");
                Long sensorId= Long.parseLong(json.getString("sensorId"));
                sensorEvent.setUserId(userId);
                sensorEvent.setStartTime(startTime);
                sensorEvent.setEndTime(endTime);
                sensorEvent.setSensorEventName(sensorEventName);
                sensorEvent.setSensorId(sensorId);
                sensorEventService.insert(sensorEvent);
                Result result1 = new Result(200, "SUCCESS", "sensor_event_insert");
                String enventList1 = JSONObject.toJSONString(result1);
                ctx.writeAndFlush(new TextWebSocketFrame(enventList1));
                break;
            case"sensor_event_start":
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formatData = df.format(new Date());
                SensorEvent sensorEvent1=new SensorEvent();
                Long userId1 = Long.parseLong(json.getString("userId"));
                String startTime1 =formatData;
//                String endTime1 = "";
                String sensorEventName1 = json.getString("sensorEventName");
//                Long sensorId1= Long.parseLong(json.getString("sensorId"));
                sensorEvent1.setUserId(userId1);
                sensorEvent1.setStartTime(startTime1);
//                sensorEvent1.setEndTime(endTime1);
                sensorEvent1.setSensorEventName(sensorEventName1);
//                sensorEvent1.setSensorId(sensorId1);
                sensorEventService.insert(sensorEvent1);
                Result resultstart = new Result(200, "SUCCESS", "sensor_event_start");
                String enventListstart = JSONObject.toJSONString(resultstart);
                ctx.writeAndFlush(new TextWebSocketFrame(enventListstart));
                break;
            case"sensor_event_end":
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formatData1 = df1.format(new Date());
                SensorEvent sensorEvent2=new SensorEvent();
                Long userId2 = Long.parseLong(json.getString("userId"));
                //String startTime2 =formatData1;
                String endTime2 = formatData1;
                String sensorEventName2 = json.getString("sensorEventName");
//                Long sensorId2= Long.parseLong(json.getString("sensorId"));
                sensorEvent2.setUserId(userId2);
                //sensorEvent2.setStartTime(startTime2);
                sensorEvent2.setEndTime(endTime2);
                sensorEvent2.setSensorEventName(sensorEventName2);
//                sensorEvent2.setSensorId(sensorId2);
                sensorEventService.update(sensorEvent2);
                Result resultEnd = new Result(200, "SUCCESS", "sensor_event_end");
                String enventListEnd = JSONObject.toJSONString(resultEnd);
                ctx.writeAndFlush(new TextWebSocketFrame(enventListEnd));
                break;
                //查找传感器事件集合
            case "sensor_findList":
                //{"command":"sensor_findList","userId":"2"}
                Long userId4 = Long.parseLong(json.getString("userId"));
                List<SensorEvent> list = sensorEventService.findList(userId4);
                list = sensorEventService.findList(userId4);
                Result result = new Result(200, "SUCCESS", "sensor_findList", list);
                String enventList = JSONObject.toJSONString(result);
                ctx.writeAndFlush(new TextWebSocketFrame(enventList));
                break;
                //查找远程监控集合
            case "sensor_now_findList":
                //{"command":"sensor_findList","userId":"2"}
                Long userid3 = Long.parseLong(json.getString("userId"));
                List<SensorEvent> nowlist;
                if(userid3==1L){
                    nowlist = sensorEventService.findAll();
                    List arrayList=new ArrayList();
                    for(SensorEvent sensor :nowlist){
                        String endTim=sensor.getEndTime();
                        if(StringUtils.isEmpty(endTim)){
                            arrayList.add(sensor);
                        }
                    }
                    Result nowresult = new Result(200, "SUCCESS", "sensor_now_findList", arrayList);
                    String nowenventList = JSONObject.toJSONString(nowresult);
                    ctx.writeAndFlush(new TextWebSocketFrame(nowenventList));
                }else {
                    nowlist = sensorEventService.findList(userid3);
                    Result nowresult = new Result(200, "SUCCESS", "sensor_now_findList", nowlist);
                    String nowenventList = JSONObject.toJSONString(nowresult);
                    ctx.writeAndFlush(new TextWebSocketFrame(nowenventList));
                }

                break;
                //查找事件的详细信息
            case  "sensor_findLog_byTime":
            //{"command":"sensor_findLog_byTime","startTime":"2019-10-30 14:09:36","endTime":"2019-10-30 14:09:40","currentPage":"1","pageSize":"10"}
                String selectStartTime = json.getString("startTime");
                String selectEndTime = json.getString("endTime");
                long currentPage = Long.parseLong( json.getString("currentPage"));
                long pageSize = Long.parseLong(json.getString("pageSize"));
                Long sensorId3= Long.parseLong(json.getString("sensorId"));
                try {
                if(sensorId3==88853) {
                    Page page = new Page<ZullProperty>(currentPage, pageSize);
                    Result sensorlist = sensorService.selectPage(page, selectStartTime, selectEndTime);
                    String sesorList = JSONObject.toJSONString(sensorlist);
                    ctx.writeAndFlush(new TextWebSocketFrame(sesorList));
                }
                if(sensorId3==88682){
                    Page page = new Page<LaserSensor>(currentPage, pageSize);
                    Result sensorlist = laserSensorService.selectPage(page, selectStartTime, selectEndTime);
                    String sesorList = JSONObject.toJSONString(sensorlist);
                    ctx.writeAndFlush(new TextWebSocketFrame(sesorList));
                }
                }catch (Exception e){
                    System.out.println(e);
                }
                break;
        }

    }
    //从订阅信息处获得传感器列表
public void checkSensor(Map sensorMap){
        this.sensorMap=sensorMap;
}
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        sensorData.clearObserver();
        ctx.close();
        ctx.channel().close();
    }
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
