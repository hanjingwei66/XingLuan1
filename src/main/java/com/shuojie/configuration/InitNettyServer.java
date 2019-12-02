package com.shuojie.configuration;

import com.shuojie.nettyService.MyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
@Order(value = 2)
public class InitNettyServer implements CommandLineRunner {
    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private int port;
    @Autowired
    private MyServer myServer;

    @Override
    public void run(String... args) throws Exception {
//       myServer.start();
        InetSocketAddress address = new InetSocketAddress(host, port);
        ChannelFuture channelFuture = myServer.bing(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> myServer.destroy()));
       channelFuture.channel().closeFuture().sync();
       System.out.println("Netty 服务端启动成功");
    }
}
