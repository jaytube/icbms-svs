package com.icbms.core.service.sys;


import com.icbms.common.util.Page;
import com.icbms.repository.domain.sys.NoticeEntity;

import java.util.List;
import java.util.Map;

/**
 * 通知
 * 
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-08-31 15:59:09
 */
public interface NoticeService {
	
	NoticeEntity queryObject(String id);
	
	List<NoticeEntity> queryList(Map<String, Object> map);

    List<NoticeEntity> queryListByBean(NoticeEntity entity);
	
	int queryTotal(Map<String, Object> map);
	
	int save(NoticeEntity notice);
	
	int update(NoticeEntity notice);
	
	int delete(String id);
	
	int deleteBatch(String[] ids);

	/**
	 * 分页列表
	 * @param noticeEntity
	 * @param pageNum
	 * @return
	 */
	Page<NoticeEntity> findPage(NoticeEntity noticeEntity, int pageNum);

	/**
	 * 我的通知分页列表
	 * @param noticeEntity
	 * @param pageNum
	 * @return
	 */
	Page<NoticeEntity> findMyNoticePage(NoticeEntity noticeEntity, int pageNum);

	/**
	 * 我的通知条数
	 * @return
	 */
	int MyNoticeCount();


}
