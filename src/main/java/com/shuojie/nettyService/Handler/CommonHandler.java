package com.shuojie.nettyService.Handler;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.system.PictureList;
import com.shuojie.service.sysService.PictureListService;
import com.shuojie.utils.addressUtil.AddressUtils;
import com.shuojie.utils.vo.Result;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.List;

@Component
@ChannelHandler.Sharable
public class CommonHandler extends SimpleChannelInboundHandler<JSONObject> {
    @Autowired
    private PictureListService pictureListService;
    @Autowired
    RestTemplate restTemplate;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JSONObject json) throws Exception {
        //JSONObject json = JSONObject.parseObject(msg.text());//json字符串转json对象
        String command = json.getString("command");
        ByteBuf buf = ctx.alloc().directBuffer();
//        try {
//            if (!command.substring(0, 6).equals("common")) {
////                buf.retain();//检查引用计数器是否是 1
//                msg.retain();
//                ctx.fireChannelRead(msg);
//            }
//        } finally {
////            buf.release();
//        }
        switch (command) {
            case "common_pictureList"://图例集
                Integer type = json.getInteger("type");
                String jsonString =json.toString();
                PictureList pictureList = JSONObject.parseObject(jsonString,PictureList.class);
                List<PictureList> pictureLists = pictureListService.selectList(pictureList);
                Result result=new Result (200,"SUCCESS","common_pictureList",pictureLists);
                result.setType(type);
                String jsonPictureLists = JSONObject.toJSONString(result);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(jsonPictureLists));
                break;
            case "common_getAddress":
//                InetSocketAddress socketAddress= (InetSocketAddress)ctx.channel().remoteAddress();
//                System.out.println("收到" + ctx.channel().id().asLongText() + "ip"+socketAddress.getAddress().getHostAddress());
//                AddressUtils addressUtils = new AddressUtils();
//                String ip = "47.98.193.195";
//                String address = "";
//                try {
//                    address = addressUtils.getAddresses("ip=" + ip, "utf-8");
//                }catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                JSONObject jsonObject= JSONObject.parseObject(address);
//
//                jsonObject = jsonObject.getJSONObject("data");
//                //通过相应的get方法,获取相应的属性
//
//                String city = jsonObject.getString("city");//城市
//
//                System.out.println("解析得到的地址为："+ city + "市,");
                String urlStr = "http://ip.taobao.com/service/getIpInfo.php?ip="+"47.98.193.195";
//                ResponseEntity<String> response = restTemplate.getForEntity(urlStr, String.class);
//                System.out.println(response);
                break;
            case "common_weather"://天气
                InetSocketAddress socketAddress= (InetSocketAddress)ctx.channel().remoteAddress();
                System.out.println("收到" + ctx.channel().id().asLongText() + "ip"+socketAddress.getAddress().getHostAddress());
                AddressUtils addressUtils = new AddressUtils();
//                String ip = "47.98.193.195";
                String ip =socketAddress.getAddress().getHostAddress();
                String address = "";
                try {
                    address = addressUtils.getAddresses("ip=" + ip, "utf-8");
                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject= JSONObject.parseObject(address);

                jsonObject = jsonObject.getJSONObject("data");
                //通过相应的get方法,获取相应的属性

                String city = jsonObject.getString("city");//城市

                System.out.println("解析得到的地址为："+ city + "市,");
                String apiURL="";
                if(city.equals("内网IP")){
                    apiURL = "http://wthrcdn.etouch.cn/weather_mini?city=" + "杭州市";
                }else{
                    apiURL = "http://wthrcdn.etouch.cn/weather_mini?city=" + city;
                }

                ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiURL, String.class);
                Object parse = JSONObject.parse(responseEntity.getBody());
                Result result1 =new Result(200,"SUCCESS","common_weather",parse);
                String Respone = JSONObject.toJSONString(result1);

                ctx.channel().writeAndFlush(new TextWebSocketFrame(Respone));
                System.out.println(responseEntity.getBody());
                break;
        }
    }
}
