package com.icbms.core.service.deviceinfo;

import com.icbms.repository.domain.deviceinfo.DeviceBoxInfoEntity;
import com.icbms.repository.domain.projectinfo.DboxLocLinkEntity;
import com.icbms.repository.domain.sys.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 电箱设备基础表; InnoDB free: 401408 kB
 * 
 * @author Raymond
 * @email rui.sun.java@gmail.com
 * @date 2018-03-13 15:57:43
 */
public interface DeviceBoxInfoService {

	DeviceBoxInfoEntity queryObject(String id);

	List<DeviceBoxInfoEntity> queryList(Map<String, Object> map);

	List<DeviceBoxInfoEntity> queryListByBean(DeviceBoxInfoEntity entity);

	int queryTotal(Map<String, Object> map);

	int save(DeviceBoxInfoEntity deviceBoxInfo);

	int update(DeviceBoxInfoEntity deviceBoxInfo);

	int xyReset(DeviceBoxInfoEntity deviceBoxInfo);

	int delete(String id);

	int deleteBatch(String[] ids, String type);

	List<DeviceBoxInfoEntity> findDeviceBoxsInfoByLId(String projectId, String locationId);

	List<DeviceBoxInfoEntity> findDeviceBoxsInfoByProjectId(String projectId);

	void updateDeviceBoxOnline(String deviceMac, String online);

	// 保持电箱和位置
	void saveBoxLocLink(DboxLocLinkEntity dboxLocation);

	// 根据电箱ID获取配置的电箱位置关联关系
	List<DboxLocLinkEntity> queryLinkListByBoxId(Map<String, String> paramsMap);

	// 批量导入位置和电箱
	void saveBoxLocBatch(List<Map<String, String>> result, String projectId, UserEntity currentUser);

	DeviceBoxInfoEntity queryByMac(String deviceMac, String projectId);

	DeviceBoxInfoEntity queryProjectMac(String projectId, String deviceMac);

	List<DeviceBoxInfoEntity> findDeviceBoxsInfoLike(String projectId, String deviceMac);

	int deleteProjectDeviceBox(String projectId);

	int updateBoxPlacedFlag(String projectId, String deviceBoxMac, String placedFlag);
}
