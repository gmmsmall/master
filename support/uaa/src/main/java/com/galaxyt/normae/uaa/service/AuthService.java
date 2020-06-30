package com.galaxyt.normae.uaa.service;

import com.galaxyt.normae.core.exception.GlobalExceptionCode;
import com.galaxyt.normae.core.util.seurity.JWTUtil;
import com.galaxyt.normae.core.wrapper.GlobalResponseWrapper;
import com.galaxyt.normae.uaa.exception.AccountDisabledException;
import com.galaxyt.normae.uaa.exception.UsernameNotFoundException;
import com.galaxyt.normae.uaa.exception.WrongPasswordException;
import com.galaxyt.normae.uaa.pojo.bo.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权业务层
 * @author zhouqi
 * @date 2020/5/21 11:05
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/21 11:05     zhouqi          v1.0.0           Created
 *
 */
@Service
public class AuthService {


    @Autowired
    private UserService userService;

    /**
     * JWT 的签名
     */
    @Value("${jwt.sign}")
    private String jwtSign;

    /**
     * JWT 的过期时间
     */
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    /**
     * 根据用户名获取 jwt 令牌
     * @param username  用户名
     * @param password  密码 , 不论加密未加密 , 若密码正确 , 该密码应该与数据库中的密码一致 , 密码可为空字符串 , 但不能为 null , 若为空字符串则判定为无密码登录 , 则不会再进行密码验证
     * @return
     */
    public GlobalResponseWrapper auth(String username, String password) {

        UserDetails userDetails = null;

        try {
            userDetails = this.userService.load(username, password);
        } catch (AccountDisabledException e) {  //帐号被禁用
            return new GlobalResponseWrapper(GlobalExceptionCode.ACCOUNT_IS_DISABLED);
        } catch (UsernameNotFoundException e) { //用户名不存在
            return new GlobalResponseWrapper(GlobalExceptionCode.USERNAME_IS_NOT_FOUNT);
        } catch (WrongPasswordException e) {    //密码输入错误
            return new GlobalResponseWrapper(GlobalExceptionCode.PASSWORD_IS_WRONG);
        }

        //生成 token
        String jwt = JWTUtil.INSTANCE.generate(String.valueOf(userDetails.getUserId()), userDetails.getUsername(), userDetails.getRoles(), userDetails.getAuthorities(), username, this.jwtSign, this.jwtExpiration);
        return new GlobalResponseWrapper().data(jwt);

    }


    public static void main(String[] args) {


        String userId = "111";
        String username = "test";

        List<String> roles = new ArrayList<>();

        List<String> authorities = new ArrayList<>();

        //authorities.add("check");
        authorities.add("captcha");

        authorities.add("token");
        authorities.add("register");


        String jwt = JWTUtil.INSTANCE.generate(userId, username, roles, authorities, username, "normae", 7200000L);

        System.out.println(jwt);
    }




}
