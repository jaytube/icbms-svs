package com.icbms.core.service.devicelog;

import com.icbms.common.util.PageInfo;
import com.icbms.repository.domain.devicelog.DeviceSwitchInfoLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 空开设备日志表; InnoDB free: 398336 kB
 * 
 * @author hxy
 * @email rui.sun.java@gmail.com
 * @date 2018-05-15 21:44:00
 */
public interface DeviceSwitchInfoLogService {

	DeviceSwitchInfoLogEntity queryObject(String id);

	List<DeviceSwitchInfoLogEntity> queryList(Map<String, Object> map);

	List<DeviceSwitchInfoLogEntity> queryListByBean(DeviceSwitchInfoLogEntity entity);

	int queryTotal(Map<String, Object> map);

	int save(DeviceSwitchInfoLogEntity deviceSwitchInfoLog);

	int update(DeviceSwitchInfoLogEntity deviceSwitchInfoLog);

	int delete(String id);

	int deleteBatch(String[] ids);

	public PageInfo<DeviceSwitchInfoLogEntity> queryAppPage(String projectId, String beginTime, String endTime,
															String page, String pageSize);

	int insertDeviceSwitchHis(String synDate);

	int deleteDeviceSwitchLog(String synDate);

	List<DeviceSwitchInfoLogEntity> getDeviceBoxHistory(String deviceBoxId, String projectId, String hours);
}
