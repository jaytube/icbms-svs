package com.icbms.repository.dao.app;

import com.icbms.repository.domain.app.ApiUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户表
 * 
 * @author chenshun
 * @email rui.sun.java@gmail.com
 * @date 2017-05-03 09:41:38
 */
@Mapper
public interface ApiUserDao {
    /**
     * 根据id获取用户相关信息
     * @param id
     * @return
     */
    ApiUserEntity userInfo(String id);
}
