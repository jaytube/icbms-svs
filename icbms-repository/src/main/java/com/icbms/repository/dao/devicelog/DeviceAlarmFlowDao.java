package com.icbms.repository.dao.devicelog;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.devicelog.DeviceAlarmFlowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeviceAlarmFlowDao extends BaseDao<DeviceAlarmFlowEntity> {

	public List<DeviceAlarmFlowEntity> queryFlowPage(@Param("projectId") String projectId,
                                                     @Param("status") String status, @Param("alarmStartDate") String alarmStartDate,
                                                     @Param("alarmEndDate") String alarmEndDate, @Param("deviceBoxMac") String deviceBoxMac,
                                                     @Param("standNo") String standNo, @Param("alarmType") String alarmType, @Param("offset") Integer offset,
                                                     @Param("limit") Integer limit);

	public int queryFlowTotal(@Param("projectId") String projectId,
                              @Param("status") String status, @Param("alarmStartDate") String alarmStartDate,
                              @Param("alarmEndDate") String alarmEndDate, @Param("deviceBoxMac") String deviceBoxMac,
                              @Param("standNo") String standNo, @Param("alarmType") String alarmType);
}
