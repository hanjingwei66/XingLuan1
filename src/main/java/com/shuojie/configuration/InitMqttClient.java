package com.shuojie.configuration;

import com.shuojie.mqttClient.Mqttclien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class InitMqttClient implements CommandLineRunner {
    @Autowired
    private Mqttclien  mqttclien;
    @Override
    public void run(String... args) throws Exception {
//        mqttclien.start();
//        System.out.println("MQTT客户端启动成功");
    }
}
