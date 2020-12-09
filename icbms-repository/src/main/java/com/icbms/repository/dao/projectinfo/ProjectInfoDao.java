package com.icbms.repository.dao.projectinfo;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.projectinfo.ProjectInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 项目基础表; InnoDB free: 401408 kB
 * 
 * @author Raymond
 * @email rui.sun.java@gmail.com
 * @date 2018-03-13 15:14:33
 */
@Mapper
public interface ProjectInfoDao extends BaseDao<ProjectInfoEntity> {
	
	public List<ProjectInfoEntity> queryListAll();
}
