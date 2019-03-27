package com.zcwl.user.dal;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zcwl.user.dto.ResourceDto;


/**
 * 节点资源操作接口
 * 
 * @author LiangTh
 * 
 */
@Transactional()
public interface ResourceDao {

	/**
	 * 通过ID查询资源信息
	 * 
	 * @param id
	 * @throws Exception
	 */
	ResourceDto getResource(int id) throws Exception;

	/**
	 * 通过父节点ID查询资源列表
	 * 
	 * @param pid
	 * @throws Exception
	 */
	List<ResourceDto> getListByUrl(String url) throws Exception;
	
	/**
	 * 通过父节点ID查询资源列表
	 * 
	 * @param pid
	 * @throws Exception
	 */
	List<ResourceDto> getChildResourceListByPid(int pid) throws Exception;

	/**
	 * 查询树形结构的资源列表
	 * 
	 * @param pid
	 * @throws Exception
	 */
	List<ResourceDto> getResourceListByTree() throws Exception;
	
	/**
	 * 查询树形结构的资源列表
	 * 
	 * @param pid
	 * @throws Exception
	 */
	List<ResourceDto> getResourceListByRole(int roleId, int type) throws Exception;
	
	/**
	 * 资源列表
	 */
	public List<ResourceDto> getResourceList(int siteId) throws Exception;

}
