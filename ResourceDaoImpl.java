package com.zcwl.user.dal.impl;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.zcwl.user.dal.ResourceDao;
import com.zcwl.user.dto.ResourceDto;


/**
 * 节点资源操作接口
 * 
 * @author LTH
 * 
 */
@Service("resourceDao")
public class ResourceDaoImpl implements ResourceDao {
	protected static final Logger log = LoggerFactory
			.getLogger(ResourceDaoImpl.class);

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	private static final String sql_getById = "SELECT `ID`,`NAME`,`STATUS`,`PID`,`ISCHILD`,`LEVEL`,`SORT`,`GROUPID`,`MODULE`,`URL`,`DSCRIBE` FROM `ZCWL_SYSTEM_RESOURCE` WHERE `ID`=? ";
	private static final String sql_getList = "SELECT `ID`,`NAME`,`STATUS`,`PID`,`ISCHILD`,`LEVEL`,`SORT`,`GROUPID`,`MODULE`,`URL`,`DSCRIBE` FROM `ZCWL_SYSTEM_RESOURCE` WHERE `PID`=? ORDER BY `SORT`";



	

	@SuppressWarnings("unchecked")
	
	public List<ResourceDto> getChildResourceListByPid(int pid)
			throws Exception {
		return this.jdbcTemplate.query(sql_getList, new Object[] { pid },
				new BeanPropertyRowMapper(ResourceDto.class));
	}

	
	public ResourceDto getResource(int id) throws Exception {
		return (ResourceDto) this.jdbcTemplate.queryForObject(sql_getById,
				new Object[] { id }, new BeanPropertyRowMapper(
						ResourceDto.class));
	}

	private static final String sql_getListByTree = "SELECT ID,NAME,STATUS,PID,ISCHILD,LEVEL,SORT,GROUPID,URL,DSCRIBE FROM ZCWL_SYSTEM_RESOURCE WHERE STATUS=0";
	@SuppressWarnings("unchecked")
	
	public List<ResourceDto> getResourceListByTree() throws Exception {
		return this.jdbcTemplate.query(sql_getListByTree, 
				new BeanPropertyRowMapper(ResourceDto.class));
	}

	@SuppressWarnings("unchecked")
	
	public List<ResourceDto> getResourceListByRole(int roleId, int type) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append("SELECT `ID`,`NAME`,`STATUS`,`PID`,`ISCHILD`,`LEVEL`,`SORT`,`GROUPID`,`MODULE`,`URL`,`DSCRIBE`,`ICO_URL`  FROM" +
				" `ZCWL_SYSTEM_RESOURCE` T1 WHERE T1.STATUS = 0 AND  T1.ID IN(SELECT T2.RESOURCE_ID FROM ZCWL_SYSTEM_ROLE_RESOURCE T2 ");
		if(roleId!=0){
			sb.append(" WHERE T2.ROLE_ID=?");
			params.add(roleId);
		}
		sb.append(")");
		if(type!=0){
			sb.append(" and T1.TYPE=? ");
			params.add(type);
		}
		return this.jdbcTemplate.query(sb.toString(),
				params.toArray(), new BeanPropertyRowMapper(
						ResourceDto.class));
	}

	
	
	public List<ResourceDto> getResourceList(int siteId) throws Exception {
		String sql = " SELECT * FROM `ZCWL_SYSTEM_RESOURCE` WHERE `SITE_ID`="+siteId+" ";
		List<ResourceDto> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper(ResourceDto.class));
		return list;
	}

}
