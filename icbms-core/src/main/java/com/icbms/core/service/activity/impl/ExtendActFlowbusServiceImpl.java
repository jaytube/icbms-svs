package com.icbms.core.service.activity.impl;

import com.icbms.core.service.activity.ExtendActFlowbusService;
import com.icbms.repository.dao.activity.ExtendActFlowbusDao;
import com.icbms.repository.domain.activity.ExtendActFlowbusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("extendActFlowbusService")
public class ExtendActFlowbusServiceImpl implements ExtendActFlowbusService {
	@Autowired
	private ExtendActFlowbusDao extendActFlowbusDao;
	
	@Override
	public ExtendActFlowbusEntity queryObject(String id){
		return extendActFlowbusDao.queryObject(id);
	}

	@Override
	public void save(ExtendActFlowbusEntity extendActFlowbus){
		extendActFlowbusDao.save(extendActFlowbus);
	}
	
	@Override
	public void update(ExtendActFlowbusEntity extendActFlowbus){
		extendActFlowbusDao.update(extendActFlowbus);
	}
	@Override
	public void delete(String id){
		extendActFlowbusDao.delete(id);
	}

	@Override
	public int updateByBusId(ExtendActFlowbusEntity extendActFlowbusEntity) {
		return extendActFlowbusDao.updateByBusId(extendActFlowbusEntity);
	}

	@Override
	public ExtendActFlowbusEntity queryByBusIdInsId(String instanceId, String busId) {
		Map<String,Object> params = new HashMap<>();
		params.put("instanceId",instanceId);
		params.put("busId",busId);
		return extendActFlowbusDao.queryByBusIdInsId(params);
	}
}
