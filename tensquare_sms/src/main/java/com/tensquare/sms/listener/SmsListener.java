package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.rabbitmq.client.impl.Environment;
import com.tensquare.sms.util.SmsUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信监听类
 * 交给spring容器管理
 * 声明是rabbit的监听类
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    /**
     * 发送短信
     * 实际逻辑中是调用阿里短信接口发送短信
     */
    @RabbitHandler
    public void sendSms(Map<String,String> message){
        System.out.println("手机号为："+message.get("mobile"));
        System.out.println("验证码为："+message.get("code"));
        String mobile = message.get("mobile");
        String code = message.get("code");
        //使用阿里短信工具类发送短信
        try {
            smsUtils.sendSms(mobile, code);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
