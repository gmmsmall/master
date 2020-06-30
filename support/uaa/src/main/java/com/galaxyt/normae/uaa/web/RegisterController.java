package com.galaxyt.normae.uaa.web;

import com.galaxyt.normae.api.uaa.dto.RegisterDto;
import com.galaxyt.normae.core.annotation.NotWrapper;
import com.galaxyt.normae.core.wrapper.GlobalResponseWrapper;
import com.galaxyt.normae.security.provider.Authority;
import com.galaxyt.normae.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册服务
 * @author zhouqi
 * @date 2020/5/28 14:43
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/28 14:43     zhouqi          v1.0.0           Created
 *
 */
@RestController
@RequestMapping("register")
public class RegisterController {


    @Autowired
    private UserService userService;


    /**
     * 注册方法
     * 需要调用方自行验证帐号密码的合法性
     * @param registerDto
     */
    //@Authority(mark = "register",name = "注册方法")
    @NotWrapper
    @PostMapping
    public GlobalResponseWrapper register(@RequestBody RegisterDto registerDto) {
        return this.userService.register(registerDto);
    }

}
