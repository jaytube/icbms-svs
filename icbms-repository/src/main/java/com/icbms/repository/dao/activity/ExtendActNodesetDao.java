package com.icbms.repository.dao.activity;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.activity.ExtendActNodesetEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流程节点配置
 * 
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-07-24 13:28:51
 */
@Mapper
public interface ExtendActNodesetDao extends BaseDao<ExtendActNodesetEntity> {
    /**
     * 根据nodeId查询节点信息
     * @param nodeId
     * @return
     */
    ExtendActNodesetEntity queryByNodeId(String nodeId);

    /**
     * 根据nodeId和模型id查询节点信息
     * @param nodeId
     * @param modelId
     * @return
     */
    ExtendActNodesetEntity queryByNodeIdModelId(@Param("nodeId") String nodeId, @Param("modelId") String modelId);

}
