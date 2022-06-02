package com.billwen.learning.imooc.uaa.service;

import cn.leancloud.sms.AVSMS;
import cn.leancloud.sms.AVSMSOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "mooc.sms-provider", name = "name", havingValue = "leancloud")
public class LeanCloudSmsService implements SmsService{
    @Override
    public void send(String mobile, String msg) {
        var option = new AVSMSOption();
        option.setTtl(10);
        option.setApplicationName("慕课网实战Application Security");
        option.setOperation("两步验证");
        option.setTemplateName("登录验证");
        option.setSignatureName("慕课网");
        option.setType(AVSMS.TYPE.TEXT_SMS);
        option.setEnvMap(Map.of("smsCode", msg));

        /*
        AVSMS.requestSMSCodeInBackground(mobile, option)
                .take(1)
                .subscribe(
                        (res) -> log.info("短信发送成功 {}", res),
                        (err) -> log.error("发送短信时产生服务端异常 {}", err.getLocalizedMessage())
                );
        */
        log.info("发送给 {}, 内容 {}", mobile, msg);
    }
}
