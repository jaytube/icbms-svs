package com.icbms.core.service.deviceinfo.impl;

import com.icbms.common.util.Utils;
import com.icbms.core.service.deviceinfo.DeviceSwitchInfoService;
import com.icbms.repository.dao.deviceinfo.DeviceSwitchInfoDao;
import com.icbms.repository.domain.deviceinfo.DeviceSwitchInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("deviceSwitchInfoService")
public class DeviceSwitchInfoServiceImpl implements DeviceSwitchInfoService {
	@Autowired
	private DeviceSwitchInfoDao deviceSwitchInfoDao;
	
	@Override
	public DeviceSwitchInfoEntity queryObject(String id){
		return deviceSwitchInfoDao.queryObject(id);
	}
	
	@Override
	public List<DeviceSwitchInfoEntity> queryList(Map<String, Object> map){
		return deviceSwitchInfoDao.queryList(map);
	}

    @Override
    public List<DeviceSwitchInfoEntity> queryListByBean(DeviceSwitchInfoEntity entity) {
        return deviceSwitchInfoDao.queryListByBean(entity);
    }
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return deviceSwitchInfoDao.queryTotal(map);
	}
	
	@Override
	public int save(DeviceSwitchInfoEntity deviceSwitchInfo){
        deviceSwitchInfo.setId(Utils.uuid());
		return deviceSwitchInfoDao.save(deviceSwitchInfo);
	}
	
	@Override
	public int update(DeviceSwitchInfoEntity deviceSwitchInfo){
        return deviceSwitchInfoDao.update(deviceSwitchInfo);
	}
	
	@Override
	public int delete(String id){
        return deviceSwitchInfoDao.delete(id);
	}
	
	@Override
	public int deleteBatch(String[] ids){
        return deviceSwitchInfoDao.deleteBatch(ids);
	}
	
}
