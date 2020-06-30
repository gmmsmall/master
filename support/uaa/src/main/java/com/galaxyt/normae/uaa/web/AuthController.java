package com.galaxyt.normae.uaa.web;

import com.galaxyt.normae.core.annotation.NotWrapper;
import com.galaxyt.normae.core.wrapper.GlobalResponseWrapper;
import com.galaxyt.normae.security.provider.Authority;
import com.galaxyt.normae.uaa.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 授权 Controller
 * @author zhouqi
 * @date 2020/5/21 10:57
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/21 10:57     zhouqi          v1.0.0           Created
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 根据用户名和密码获取用户的 JWT 令牌
     *
     * @param username 用户名
     * @param password 密码  加密后的 , 请求之后 token 方法不会对该密码进行加密 , 不可为 null , 但允许为 "" , 若为空字符串则代表本次请求为无密码登录 , 可用于短信验证码登录等方式
     * @return
     */
    //@Authority(mark = "token",name = "登录授权")
    @NotWrapper
    @GetMapping("token")
    public GlobalResponseWrapper token(@RequestParam("username") String username,
                                       @RequestParam(value = "password", required = false) String password) {
        return this.authService.auth(username, password);
    }

}
