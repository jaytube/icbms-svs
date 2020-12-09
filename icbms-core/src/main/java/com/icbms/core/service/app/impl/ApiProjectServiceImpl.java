package com.icbms.core.service.app.impl;

import com.icbms.core.service.app.ApiProjectService;
import com.icbms.repository.dao.projectinfo.ProjectInfoDao;
import com.icbms.repository.domain.projectinfo.ProjectInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("projectApiService")
public class ApiProjectServiceImpl implements ApiProjectService {

	@Autowired
	private ProjectInfoDao projectInfoDao;

	@Override
	public ProjectInfoEntity queryObject(String id) {
		return null;
	}

	@Override
	public List<ProjectInfoEntity> queryList(Map<String, Object> map) {
		return null;
	}

	@Override
	public List<ProjectInfoEntity> queryListByBean(ProjectInfoEntity entity) {
		return null;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return 0;
	}

	@Override
	public int save(ProjectInfoEntity projectInfo) {
		return 0;
	}

	@Override
	public int update(ProjectInfoEntity projectInfo) {
		return 0;
	}

	@Override
	public int delete(String id) {
		return 0;
	}

	@Override
	public int deleteBatch(String[] ids) {
		return 0;
	}

	@Override
	public List<ProjectInfoEntity> queryListAll() {
		return projectInfoDao.queryListAll();
	}

	@Override
	public List<String> queryRoleIdList(String projectId) {
		// TODO Auto-generated method stub
		return null;
	}

}
