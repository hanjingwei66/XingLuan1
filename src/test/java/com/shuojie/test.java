package com.shuojie;

import com.shuojie.domain.User;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

//
//import jdk.internal.org.objectweb.asm.tree.analysis.Value;
//import org.junit.Test;
//
//import java.security.Key;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.BiConsumer;
//import java.util.function.Consumer;
//import java.util.function.Supplier;
//
 public class test {
    public static void main(String[] args) throws ParseException {
        Date data=new Date();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time="2018-09-29 16:39:00";
        Date date = format.parse("2019-10-30 14:09:35");
        //日期转时间戳（毫秒）
        long time1=date.getTime()/1000;

        System.out.print("Format To times:"+time1);

    }


}
class Piao implements  Runnable{

    @Override
    public void run() {
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("1",new User());
    }
}

 class ThreadTest {
    public static void main(String[] args) {
        Piao piao =new Piao();
        Thread thread=new Thread(piao);
        thread.start();
    }
}