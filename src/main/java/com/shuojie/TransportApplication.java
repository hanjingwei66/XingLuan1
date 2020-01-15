package com.shuojie;



import com.shuojie.nettyService.MyServer;
import io.netty.channel.ChannelFuture;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.servlet.MultipartConfigElement;
import java.net.InetSocketAddress;

//@EnableJpaAuditing
//@EnableScheduling
@SpringBootApplication
@MapperScan("com.shuojie.dao")
//@ComponentScan("com.shuojie")
//@ServletComponentScan
//@EnableAsync
public class TransportApplication extends SpringBootServletInitializer implements CommandLineRunner {
    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private int port;
    @Autowired
    private MyServer myServer;
    public static void main(String[] args) {
        SpringApplication.run(TransportApplication.class,args);

    }
    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
    @Override
    public void run(String... args) throws Exception {
//        myServer.bing(host,port);
        InetSocketAddress address = new InetSocketAddress(host, port);
        ChannelFuture channelFuture = myServer.bing(address);
        System.out.println("Netty 服务端启动成功");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> myServer.destroy()));
        channelFuture.channel().closeFuture().sync();

    }

  /*  @Override
    public void run (String ...args)throws Exception{
        new MyServer();
    // new Mqttclien();implements CommandLineRunner
}*/
}
