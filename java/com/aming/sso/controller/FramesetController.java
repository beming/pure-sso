package com.aming.sso.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.aming.sso.ContextDefine;
import com.aming.sso.model.DwzCallBackModel;
import com.aming.sso.model.PurviewModel;
import com.aming.sso.model.UserSysModel;
import com.sendtend.util.PageUtil;

@Controller
@RequestMapping("/frameset.do")
public class FramesetController extends MultiActionController {
	protected final transient Log log = LogFactory.getLog(FramesetController.class);
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "tag=dialogLogin")
	public String dialogLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		//如果session已经有了，就不在查询了
		return "admin/login_dialog";
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "tag=sidebar_1")
	public String sidebarOne(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		//如果session已经有了，就不在查询了
		return "admin/sidebar_1";
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "tag=sidebar_2")
	public String sidebarTwo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		//如果session已经有了，就不在查询了
		return "admin/sidebar_2";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param sysId
	 * @return
	 */
	@RequestMapping(params = "tag=listPostInfo")
	public String refer(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("sysId") String sysId) {
		boolean hasSys = false;
		if(sysId != null && !sysId.trim().isEmpty()) {
			PurviewModel pm = (PurviewModel) session.getAttribute(ContextDefine.LOGIN_USER_RIGHT);
			List<UserSysModel> sysList = pm.getSysSet();
			for(UserSysModel usm:sysList) {
				if(usm.getSys_id().intValue() == new Integer(sysId).intValue()) {
					hasSys = true;
					request.setAttribute("sys", usm);
					map.addAttribute(usm);
					map.addAttribute("sys", usm);
					//log.info(" ------url: " + usm.getSys_url());
					/*
					String location = usm.getSys_url() + "?" + usm.getLogin_field()+"=" + usm.getSysuser_id() + "&" + usm.getPwd_field() + "=" + usm.getSysuser_pwd();
					log.info("------forward url:" + location);
					try {
						response.sendRedirect(location);
					} catch (IOException e) {
						e.printStackTrace();
					}
					*/
					break;
				}
			}
		}
		if(!hasSys) {
			DwzCallBackModel dm = new DwzCallBackModel();
			dm.setStatusCode("300");
			dm.setMessage("单点系统不存在，请联系管理员！");
			dm.setCallbackType(DwzCallBackModel.callbackType_CLOSECURRENT);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		}
		return "admin/forward";
	}
}
