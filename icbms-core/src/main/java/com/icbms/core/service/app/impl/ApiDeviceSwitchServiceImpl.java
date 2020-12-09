package com.icbms.core.service.app.impl;

import com.icbms.core.service.app.ApiDeviceSwichService;
import com.icbms.repository.dao.deviceinfo.DeviceSwitchInfoDao;
import com.icbms.repository.domain.deviceinfo.DeviceSwitchInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("deviceSwitchApiService")
public class ApiDeviceSwitchServiceImpl implements ApiDeviceSwichService {
	@Autowired
	private DeviceSwitchInfoDao switchInfodao;
	
	@Override
	public DeviceSwitchInfoEntity queryObject(String id) {
		return null;
	}

	@Override
	public List<DeviceSwitchInfoEntity> queryList(Map<String, Object> map) {
		return null;
	}

	@Override
	public List<DeviceSwitchInfoEntity> queryListByBean(DeviceSwitchInfoEntity entity) {
		return null;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return 0;
	}

	@Override
	public int save(DeviceSwitchInfoEntity deviceSwitchInfo) {
		return 0;
	}

	@Override
	public int update(DeviceSwitchInfoEntity deviceSwitchInfo) {
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

}
