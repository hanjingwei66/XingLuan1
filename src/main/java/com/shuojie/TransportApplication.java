package com.shuojie;



import org.mybatis.spring.annotation.MapperScan;
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

//@EnableJpaAuditing
//@EnableScheduling
@SpringBootApplication
@MapperScan("com.shuojie.dao")
//@ComponentScan("com.shuojie")
//@ServletComponentScan
//@EnableAsync
public class TransportApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(TransportApplication.class,args);

    }
    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }


  /*  @Override
    public void run (String ...args)throws Exception{
        new MyServer();
    // new Mqttclien();implements CommandLineRunner
}*/
}
