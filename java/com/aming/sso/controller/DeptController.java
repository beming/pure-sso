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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.aming.sso.ContextDefine;
import com.aming.sso.entity.TblDepartment;
import com.aming.sso.entity.TblMenu;
import com.aming.sso.entity.TblUser;
import com.aming.sso.model.DwzCallBackModel;
import com.aming.sso.model.PurviewModel;
import com.aming.sso.service.DeptService;
import com.sendtend.util.BeanTool;
import com.sendtend.util.MD5;
import com.sendtend.util.PageUtil;
import com.sendtend.util.StringUtil;

@Controller
@RequestMapping("/dept.do")
public class DeptController extends MultiActionController {
	protected final transient Log log = LogFactory.getLog(DeptController.class);
	@Autowired
	private DeptService deptService;
	
	@RequestMapping
	public String toDept(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam String rel) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toDept:");
		}
		request.setAttribute("rel", rel);
		return "dept/dept";
	}
	
	@RequestMapping(params = "tag=toQuery")
	public String toQueryDepts(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toDepts:");
		}
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String orderDirectionStr = request.getParameter("orderDirection");
		int orderDirection = -1;
		if(orderDirectionStr != null) {
			orderDirection = Integer.parseInt(orderDirectionStr);
		}
		String depName = request.getParameter("depName");
		TblDepartment dep = new TblDepartment();
		if(depName != null  && !depName.isEmpty()) {
			dep.setDepName(depName);
		}
 		int thePage = new Integer(pageNum==null?"1":pageNum);
		int pageSize = new Integer(numPerPage==null?"20":numPerPage);
		PageUtil pu = deptService.listDepts(dep, orderDirectionStr, thePage, pageSize);
		pu.setOrderDirection(orderDirection);
		request.setAttribute("PageUtil", pu);
		return "dept/queryDepts";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=list")
	public String listDept(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblDepartment dept, @RequestParam String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listDept");
		}
		request.setAttribute("rel", rel);
		if(dept.getDepName() != null && dept.getDepName().isEmpty()) {
			dept.setDepName(null);
		}
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String orderDirection = request.getParameter("orderDirection");
		int thePage = new Integer(pageNum==null?"1":pageNum);
		int pageSize = new Integer(numPerPage==null?"20":numPerPage);
		if(request.getParameter("depStatus") == null && dept.getDepStatus() == null) {
			dept.setDepStatus(new Byte("1"));
		}
		PageUtil pu = deptService.listDepts(dept, orderDirection, thePage, pageSize);
		Map<String, Object> hmap = BeanTool.beanToMap(pu);
		map.addAllAttributes(hmap);
		request.setAttribute("PageUtil", pu);
		map.put("TblDepartment", dept);
		return "dept/deptList";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=listDepts")
	public @ResponseBody String findDept(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblDepartment dep) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listDept");
		}
		String keyword = request.getParameter("keyword");
		if(keyword != null && !keyword.isEmpty()) {
			keyword = "%" + keyword + "%";
			dep.setDepName(keyword);
		}
		if(request.getParameter("status") == null && dep.getDepStatus() == null) {
			dep.setDepStatus(new Byte("1"));
		}
		List<?> list = deptService.findDeptsByExample(dep);
		PropertyFilter filter = new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
		        if("depId".equals(name)) {
		            return true;
		        }
		        if("depName".equals(name)) {
		            return true;
		        }
		        return false;
		    }
		}; 
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.getPropertyFilters().add(filter);
		serializer.write(list);
		log.debug("----------" + out.toString());
		return StringUtil.toChartset(out.toString(), "UTF-8", "ISO8859-1");
	}
	
	@RequestMapping(params = "tag=deptTreeLookup")
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblDepartment dep) {
		/*
		 * 获取部门tree
		 */
		//处理业务菜单
		PageUtil pu = deptService.listDepts(dep, "depId", -1, 100);
		List<TblDepartment> tmpList = (List<TblDepartment>) pu.getDataSet();
		StringBuffer cateHtml = new StringBuffer("");
		//子循环标志
		boolean subR = true;
		boolean hasSub = false;
		//boolean top3L = false;
		boolean top1Sub = false;
		boolean top1flag = false;
		for(TblDepartment tm:tmpList) {
			top1Sub = false;
			top1flag = true;
			
				cateHtml.append("</li>");
		}
		if(log.isDebugEnabled()) {
			log.debug("-----------------get menu tree result:");
			log.debug(cateHtml);
		}
		request.setAttribute("cateHtml", cateHtml);
		return "dept/deptTreeLookup";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=add")
	public String addDept(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listDept");
		}
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "save");
		TblDepartment dept = new TblDepartment();
		dept.setDepStatus(new Byte("1"));
		map.put("TblDepartment", dept);
		return "dept/dept";
	}
	
	@RequestMapping(params = "tag=edit")
	public String editDept(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam String depId, @RequestParam String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listDept");
		}
		request.setAttribute("rel", rel);
		TblDepartment dept = deptService.findDeptById(new Integer(depId));
		Map<String, Object> hmap = BeanTool.beanToMap(dept);
		map.addAllAttributes(hmap);
		request.setAttribute("tag", "update");
		map.put("TblDepartment", dept);
		return "dept/dept";
	}
	
	@RequestMapping(params = "tag=update")
	public String updateDept(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblDepartment dept, @RequestParam String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listDept");
		}
		request.setAttribute("rel", rel);
		DwzCallBackModel dm = new DwzCallBackModel();
		if(dept == null || dept.getDepId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			request.setAttribute("tag", "update");
			map.put("TblDepartment", dept);
			return "dept/dept";
		}
		Map<String, Object> hmap = BeanTool.beanToMap(deptService.saveDept(dept));
		map.addAllAttributes(hmap);
		dm.setStatusCode("200");
		dm.setMessage("部门信息修改成功！");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/dept.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("tag", "update");
		map.put("TblDepartment", dept);
		return "dept/dept";
	}
	
	@RequestMapping(params = "tag=save")
	public String saveDept(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblDepartment dept, @RequestParam String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listDept");
		}
		request.setAttribute("rel", rel);
		DwzCallBackModel dm = new DwzCallBackModel();
		dept = deptService.saveDept(dept);
		dm.setMessage("部门新增成功!");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/dept.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		map.put("TblDepartment", dept);
		return "dept/dept";
	}
	
	@RequestMapping(params = "tag=delete")
	public String delDept(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblDepartment dept, @RequestParam String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listDept");
		}
		request.setAttribute("rel", rel);
		DwzCallBackModel dm = new DwzCallBackModel();
		if(dept == null || dept.getDepId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			dm.setCallbackType("forward");
			dm.setForwardUrl("/dept.do?tag=list&rel=" + rel + "&num=" + Math.random());
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			return "dept/deptList";
		}
		if(deptService.deleteDept(dept) >0) {
			dm.setMessage("部门删除成功!");
			dm.setStatusCode("200");
		} else {
			dm.setMessage("部门删除失败，请重试或联系管理员!");
			dm.setStatusCode("300");
		}
		dm.setCallbackType("forward");
		dm.setForwardUrl("/dept.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		map.put("TblDepartment", dept);
		return "dept/deptList";
	}
}
