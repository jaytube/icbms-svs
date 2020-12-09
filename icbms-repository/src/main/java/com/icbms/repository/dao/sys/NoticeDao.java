package com.icbms.repository.dao.sys;


import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.sys.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 通知
 * 
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-08-31 15:59:09
 */
@Mapper
public interface NoticeDao extends BaseDao<NoticeEntity> {

    /**
     * 我的通知列表
     * @param noticeEntity
     * @return
     */
    List<NoticeEntity> findMyNotice(NoticeEntity noticeEntity);
}
