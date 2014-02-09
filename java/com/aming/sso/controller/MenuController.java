package com.aming.sso.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.aming.sso.entity.TblMenu;
import com.aming.sso.model.DwzCallBackModel;
import com.aming.sso.service.MenuService;
import com.sendtend.util.BeanTool;
import com.sendtend.util.PageUtil;

@Controller
@RequestMapping("/menu.do")
public class MenuController extends MultiActionController {
	protected final transient Log log = LogFactory.getLog(MenuController.class);
	@Autowired
	private MenuService menuService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param rel
	 * @return
	 */
	@RequestMapping
	public String toMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toMenu:");
		}
		request.setAttribute("rel", rel);
		return "menu/menu";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param menu
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=list")
	public String listMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblMenu menu) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listMenu");
		}
		String rel = request.getParameter("rel");
		request.setAttribute("rel", rel);
		if(menu.getMenuName() != null && menu.getMenuName().isEmpty()) {
			menu.setMenuName(null);
		}
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		int thePage = new Integer(pageNum==null?"1":pageNum);
		int pageSize = new Integer(numPerPage==null?"20":numPerPage);
		if(request.getParameter("menuStatus") == null && menu.getMenuStatus() == null) {
			menu.setMenuStatus(1);
		}
		PageUtil pu = menuService.listMenus(menu, thePage, pageSize);
		Map<String, Object> hmap = BeanTool.beanToMap(pu);
		if(log.isDebugEnabled()) {
			log.debug("------------to view menu's data:" + hmap);
		}
		map.addAllAttributes(hmap);
		request.setAttribute("PageUtil", pu);
		map.put("TblMenu", menu);
		return "menu/menuList";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping(params = "tag=treeList")
	public String listTreeMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listMenu");
		}
		TblMenu menu = new TblMenu();
		menu.setMenuStatus(1);
		menu.setIsMenu(1);
		@SuppressWarnings("unchecked")
		List<TblMenu> list = (List<TblMenu>) menuService.listTreeMenus(menu, 0, -1).getDataSet();
		//List<MenuItem> miList = new ArrayList<MenuItem>();
		//Map<Integer, MenuItem> mm = new HashMap<Integer, MenuItem>();
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
		for(TblMenu tm:list) {
			//MenuItem mi = new MenuItem(tm.getMenuId(), tm.getMenuName(), tm.getMenuLevel(), tm.getParentId(), tm.getNavcode());
			//mm.put(tm.getMenuId(), mi);
			//miList.add(mi);
			//top3L = false;
			top1Sub = false;
			top1flag = true;
			if(tm.getParentId().intValue() == 0) {
				cateHtml.append("<li><a href=\"javascript:\" onclick=\"$.bringBack({parentId:'" + tm.getMenuId() 
						+ "', parentMenuName:'" + tm.getMenuName() + "', menuLevel:'" + (tm.getMenuLevel()) + "'})\">" + tm.getMenuName() + "</a>");
				for(TblMenu sub:list) {
					if(sub.getParentId().equals(tm.getMenuId())) {
						top1Sub = true;
						if(top1flag) {
							cateHtml.append("<ul>");
							top1flag = false;
						}
						cateHtml.append("<li><a href=\"javascript:\" onclick=\"$.bringBack({parentId:'" + sub.getMenuId() 
								+ "', parentMenuName:'" + sub.getMenuName() + "', menuLevel:'" + (sub.getMenuLevel()+1) + "'})\">" + sub.getMenuName() + "</a>");
						subR = true;
						hasSub = false;
						for(TblMenu tmp:list) {
							if(tmp.getParentId().intValue() != 0 && tmp.getParentId().intValue() == sub.getMenuId().intValue()) {
								//top3L = true;
								hasSub = true;
								if(subR) {
									cateHtml.append("<ul>");
									subR = false;
								}
								cateHtml.append("<li><a href=\"javascript:\" onclick=\"$.bringBack({parentId:'" + tmp.getMenuId() 
										+ "', parentMenuName:'" + tmp.getMenuName() + "', menuLevel:'" + (tmp.getMenuLevel()+2) + "'})\">" + tmp.getMenuName() + "</a></li>");
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
		
		//cateHtml.append("</ul></li>");
		if(log.isDebugEnabled()) {
			log.debug("-----------------get menu tree result:");
			log.debug(cateHtml);
		}
		
		request.setAttribute("cateHtml", cateHtml);
		return "menu/menuTreeList";
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
	@RequestMapping(params = "tag=add")
	public String addMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listMenu");
		}
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "save");
		TblMenu menu = new TblMenu();
		menu.setMenuStatus(1);
		menu.setIsMenu(1);
		menu.setTopMenuFlag(1);
		map.put("TblMenu", menu);
		return "menu/menu";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param menuId
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=edit")
	public String editMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("menuId") String menuId, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listMenu");
		}
		request.setAttribute("rel", rel);
		TblMenu menu = menuService.findMenuById(new Integer(menuId));
		Map<String, Object> hmap = BeanTool.beanToMap(menu);
		if(menu.getParentId().intValue() != 0) {
			TblMenu tmp = menuService.findMenuById(menu.getParentId());
			request.setAttribute("parentMenuName", tmp.getMenuName());
		} else {
			request.setAttribute("parentMenuName", "顶模块");
		}
		map.addAllAttributes(hmap);
		request.setAttribute("tag", "update");
		map.put("TblMenu", menu);
		return "menu/menu";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param menu
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=update")
	public String updateMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblMenu menu, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listMenu");
		}
		request.setAttribute("rel", rel);
		DwzCallBackModel dm = new DwzCallBackModel();
		if(menu == null || menu.getMenuId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			dm.setCallbackType("closeCurrent");
			dm.setForwardUrl("/menu.do?tag=list&rel=" + rel + "&num=" + Math.random());
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			request.setAttribute("tag", "update");
			map.put("TblMenu", menu);
			return "menu/menu";
		}
		if(menu == null || menu.getMenuLevel().intValue() >= 3) {
			dm.setMessage("系统暂最大支持三层子菜单！");
			dm.setStatusCode("300");
			dm.setCallbackType("closeCurrent");
			dm.setForwardUrl("/menu.do?tag=list&rel=" + rel + "&num=" + Math.random());
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			request.setAttribute("tag", "update");
			map.put("TblMenu", menu);
			return "menu/menu";
		}
		Map<String, Object> hmap = BeanTool.beanToMap(menuService.saveMenu(menu));
		map.addAllAttributes(hmap);
		dm.setStatusCode("200");
		dm.setMessage("菜单信息修改成功！");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/menu.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("tag", "update");
		map.put("TblMenu", menu);
		return "menu/menu";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param menu
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=save")
	public String saveMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblMenu menu, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listMenu" + menu.toString());
		}
		request.setAttribute("rel", rel);
		DwzCallBackModel dm = new DwzCallBackModel();
		if(menu == null || menu.getMenuLevel().intValue() >= 3) {
			dm.setMessage("系统暂最大支持三层子菜单！");
			dm.setStatusCode("300");
			dm.setCallbackType("closeCurrent");
			dm.setForwardUrl("/menu.do?tag=list&rel=" + rel + "&num=" + Math.random());
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			request.setAttribute("tag", "add");
			map.put("TblMenu", menu);
			return "menu/menu";
		}
		menu = menuService.saveMenu(menu);
		dm.setMessage("菜单新增成功!");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/menu.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		map.put("TblMenu", menu);
		return "menu/menu";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @param menu
	 * @param rel
	 * @return
	 */
	@RequestMapping(params = "tag=delete")
	public String delMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblMenu menu, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listMenu");
		}
		request.setAttribute("rel", rel);
		DwzCallBackModel dm = new DwzCallBackModel();
		if(menu == null || menu.getMenuId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			return "menu/menuList";
		}
		if(menuService.deleteMenu(menu) >0) {
			dm.setMessage("菜单删除成功!");
			dm.setStatusCode("200");
		} else {
			dm.setMessage("菜单删除失败，请重试或联系管理员!");
			dm.setStatusCode("300");
		}
		dm.setCallbackType("forward");
		dm.setForwardUrl("/menu.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		map.put("TblMenu", menu);
		return "menu/menuList";
	}
}
