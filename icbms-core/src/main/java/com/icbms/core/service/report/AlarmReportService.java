package com.icbms.core.service.report;

import com.icbms.repository.domain.report.AlarmInfoReportEntity;

import java.util.List;
import java.util.Map;

public interface AlarmReportService {

    AlarmInfoReportEntity queryObject(String id);

    List<AlarmInfoReportEntity> queryList(Map<String, Object> map);

    List<AlarmInfoReportEntity> queryListByBean(AlarmInfoReportEntity entity);

    int queryTotal(Map<String, Object> map);

    int save(AlarmInfoReportEntity deviceAlarmInfoLog);

    int update(AlarmInfoReportEntity deviceAlarmInfoLog);

    int delete(String id);

    int deleteBatch(String[] ids);
}
