package com.aming.sso.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.aming.sso.ContextDefine;
import com.aming.sso.entity.TblRole;
import com.aming.sso.entity.TblUser;
import com.aming.sso.entity.TblUserRole;
import com.aming.sso.model.DwzCallBackModel;
import com.aming.sso.model.UserRoleModel;
import com.aming.sso.service.RoleService;
import com.aming.sso.service.UserService;
import com.sendtend.util.BeanTool;
import com.sendtend.util.CustomBigDecimalEditor;
import com.sendtend.util.DateTool;
import com.sendtend.util.MD5;
import com.sendtend.util.PageUtil;
import com.sendtend.util.StringUtil;

@Controller
@RequestMapping("/user.do")
public class UserController extends MultiActionController {
	protected final transient Log log = LogFactory.getLog(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param rel
	 * @return
	 */
	@RequestMapping
	public String toUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug(" ----- call toUser:");
		}
		request.setAttribute("rel", rel);
		return "user/user";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=list")
	public String listUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblUser user, @RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug("------------ call listUser");
		}
		request.setAttribute("rel", rel);
		if (user.getLoginName() != null && user.getLoginName().isEmpty()) {
			user.setLoginName(null);
		}
		if (user.getUserName() != null && user.getUserName().isEmpty()) {
			user.setUserName(null);
		}
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		int thePage = new Integer(pageNum == null ? "1" : pageNum);
		int pageSize = new Integer(numPerPage == null ? "20" : numPerPage);
		log.debug("----------------" + user.getStatus());
		if(request.getParameter("status") == null && user.getStatus() == null) {
			user.setStatus(1);
		}
		PageUtil pu = userService.listUsers(user, null, thePage, pageSize);
		Map<String, Object> hmap = BeanTool.beanToMap(pu);
		map.addAllAttributes(hmap);
		request.setAttribute("PageUtil", pu);
		map.put("TblUser", user);
		return "user/userList";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "tag=listUsers")
	public @ResponseBody String findUsers(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblUser user) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listUsers");
		}
		String keyword = request.getParameter("keyword");
		String isLoginName = request.getParameter("isLoginName");
		String returnId = request.getParameter("returnId");
		if(keyword != null && !keyword.trim().isEmpty()) {
			keyword = "%" + keyword + "%";
			if(isLoginName != null && !isLoginName.equals("true")) {
				user.setLoginName(keyword);
			} else {
				user.setUserName(keyword);
			}
		}
		if(request.getParameter("status") == null && user.getUserId() == null) {
			user.setStatus(1);
		}
		List<?> list = userService.findUserByExample(user);
		PropertyFilter filter = new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
		        if("userId".equals(name)) {
		            return true;
		        }
		        if("userName".equals(name)) {
		            return true;
		        }
		        if("loginName".equals(name)) {
		            return true;
		        }
		        return false;
		    }
		};
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.getPropertyFilters().add(filter);
		serializer.write(list);
		String rst = out.toString();
		if(returnId != null && returnId.trim().endsWith("true")) {
			rst = rst.replaceAll("userId", "id");
			if(log.isDebugEnabled()) {
				log.debug("------ replace userId with id. THE result String: " + rst);
			}
		}
		return StringUtil.toChartset(rst, "UTF-8", "ISO8859-1");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "tag=toQuery")
	public String toQueryUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toQueryUser:");
		}
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String orderDirectionStr = request.getParameter("orderDirection");
		int orderDirection = -1;
		if(orderDirectionStr != null) {
			orderDirection = Integer.parseInt(orderDirectionStr);
		}
		String userName = request.getParameter("userName");
		String loginName = request.getParameter("loginName");
		TblUser user = new TblUser();
		if(userName != null  && !userName.isEmpty()) {
			user.setUserName(userName);
		}
		if(loginName != null  && !loginName.isEmpty()) {
			user.setLoginName(loginName);
		}
		if(user.getStatus() == null) {
			user.setStatus(1);
		}
 		int thePage = new Integer(pageNum==null?"1":pageNum);
		int pageSize = new Integer(numPerPage==null?"20":numPerPage);
		PageUtil pu = userService.listUsers(user, orderDirectionStr, thePage, pageSize);
		pu.setOrderDirection(orderDirection);
		request.setAttribute("PageUtil", pu);
		return "user/queryUser";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=add")
	public String addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug("------------ call listUser");
		}
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "save");
		TblUser user = new TblUser();
		try {
			user.setBirthday(DateTool.strToDate("1990-01-01", "yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setStatus(1);
		user.setSexy(1);
		map.put("TblUser", user);
		return "user/user";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=chgPwd")
	public String toChgUserPwd(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug("------------ call listUser");
		}
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "updatePwd");
		TblUser user = new TblUser();
		map.put("TblUser", user);
		return "user/chgPwd";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=userRole")
	public String toUserRole(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblUser user, @RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug("------------ call listUser");
		}
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "saveUserRole");
		user = userService.findUserById(user.getUserId());
		Map<String, Object> hmap = BeanTool.beanToMap(user);
		map.addAllAttributes(hmap);
		//查找用户其他资料，id对应的name
		TblUser findUser = new TblUser();
		findUser.setStatus(1);
		findUser.setUserId(user.getUserId());
		Map<String, Object> elseProp = userService.findUsersOtherProperties(findUser);
		map.addAllAttributes(elseProp);
		map.put("TblUser", findUser);
		//角色列表
		TblRole role = new TblRole();
		role.setRoleStatus(new Byte("1"));
		List<TblRole> rl = roleService.findRoleByExample(role);
		//从缓存汇总获得用户的角色
		//PurviewModel pm = (PurviewModel) session.getAttribute(ContextDefine.LOGIN_USER_RIGHT);
		List<TblRole> tr = roleService.listUserRole(findUser);
		UserRoleModel urm = null;
		List<UserRoleModel> urmList = new ArrayList<UserRoleModel>();
		for(TblRole ar:rl) {
			urm = new UserRoleModel();
			urm.setUserId(user.getUserId());
			try {
				BeanTool.copyProperties(urm, ar);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			for(TblRole tmpRole:tr) {
				if(tmpRole.getRoleId().intValue() == ar.getRoleId().intValue()) {
					urm.setChecked(true);
					break;
				}
			}
			urmList.add(urm);
		}
		request.setAttribute("userRoleList", urmList);
		return "user/userRole";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param user
	 * @param rel
	 * @return
	 */
	@Transactional
	@RequestMapping(params = "tag=saveUserRole")
	public String saveUserRole(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblUser user, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call saveUserRole");
		}
		String[] ids = request.getParameterValues("roleIds");
		DwzCallBackModel dm = new DwzCallBackModel();
		if(user == null || user.getUserId() == null || ids == null || ids.length<=0) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			request.setAttribute("rel", rel);
			request.setAttribute("tag", "saveUserRole");
			map.put("TblUser", user);
			return this.toUserRole(request, response, session, map, user, rel);
		}
		//do
		//先删掉所有的,再新增
		roleService.deleteUserRole(user);
		for(String id:ids) {
			TblUserRole ur = new TblUserRole();
			ur.setRoleId(new Integer(id));
			ur.setUserId(user.getUserId());
			roleService.saveUserRole(ur);
		}
		dm.setMessage("用户授权成功！");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/user.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "saveUerRole");
		map.put("TblUser", user);
		return "user/userList";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param userId
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=edit")
	public String editUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("userId") String userId,
			@RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug("------------ call listUser");
		}
		request.setAttribute("rel", rel);
		TblUser user = userService.findUserById(new Integer(userId));
		Map<String, Object> hmap = BeanTool.beanToMap(user);
		map.addAllAttributes(hmap);
		//查找用户其他资料，id对应的name
		TblUser findUser = new TblUser();
		findUser.setStatus(1);
		findUser.setUserId(user.getUserId());
		Map<String, Object> pm = userService.findUsersOtherProperties(findUser);
		map.addAllAttributes(pm);
		request.setAttribute("tag", "update");
		map.put("TblUser", user);
		return "user/user";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param user
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=update")
	public String updateUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblUser user, @RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug("------------ call listUser");
		}
		request.setAttribute("rel", rel);
		DwzCallBackModel dm = new DwzCallBackModel();
		if (user == null || user.getUserId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			dm.setCallbackType("closeCurrent");
			dm.setForwardUrl("/user.do?tag=list&rel=" + rel + "&num=" + Math.random());
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			return "user/userList";
		}
		Map<String, Object> hmap = BeanTool.beanToMap(userService.saveUser(user));
		map.addAllAttributes(hmap);
		dm.setMessage("用户信息修改成功！");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/user.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("tag", "update");
		map.put("TblUser", user);
		return "user/user";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param user
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=updatePwd")
	public String updateUserPwd(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblUser user, @RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug("------------ call listUser");
		}
		request.setAttribute("tag", "updatePwd");
		map.put("TblUser", user);
		DwzCallBackModel dm = new DwzCallBackModel();
		request.setAttribute("rel", rel);
		String oldPwd = request.getParameter("oldPwd");
		oldPwd = new MD5().getMD5ofStr(oldPwd);
		TblUser srcUser = (TblUser) session.getAttribute(ContextDefine.LOGIN_ADMIN);
		if(oldPwd == null || oldPwd.isEmpty() || user.getPasswd() == null || !srcUser.getPasswd().equals(oldPwd)) {
			dm.setMessage("旧密码有误，请重试!");
			dm.setStatusCode("300");
			dm.setCallbackType("closeCurrent");
			dm.setForwardUrl("/user.do?tag=chgPwd&rel=" + rel + "&num=" + Math.random());
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			return "user/chgPwd";
		}
		user.setUserId(srcUser.getUserId());
		if(userService.updateUserPwd(user) <= 0) {
			dm.setMessage("更新失败，请检查数据是否正确提交!");
			dm.setStatusCode("300");
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			return "user/chgPwd";
		}
		dm.setMessage("用户密码更新成功！");
		dm.setStatusCode("200");
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("tag", "updatePwd");
		map.put("TblUser", user);
		return "user/chgPwd";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param user
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=save")
	public String saveUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblUser user, @RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug("------------ call listUser");
		}
		DwzCallBackModel dm = new DwzCallBackModel();
		request.setAttribute("rel", rel);
		String repasswd = request.getParameter("repasswd");
		if (repasswd != null && !repasswd.equals(user.getPasswd())) {
			dm.setMessage("两次输入密码不一致!");
			dm.setStatusCode("300");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			map.put("TblUser", user);
			return "user/user";
		}
		user = userService.saveUser(user);
		dm.setMessage("用户新增成功!");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/user.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		map.put("TblUser", user);
		return "user/user";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param user
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=delete")
	public String delUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblUser user, @RequestParam("rel") String rel) {
		if (log.isDebugEnabled()) {
			log.debug("------------ call listUser");
		}
		request.setAttribute("rel", rel);
		DwzCallBackModel dm = new DwzCallBackModel();
		if (user == null || user.getUserId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			dm.setCallbackType("forward");
			dm.setForwardUrl("/user.do?tag=list&rel=" + rel + "&num=" + Math.random());
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			return "user/userList";
		}
		if (userService.deleteUser(user) > 0) {
			dm.setMessage("用户删除成功!");
			dm.setStatusCode("200");
		} else {
			dm.setMessage("用户删除失败，请重试或联系管理员!");
			dm.setStatusCode("300");
		}
		dm.setCallbackType("forward");
		dm.setForwardUrl("/user.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		map.put("TblUser", user);
		return "user/userList";
	}

	/**
	 * 
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("------------ call initBinder");
		}
		CustomBigDecimalEditor ce = new CustomBigDecimalEditor();
		binder.registerCustomEditor(BigDecimal.class, ce);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(java.util.Date.class, editor);
		binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
		binder.registerCustomEditor(Byte.class, null, new CustomNumberEditor(Byte.class, null, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
}
