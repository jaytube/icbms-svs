package com.icbms.core.service.devicelog.impl;

import com.alibaba.fastjson.JSONObject;
import com.icbms.common.util.PageInfo;
import com.icbms.common.util.PageUtils;
import com.icbms.common.util.RedisUtil;
import com.icbms.common.util.Utils;
import com.icbms.core.service.deviceinfo.DeviceBoxInfoService;
import com.icbms.core.service.devicelog.DeviceAlarmInfoLogService;
import com.icbms.core.service.sys.CodeService;
import com.icbms.repository.dao.devicelog.DeviceAlarmInfoLogDao;
import com.icbms.repository.domain.deviceinfo.DeviceBoxInfoEntity;
import com.icbms.repository.domain.devicelog.DeviceAlarmInfoLogEntity;
import com.icbms.repository.domain.devicelog.DeviceAlarmStatEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("deviceAlarmInfoLogService")
public class DeviceAlarmInfoLogServiceImpl implements DeviceAlarmInfoLogService {
	@Autowired
	private DeviceAlarmInfoLogDao deviceAlarmInfoLogDao;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private DeviceBoxInfoService deviceBoxInfoService;

	@Autowired
	private CodeService codeService;

	@Override
	public DeviceAlarmInfoLogEntity queryObject(String id) {
		return deviceAlarmInfoLogDao.queryObject(id);
	}

	@Override
	public List<DeviceAlarmInfoLogEntity> queryList(Map<String, Object> map) {
		return deviceAlarmInfoLogDao.queryList(map);
	}

	@Override
	public List<DeviceAlarmInfoLogEntity> queryListByBean(DeviceAlarmInfoLogEntity entity) {
		return deviceAlarmInfoLogDao.queryListByBean(entity);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return deviceAlarmInfoLogDao.queryTotal(map);
	}

	@Override
	public int save(DeviceAlarmInfoLogEntity deviceAlarmInfoLog) {
		deviceAlarmInfoLog.setId(Utils.uuid());
		return deviceAlarmInfoLogDao.save(deviceAlarmInfoLog);
	}

	@Override
	public int update(DeviceAlarmInfoLogEntity deviceAlarmInfoLog) {
		return deviceAlarmInfoLogDao.update(deviceAlarmInfoLog);
	}

	@Override
	public int delete(String id) {
		return deviceAlarmInfoLogDao.delete(id);
	}

	@Override
	public int deleteBatch(String[] ids) {
		return deviceAlarmInfoLogDao.deleteBatch(ids);
	}

	@Override
	public DeviceAlarmInfoLogEntity doFindDeviceAlarmIsExist(String deviceBoxId) {
		return deviceAlarmInfoLogDao.doFindDeviceAlarmIsExist(deviceBoxId);
	}

	@Override
	public PageInfo<DeviceAlarmInfoLogEntity> queryAppPage(String beginTime, String endTime, String page,
														   String pageSize, String projectId, String deviceBoxId, String alarmLevel) {
		if (StringUtils.isBlank(page)) {
			page = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "5";
		}
		int curPage = Integer.parseInt(page);

		Integer offset = (curPage - 1) * Integer.parseInt(pageSize);
		List<DeviceAlarmInfoLogEntity> result = this.deviceAlarmInfoLogDao.queryAppList(beginTime, endTime, null, null,
				offset, Integer.parseInt(pageSize), projectId, null, deviceBoxId, alarmLevel);

		if (null != result && 0 < result.size()) {
			Map<String, String> levelMap = this.codeService.queryChildsMapByMark("alarm_level");
			for (DeviceAlarmInfoLogEntity alarm : result) {
				if (levelMap.containsKey(alarm.getAlarmLevel())) {
					alarm.setAlarmLevelName(levelMap.get(alarm.getAlarmLevel()));
				}
			}
		}

		int totalCount = this.deviceAlarmInfoLogDao.queryAppTotal(beginTime, endTime, null, null, projectId, null,
				deviceBoxId, alarmLevel);
		int totalPage = totalCount / Integer.parseInt(pageSize);
		if (totalCount % Integer.parseInt(pageSize) != 0) {
			totalPage = totalPage + 1;
		}
		PageInfo<DeviceAlarmInfoLogEntity> pageInfo = new PageInfo<DeviceAlarmInfoLogEntity>();
		pageInfo.setTotal(String.valueOf(totalCount));
		pageInfo.setTotalPage(String.valueOf(totalPage));
		pageInfo.setPage(page);
		pageInfo.setDataList(result);
		return pageInfo;
	}

