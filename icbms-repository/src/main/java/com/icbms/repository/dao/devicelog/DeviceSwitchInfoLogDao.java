package com.icbms.repository.dao.devicelog;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.devicelog.DeviceSwitchInfoLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 空开设备日志表; InnoDB free: 398336 kB
 * 
 * @author hxy
 * @email rui.sun.java@gmail.com
 * @date 2018-05-15 21:44:00
 */
@Mapper
public interface DeviceSwitchInfoLogDao extends BaseDao<DeviceSwitchInfoLogEntity> {
	public List<DeviceSwitchInfoLogEntity> queryAppList(@Param("projectId") String projectId,
                                                        @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("offset") Integer offset,
                                                        @Param("limit") Integer limit);

	public int queryAppTotal(@Param("projectId") String projectId, @Param("beginTime") String beginTime,
                             @Param("endTime") String endTime);

	int insertDeviceSwitchHis(@Param("synDate") String synDate);

	int deleteDeviceSwitchLog(@Param("synDate") String synDate);

	List<DeviceSwitchInfoLogEntity> getDeviceBoxHistory(@Param("deviceBoxId") String deviceBoxId,
                                                        @Param("projectId") String projectId, @Param("hours") String hours);
}