package com.shuojie.utils.sms;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.shuojie.service.RedisService;
import com.shuojie.utils.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MessageUtil {
    @Autowired
    private RedisService redisService;
    /****
     * 注入配置文件数据
     */
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    //过期时间60秒
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;



    /**
     * @Description //验证阿里云开发者身份
     **/
//    DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI41s7UdycZniy", "HngXYiuYNWWPfBjCbHNQZuwhqtwDjQ");
    DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIbKdu2rgAi7It", "2CYGQ7FjYzyzmb1BVeelzGrQq4PoJu");
    IAcsClient client = new DefaultAcsClient(profile);
    Result res;
    public Result SendMessage(String PhoneNumbers) {
        System.out.println(PhoneNumbers);
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            sb.append(1+random.nextInt(9));
        }
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        request.putQueryParameter("SignName", "hangqingmumu");
        request.putQueryParameter("TemplateCode", "SMS_172207324");
        request.putQueryParameter("TemplateParam", "{code:"+sb.toString()+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
          String returnstr = response.getData();
          JSONObject returnjsonstr = JSONObject.parseObject(returnstr);
          String aaa = returnjsonstr.getString("Message");
          System.out.println(returnjsonstr.getString("Message"));
          //key电话和value 验证码 存入redis 设置过期时间
          if(aaa.equals("OK")) {
            redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + PhoneNumbers, sb.toString());
            redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + PhoneNumbers, AUTH_CODE_EXPIRE_SECONDS);
              res= new Result(200,returnjsonstr.getString("Message"),"sendMsg");
          }else{
              res= new Result(203,returnjsonstr.getString("Message"),"sendMsg");
          }


            return res;
//          return aaa;
        } catch (ServerException e) {
            e.printStackTrace();

        } catch (ClientException e) {
            e.printStackTrace();

        }
            res.setCode(400);
            res.setMessage("系统故障");
        return res;
    };


}