	@Override
	public PageInfo<DeviceAlarmInfoLogEntity> queryDeviceAlarmPage(String beginTime, String endTime, String type,
			String deviceBoxMac, String projectId, String locationId, String page, String pageSize, String alarmLevel) {
		Map<String, String> boxMap = new HashMap<String, String>();
		if (StringUtils.isBlank(page)) {
			page = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "5";
		}
		int curPage = Integer.parseInt(page);

		Integer offset = (curPage - 1) * Integer.parseInt(pageSize);
		List<DeviceAlarmInfoLogEntity> result = this.deviceAlarmInfoLogDao.queryAppList(beginTime, endTime, type,
				deviceBoxMac, offset, Integer.parseInt(pageSize), projectId, locationId, null, alarmLevel);
		if (null != result && 0 < result.size()) {
			Map<String, String> levelMap = this.codeService.queryChildsMapByMark("alarm_level");
			for (DeviceAlarmInfoLogEntity alarm : result) {
				if (levelMap.containsKey(alarm.getAlarmLevel())) {
					alarm.setAlarmLevelName(levelMap.get(alarm.getAlarmLevel()));
				}
			}
		}

		int totalCount = this.deviceAlarmInfoLogDao.queryAppTotal(beginTime, endTime, type, deviceBoxMac, projectId,
				locationId, null, alarmLevel);
		int totalPage = totalCount / Integer.parseInt(pageSize);
		if (totalCount % Integer.parseInt(pageSize) != 0) {
			totalPage = totalPage + 1;
		}

		PageUtils pageUtil = new PageUtils(result, totalCount, Integer.valueOf(pageSize), Integer.valueOf(page));
		PageInfo<DeviceAlarmInfoLogEntity> pageInfo = new PageInfo<DeviceAlarmInfoLogEntity>();
		pageInfo.setTotal(String.valueOf(totalCount));
		pageInfo.setTotalPage(String.valueOf(totalPage));
		pageInfo.setPage(page);
		pageInfo.setDataList(result);
		return pageInfo;
	}

	public List<DeviceAlarmStatEntity> doStatDeviceAlarm(String projectId, String startDate) {
		return this.deviceAlarmInfoLogDao.doStatDeviceAlarm(projectId, startDate);
	}

	public List<DeviceAlarmInfoLogEntity> queryDeviceAlarmList(String deviceBoxId) {
		return this.deviceAlarmInfoLogDao.queryDeviceAlarmList(deviceBoxId);
	}

	public void doSetNewestDeviceAlarm(DeviceBoxInfoEntity box) {
		List<DeviceAlarmInfoLogEntity> alarmList = new ArrayList<DeviceAlarmInfoLogEntity>();
		String field = Integer.parseInt(box.getDeviceBoxNum().substring(10)) + "_";
		Map<String, String> result = redisUtil.fuzzyGet(0, "ALARM_DATA", field);
		for (String k : result.keySet()) {
			String resultJson = result.get(k);
			JSONObject jsonObj = JSONObject.parseObject(resultJson);
			DeviceAlarmInfoLogEntity temp = new DeviceAlarmInfoLogEntity();
			temp.setDeviceBoxMac(box.getDeviceBoxName());
			temp.setDeviceBoxId(box.getId());
			temp.setNode("线路" + (Integer.parseInt(k.split("_")[2]) + 1));
			temp.setType(jsonObj.getString("alarmType"));
			temp.setAlarmLevel(jsonObj.getString("alarmLevel"));
			temp.setInfo(jsonObj.getString("alarmContent"));
			temp.setAlarmStatus(jsonObj.getString("alarmStatus"));
			alarmList.add(temp);
		}
		if (alarmList.size() == 0) {
			box.setAlarm(null);
			box.setAllAlarm(null);
		} else {
			box.setAllAlarm(alarmList);
			box.setAlarm(this.getHighestLevel(alarmList));
		}
	}

	/**
	 * @param alarmList
	 *            告警集合
	 * @return
	 */
	public DeviceAlarmInfoLogEntity getHighestLevel(List<DeviceAlarmInfoLogEntity> alarmList) {
		AlarmLevelCompare compare = new AlarmLevelCompare();
		Collections.sort(alarmList, compare);
		return alarmList.get(0);
	}

	public class AlarmLevelCompare implements Comparator<DeviceAlarmInfoLogEntity> {
		public int compare(DeviceAlarmInfoLogEntity m1, DeviceAlarmInfoLogEntity m2) {
			return m2.getAlarmLevel().compareTo(m1.getAlarmLevel());
		}
	}
}