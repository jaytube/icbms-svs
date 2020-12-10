package com.icbms.core.service.report;

import com.icbms.repository.domain.report.ElectricDailyEntity;
import com.icbms.repository.domain.report.ElectricReportEntity;

import java.util.List;
import java.util.Map;

public interface ElectricReportService {

    public List<ElectricReportEntity> queryList(Map<String, Object> map);

    public int queryTotal(Map<String, Object> map);

    public List<ElectricDailyEntity> queryDailyElectricCnt(Map<String, Object> map);

    public int queryDailyElectricCntTotal(Map<String, Object> map);
}
