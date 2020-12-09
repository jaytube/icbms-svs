package com.icbms.core.service.kk.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbms.common.util.DateConverUtil;
import com.icbms.common.util.DateUtils;
import com.icbms.common.util.PageInfo;
import com.icbms.common.util.RedisUtil;
import com.icbms.core.service.deviceinfo.DeviceBoxInfoService;
import com.icbms.core.service.devicelog.DeviceAlarmInfoLogService;
import com.icbms.core.service.devicelog.DeviceElectricityLogService;
import com.icbms.core.service.kk.KkService;
import com.icbms.core.service.projectinfo.ProjectInfoService;
import com.icbms.core.service.sys.UserService;
import com.icbms.core.socket.cinterface.ClientRequestCmdSend;
import com.icbms.core.util.JiguangPush;
import com.icbms.core.util.KkBizUtil;
import com.icbms.repository.domain.deviceinfo.DeviceBoxInfoEntity;
import com.icbms.repository.domain.deviceinfo.DeviceSwitchInfoEntity;
import com.icbms.repository.domain.devicelog.DeviceAlarmInfoLogEntity;
import com.icbms.repository.domain.devicelog.DeviceElectricityLogEntity;
import com.icbms.repository.domain.kk.DeviceAlarm;
import com.icbms.repository.domain.kk.DeviceBoxConfigEntity;
import com.icbms.repository.domain.kk.LineUpper;
import com.icbms.repository.domain.projectinfo.ProjectInfoEntity;
import com.icbms.repository.domain.sys.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service("kkService")
public class KkServiceImpl implements KkService {
	private final static Logger logger = LoggerFactory.getLogger(KkServiceImpl.class);

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private DeviceBoxInfoService deviceBoxInfoService;

	@Autowired
	private DeviceAlarmInfoLogService deviceAlarmInfoLogService;

	@Autowired
	private DeviceElectricityLogService deviceElectricityLogService;

	@Autowired
	private ProjectInfoService projectInfoService;

	@Autowired
	private UserService userService;

/*	@Override
	public String setCmdSwitch(String deviceBoxMac, String switchAddStrs, String cmd, String projectId) {
		String result = "0";
		// 0表示分闸 1表示合闸
		if ("open".equals(cmd)) {
			cmd = "1";
		} else if ("close".equals(cmd)) {
			cmd = "0";
		}
		DeviceBoxInfoEntity deviceBox = this.deviceBoxInfoService.queryByMac(deviceBoxMac, projectId);
		if (null != deviceBox) {
			String deviceBoxAddress = Integer.parseInt(deviceBox.getDeviceBoxNum().substring(10)) + "";
			String resultJson = redisUtil.hget(0, "TERMINAL_STATUS", deviceBoxAddress);
			JSONObject dataObj = JSONObject.fromObject(resultJson);
			String gatewayAddress = dataObj.getString("gatewayId");
			String[] switchIds = switchAddStrs.split(",");
			if (switchIds != null && switchIds.length > 0) {
				for (int i = 0; i < switchIds.length; i++) {
					// 这里一个一个开关调用
					ClientRequestCmdSend.getIntance().sendControlSwichCmd(gatewayAddress, deviceBoxAddress,
							switchIds[i], cmd);

				}
			}
		}
		return result;
	}*/

	public String setCmdSwitch(String projectId, String deviceBoxMac, String switchAddStrs, String cmd) {
		String result = "0";
		// 0表示分闸 1表示合闸
		if ("open".equals(cmd)) {
			cmd = "1";
		} else if ("close".equals(cmd)) {
			cmd = "0";
		}
		DeviceBoxInfoEntity deviceBox = this.deviceBoxInfoService.queryProjectMac(projectId, deviceBoxMac);
		if (null != deviceBox) {
			String deviceBoxAddress = Integer.parseInt(deviceBox.getDeviceBoxNum().substring(10)) + "";
			String resultJson = redisUtil.hget(0, "TERMINAL_STATUS", deviceBoxAddress);
			JSONObject dataObj = JSONObject.parseObject(resultJson);
			String gatewayAddress = dataObj.getString("gatewayId");
			String[] switchIds = switchAddStrs.split(",");
			if (switchIds != null && switchIds.length > 0) {
				for (int i = 0; i < switchIds.length; i++) {
					// 这里一个一个开关调用
					ClientRequestCmdSend.getIntance().sendControlSwichCmd(gatewayAddress, deviceBoxAddress,
							switchIds[i], cmd);

				}
			}
		}
		return result;
	}

