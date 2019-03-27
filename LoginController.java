package com.zcwl.redpack.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcwl.redpack.service.LoginService;
import com.zcwl.user.dal.OperatorDao;
import com.zcwl.user.dto.OperatorDto;
import com.zcwl.user.dto.ResourceDto;
import com.zcwl.user.dto.RoleDto;

/**
 * 角色权限菜单列表
 */
@Controller
public class LoginController {
	public static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private LoginService loginService;
	@Autowired
	private OperatorDao operatorDao;


	protected static final Logger LOG = LoggerFactory
			.getLogger(LoginController2.class);


	// 跳转到主页面
	@RequestMapping("/main.do")
	public String main(ModelMap model, HttpSession session, String pid) {
		OperatorDto operatorDto = (OperatorDto) session
				.getAttribute("operatorDto");
		List<ResourceDto> resourceList = null;// 节点资源列表
		try {
			List<RoleDto> list = operatorDao.getBindRoles(operatorDto.getId());
			for (RoleDto roleDto : list) {
				// 多个角色取最大权限集
				resourceList = loginService.getResourceListByRole(roleDto
						.getId());
				Map<Integer, List<ResourceDto>> roleResMap = new HashMap<Integer, List<ResourceDto>>();
				getChild(resourceList, roleResMap);
				session.setAttribute("resourceList", roleResMap);
			}
			model.addAttribute("roleId", list.get(0).getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("userDto", operatorDto);
		model.addAttribute("resourceList", resourceList);
		return "main";
	}

	// 递归循环子菜单
	private void getChild(List<ResourceDto> resourceList,
			Map<Integer, List<ResourceDto>> roleResMap) {
		for (ResourceDto tmp : resourceList) {
			roleResMap.put(tmp.getId(), tmp.getChildren());
			List<ResourceDto> isChildList = tmp.getChildren();
			if (isChildList != null) {
				getChild(isChildList, roleResMap);
			}
		}
	}
	
}
