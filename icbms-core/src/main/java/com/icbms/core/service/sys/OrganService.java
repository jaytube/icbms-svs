package com.icbms.core.service.sys;


import com.icbms.common.util.Page;
import com.icbms.common.dto.UserWindowDto;
import com.icbms.repository.domain.sys.OrganEntity;

import java.util.List;
import java.util.Map;

/**
 * 组织表
 * 
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-07-14 13:42:42
 */
public interface OrganService {
	
	OrganEntity queryObject(String id);
	
	List<OrganEntity> queryList(Map<String, Object> map);

	List<OrganEntity> queryListByBean(OrganEntity organEntity);
	
	int queryTotal(Map<String, Object> map);
	
	String save(OrganEntity organ);

	int update(OrganEntity organ);

	int delete(String id);

	int deleteBatch(String[] ids);

	/**
	 * 根据code查询可能的组织
	 * @param code
	 * @return
	 */
	List<OrganEntity> queryListByCode(String code);

	/**
	 * 更新机构状态
	 * @param ids
	 * @param type
	 * @return
	 */
	void updateIsdel(String ids, String type);

	/**
	 * 分页查询组织审批选择范围
	 * @return
	 */
	Page<UserWindowDto> queryPageByDto(UserWindowDto userWindowDto, int pageNum);

}
