package com.icbms.core.service.report.impl;

import com.icbms.common.util.Utils;
import com.icbms.core.service.report.AlarmReportService;
import com.icbms.repository.dao.report.AlarmInfoReportDao;
import com.icbms.repository.domain.report.AlarmInfoReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("alarmReportService")
public class AlarmReportServiceImpl implements AlarmReportService {
    @Autowired
    private AlarmInfoReportDao alarmInfoReportDao;


    @Override
    public AlarmInfoReportEntity queryObject(String id) {
        return alarmInfoReportDao.queryObject(id);
    }

    @Override
    public List<AlarmInfoReportEntity> queryList(Map<String, Object> map) {

        return alarmInfoReportDao.queryList(map);
    }

    @Override
    public List<AlarmInfoReportEntity> queryListByBean(AlarmInfoReportEntity entity) {
        return alarmInfoReportDao.queryListByBean(entity);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return alarmInfoReportDao.queryTotal(map);
    }

    @Override
    public int save(AlarmInfoReportEntity deviceAlarmInfoLog) {
        deviceAlarmInfoLog.setId(Utils.uuid());
        return alarmInfoReportDao.save(deviceAlarmInfoLog);
    }

    @Override
    public int update(AlarmInfoReportEntity deviceAlarmInfoLog) {
        return alarmInfoReportDao.update(deviceAlarmInfoLog);
    }

    @Override
    public int delete(String id) {
        return alarmInfoReportDao.delete(id);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return alarmInfoReportDao.deleteBatch(ids);
    }
}
