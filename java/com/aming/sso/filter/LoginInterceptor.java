package com.aming.sso.filter;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.alibaba.fastjson.JSON;
import com.aming.sso.ContextDefine;
import com.aming.sso.ControlDefine;
import com.aming.sso.controller.FramesetController;
import com.aming.sso.controller.LoginController;
import com.aming.sso.entity.TblMenu;
import com.aming.sso.entity.TblRoleMenu;
import com.aming.sso.model.DwzCallBackModel;
import com.aming.sso.model.PurviewModel;
import com.sendtend.util.PageUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	protected final transient Log log = LogFactory.getLog(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(log.isDebugEnabled()) {
			log.debug("----------------- login filter!!" + handler.toString());
		}
		//st framework's feature
		HttpSession session = request.getSession();
		Properties prop = (Properties) session.getServletContext().getAttribute(ControlDefine.CTRL_DATA);
		String InterceptorIngorClassStr = prop.getProperty(ControlDefine.INTERCEPTOR_INGOR_CLASS);
		if(InterceptorIngorClassStr != null && !InterceptorIngorClassStr.isEmpty()) {
			String[] clzzs = InterceptorIngorClassStr.split(",");
			for(String clzz:clzzs) {
				if(clzz.length()<5) {
					continue;
				}
				Class<?> clazz = Class.forName(clzz);
				if(clazz.isInstance(handler)) {
					if(log.isDebugEnabled()) {
						log.debug("---- yes! can access url instance: " + clzz);
					}
					return true;
				}
			}
		}
		String InterceptorIngorSessionStr = prop.getProperty(ControlDefine.INTERCEPTOR_INGOR_SESSION);
		if(InterceptorIngorSessionStr != null && !InterceptorIngorSessionStr.isEmpty()) {
			String[] iisStr = InterceptorIngorSessionStr.split(",");
			for(String iis:iisStr) {
				if(iis.length()<=1) {
					continue;
				}
				if(session.getAttribute(iis) != null) {
					return true;
				}
			}
		}
		String forwardUrl = prop.getProperty(ControlDefine.INTERCEPTOR_FORWARDURL);
		if(forwardUrl != null && forwardUrl.length()>3) {
			response.sendRedirect(forwardUrl);
			return false;
		}
		//end of st framework's feature
		if(request.getSession() != null && request.getSession().getAttribute(ContextDefine.LOGIN_ADMIN) != null) {
			PurviewModel pm = (PurviewModel) request.getSession().getAttribute(ContextDefine.LOGIN_USER_RIGHT);
			List<TblMenu> funcList = pm.getRightSet();
			List<TblRoleMenu> rmList = pm.getRoleMenuSet();
			String tag = request.getParameter("tag");
			String reqUrl = request.getRequestURI();
			if(log.isDebugEnabled()) {
				log.debug(" -------- REQ URL: " + reqUrl);
				log.debug("--------- req tag: " + tag);
			}
			boolean hasRight = false;
			Integer menuId = null;
			for(TblMenu menu:funcList) {
				//如果URL能访问
				if(menu.getMenuUrl().startsWith(reqUrl)) {
					menuId = menu.getMenuId();
					//是否有授权增删改查
					for(TblRoleMenu rm:rmList) {
						if(menuId.intValue() == rm.getMenuId().intValue()) {
							if(tag.startsWith("add") || tag.startsWith("save") || ContextDefine.RIGHT_ADD.contains(tag)) {
								if(rm.getRAdd().intValue() == 1) {
									hasRight = true;
									break;
								}
							}
							if(tag.startsWith("del") || tag.startsWith("batchDel") || ContextDefine.RIGHT_DEL.contains(tag)) {
								if(rm.getRDel().intValue() == 1) {
									hasRight = true;
									break;
								}
							}
							if(tag.startsWith("list") || tag.startsWith("query") || ContextDefine.RIGHT_SELECT.contains(tag)) {
								if(rm.getRSelect().intValue() == 1) {
									hasRight = true;
									break;
								}
							}
							if(tag.startsWith("update") || tag.startsWith("edit") || tag.startsWith("grant") || ContextDefine.RIGHT_UPDATE.contains(tag)) {
								if(rm.getRUpdate().intValue() == 1) {
									hasRight = true;
									break;
								}
							}
						}
					}
					if(hasRight) {
						break;
					}
				}
			}
			if(hasRight) {
				return true;
			} else {
				DwzCallBackModel dm = new DwzCallBackModel();
				dm.setStatusCode("300");
				dm.setMessage("权限不足，请联系管理员!");
				PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
				return false;
			}
		} else {
			if(handler instanceof FramesetController) {
				if(request.getParameter("tag") != null && request.getParameter("tag").equals("dialogLogin")) {
					return true;
				}
			} else if(handler instanceof DefaultServletHttpRequestHandler) {
				response.sendRedirect("/login.do");
				return false;
			}
			//response.sendRedirect("/login.do?method=logout");
			DwzCallBackModel dm = new DwzCallBackModel();
			dm.setStatusCode("301");
			dm.setMessage("登录已超时，请重新登录!");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			return false;
		}
	}
}
