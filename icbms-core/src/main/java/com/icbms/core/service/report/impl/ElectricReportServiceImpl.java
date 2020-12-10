package com.icbms.core.service.report.impl;

import com.icbms.core.service.report.ElectricReportService;
import com.icbms.repository.dao.report.ElectricReportDao;
import com.icbms.repository.domain.report.ElectricDailyEntity;
import com.icbms.repository.domain.report.ElectricReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("electricReportService")
public class ElectricReportServiceImpl implements ElectricReportService {

    @Autowired
    private ElectricReportDao electricReportDao;

    @Override
    public List<ElectricReportEntity> queryList(Map<String, Object> map) {

        return electricReportDao.findElectricByDay(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return electricReportDao.findElectricByDayTotal(map);
    }

    @Override
    public List<ElectricDailyEntity> queryDailyElectricCnt(Map<String, Object> map) {

        return electricReportDao.doStatDailyElectricCnt(map);
    }

    @Override
    public int queryDailyElectricCntTotal(Map<String, Object> map) {
        return electricReportDao.doStatDailyElectricCntTotal(map);
    }

}
