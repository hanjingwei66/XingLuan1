package com.shuojie.utils.nettyUtil;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginCheckUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
    public static void Sensor(Channel channel) {
        channel.attr(Attributes.SENSOR).set(true);
    }
    public static void noSensor(Channel channel) {
        channel.attr(Attributes.SENSOR).set(null);
    }
    public static boolean hasSensor(Channel channel) {
        Attribute<Boolean> SensorAttr = channel.attr(Attributes.SENSOR);

        return SensorAttr.get() != null;
    }
}
