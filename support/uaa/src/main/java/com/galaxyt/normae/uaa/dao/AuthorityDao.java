package com.galaxyt.normae.uaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.galaxyt.normae.core.enums.Deleted;
import com.galaxyt.normae.uaa.enums.Disabled;
import com.galaxyt.normae.uaa.pojo.po.Authority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限 dao
 * @author zhouqi
 * @date 2020/5/20 10:28
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/20 10:28     zhouqi          v1.0.0           Created
 *
 */
@Repository
public interface AuthorityDao extends BaseMapper<Authority> {

    /**
     * 查询全部的权限列表
     * @return
     */
    @Select(" SELECT a.mark FROM t_authority a ")
    List<String> selectAll();

    /**
     * 根据用户 id 查询该用户全部的权限
     * @param userId
     * @param disabled
     * @param deleted
     * @return
     */
    @Select(" SELECT a.mark FROM t_user_role ur LEFT JOIN t_role_authority ra ON ur.role_id = ra.role_id LEFT JOIN t_authority a ON ra.authority_id = a.id WHERE ur.user_id = #{userId} AND a.disabled = #{disabled.code} AND a.deleted = #{deleted.code} ")
    List<String> selectByUserId(@Param("userId") Long userId, @Param("disabled") Disabled disabled, @Param("deleted") Deleted deleted);

}
