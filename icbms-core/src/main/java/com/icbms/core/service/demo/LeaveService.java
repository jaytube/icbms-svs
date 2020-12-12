package com.icbms.core.service.demo;

import com.icbms.common.util.Page;
import com.icbms.repository.domain.demo.LeaveEntity;

import java.util.List;
import java.util.Map;

/**
 * 请假流程测试
 *
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-07-31 13:33:23
 */
public interface LeaveService {

    LeaveEntity queryObject(String id);

    List<LeaveEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(LeaveEntity leave);

    void update(LeaveEntity leave);

    int delete(String id);

    void deleteBatch(String[] ids);

    Page<LeaveEntity> findPage(LeaveEntity leaveEntity, int pageNum);
}
