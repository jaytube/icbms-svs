package com.icbms.repository.dao.sys.schedule;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.sys.schedule.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 类ScheduleJobDao的功能描述:
 * 定时任务
 *
 * @auther hxy
 * @date 2017-08-25 16:14:04
 */
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {

    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);
}
