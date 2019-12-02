package com.shuojie.nettyService;

import com.shuojie.nettyService.Handler.*;
import com.shuojie.utils.ssl.SslUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.FileInputStream;
import java.security.KeyStore;

@Component("WebSocketChannelInitializer")
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;
    @Autowired
    private SelectHandler selectHandler;
    @Autowired
    private AuthHandler authHandler;
//    @Autowired
//    private MapsWebSocketFrameHandle mapsWebSocketFrameHandle;
    @Autowired
    private BinaryWebSocketFrameHandler binaryWebSocketFrameHandler;
//    @Autowired
//    private CommonHandler commonHandler;

//    private final SslContext context;
//
//    public  WebSocketChannelInitializer(SslContext context){
//        this.context=context;
//}
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        SelfSignedCertificate cert = new SelfSignedCertificate();
//        SSLContext context = SslUtil.createSSLContext("JKS" ,"D:/workSpace/shuojie/gornix.jks" ,"123456");
//
//        SslContext context = SslContext.newServerContext(cert.certificate(), cert.privateKey());
//        SSLEngine sslEngine = context.createSSLEngine();
//        sslEngine.setUseClientMode(false); /// 是否使用客户端模式
//        sslEngine.setNeedClientAuth(false); ////是否需要验证客户端



        SelfSignedCertificate cert = new SelfSignedCertificate();
        SslContext context = SslContext.newServerContext(cert.certificate(), cert.privateKey());
        SSLEngine engine = context.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        engine.setNeedClientAuth(false);
//        pipeline.addLast("SslHandler",new SslHandler(engine));
        //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
        pipeline.addLast(new HttpServerCodec());
        //mqtt 协议的编码解码器
        pipeline.addLast(new MqttDecoder());
        pipeline.addLast(MqttEncoder.INSTANCE);
        //以块的方式来写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
        pipeline.addLast(new HttpObjectAggregator(8192));
        // webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketFrameAggregator(10 * 1024 * 1024));
        //ws://server:port/context_path
        //ws://localhost:9999/ws
        //参数指的是contex_path
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, 10485760));
//        pipeline.addLast(new ObjectEncoder());
//        pipeline.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));

        //websocket定义了传递数据的6中frame类型
//        pipeline.addLast(new com.shuojie.nettyService.Handler.SensorHandler());
        pipeline.addLast("TextWebSocketFrameHandler", textWebSocketFrameHandler);
//        pipeline.addLast("AuthHandler", authHandler);
        pipeline.addLast("selectHandler",selectHandler);
//        pipeline.addLast(new FileUploadServerHandler());
        pipeline.addLast("BinaryWebSocketFrameHandler", binaryWebSocketFrameHandler);
//        pipeline.addLast("CommonHandler", commonHandler);
//        new StringDecoder();
        //测试git0281470051500F24474E524D432C3032353934382E30302C412C333431302E33333136332C4E2C31303834382E35383833312C452C302E3035392C2C3135313031392C2C2C442A36380D0A1303020102004857A203

    }

}
