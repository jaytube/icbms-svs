package com.icbms.repository.dao.devicelog;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.devicelog.DeviceElecStatEntity;
import com.icbms.repository.domain.devicelog.DeviceElectricityLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 每日用电日志表; InnoDB free: 395264 kB
 * 
 * @author hxy
 * @email rui.sun.java@gmail.com
 * @date 2018-05-17 00:47:37
 */
@Mapper
public interface DeviceElectricityLogDao extends BaseDao<DeviceElectricityLogEntity> {

	public List<DeviceElecStatEntity> doStatDeviceElec(@Param("projectId") String projectId,
                                                       @Param("startDate") String startDate);

    public List<DeviceElectricityLogEntity> doStatElectricCnt(@Param("projectId") String projectId,
                                                              @Param("statDate") String statDate);

    public void doDeleteElectricCnt(@Param("projectId") String projectId, @Param("statDate") String statDate);

}
