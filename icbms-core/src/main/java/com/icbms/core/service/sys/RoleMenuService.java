package com.icbms.core.service.sys;

import java.util.List;
import java.util.Map;

/**
 * 权限角色表
 * 
 * @author chenshun
 * @email rui.sun.java@gmail.com
 * @date 2017-05-03 10:07:59
 */
public interface RoleMenuService {

	
	List<String> queryListByRoleId(String id);

	void save(Map<String, Object> map);
	
	void delete(String roleId);
	
	void deleteBatch(String[] roleIds);
}
