package com.galaxyt.normae.uaa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.galaxyt.normae.api.uaa.dto.RegisterDto;
import com.galaxyt.normae.core.enums.Deleted;
import com.galaxyt.normae.core.exception.GlobalExceptionCode;
import com.galaxyt.normae.core.wrapper.GlobalResponseWrapper;
import com.galaxyt.normae.uaa.dao.AuthorityDao;
import com.galaxyt.normae.uaa.dao.RoleDao;
import com.galaxyt.normae.uaa.dao.UserDao;
import com.galaxyt.normae.uaa.enums.Disabled;
import com.galaxyt.normae.uaa.exception.AccountDisabledException;
import com.galaxyt.normae.uaa.exception.UsernameNotFoundException;
import com.galaxyt.normae.uaa.exception.WrongPasswordException;
import com.galaxyt.normae.uaa.pojo.bo.UserDetails;
import com.galaxyt.normae.uaa.pojo.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户业务层
 * @author zhouqi
 * @date 2020/5/21 10:58
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/21 10:58     zhouqi          v1.0.0           Created
 *
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AuthorityDao authorityDao;


    /**
     * 系统超级管理员角色名称
     */
    @Value("${system.administrator.roleName}")
    private String systemAdministratorRoleName;

    /**
     * 系统超级管理员角色标识
     */
    @Value("${system.administrator.roleMark}")
    private String systemAdministratorRoleMark;

    /**
     * 初始化系统管理员帐号
     */
    @Value("${system.administrator.username}")
    private String systemAdministratorUsername;



    /**
     * 根据用户名和密码获取用户角色权限详情
     * @param username  用户名
     * @param password  密码 , 不论加密未加密 , 若密码正确 , 该密码应该与数据库中的密码一致 , 密码可为空字符串 , 但不能为 null , 若为空字符串则判定为无密码登录 , 则不会再进行密码验证
     * @return
     */
    public UserDetails load(String username, String password) throws AccountDisabledException, UsernameNotFoundException, WrongPasswordException {

        //根据用户名查询用户对象 , 此对象拥有逻辑删除功能 , 若进行过逻辑删除查询不出来
        User user = this.userDao.selectOne(new QueryWrapper<User>()
                .eq("username", username));

        //将查询出来的用户对象包装成 Optional 对象
        Optional<User> userO = Optional.ofNullable(user);

        //若用户不存在则抛出异常
        userO.orElseThrow(UsernameNotFoundException::new);

        //检查用户是否已被禁用
        if (user.getDisabled() == Disabled.TRUE) {
            throw new AccountDisabledException();
        }

        //先判断传入的密码是否为空字符串,若为空字符串则判定为无密码登录,不再进行密码验证
        //否则会进行密码验证
        if (!"".equals(password)) {
            //检查密码是否正确
            if (!user.getPassword().equals(password)) {
                throw new WrongPasswordException();
            }
        }

        //查询当前登录用户下未被禁用且未被删除的角色列表
        List<String> roles =  null;
        //查询当前登录用户下未被禁用且未被删除的权限列表
        List<String> authorities = null;

        if (username.equals(this.systemAdministratorUsername)) {    //超级管理员帐号默认的角色和全部的权限
            roles = Arrays.asList(this.systemAdministratorRoleMark);
            authorities = this.authorityDao.selectAll();
        } else {    //普通用户从数据库中查询出其拥有的角色和权限
            roles = this.roleDao.selectByUserId(user.getId(), Disabled.FALSE, Deleted.FALSE);
            authorities = this.authorityDao.selectByUserId(user.getId(), Disabled.FALSE, Deleted.FALSE);
        }

        //将角色和权限去重并放入集合中
        roles = roles.parallelStream().distinct().collect(Collectors.toList());
        authorities = authorities.parallelStream().distinct().collect(Collectors.toList());

        //封装成 UserDetails
        UserDetails userDetails = new UserDetails(user.getId(), username, roles, authorities);

        return userDetails;
    }

    /**
     * 注册功能
     * @param registerDto
     * @return
     */
    @Transactional
    public GlobalResponseWrapper register(RegisterDto registerDto) {

        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setCreateUserId(0L);
        user.setUpdateUserId(0L);
        user.setDisabled(Disabled.FALSE);
        user.setDeleted(Deleted.FALSE.getCode());

        try {
            this.userDao.insert(user);
            return new GlobalResponseWrapper();
        } catch (Exception e) { //唯一索引约束 , 用户名已存在
            return new GlobalResponseWrapper(GlobalExceptionCode.USERNAME_ALREADY_EXISTS);
        }

    }




}
