package com.icbms.core.service.demo.impl;

import com.icbms.common.constant.Constant;
import com.icbms.common.exception.MyException;
import com.icbms.common.util.Page;
import com.icbms.common.util.PageHelper;
import com.icbms.common.util.StringUtils;
import com.icbms.common.util.Utils;
import com.icbms.core.service.demo.LeaveService;
import com.icbms.core.util.UserUtils;
import com.icbms.repository.dao.demo.LeaveDao;
import com.icbms.repository.domain.demo.LeaveEntity;
import com.icbms.repository.domain.sys.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("leaveService")
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveDao leaveDao;

    @Override
    public LeaveEntity queryObject(String id) {
        if (StringUtils.isEmpty(id)) {
            new MyException("id不为空!");
        }
        return leaveDao.queryObject(id);
    }

    @Override
    public List<LeaveEntity> queryList(Map<String, Object> map) {
        return leaveDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return leaveDao.queryTotal(map);
    }

    @Override
    public void save(LeaveEntity leave) {
        UserEntity currentUser = UserUtils.getCurrentUser();
        leave.setCode(Utils.getCode("D"));
        leave.setStatus(Constant.ActStauts.DRAFT.getValue());
        leave.setCreateId(currentUser.getId());
        leave.setCreateTime(new Date());
        leave.setId(Utils.uuid());
        leave.setStatus(Constant.ActStauts.DRAFT.getValue());
        leave.setUserId(UserUtils.getCurrentUserId());
        leave.setBapid(currentUser.getBapid());
        leave.setBaid(currentUser.getBaid());//未改

        leaveDao.save(leave);
    }

    @Override
    public void update(LeaveEntity leave) {
        if (StringUtils.isEmpty(leave.getId())) {
            throw new MyException("请假id不能为空");
        }
        leave.setUpdateId(UserUtils.getCurrentUserId());
        leave.setUpdateTime(new Date());
        leaveDao.update(leave);
    }

    @Override
    public int delete(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new MyException("请假id不能为空");
        }
        return leaveDao.delete(id);
    }

    @Override
    public void deleteBatch(String[] ids) {
        leaveDao.deleteBatch(ids);
    }

    @Override
    public Page<LeaveEntity> findPage(LeaveEntity leaveEntity, int pageNum) {
        PageHelper.startPage(pageNum, Constant.pageSize);
        leaveDao.queryList(leaveEntity);
        return PageHelper.endPage();
    }

}

