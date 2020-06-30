package com.galaxyt.normae.uaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.galaxyt.normae.uaa.pojo.po.UserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关联表 dao
 * @author zhouqi
 * @date 2020/5/20 16:26
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/20 16:26     zhouqi          v1.0.0           Created
 *
 */
@Repository
public interface UserRoleDao extends BaseMapper<UserRole> {
}
