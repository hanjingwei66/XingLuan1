package com.shuojie.nettyService;

import com.shuojie.utils.autowiredUtil.SpringUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

//websocket长连接示例
@Component
public class MyServer {
    @Autowired
    private WebSocketChannelInitializer webSocketChannelInitializer;
    private Logger logger = LoggerFactory.getLogger(MyServer.class);
    //配置服务端NIO线程组
    private final EventLoopGroup parentGroup = new NioEventLoopGroup(); //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
    private final EventLoopGroup childGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture bing(InetSocketAddress address) {
        WebSocketChannelInitializer webSocketChannelInitializer =(WebSocketChannelInitializer) SpringUtil.getBean("WebSocketChannelInitializer");
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)    //非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(webSocketChannelInitializer);

            channelFuture = b.bind(address).syncUninterruptibly();
            channel = channelFuture.channel();
//            //定时调度任务
//            channel.eventLoop().scheduleAtFixedRate(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("心跳连接");
//                }
//            },6, 6, TimeUnit.SECONDS);
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(!channelFuture.isSuccess()){
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                logger.info("Netty正在启动。。。 ");
            } else {
                logger.error("itstac k-demo-netty server start error. ");
            }
        }
        return channelFuture;
    }

    public void destroy() {
        if (null == channel) return;
        channel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }


//    public void start() throws Exception {
//
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup wokerGroup = new NioEventLoopGroup();
//
//        try {
//            ServerBootstrap serverBootstrap = new ServerBootstrap();
//            serverBootstrap.group(bossGroup, wokerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
////                    .option(ChannelOption.TCP_NODELAY, Boolean.valueOf(true))
////                    .option(ChannelOption.SO_KEEPALIVE,true)
//                    .handler(new LoggingHandler(LogLevel.TRACE))//TRACE
//                    .childHandler(webSocketChannelInitializer);
//            ChannelFuture channelFuture = serverBootstrap
//                    .bind(new InetSocketAddress("",80)).sync();
//            System.out.println("启动成功");
////            channelFuture.channel().closeFuture().sync();
//
//        } finally {
//            System.out.println("关闭");
////            bossGroup.shutdownGracefully();
////            wokerGroup.shutdownGracefully();
//
//        }
//
//    }

}
