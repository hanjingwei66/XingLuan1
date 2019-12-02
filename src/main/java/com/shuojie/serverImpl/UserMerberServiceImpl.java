package com.shuojie.serverImpl;


import com.shuojie.service.RedisService;
import com.shuojie.service.UserMerberService;
import com.shuojie.utils.sms.MessageUtil;
import com.shuojie.utils.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("UserMerberServiceImpl")
public class UserMerberServiceImpl implements UserMerberService {
    /***
     * 注入redis模版
     */
    @Autowired
    private RedisService redisService;
    @Autowired
    private MessageUtil messageUitl;
    /****
     * 注入配置文件数据
     */
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    //过期时间60秒
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;


//    @Override
//    public String register(String PhoneNumbers, String authCode) {
//        /****
//         * 手机号和注册码前台用户发送
//         */
//        //首先比对验证码是否失效
//        String redisauthcode= redisService.get(REDIS_KEY_PREFIX_AUTH_CODE+PhoneNumbers);
//        if(StringUtils.isEmpty(redisauthcode)){
//            //如果未取到则过期
//            return "验证码已失效";
//        }
//        if(!"".equals(redisauthcode)&&!authCode.equals(redisauthcode)){
//            return "验证码错误";
//        }
//        return  "用户登录成功";
//    }


    @Override
    public Result sendMsg(String PhoneNumbers) {
        Result msg= messageUitl.SendMessage(PhoneNumbers);
        System.out.println(PhoneNumbers);
        return msg;
    }



}
