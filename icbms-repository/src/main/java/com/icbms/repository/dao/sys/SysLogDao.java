package com.icbms.repository.dao.sys;


import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.sys.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类SysLogDao的功能描述:
 * 系统日志
 * @auther hxy
 * @date 2017-08-25 16:14:57
 */
@Mapper
public interface SysLogDao extends BaseDao<SysLogEntity> {
	
}
