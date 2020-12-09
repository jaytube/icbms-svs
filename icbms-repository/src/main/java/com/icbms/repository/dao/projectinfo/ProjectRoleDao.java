package com.icbms.repository.dao.projectinfo;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.projectinfo.ProjectRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 项目角色关系表
 * 
 * @author hxy
 * @email rui.sun.java@gmail.com
 * @date 2019-10-15 22:59:42
 */
@Mapper
public interface ProjectRoleDao extends BaseDao<ProjectRoleEntity> {

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<String> queryRoleIdList(String projectId);

}
