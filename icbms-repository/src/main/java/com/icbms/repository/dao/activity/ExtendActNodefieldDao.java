package com.icbms.repository.dao.activity;

import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.activity.ExtendActNodefieldEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 流程节点对应的条件
 * 
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-07-24 13:28:51
 */
@Mapper
public interface ExtendActNodefieldDao extends BaseDao<ExtendActNodefieldEntity> {

    /**
     * 根据节点id删除
     * @param nodeId
     */
    void delByNodeId(String nodeId);

    /**
     * 根据节点集合查询
     * @param nodes
     * @return
     */
    List<ExtendActNodefieldEntity> queryByNodes(List<String> nodes);
}
