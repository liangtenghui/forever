package com.zcwl.user.dal;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zcwl.user.dto.OperatorDto;
import com.zcwl.user.dto.RoleDto;
import com.zcwl.user.util.Page;


/**
 * 操作员操作接口
 * 
 * @author LiangTh
 * 
 */
@Transactional()
public interface OperatorDao{


	/**
	 * 通过ID查询操作员信息
	 * 
	 * @param id
	 * @throws Exception
	 */
	OperatorDto getOperator(int id) throws Exception;
	

	/**
	 * 查询操作员列表
	 * 
	 * @param pid
	 * @throws Exception
	 */
	List<OperatorDto> getOperatorList() throws Exception;

	/**
	 * 设置操作员的角色
	 * 
	 * @param optId
	 *            操作员Id
	 * @param roleId
	 *            角色Id
	 * @throws Exception
	 */
	void bindRole(int optId, int roleId) throws Exception;

	/**
	 * 设置操作员的角色
	 * 
	 * @param optId
	 * @param roleIds
	 * @throws Exception
	 */
	void bindRole(int optId, int[] roleIds) throws Exception;

	void rebindRole(int optId, int roleId) throws Exception;

	/**
	 * 获取操作员的角色
	 * 
	 * @param optId
	 *            操作员Id
	 * @param roleId
	 *            角色Id
	 * @throws Exception
	 */
	List<RoleDto> getBindRoles(int optId) throws Exception;   //by

	/**
	 * 取角色ID
	 * 
	 * @param operatorId
	 * @return
	 * @throws Exception
	 */
	public List<RoleDto> getRoleByOperatorId(int operatorId) throws Exception;
	
	public void bindResource(int operatorId, List<String> resoureIds) throws Exception;

	

	public OperatorDto getOperatorById(String id);
	
	
	
}
