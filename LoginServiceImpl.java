package com.zcwl.redpack.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcwl.core.dto.Page;
import com.zcwl.redpack.service.LoginService;
import com.zcwl.user.dal.MessageCountDao;
import com.zcwl.user.dal.ResourceDao;
import com.zcwl.user.dto.MessageCountDto;
import com.zcwl.user.dto.ResourceDto;
import com.zcwl.user.dto.RoleResourceDto;

@Transactional(rollbackFor=Exception.class)
@Service("loginService")
public class LoginServiceImpl implements LoginService{
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private MessageCountDao countDao;
	
	@Autowired
	private UserDao userDao;
	
	private static ComparatorResource comparator = new ComparatorResource();
	
	@Override
	public List<ResourceDto> getResourceListByRole(int roleId) throws Exception {
		List<ResourceDto> resourceList = resourceDao.getResourceListByRole(roleId, 0);
		List<ResourceDto> newResourceList = sortList(resourceList);
		// 缓存处理结果
		return newResourceList;
	}
	
	
	private List<ResourceDto> sortList(List<ResourceDto> resourceList) {
		List<ResourceDto> newResourceList = null;
		if (resourceList != null && resourceList.size() > 0) {
			Collections.sort(resourceList, comparator);
			Map<Integer, List<ResourceDto>> map = new HashMap<Integer, List<ResourceDto>>();
			newResourceList = new ArrayList<ResourceDto>();
			for (ResourceDto tmp : resourceList) {
				// 暂时处理一个层级的节点
				if (tmp.getPid() == 0) {
					newResourceList.add(tmp);
				} else {
					List<ResourceDto> tmpList = map.get(tmp.getPid());
					if (tmpList == null) {
						tmpList = new ArrayList<ResourceDto>();
					}
					tmpList.add(tmp);
					map.put(tmp.getPid(), tmpList);
				}
			}
			Collections.sort(newResourceList, comparator);
			for (ResourceDto tmp : resourceList) {
				if (tmp.getIschild() == ResourceDto.HAS_CHILD) {
					List<ResourceDto> tmpList = map.get(tmp.getId());
					if(tmpList!=null&&tmpList.size()!=0){						
						Collections.sort(tmpList, comparator);
						tmp.setChildren(tmpList);
					}
				}
			}
		}
		return newResourceList;
	}
	
	public static class ComparatorResource implements Comparator<ResourceDto> {
		@Override
		public int compare(ResourceDto o1, ResourceDto o2) {
			ResourceDto dto0 = o1;
			ResourceDto dto1 = o2;
			if (dto0.getSort() > dto1.getSort()) {
				return 1;
			} else if (dto0.getSort() == dto1.getSort()) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	
}


