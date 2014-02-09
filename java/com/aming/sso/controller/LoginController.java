package com.aming.sso.controller;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.aming.sso.ContextDefine;
import com.aming.sso.entity.TblMenu;
import com.aming.sso.entity.TblUser;
import com.aming.sso.model.DwzCallBackModel;
import com.aming.sso.model.PurviewModel;
import com.aming.sso.service.InitContext;
import com.aming.sso.service.UserService;
import com.sendtend.util.IdentifyingCode;
import com.sendtend.util.MD5;
import com.sendtend.util.PageUtil;

@Controller
@RequestMapping("/login.do")
public class LoginController extends MultiActionController {
	protected final transient Log log = LogFactory.getLog(LoginController.class);
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping
	public String toLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toLogin:");
		}
		return "adminLogin";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param loginName
	 */
	@RequestMapping(params = "tag=queryUserByLoginName")
	public @ResponseBody Object findUserByLognName(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam("loginName") String loginName) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toAjaxLogin:");
		}
		DwzCallBackModel dm = new DwzCallBackModel();
		if(loginName == null || loginName.isEmpty()) {
			dm.setStatusCode("300");
			dm.setMessage("请输入登录名");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		}
		TblUser user = new TblUser();
		user.setLoginName(loginName);
		List<?> list = userService.findUserByExample(user);
		if(list != null && list.size()>0) {
			return "该登录名已存在！";
		}
		return new Boolean(true);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param user
	 */
	@RequestMapping(params = "tag=ajaxLogin")
	public void ajaxLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, TblUser user) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toAjaxLogin:");
		}
		DwzCallBackModel dm = new DwzCallBackModel();
		if(user == null || user.getLoginName() == null || user.getPasswd() == null) {
			dm.setStatusCode("300");
			dm.setMessage("请输入用户名和密码");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		}
		user.setPasswd(new MD5().getMD5ofStr(user.getPasswd()));
		List<?> list = userService.findUserByExample(user);
		TblUser suser = (TblUser) (list==null||list.isEmpty()?null:list.get(0));
		if(suser == null) {
			dm.setStatusCode("300");
			dm.setMessage("用户名或密码有误，请重新输入");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		}
		/*
		 * 获取用户菜单和权限
		 */
		PurviewModel pm = userService.listUserRight(suser);
		session.setAttribute(ContextDefine.LOGIN_USER_RIGHT, pm);
		request.setAttribute("userMenu", pm);
		session.setAttribute(ContextDefine.LOGIN_ADMIN, suser);
		dm.setStatusCode("200");
		dm.setCallbackType(DwzCallBackModel.callbackType_CLOSECURRENT);
		/*
		 * set入sid
		 */
		/*
		String sid = session.getId();
		userService.setSidByUser(suser, sid);
		*/
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
	}
	
	/**
	 * 学院学生毕业考核数据
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=login")
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session, TblUser user) {
		if(user == null || user.getLoginName() == null || user.getPasswd() == null) {
			request.setAttribute("msg", "请输入用户名和密码信息");
			return "adminLogin";
		}
		user.setPasswd(new MD5().getMD5ofStr(user.getPasswd()));
		List<?> list = userService.findUserByExample(user);
		TblUser suser = (TblUser) (list==null||list.isEmpty()?null:list.get(0));
		if(suser == null) {
			request.setAttribute("msg", "用户名或密码有误，请重试");
			return "adminLogin";
		}
		if(log.isDebugEnabled()) {
			log.debug("--------user:" + suser.getLoginName() + "登录成功!");
		}
		/*
		 * 获取用户菜单和权限
		 */
		PurviewModel pm = userService.listUserRight(suser);
		//处理业务菜单
		List<TblMenu> tmList = pm.getMenuSet();
		StringBuffer cateHtml = new StringBuffer("");
		//cateHtml.append("<li><a href=\"javascript:\" onclick=\"$.bringBack({parentId:'0', parentMenuName:'顶模块', menuLevel:'1'})\">顶模块</a>");
		//Iterator<Integer> key = mm.keySet().iterator();
		//cateHtml.append("<ul>");
		//子循环标志
		boolean subR = true;
		boolean hasSub = false;
		//boolean top3L = false;
		boolean top1Sub = false;
		boolean top1flag = false;
		for(TblMenu tm:tmList) {
			//MenuItem mi = new MenuItem(tm.getMenuId(), tm.getMenuName(), tm.getMenuLevel(), tm.getParentId(), tm.getNavcode());
			//mm.put(tm.getMenuId(), mi);
			//miList.add(mi);
			//top3L = false;
			top1Sub = false;
			top1flag = true;
			if(tm.getParentId().intValue() == 0 && tm.getTopMenuFlag().intValue() == 1) {
				if(tm.getMenuUrl() == null || tm.getMenuUrl().trim().length() <= 0) {
					cateHtml.append("<li><a href=\"" + tm.getMenuUrl() + "&rel=\"" + tm.getNavcode() + "\" target=\"navTab\" rel=\""+ tm.getNavcode() + "\">" + tm.getMenuName() + "</a>");
				} else {
					cateHtml.append("<li><a href=\"javascript:;\">" + tm.getMenuName() + "</a>");
				}
				for(TblMenu sub:tmList) {
					log.debug("-----------11111111111----------" + sub.getMenuUrl());
					if(sub.getParentId().intValue() == tm.getMenuId().intValue()) {
						top1Sub = true;
						if(top1flag) {
							cateHtml.append("<ul>");
							top1flag = false;
						}
						if(sub.getMenuUrl() == null || sub.getMenuUrl().trim().length() <= 0) {
							cateHtml.append("<li><a href=\"" + sub.getMenuUrl() + "\" target=\"navTab\" rel=\""+ sub.getNavcode() + "\">" + sub.getMenuName() + "</a>");
						} else {
							cateHtml.append("<li><a href=\"javascript:;\">" + sub.getMenuName() + "</a>");
						}
						subR = true;
						hasSub = false;
						for(TblMenu tmp:tmList) {
							if(tmp.getParentId().intValue() != 0 && tmp.getParentId().intValue() == sub.getMenuId().intValue()) {
								//top3L = true;
								hasSub = true;
								if(subR) {
									cateHtml.append("<ul>");
									subR = false;
								}
								
								if(tmp.getMenuUrl() == null || tmp.getMenuUrl().trim().length() <= 0) {
									cateHtml.append("<li><a href=\"" + tmp.getMenuUrl() + "\" target=\"navTab\" rel=\""+ tmp.getNavcode() + "\">" + tmp.getMenuName() + "</a></li>");
								} else {
									cateHtml.append("<li><a href=\"javascript:;\">" + tmp.getMenuName() + "</a>");
								}
							}
						}
						if(hasSub) {
							cateHtml.append("</ul>");
							hasSub = false;
						}
					}
					if(top1Sub) {
						cateHtml.append("</ul>");
						top1Sub = false;
					}
				}
				cateHtml.append("</li>");
			}
		}
		if(log.isDebugEnabled()) {
			log.debug("-----------------get menu tree result:");
			log.debug(cateHtml);
		}
		if(log.isDebugEnabled()) {
			log.debug("------ user Purview model data:" + pm!=null?pm.toString():"null");
		}
		request.setAttribute("cateHtml", cateHtml);
		session.setAttribute(ContextDefine.LOGIN_USER_RIGHT, pm);
		request.setAttribute("userMenu", pm);
		session.setAttribute(ContextDefine.LOGIN_ADMIN, suser);
		/*
		 * set sid
		 */
		/*
		String sid = session.getId();
		if(log.isDebugEnabled()) {
			log.debug("----------- sid:" + sid);
		}
		int i = userService.setSidByUser(suser, sid);
		if(log.isDebugEnabled()) {
			log.debug("----------- update sid result:" + i);
		}
		*/
		return "admin/index";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=getIdentifyCode", method=RequestMethod.POST)
	@ResponseBody
	public void getIdentifyCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		//设置不缓存图片  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "No-cache");  
        response.setDateHeader("Expires", 0) ;  
        //指定生成的相应图片  
        response.setContentType("image/jpeg") ;  
        IdentifyingCode idCode = new IdentifyingCode();  
        BufferedImage image =new BufferedImage(idCode.getWidth() , idCode.getHeight() , BufferedImage.TYPE_INT_BGR) ;  
        Graphics2D g = image.createGraphics() ;  
        //定义字体样式  
        Font myFont = new Font("黑体" , Font.BOLD , 16) ;  
        //设置字体  
        g.setFont(myFont) ;  
          
        g.setColor(idCode.getRandomColor(200 , 250)) ;  
        //绘制背景  
        g.fillRect(0, 0, idCode.getWidth() , idCode.getHeight()) ;  
          
        g.setColor(idCode.getRandomColor(180, 200)) ;  
        idCode.drawRandomLines(g, 160) ;  
        String idStr = idCode.drawRandomString(4, g) ;  
        g.dispose() ;  
        if(log.isDebugEnabled()) {
        	log.debug("----- identify Font:" + idStr);
        }
        session.setAttribute(ContextDefine.IDENTIFY_CODE, idStr);
        try {
			ImageIO.write(image, "JPEG", response.getOutputStream()) ;
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param user
	 * @param idVeri
	 */
	@RequestMapping(params = "tag=getPwd",method=RequestMethod.POST)
	public void getPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam("idVeri") String idVeri) {
		if(log.isInfoEnabled()) {
			log.info("用户提交登录ajax");
		}
		String rstr = null;
		String openGetPwd = (String)InitContext.getInstance().getCtrlDataByTag(session, ContextDefine.OPENGETPWD);
		if(openGetPwd.equalsIgnoreCase("false")) {
			rstr = "0系统禁止获取用户原密码!";
			PageUtil.writeMsgToClient(response, rstr);
			return;
		}
		return;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=logout",method=RequestMethod.GET)
	public String logOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.invalidate();
		return "adminLogin";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=kickedOut",method=RequestMethod.GET)
	public void kickedOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		PageUtil.writeMsgToClient(response, "0kickedOut");
		session.invalidate();
	}
}
