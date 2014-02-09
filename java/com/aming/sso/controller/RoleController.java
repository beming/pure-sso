package com.aming.sso.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.aming.sso.entity.TblMenu;
import com.aming.sso.entity.TblRole;
import com.aming.sso.entity.TblRoleMenu;
import com.aming.sso.model.DwzCallBackModel;
import com.aming.sso.model.RoleMenuModel;
import com.aming.sso.service.MenuService;
import com.aming.sso.service.RoleService;
import com.sendtend.util.BeanTool;
import com.sendtend.util.PageUtil;

@Controller
@RequestMapping("/role.do")
public class RoleController extends MultiActionController {
	protected final transient Log log = LogFactory.getLog(RoleController.class);
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	
	private final String R_SELECT = "r_q_";
	private final String R_ADD = "r_a_";
	private final String R_UPDATE = "r_u_";
	private final String R_DEL = "r_d_";
	private final String R_EXEC = "r_e_";
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param rel
	 * @return
	 */
	@RequestMapping
	public String toRole(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toRole:");
		}
		request.setAttribute("rel", rel);
		return "role/role";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param role
	 * @param rel
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "tag=editRoleMenu")
	public String toRoleMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblRole role, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listRoleMenu, roleId:" + role.getRoleId());
		}
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "saveRoleMenu");
		DwzCallBackModel dm = new DwzCallBackModel();
		if(role.getRoleId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			map.put("TblRole", role);
			return "role/roleMenuList";
		}
		TblRole toRole = roleService.findRoleById(role.getRoleId());
		map.addAttribute(toRole);
		map.put("TblRole", toRole);
		TblMenu menu = new TblMenu();
		menu.setMenuStatus(1);
		List<TblMenu> list = (List<TblMenu>) menuService.findMenuByExample(menu);
		//从缓存中获得
		List<RoleMenuModel> rmml = new ArrayList<RoleMenuModel>();
		List<TblRoleMenu> rmlist = roleService.listRoleMenuByRoleId(toRole);
		RoleMenuModel rmm = null;
		boolean has = false;
		for(TblMenu tm:list) {
			has = false;
			rmm = new RoleMenuModel();
			try {
				BeanTool.copyProperties(rmm, tm);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//批量处理RoleMenu, 
			for(TblRoleMenu trm:rmlist) {
				if(trm.getMenuId().intValue() == tm.getMenuId().intValue()) {
					rmm.setChecked(true);
					has = true;
					if(trm.getRAdd() == 1) {
						rmm.setRAdd(true);
					}
					if(trm.getRDel() == 1) {
						rmm.setRDel(true);
					}
					if(trm.getRSelect() == 1) {
						rmm.setRSelect(true);
					}
					if(trm.getRUpdate() == 1) {
						rmm.setRUpdate(true);
					}
					if(trm.getRExec() == 1) {
						rmm.setRExec(true);
					}
					rmml.add(rmm);
				}
			}
			if(!has) {
				rmml.add(rmm);
			}
		}
		
		request.setAttribute("menuList", rmml);
		return "role/roleMenuList";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param role
	 * @param rel
	 * @return
	 */
	@Transactional
	@RequestMapping(params = "tag=saveRoleMenu")
	public String saveRoleMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblRole role, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listRole");
		}
		String[] ids = request.getParameterValues("ids");
		DwzCallBackModel dm = new DwzCallBackModel();
		if(role == null || role.getRoleId() == null || ids == null || ids.length<=0) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			request.setAttribute("rel", rel);
			request.setAttribute("tag", "update");
			map.put("TblRole", role);
			return this.toRoleMenu(request, response, session, map, role, rel);
		}
		//do
		//先删掉所有的,再新增
		roleService.delRoleMenu(role);
		for(String id:ids) {
			TblRoleMenu rm = new TblRoleMenu();
			rm.setRoleId(role.getRoleId());
			rm.setMenuId(new Integer(id));
			if(request.getParameter(this.R_ADD + id) != null) {
				rm.setRAdd(1);
			} else {
				rm.setRAdd(0);
			}
			if(request.getParameter(this.R_SELECT + id) != null) {
				rm.setRSelect(1);
			} else {
				rm.setRSelect(0);
			}
			if(request.getParameter(this.R_DEL + id) != null) {
				rm.setRDel(1);
			} else {
				rm.setRDel(0);
			}
			if(request.getParameter(this.R_UPDATE + id) != null) {
				rm.setRUpdate(1);
			} else {
				rm.setRUpdate(0);
			}
			if(request.getParameter(this.R_EXEC + id) != null) {
				rm.setRExec(1);
			} else {
				rm.setRExec(0);
			}
			roleService.saveRoleMenu(rm);
		}
		dm.setMessage("角色授权成功！");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/role.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "update");
		map.put("TblRole", role);
		return "role/roleList";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=list")
	public String listRole(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblRole role, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listRole, roleName:" + role.getRoleName());
		}
		if(role.getRoleName() != null && role.getRoleName().isEmpty()) {
			role.setRoleName(null);
		}
		request.setAttribute("rel", rel);
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		int thePage = new Integer(pageNum==null?"1":pageNum);
		int pageSize = new Integer(numPerPage==null?"20":numPerPage);
		if(request.getParameter("roleStatus") == null && role.getRoleStatus() == null) {
			role.setRoleStatus(new Byte("1"));
		}
		PageUtil pu = roleService.listRoles(role, thePage, pageSize);
		Map<String, Object> hmap = BeanTool.beanToMap(pu);
		map.addAllAttributes(hmap);
		request.setAttribute("PageUtil", pu);
		map.put("TblRole", role);
		return "role/roleList";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=add")
	public String addRole(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listRole");
		}
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "save");
		TblRole role = new TblRole();
		role.setRoleStatus(new Byte("1"));
		map.put("TblRole", role);
		return "role/role";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param roleId
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=edit")
	public String editRole(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("roleId") String roleId, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listRole");
		}
		request.setAttribute("rel", rel);
		TblRole role = roleService.findRoleById(new Integer(roleId));
		Map<String, Object> hmap = BeanTool.beanToMap(role);
		map.addAllAttributes(hmap);
		request.setAttribute("tag", "update");
		map.put("TblRole", role);
		return "role/role";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param role
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=update")
	public String updateRole(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblRole role, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listRole");
		}
		DwzCallBackModel dm = new DwzCallBackModel();
		if(role == null || role.getRoleId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			request.setAttribute("rel", rel);
			request.setAttribute("tag", "update");
			map.put("TblRole", role);
			return "role/role";
		}
		Map<String, Object> hmap = BeanTool.beanToMap(roleService.saveRole(role));
		map.addAllAttributes(hmap);
		dm.setMessage("角色信息修改成功！");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/role.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "update");
		map.put("TblRole", role);
		return "role/role";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param role
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=save")
	public String saveRole(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblRole role, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listRole");
		}
		DwzCallBackModel dm = new DwzCallBackModel();
		role = roleService.saveRole(role);
		dm.setMessage("角色新增成功!");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/role.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("rel", rel);
		map.put("TblRole", role);
		return "role/role";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param role
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=delete")
	public String delRole(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblRole role, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listRole");
		}
		DwzCallBackModel dm = new DwzCallBackModel();
		if(role == null || role.getRoleId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			dm.setCallbackType("forward");
			dm.setForwardUrl("/role.do?tag=list&rel=" + rel + "&num=" + Math.random());
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			return "role/roleList";
		}
		if(roleService.deleteRole(role) >0) {
			dm.setMessage("角色删除成功!");
			dm.setStatusCode("200");
		} else {
			dm.setMessage("角色删除失败，请重试或联系管理员!");
			dm.setStatusCode("300");
		}
		dm.setCallbackType("forward");
		dm.setForwardUrl("/role.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("rel", rel);
		map.put("TblRole", role);
		return "role/roleList";
	}
}
