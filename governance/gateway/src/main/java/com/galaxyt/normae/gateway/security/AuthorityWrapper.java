package com.galaxyt.normae.gateway.security;


import lombok.Data;

/**
 * 用于封装一个请求的基本信息
 * @author zhouqi
 * @date 2020/6/9 17:02
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/6/9 17:02     zhouqi          v1.0.0           Created
 *
 */
@Data
public class AuthorityWrapper {

    /**
     * 请求地址
     */
    private String url;

    /**
     * 类型 , 如 GET POST PUT 等
     */
    private String method;

    /**
     * 唯一标识 , 同一个项目中(即同一个微服务)中应唯一
     */
    private String mark;

    /**
     * 该权限的名称 , 开发人员提供该名称时应尽可能的体现业务性
     */
    private String name;

    /**
     * 该接口是否需要登录才能够访问
     */
    private boolean login;

    /**
     * 该接口是否需要拥有该权限才能够访问
     * 仅当 isLogin = true 时该属性有效
     */
    private boolean haveAuthority;

}
