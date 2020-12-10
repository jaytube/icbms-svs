package com.icbms.repository.dao.report;

import com.icbms.repository.domain.report.ElectricDailyEntity;
import com.icbms.repository.domain.report.ElectricReportEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ElectricReportDao {

    public List<ElectricReportEntity> findElectricByDay(Map<String, Object> map);

    public int findElectricByDayTotal(Map<String, Object> map);

    public List<ElectricDailyEntity> doStatDailyElectricCnt(Map<String, Object> map);

    public int doStatDailyElectricCntTotal(Map<String, Object> map);
}
