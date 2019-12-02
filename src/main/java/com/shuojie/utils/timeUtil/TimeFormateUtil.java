package com.shuojie.utils.timeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormateUtil {

    public static Long timeToStamp(String time){
        long timeStamp;
        try {
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(time);
            //日期转时间戳（毫秒）
             timeStamp=date.getTime()/1000;
            return timeStamp;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public static String StampToTime(long timeStamp){
        String time;
        try {
            if(timeStamp>9999999999L){
                 time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeStamp);
            }else {
                 time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp * 1000));
            }
            return time;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
