package com.icbms.repository.domain.projectinfo;

import com.icbms.repository.domain.base.BaseEntity;
import com.icbms.repository.domain.deviceinfo.DeviceBoxInfoEntity;

import java.io.Serializable;

public class DboxLocLinkEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	// id主键
	private String id;
	//电箱信息
	private DeviceBoxInfoEntity dboxInfo;
	//位置信息
	private LocationInfoEntity locationInfo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DeviceBoxInfoEntity getDboxInfo() {
		return dboxInfo;
	}
	public void setDboxInfo(DeviceBoxInfoEntity dboxInfo) {
		this.dboxInfo = dboxInfo;
	}
	public LocationInfoEntity getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(LocationInfoEntity locationInfo) {
		this.locationInfo = locationInfo;
	}
	
	

}
