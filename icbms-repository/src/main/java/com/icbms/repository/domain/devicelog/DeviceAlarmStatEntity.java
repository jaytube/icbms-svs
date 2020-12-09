package com.icbms.repository.domain.devicelog;

public class DeviceAlarmStatEntity {

    private String type;

	private Integer count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
