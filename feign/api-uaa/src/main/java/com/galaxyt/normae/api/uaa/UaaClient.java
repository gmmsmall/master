package com.galaxyt.normae.api.uaa;

import com.galaxyt.normae.api.uaa.dto.RegisterDto;
import com.galaxyt.normae.core.wrapper.GlobalResponseWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * UAA 服务 Feign 客户端
 * @author zhouqi
 * @date 2020/5/28 14:18
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/28 14:18     zhouqi          v1.0.0           Created
 *
 */
@Primary
@FeignClient(value = "uaa", fallbackFactory = UaaFallback.class)
public interface UaaClient {


    /**
     * 根据用户名和密码获取用户的 JWT 令牌
     *
     * @param username 用户名
     * @param password 密码  加密后的 , 请求之后 token 方法不会对该密码进行加密 , 不可为 null , 但允许为 "" , 若为空字符串则代表本次请求为无密码登录 , 可用于短信验证码登录等方式
     * @return
     */
    @GetMapping("/auth/token")
    GlobalResponseWrapper authToken(@RequestParam("username") String username,
                                    @RequestParam(value = "password", required = false) String password);


    /**
     * 注册方法
     * 需要调用方自行验证帐号密码的合法性
     * @param registerDto
     * @return
     */
    @PostMapping("/register")
    GlobalResponseWrapper register(@RequestBody RegisterDto registerDto);



}
