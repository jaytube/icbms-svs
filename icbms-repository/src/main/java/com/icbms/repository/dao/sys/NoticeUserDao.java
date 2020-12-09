package com.icbms.repository.dao.sys;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.sys.NoticeUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知和用户关系表
 * 
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-08-31 15:58:58
 */
@Mapper
public interface NoticeUserDao extends BaseDao<NoticeUserEntity> {
    /**
     * 根据通知id和用户id 更新
     * @param noticeUserEntity
     * @return
     */
    int updateByNoticeIdUserId(NoticeUserEntity noticeUserEntity);
}
