package com.shuojie.nettyService.Handler;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.Model;
import com.shuojie.domain.maps.CurrentInfo;
import com.shuojie.domain.maps.CurrentLine;
import com.shuojie.domain.maps.MapPoints;
import com.shuojie.domain.maps.Origin;
import com.shuojie.service.ModelService;
import com.shuojie.service.mapsService.CurrentInfoService;
import com.shuojie.service.mapsService.CurrentLineService;
import com.shuojie.service.mapsService.MapPointsService;
import com.shuojie.service.mapsService.OriginService;
import com.shuojie.utils.vo.Result;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
@ChannelHandler.Sharable
public class MapsWebSocketFrameHandle extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static MapsWebSocketFrameHandle mapsWebSocketFrameHandle;

    @Autowired
    private CurrentInfoService currentService;

    @Autowired
    private CurrentLineService currentLineService;

    @Autowired
    private MapPointsService mapPointsService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private OriginService originService;

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private ConcurrentMap<Object, Channel> serverChannels = PlatformDependent.newConcurrentHashMap();

    public ConcurrentMap<Object, Channel> getServerChannels() {
        return serverChannels;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        channels.add(ctx.channel());
//        ByteBuf buf = ctx.alloc().directBuffer();//从 channel 获取 ByteBufAllocator 然后分配一个 ByteBuf
        JSONObject json = JSONObject.parseObject(msg.text().toString());//json字符串转json对象
        String command = json.getString("command");

        CurrentInfo current = new CurrentInfo();
        CurrentLine currentLine = new CurrentLine();
        MapPoints mapPoints = new MapPoints();
        Model model = new Model();

        if (!command.substring(0, 4).equals("maps")) {
//            buf.retain();//检查引用计数器是否是 1
            msg.retain();
            ctx.fireChannelRead(msg);
        }

        switch (command) {

            //添加当前线路line_name,clid,current_shijian
            case "maps_insertCurrentInfo":
                current.setCurrentShijian(json.getString("currentShijian"));
                current.setLineName(json.getString("lineName"));
                Result cur = currentService.insertCurrentInfo(current);
                String insertCurrentInfoResponse = JSONObject.toJSONString(cur);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(insertCurrentInfoResponse));
                break;

            //添加当前线路经纬度
            case "maps_insertCurrentLine":
                currentLine.setCuLiLongitude(json.getDouble("cuLiLongitude"));
                currentLine.setCuLiLatitude(json.getDouble("cuLiLatitude"));
                currentLine.setCuid(Integer.valueOf(json.getString("cuid")));
                Result curLin = currentLineService.insertCurrentLine(currentLine);
                String insertCurrentLineResponse = JSONObject.toJSONString(curLin);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(insertCurrentLineResponse));
                break;

            //添加数据空间地图初始点
            case "maps_insertMapPoints":
                mapPoints.setMaLongitude(json.getDouble("maLongitude"));
                mapPoints.setMaLatitude(json.getDouble("maLatitude"));
                mapPoints.setPointsName(json.getString("pointsName"));
                Result map = mapPointsService.insertMapPoints(mapPoints);
                String insertMapPointsResponse = JSONObject.toJSONString(map);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(insertMapPointsResponse));
                break;

            //线路方案切换
            case "maps_getOriginLine":
                Result originLine = originService.getOriginLine();
                String originLineResponse = JSONObject.toJSONString(originLine);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(originLineResponse));
                break;

                //添加模型信息
            case "maps_insertModel":
                try {
                    model.setModelName(json.getString("modelName"));
                    model.setMoLatitude(json.getDouble("moLatitude"));
                    model.setMoLongitude(json.getDouble("moLongitude"));
                    model.setDirection(json.getString("direction"));
                    Result mod = modelService.insertModel(model);
                    String insertModelResponse = JSONObject.toJSONString(mod);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(insertModelResponse));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            //查询全部模型信息
            case"maps_getModel":
                Result modelList = modelService.getModel();
                String getModelReponse = JSONObject.toJSONString(modelList);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(getModelReponse));
                break;
        }
    }
}
