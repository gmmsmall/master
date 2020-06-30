package com.galaxyt.normae.sms.web;

import com.galaxyt.normae.core.exception.GlobalException;
import com.galaxyt.normae.core.exception.GlobalExceptionCode;
import com.galaxyt.normae.core.util.seurity.RegexUtil;
import com.galaxyt.normae.core.wrapper.GlobalResponseWrapper;
import com.galaxyt.normae.security.provider.Authority;
import com.galaxyt.normae.sms.enums.CaptchaType;
import com.galaxyt.normae.sms.enums.MessageType;
import com.galaxyt.normae.sms.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息
 * @author zhouqi
 * @date 2020/5/29 10:01
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/29 10:01     zhouqi          v1.0.0           Created
 *
 */
@RestController
public class MessageController {


    @Autowired
    private CaptchaService captchaService;


    /**
     * 发送短信验证码
     *
     * @return
     */
    @Authority(mark = "captcha",name = "获取验证码",isLogin = false)
    @GetMapping("captcha")
    public GlobalResponseWrapper captcha(@RequestParam("phoneNumber") String phoneNumber,
                                         @RequestParam("captchaType") Integer captchaType) {

        //检查手机号是否合法
        this.checkPhoneNumber(phoneNumber);

        //检查短信验证码类型是否合法
        CaptchaType.getByCode(captchaType);

        //发送短信验证码
        return this.captchaService.send(phoneNumber, MessageType.getByCode(captchaType));
    }


    /**
     * 手机号检查
     * @param phoneNumber
     */
    private void checkPhoneNumber(String phoneNumber) {

        if (!RegexUtil.phoneNumber(phoneNumber)) {
            throw new GlobalException(GlobalExceptionCode.PHONE_NUMBER_FORMAT_WRONG);
        }

    }

}
