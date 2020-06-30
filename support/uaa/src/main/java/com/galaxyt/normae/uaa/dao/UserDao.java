package com.galaxyt.normae.uaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.galaxyt.normae.uaa.pojo.po.User;
import org.springframework.stereotype.Repository;

/**
 * 用户 dao
 * @author zhouqi
 * @date 2020/5/20 10:27
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/20 10:27     zhouqi          v1.0.0           Created
 *
 */
@Repository
public interface UserDao extends BaseMapper<User> {
}
