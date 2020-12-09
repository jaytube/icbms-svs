package com.icbms.repository.dao.devicelog;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.devicelog.DeviceAlarmInfoLogEntity;
import com.icbms.repository.domain.devicelog.DeviceAlarmStatEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 空开设备警告日志表; InnoDB free: 397312 kB
 * 
 * @author hxy
 * @email rui.sun.java@gmail.com
 * @date 2018-05-16 19:57:54
 */
@Mapper
public interface DeviceAlarmInfoLogDao extends BaseDao<DeviceAlarmInfoLogEntity> {

	public DeviceAlarmInfoLogEntity doFindDeviceAlarmIsExist(@Param("deviceBoxId") String deviceBoxId);

	public DeviceAlarmInfoLogEntity doFindNewestDeviceAlarm(@Param("deviceBoxId") String deviceBoxId);

	public List<DeviceAlarmInfoLogEntity> queryAppList(@Param("beginTime") String beginTime,
                                                       @Param("endTime") String endTime, @Param("type") String type, @Param("deviceBoxMac") String deviceBoxMac,
                                                       @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("projectId") String projectId,
                                                       @Param("locationId") String locationId, @Param("deviceBoxId") String deviceBoxId,
                                                       @Param("alarmLevel") String alarmLevel);

	public int queryAppTotal(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                             @Param("type") String type, @Param("deviceBoxMac") String deviceBoxMac,
                             @Param("projectId") String projectId, @Param("locationId") String locationId,
                             @Param("deviceBoxId") String deviceBoxId, @Param("alarmLevel") String alarmLevel);

	public List<DeviceAlarmStatEntity> doStatDeviceAlarm(@Param("projectId") String projectId,
														 @Param("startDate") String startDate);

	public List<DeviceAlarmInfoLogEntity> queryDeviceAlarmList(@Param("deviceBoxId") String deviceBoxId);
}