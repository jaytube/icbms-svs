package com.icbms.repository.dao.demo;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.demo.LeaveEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假流程测试
 *
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-07-31 13:33:23
 */
@Mapper
public interface LeaveDao extends BaseDao<LeaveEntity> {

}
