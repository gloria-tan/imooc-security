package com.billwen.learning.imooc.imoocsecurity.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.billwen.learning.imooc.imoocsecurity.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

// mooc.sms-provider.name=ali
@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "mooc.sms-provider", name="name", havingValue = "ali")
public class AliSmsService implements SmsService{

    private final AppProperties appProperties;
    /*
    private final IAcsClient client;
    */

    @Override
    public void send(String mobile, String msg) {
        var request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(appProperties.getSmsProvider().getApiUrl());
        request.setSysAction("SendSms");
        request.setSysVersion("2017-05-25");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "登录验证");
        request.putQueryParameter("TemplateCode", "SMS_1610048");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + msg + "\",\"product\":\"慕课网实战Spring Security\"}");

        try {
            // var response = client.getCommonResponse(request);
            // log.info("短信发送结果 {}", response.getData());
            log.info("发送给 {}, 内容 {}", mobile, msg);
        }
        finally {
            log.info("短信发送完成");
        }
    }
}
