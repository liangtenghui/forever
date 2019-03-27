package com.zcwl.redpack.service;

import java.util.List;

import com.zcwl.core.dto.Page;
import com.zcwl.members.dto.UserSurveyDto;
import com.zcwl.user.dto.ResourceDto;
import com.zcwl.user.dto.RoleResourceDto;



/**
 * @author LTH
 *
 */
public interface LoginService {
	
	/**
	 * 查询角色对应的节点列表
	 */
	public List<ResourceDto> getResourceListByRole(int roleId) throws Exception;
	
	/**
	 * 查询当前登录用户的权限
	 */
	public List<RoleResourceDto> getUserResource(Integer roleId);

	
}
