package com.icbms.repository.dao.sys;

import com.icbms.common.dto.UserWindowDto;
import com.icbms.repository.dao.base.BaseDao;
import com.icbms.repository.domain.sys.OrganEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 组织表
 *
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-07-14 13:42:42
 */
@Mapper
public interface OrganDao extends BaseDao<OrganEntity> {
    /**
     * 根据实体条件查询
     *
     * @return
     */
    List<UserWindowDto> queryPageByDto(UserWindowDto userWindowDto);
}
