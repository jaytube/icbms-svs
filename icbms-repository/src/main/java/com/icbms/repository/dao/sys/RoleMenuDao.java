package com.icbms.repository.dao.sys;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 权限角色表
 * 
 * @author chenshun
 * @email rui.sun.java@gmail.com
 * @date 2017-05-03 10:07:59
 */
@Mapper
public interface RoleMenuDao {
    int delete(Map<String, Object> map);

    int deleteBatch(Object[] ids);

    int delete(Object id);

    void save(Map<String, Object> map);

    List<String> queryListByRoleId(String roleId);
}
