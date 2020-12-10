package com.icbms.repository.dao.sys.schedule;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.sys.schedule.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类ScheduleJobLogDao的功能描述:
 * 定时任务日志
 *
 * @auther hxy
 * @date 2017-08-25 16:13:41
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {

}
