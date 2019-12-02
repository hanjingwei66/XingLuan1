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
}
