package com.icbms.repository.dao.sys;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.sys.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户角色关系表
 * 
 * @author chenshun
 * @email rui.sun.java@gmail.com
 * @date 2017-05-03 10:07:59
 */
@Mapper
public interface UserRoleDao extends BaseDao<UserRoleEntity> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<String> queryRoleIdList(String userId);

    /**
     * 根据用户list批量删除用户角色中间表
     * @param users
     * @return
     */
    int deleteBatchByUserId(String[] users);

    /**
     * 根据角色list批量删除用户角色中间表
     * @param roles
     * @return
     */
    int deleteBatchByRoleId(String[] roles);

	
}
