package com.zcwl.user.dal.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.zcwl.user.dal.OperatorDao;
import com.zcwl.user.dto.OperatorDto;
import com.zcwl.user.dto.RoleDto;
import com.zcwl.user.util.DateUtil;
import com.zcwl.user.util.MD5;
import com.zcwl.user.util.MD5Util;
import com.zcwl.user.util.Page;


/**
 * 操作员操作接口
 * 
 * @author Liangth
 * 
 */
@SuppressWarnings("unchecked")
@Service("operatorDao")
public class OperatorDaoImpl implements OperatorDao {
	private static final int limitStart = 0;
	private static final int limitEnd = 100;
	protected static final Logger log = LoggerFactory
			.getLogger(OperatorDaoImpl2.class);

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	private static final String sql_bind_role = "INSERT INTO `ZCWL_SYSTEM_OPERATOR_ROLE`(`OPERATOR_ID`, `ROLE_ID`) VALUES(?,?)";
	private static final String sql_getById = "SELECT `ID`,`CODE`,`NAME`,`PASSWORD`,`STATUS`,`LASTLOGINTIME`,`REMARK`,`CITY_ID`,`OPERATOR_LEVEL` FROM `ZCWL_SYSTEM_OPERATOR` WHERE `ID`=?";
	private static final String sql_getList = "SELECT T1.`ID`,T1.`CODE`,T1.`NAME`,T1.`PASSWORD`,T1.`STATUS`,T1.`LASTLOGINTIME`,T1.`REMARK`,T1.`OPERATOR_LEVEL` FROM `ZCWL_SYSTEM_OPERATOR` T1 WHERE 1=1 AND `STATUS` = 0";
	private static final String sql_getBindRoles = "SELECT T.ROLE_ID AS ID,T2.NAME FROM ZCWL_SYSTEM_OPERATOR_ROLE T JOIN ZCWL_SYSTEM_ROLE T2 WHERE T.ROLE_ID=T2.ID AND T.OPERATOR_ID=?"; 



	
	public OperatorDto getOperator(int id) throws Exception {
		try {
			return (OperatorDto) this.jdbcTemplate.queryForObject(sql_getById,
					new Object[] { id }, new BeanPropertyRowMapper(
							OperatorDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	
	public List<OperatorDto> getOperatorList() throws Exception {
		return this.getOperatorList(limitStart, limitEnd);
	}

	public List<OperatorDto> getOperatorList(int start, int end)
			throws Exception {
		StringBuffer sql_getListLimit = new StringBuffer(sql_getList);
		sql_getListLimit.append(" LIMIT ?,?");
		return jdbcTemplate.query(sql_getListLimit.toString(), new Object[] {
				start, end }, new BeanPropertyRowMapper(OperatorDto.class));
	}


	
	public void bindRole(int optId, int roleId) throws Exception {
		jdbcTemplate.update(sql_bind_role, new Object[] { optId, roleId });
	}
	

	
	public void bindRole(final int optId, final int[] roleIds) throws Exception {
		jdbcTemplate.batchUpdate(sql_bind_role,
				new BatchPreparedStatementSetter() {
					
					public int getBatchSize() {
						return roleIds.length;
					}

					
					public void setValues(java.sql.PreparedStatement ps, int i)
							throws SQLException {
						int roleId = roleIds[i];
						ps.setInt(1, optId);
						ps.setInt(2, roleId);
					}
				});
	}



	
	public List<RoleDto> getBindRoles(int optId) throws Exception {
		return jdbcTemplate.query(sql_getBindRoles.toString(),
				new Object[] { optId },
				new BeanPropertyRowMapper(RoleDto.class));
	}


	public OperatorDto getOperatorById(String id) {
		String sql = "SELECT t1.*,t2.role_id,t3.name role_name FROM zcwl_system_operator t1 LEFT JOIN zcwl_system_operator_role t2 ON (t1.id=t2.operator_id) LEFT JOIN zcwl_system_role t3 ON (t2.role_id=t3.id) where t1.id='"+id+"'";
		List<OperatorDto> list = this.jdbcTemplate.query(sql,new BeanPropertyRowMapper(OperatorDto.class));
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
}
