package com.galaxyt.normae.uaa.pojo.bo;

import lombok.Data;

import java.util.List;

/**
 * 用户角色权限封装对象
 * @author zhouqi
 * @date 2020/5/21 11:07
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/21 11:07     zhouqi          v1.0.0           Created
 *
 */
@Data
public class UserDetails {

    public UserDetails(Long userId, String username, List<String> roles, List<String> authorities) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.authorities = authorities;
    }

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户所拥有的角色
     */
    private List<String> roles;

    /**
     * 用户所拥有的权限
     */
    private List<String> authorities;

}
