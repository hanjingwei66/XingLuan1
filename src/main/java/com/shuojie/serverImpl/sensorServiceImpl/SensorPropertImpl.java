package com.shuojie.serverImpl.sensorServiceImpl;

import com.shuojie.service.sensorService.SensorProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 此类得到传感器字节数据进行转换
 */
@Component("SensorProperty")
public class SensorPropertImpl implements SensorProperty {

    @Override
    public Integer computeVoltage(byte a, byte b, byte c) {
        Integer voltage=((char)a-'0')*256+((char)b-'0')*16+((char)c-'0');
        return voltage;
    }

    @Override
    public Double computeSupersonic(byte a, byte b, byte c) {
        Double result = (((char) a - '0') * 256 + ((char) b - '0') * 16 + ((char) c - '0')) / 10.0;
        return result;
    }

    @Override
    public Double computeDistance(byte a, byte b) {
        Double result = ((a& 0xFF)<<8|(b & 0xFF))/10.0;
        return result;
    }

    @Override
        public double[] computeAcceleration(byte axl, byte axh, byte ayl, byte ayh, byte azl, byte azh) {
        short [] b ={ 0xF9, (short)ayh};
//        byte [] b1={(byte) 0xF9, 0x07};
        short saxh=(short)(axh& 0xFF);
        short sayh=(short)(ayh& 0xFF);
        short sazh=(short)(azh& 0xFF);
       Double x= ((short)(saxh<<8)|axl & 0xFF)/32768.00*16;
       Double y= ((short)(sayh<<8)|ayl & 0xFF)/32768.00*16;
//        Double y=((float)0xF907)/32768.00*16;
       Double z= ((short)(sazh<<8)|azl & 0xFF)/32768.00 *16;
        double[] array={x,y,z};

        return array;
    }

    @Override
    public double[] computeAngularVelocity(byte wxl, byte wxh, byte wyl, byte wyh, byte wzl, byte wzh) {
        short swxh=(short)(wxh& 0xFF);
        short swyh=(short)(wyh& 0xFF);
        short swzh=(short)(wzh& 0xFF);
        Double x= ((short)(swxh<<8)|wxl & 0xFF)/32768.00*2000;
        Double y= ((short)(swyh<<8)|wyl & 0xFF)/32768.00*2000;
        Double z= ((short)(swzh<<8)|wzl & 0xFF)/32768.00*2000;
        double[] array={x,y,z};
        return array;
    }

    @Override
    public double[] computeAngular(byte RollL, byte RollH, byte PitchL, byte PitchH, byte YawL, byte YawH) {
        short sRollH=(short)(RollH& 0xFF);
        short sPitchH=(short)(PitchH& 0xFF);
        short sYawH=(short)(YawH& 0xFF);
        Double x= ((short)(sRollH<<8)|RollL& 0xFF)/32768.00*180;
        Double y= ((short)(sPitchH<<8)|PitchL& 0xFF)/32768.00*180;
        Double z= ((short)(sYawH<<8)|YawL& 0xFF)/32768.00*180;
        double[] array={x,y,z};
        return array;
    }

    @Override
    public  double computeHight(byte h0, byte h1, byte h2, byte h3) {
        double height = ((short) h3 << 24) | ((short) h2 << 16) | ((short) h1 << 8) | h0;

        return height;
    }


}