	public List<DeviceElectricityLogEntity> getBoxDayPower(String deviceBoxMac) {
		List<DeviceElectricityLogEntity> elecList = new ArrayList<DeviceElectricityLogEntity>();
		try {
			String resultJson = KkBizUtil.getBoxDayPower(deviceBoxMac);
			JSONObject dataObj = JSONObject.parseObject(resultJson);
			String result = dataObj.getString("code");
			if ("0".equals(result)) {
				JSONArray jsonArray = dataObj.getJSONArray("data");
				if (jsonArray.size() > 0) {
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObj = jsonArray.getJSONObject(i);
						DeviceElectricityLogEntity elecInfo = new DeviceElectricityLogEntity();
						elecInfo.setAddr(jsonObj.getString("addr"));// 线路地址
						elecInfo.setElectricity(jsonObj.getString("electricity"));// 用电量
						elecList.add(elecInfo);
					}
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return elecList;
	}

	public List<LineUpper> getLineUpper(String deviceBoxMac) {
		List<LineUpper> upperList = new ArrayList<>();
		// DeviceBoxConfigEntity config =
		// deviceBoxConfigService.queryDeviceBoxConfig(deviceBoxMac);
		// if (null != config) {
		// String field = config.getGatewayAddress() + "_" +
		// config.getDeviceBoxAddress() + "_";
		// Map<String, String> result =
		// redisUtil.fuzzyGet(Integer.parseInt(config.getGatewayAddress()),
		// "LINE_PARAMS_CONFIGURATION", field);
		// for (String k : result.keySet()) {
		// String resultJson = result.get(k);
		// JSONObject jsonObj = JSONObject.fromObject(resultJson);
		// LineUpper upper = new LineUpper();
		// upper.setLine(k.split("_")[2]);
		// upper.setLineName("线路" + (Integer.parseInt(k.split("_")[2]) + 1));
		// upper.setElectricityLimit(jsonObj.getString("electricity_limit"));
		// upper.setPowerUpper(jsonObj.getString("power_upper"));
		// upper.setDeviceBoxMac(deviceBoxMac);
		// upperList.add(upper);
		// }
		// }
		return upperList;
	}

	@Override
	public List<DeviceSwitchInfoEntity> getBoxChannelsRealData(String deviceBoxMac, String projectId) {
		List<DeviceSwitchInfoEntity> switchList = new ArrayList<>();
		DeviceBoxInfoEntity boxInfo = this.deviceBoxInfoService.queryByMac(deviceBoxMac, projectId);
		if (null != boxInfo) {
			String field = Integer.parseInt(boxInfo.getDeviceBoxNum().substring(10)) + "_";
			Map<String, String> result = redisUtil.fuzzyGet(0, "REAL_DATA", field);
			for (String k : result.keySet()) {
				String resultJson = result.get(k);
				JSONObject jsonObj = JSONObject.parseObject(resultJson);
				DeviceSwitchInfoEntity dsInfo = new DeviceSwitchInfoEntity();
				dsInfo.setAddress(jsonObj.getString("switchAddr"));
				dsInfo.setDeviceSwitchName("线路" + (Integer.parseInt(jsonObj.getString("switchAddr")) + 1));
				dsInfo.setDeviceSwitchStatus("0".equals(jsonObj.getString("switchOnoff")) ? "true" : "false");
				dsInfo.setSwitchPower(jsonObj.getString("power"));// 功率
				dsInfo.setSwitchVoltage(jsonObj.getString("voltage"));// 电压
				dsInfo.setSwitchElectric(jsonObj.getString("electricCurrent"));// 电流
				dsInfo.setSwitchLeakage(jsonObj.getString("leakageCurrent"));// 漏电流
				dsInfo.setSwitchTemperature(jsonObj.getString("temperature"));// 温度

				dsInfo.setPhaseVoltageA(jsonObj.getString("phaseVoltageA"));
				dsInfo.setPhaseVoltageB(jsonObj.getString("phaseVoltageB"));
				dsInfo.setPhaseVoltageC(jsonObj.getString("phaseVoltageC"));

				dsInfo.setPhaseCurrentA(jsonObj.getString("phaseCurrentA"));
				dsInfo.setPhaseCurrentB(jsonObj.getString("phaseCurrentB"));
				dsInfo.setPhaseCurrentC(jsonObj.getString("phaseCurrentC"));
				dsInfo.setPhaseCurrentN(jsonObj.getString("phaseCurrentN"));

				dsInfo.setPhasePowerA(jsonObj.getString("phasePowerA"));
				dsInfo.setPhasePowerB(jsonObj.getString("phasePowerB"));
				dsInfo.setPhasePowerC(jsonObj.getString("phasePowerC"));
				switchList.add(dsInfo);
			}
		}
		return switchList;
	}

	@Override
	public void doKkSchedule() {
		List<ProjectInfoEntity> projectList = this.projectInfoService.queryListAll();
		for (ProjectInfoEntity tmpProject : projectList) {
			List<DeviceBoxInfoEntity> boxList = deviceBoxInfoService.findDeviceBoxsInfoByProjectId(tmpProject.getId());
			for (DeviceBoxInfoEntity box : boxList) {
				String field = Integer.parseInt(box.getDeviceBoxNum().substring(10)) + "_";
				Map<String, String> result = redisUtil.fuzzyGet(0, "REAL_DATA", field);
				for (String k : result.keySet()) {
					String resultJson = result.get(k);
					JSONObject jsonObj = JSONObject.parseObject(resultJson);
					String electricCurrent = jsonObj.getString("electricCurrent");
					if (!StringUtils.isEmpty(electricCurrent) && !StringUtils.isEmpty(box.getBoxCapacity())) {
						BigDecimal c = new BigDecimal(box.getBoxCapacity());
						BigDecimal c80 = new BigDecimal(box.getBoxCapacity()).multiply(new BigDecimal("0.8"));
						BigDecimal e = new BigDecimal(electricCurrent);

						// 用电超过上限
						if (e.compareTo(c) > 0) {
							String ks[] = k.split("_");
							DeviceAlarmInfoLogEntity alarm = new DeviceAlarmInfoLogEntity();
							alarm.setProjectId(tmpProject.getId());
							alarm.setDeviceBoxMac(box.getDeviceBoxNum());
							alarm.setDeviceBoxId(box.getId());
							alarm.setNode("线路" + (Integer.parseInt(ks[2]) + 1));
							alarm.setType("用电超标告警");
							alarm.setAlarmLevel("4");
							alarm.setInfo(
									"网关[" + ks[0] + "],电箱[" + ks[1] + "],线路[" + ks[2] + "]用电超标,当前值:" + electricCurrent);
							alarm.setAlarmStatus("1");
							alarm.setRecordTime(new Date());
							alarm.setCreateTime(new Date());
							alarm.setRemark(
									"网关[" + ks[0] + "],电箱[" + ks[1] + "],线路[" + ks[2] + "]用电超标,当前值:" + electricCurrent);
							this.deviceAlarmInfoLogService.save(alarm);

							ProjectInfoEntity project = this.projectInfoService.queryObject(tmpProject.getId());
							String projectInfo = "{id:" + tmpProject.getId() + ", projectName:"
									+ project.getProjectName() + "}";

							List<UserEntity> userList = this.userService.queryObjectByRole();
							for (UserEntity u : userList) {
								JiguangPush.push(u.getLoginName(),
										box.getDeviceBoxName() + "-" + alarm.getNode() + "发生" + alarm.getInfo(),
										projectInfo);
							}
						}

						// 用电超过上限80%
						if (e.compareTo(c80) > 0) {
							String ks[] = k.split("_");
							DeviceAlarmInfoLogEntity alarm = new DeviceAlarmInfoLogEntity();
							alarm.setProjectId(tmpProject.getId());
							alarm.setDeviceBoxMac(box.getDeviceBoxNum());
							alarm.setDeviceBoxId(box.getId());
							alarm.setNode("线路" + (Integer.parseInt(ks[2]) + 1));
							alarm.setType("用电超标预警");
							alarm.setAlarmLevel("4");
							alarm.setInfo(
									"网关[" + ks[0] + "],电箱[" + ks[1] + "],线路[" + ks[2] + "]用电超标,当前值:" + electricCurrent);
							alarm.setAlarmStatus("1");
							alarm.setRecordTime(new Date());
							alarm.setCreateTime(new Date());
							alarm.setRemark(
									"网关[" + ks[0] + "],电箱[" + ks[1] + "],线路[" + ks[2] + "]用电超标,当前值:" + electricCurrent);
							this.deviceAlarmInfoLogService.save(alarm);
						}
					}
				}
			}
		}
	}

	@Override
	public PageInfo<DeviceAlarm> getAlarmsByTime(String deviceBoxMac, String startTime, String endTime, String pageSize,
												 String page) {
		PageInfo<DeviceAlarm> pageInfo = new PageInfo<DeviceAlarm>();
		try {
			String resultJson = KkBizUtil.getAlarmsByTime(deviceBoxMac, startTime, endTime, pageSize, page);
			JSONObject dataObj = JSONObject.parseObject(resultJson);
			String result = dataObj.getString("code");
			if ("0".equals(result)) {
				JSONObject pageData = dataObj.getJSONObject("data");
				pageInfo.setTotal(pageData.getString("total"));
				pageInfo.setTotalPage(pageData.getString("totalPage"));
				JSONArray alarmsDataArray = JSONArray.parseArray(pageData.getString("datas"));
				List<DeviceAlarm> alarmList = new ArrayList<DeviceAlarm>();
				if (alarmsDataArray.size() > 0) {
					for (int i = 0; i < alarmsDataArray.size(); i++) {
						DeviceAlarm alarm = new DeviceAlarm();
						JSONObject obj = alarmsDataArray.getJSONObject(i);
						alarm.setInfo(obj.getString("info"));
						alarm.setNode(obj.getString("node"));
						alarm.setTime(obj.getString("time"));
						alarm.setType(obj.getString("type"));
						alarmList.add(alarm);
					}
				}
				pageInfo.setDataList(alarmList);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return pageInfo;
	}

	@Override
	public String setUpdateDateTime(String deviceBoxMac, String cmd) {
		String resultJson = null;
		try {
			resultJson = KkBizUtil.setUpdateDateTime(deviceBoxMac, cmd);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("======================>设置结果：" + resultJson);
		return resultJson;
	}

	@Override
	public void doKkAlarmSchedule() {
		List<ProjectInfoEntity> projectList = this.projectInfoService.queryListAll();
		for (ProjectInfoEntity tmpProject : projectList) {
			List<DeviceBoxInfoEntity> boxList = deviceBoxInfoService.findDeviceBoxsInfoByProjectId(tmpProject.getId());
			ProjectInfoEntity project = projectInfoService.queryObject(tmpProject.getId());
			String startTime = DateUtils.format(new Date()) + " 00:00";
			String endTime = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm");
			for (DeviceBoxInfoEntity box : boxList) {
				PageInfo<DeviceAlarm> pageResult = this.getAlarmsByTime(box.getDeviceBoxNum(), startTime, endTime,
						"100", "1");
				List<DeviceAlarm> tempResult = pageResult.getDataList();
				if (null != tempResult) {
					for (DeviceAlarm alarm : tempResult) {
						DeviceAlarmInfoLogEntity tmp = deviceAlarmInfoLogService.doFindDeviceAlarmIsExist(box.getId());
						if (null == tmp) {
							DeviceAlarmInfoLogEntity entity = new DeviceAlarmInfoLogEntity();
							entity.setProjectId(tmpProject.getId());
							entity.setDeviceBoxId(box.getId());
							entity.setDeviceBoxMac(box.getDeviceBoxNum());
							entity.setNode(alarm.getNode());
							entity.setType(alarm.getType());
							Date recordTime = DateConverUtil.getDbyST(alarm.getTime(), "yyyy-MM-dd HH:mm:ss");
							entity.setRecordTime(recordTime);
							entity.setInfo(alarm.getInfo());
							entity.setCreateTime(new Date());
							deviceAlarmInfoLogService.save(entity);
							String projectInfo = "{id:" + tmpProject.getId() + ", projectName:"
									+ project.getProjectName() + "}";
							JiguangPush.push("sunrui",
									box.getDeviceBoxName() + "-" + alarm.getNode() + "发生" + alarm.getInfo(),
									projectInfo);
							JiguangPush.push("icbms",
									box.getDeviceBoxName() + "-" + alarm.getNode() + "发生" + alarm.getInfo(),
									projectInfo);
						}
					}
				}
			}
		}

	}

	@Override
	@Transactional
	public void doKkDayElectricity() {
		List<ProjectInfoEntity> projectList = this.projectInfoService.queryListAll();
		for (ProjectInfoEntity tmpProject : projectList) {
			String statDate = DateUtils.format(new Date(), "yyyy-MM-dd");
			statDate = DateUtils.getPreDay(statDate, -1);
			// 先删除后插入
			this.deviceElectricityLogService.doDeleteElectricCnt(tmpProject.getId(), statDate);
			List<DeviceElectricityLogEntity> logList = this.deviceElectricityLogService
					.doStatElectricCnt(tmpProject.getId(), statDate);
			if (null != logList && 0 < logList.size()) {
				for (DeviceElectricityLogEntity log : logList) {
					log.setStatDate(statDate);
					log.setCreateTime(new Date());
					deviceElectricityLogService.save(log);
				}
			}
		}
	}

	public void processDeviceBoxOnline(List<DeviceBoxInfoEntity> deviceBoxList) {
		Map<String, String> tmpMap = redisUtil.hgetAll(0, "TERMINAL_STATUS");
		for (DeviceBoxInfoEntity box : deviceBoxList) {
			String key = Integer.parseInt(box.getDeviceBoxNum().substring(10)) + "";
			if (tmpMap.containsKey(key)) {
				JSONObject jsonObj = JSONObject.parseObject(tmpMap.get(key));
				box.setOnline(jsonObj.getString("status"));
				String gatewayAddress = jsonObj.getString("gatewayId");
				String tmpStr = redisUtil.hget(0, "GATEWARY_STATUS", gatewayAddress);
				if (!StringUtils.isEmpty(tmpStr)) {
					JSONObject tmpJsonObj = JSONObject.parseObject(tmpStr);
					DeviceBoxConfigEntity config = new DeviceBoxConfigEntity();
					config.setGatewayAddress(gatewayAddress);
					config.setGatewayStatus(tmpJsonObj.getString("status"));
				}
			} else {
				box.setOnline("1");
			}
		}

		// for (DeviceBoxInfoEntity box : deviceBoxList) {
		// if (null != box.getConfig()) {
		// String field = box.getConfig().getGatewayAddress();
		// String result = tmpResult.get(field);
		// if (!StringUtils.isEmpty(result)) {
		// JSONObject jsonObj = JSONObject.fromObject(result);
		// box.getConfig().setGatewayStatus(jsonObj.getString("status"));
		// } else {
		// box.getConfig().setGatewayStatus("1");
		// }
		// }
		// }
	}

	public Map<String, Integer> statDeviceBoxOnline(String projectId) {
		List<DeviceBoxInfoEntity> boxList = this.deviceBoxInfoService.findDeviceBoxsInfoByProjectId(projectId);
		Map<String, DeviceBoxInfoEntity> boxMap = new HashMap<String, DeviceBoxInfoEntity>();
		for (DeviceBoxInfoEntity b : boxList) {
			boxMap.put(Integer.parseInt(b.getDeviceBoxNum().substring(10)) + "", b);
		}

		Map<String, Integer> result = new HashMap<String, Integer>();
		int onlineNums = 0;
		String gateWayAddress = "0";
		Map<String, String> tmp = redisUtil.hgetAll(Integer.parseInt(gateWayAddress), "TERMINAL_STATUS");
		Map<String, String> tmpResult = new HashMap<String, String>();
		tmpResult.putAll(tmp);
		for (Map.Entry<String, String> tmpEntry : tmpResult.entrySet()) {
			JSONObject jsonObj = JSONObject.parseObject(tmpEntry.getValue());
			if ("0".equals(jsonObj.getString("status")) && boxMap.containsKey(tmpEntry.getKey())) {
				onlineNums++;
			}
		}
		result.put("onlineNums", onlineNums);
		return result;
	}

	public void doKkProject() {
		// this.deviceBoxConfigService.updateUse();
		// this.deviceBoxConfigService.updateNotUse();
	}

	@Override
	public void setTerminalPsrams(String terminalAddr, String switchAddr, String maxPower, String maxEletric) {
		ClientRequestCmdSend.getIntance().sendWriteThresholdParamsCmd("0", terminalAddr, switchAddr, "3",
				Short.parseShort(maxPower));// 配置功率门限
		ClientRequestCmdSend.getIntance().sendWriteThresholdParamsCmd("0", terminalAddr, switchAddr, "5",
				Short.parseShort(maxEletric));// 配置电流门限
	}

	@Override
	public Map<String, String> getTerminalParams(String termainlAddr, String switchAddr) {
		ClientRequestCmdSend.getIntance().sendReadThresholdParamsCmd("0", termainlAddr, switchAddr, "3");// 功率门限
		ClientRequestCmdSend.getIntance().sendReadThresholdParamsCmd("0", termainlAddr, switchAddr, "5");// 电流门限
		return null;
	}

	/**
	 * @param gwAddress
	 *            网关地址
	 * @param gwNum
	 *            组网网关个数
	 * @param gwList
	 *            组网网关列表(从小到到)
	 * @param startAddr
	 *            组网起始节点地址
	 * @param endAddr
	 *            组网结束节点地址
	 * @param gwDelayTime
	 *            组网网关之间发送延时
	 * @param dxDelayTime
	 *            组网节点之间发送延时
	 * @param getServerTimeWx
	 *            获取服务器时间准许网络延时误差
	 * @param okFlag
	 *            配置有效标志位
	 * @param queueFlag
	 *            服务器发送命令至网关，网关插队或排队发送至节点标志位
	 * @param reportCycle
	 *            上报周期
	 * @param gw2DxOverTime
	 *            网关发送节点命令回应超时时间
	 * @return
	 */
	@Override
	public void setGwParams(String gwAddress, String gwNum, String gwList, String startAddr, String endAddr,
			String gwDelayTime, String dxDelayTime, String getServerTimeWx, String okFlag, String queueFlag,
			String reportCycle, String gw2DxOverTime) {
		ClientRequestCmdSend.getIntance().setGwParams(gwAddress, gwNum, gwList, startAddr, endAddr, gwDelayTime,
				dxDelayTime, getServerTimeWx, okFlag, queueFlag, reportCycle, gw2DxOverTime);
	}

	@Override
	public void getGwParams(String gwAddress) {
		ClientRequestCmdSend.getIntance().getGwParams(gwAddress);
	}
	
	/**
	 * 
	 */
	@Override
	public void doSynTerminalConfig(String projectId) {
		ProjectInfoEntity project = this.projectInfoService.queryObject(projectId);
		if (project != null) {
			List<DeviceBoxInfoEntity> boxList = deviceBoxInfoService.findDeviceBoxsInfoByProjectId(project.getId());
			if(boxList != null && boxList.size() > 0){
				for(DeviceBoxInfoEntity deviceBoxInfo:boxList){
					String str = deviceBoxInfo.getDeviceBoxNum();
					if(str!=null && !"".equals(str) && str.length()>6){
						String terminalId = str.substring(str.length() - 6);
						while(terminalId.startsWith("0")){
							terminalId = terminalId.substring(1);								
						}
						redisUtil.hset(0, "TERMINAL_CONFIG", terminalId, deviceBoxInfo.getProjectId());
					}
				}
			}
		}
	}
	
}