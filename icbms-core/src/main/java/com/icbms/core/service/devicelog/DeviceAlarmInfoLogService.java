package com.icbms.core.service.devicelog;

import com.icbms.common.util.PageInfo;
import com.icbms.repository.domain.deviceinfo.DeviceBoxInfoEntity;
import com.icbms.repository.domain.devicelog.DeviceAlarmInfoLogEntity;
import com.icbms.repository.domain.devicelog.DeviceAlarmStatEntity;

import java.util.List;
import java.util.Map;

public interface DeviceAlarmInfoLogService {

	DeviceAlarmInfoLogEntity queryObject(String id);

	List<DeviceAlarmInfoLogEntity> queryList(Map<String, Object> map);

	List<DeviceAlarmInfoLogEntity> queryListByBean(DeviceAlarmInfoLogEntity entity);

	int queryTotal(Map<String, Object> map);

	int save(DeviceAlarmInfoLogEntity deviceAlarmInfoLog);

	int update(DeviceAlarmInfoLogEntity deviceAlarmInfoLog);

	int delete(String id);

	int deleteBatch(String[] ids);

	DeviceAlarmInfoLogEntity doFindDeviceAlarmIsExist(String deviceBoxId);

	PageInfo<DeviceAlarmInfoLogEntity> queryAppPage(String beginTime, String endTime, String page,
														   String pageSize, String projectId, String deviceBoxId, String alarmLevel);

	PageInfo<DeviceAlarmInfoLogEntity> queryDeviceAlarmPage(String beginTime, String endTime, String type,
                                                                   String deviceBoxMac, String projectId, String locationId, String page, String pageSize, String alarmLevel);

	List<DeviceAlarmStatEntity> doStatDeviceAlarm(String projectId, String startDate);

	List<DeviceAlarmInfoLogEntity> queryDeviceAlarmList(String deviceBoxId);

	void doSetNewestDeviceAlarm(DeviceBoxInfoEntity config);
}
