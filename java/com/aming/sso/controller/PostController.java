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
import com.aming.sso.entity.TblPost;
import com.aming.sso.model.DwzCallBackModel;
import com.aming.sso.service.PostService;
import com.sendtend.util.BeanTool;
import com.sendtend.util.PageUtil;
import com.sendtend.util.StringUtil;

@Controller
@RequestMapping("/post.do")
public class PostController extends MultiActionController {
	protected final transient Log log = LogFactory.getLog(PostController.class);
	@Autowired
	private PostService postService;
	
	@RequestMapping
	public String toPost(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toPost:");
		}
		request.setAttribute("rel", rel);
		return "post/post";
	}
	
	@RequestMapping(params = "tag=toQuery")
	public String toQueryPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		if(log.isDebugEnabled()) {
			log.debug(" ----- call toPost:");
		}
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String orderDirectionStr = request.getParameter("orderDirection");
		int orderDirection = -1;
		if(orderDirectionStr != null) {
			orderDirection = Integer.parseInt(orderDirectionStr);
		}
		String postName = request.getParameter("postName");
		TblPost post = new TblPost();
		if(postName != null  && !postName.isEmpty()) {
			post.setPostName(postName);
		}
 		int thePage = new Integer(pageNum==null?"1":pageNum);
		int pageSize = new Integer(numPerPage==null?"20":numPerPage);
		PageUtil pu = postService.listPost(post, orderDirectionStr, thePage, pageSize);
		pu.setOrderDirection(orderDirection);
		request.setAttribute("PageUtil", pu);
		return "post/queryPost";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=list")
	public String listPost(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblPost post, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listPost, postName:" + post.getPostName());
		}
		if(post.getPostName() != null && post.getPostName().isEmpty()) {
			post.setPostName(null);
		}
		request.setAttribute("rel", rel);
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String orderField = request.getParameter("orderDirection");
		int thePage = new Integer(pageNum==null?"1":pageNum);
		int pageSize = new Integer(numPerPage==null?"20":numPerPage);
		if(request.getParameter("postStatus") == null && post.getPostStatus() == null) {
			post.setPostStatus(new Byte("1"));
		}
		PageUtil pu = postService.listPost(post, orderField, thePage, pageSize);
		Map<String, Object> hmap = BeanTool.beanToMap(pu);
		map.addAllAttributes(hmap);
		request.setAttribute("PageUtil", pu);
		map.put("TblPost", post);
		return "post/postList";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=listPosts")
	public @ResponseBody String findPost(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblPost post) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listPost");
		}
		String keyword = request.getParameter("keyword");
		if(keyword != null && !keyword.isEmpty()) {
			keyword = "%" + keyword + "%";
			post.setPostName(keyword);
		}
		List<?> list = postService.findPostByExample(post);
		PropertyFilter filter = new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
		        if("postId".equals(name)) {
		            return true;
		        }
		        if("postName".equals(name)) {
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
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(params = "tag=add")
	public String addPost(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listPost");
		}
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "save");
		TblPost post = new TblPost();
		post.setPostStatus(new Byte("1"));
		map.put("TblPost", post);
		return "post/post";
	}
	
	@RequestMapping(params = "tag=edit")
	public String editPost(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, @RequestParam("postId") String postId, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listPost");
		}
		request.setAttribute("rel", rel);
		TblPost post = postService.findPostById(new Integer(postId));
		Map<String, Object> hmap = BeanTool.beanToMap(post);
		map.addAllAttributes(hmap);
		request.setAttribute("tag", "update");
		map.put("TblPost", post);
		return "post/post";
	}
	
	@RequestMapping(params = "tag=update")
	public String updatePost(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblPost post, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listPost");
		}
		DwzCallBackModel dm = new DwzCallBackModel();
		if(post == null || post.getPostId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			request.setAttribute("rel", rel);
			request.setAttribute("tag", "update");
			map.put("TblPost", post);
			return "post/post";
		}
		Map<String, Object> hmap = BeanTool.beanToMap(postService.savePost(post));
		map.addAllAttributes(hmap);
		dm.setMessage("岗位信息修改成功！");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/post.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("rel", rel);
		request.setAttribute("tag", "update");
		map.put("TblPost", post);
		return "post/post";
	}
	
	@RequestMapping(params = "tag=save")
	public String savePost(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblPost post, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listPost");
		}
		DwzCallBackModel dm = new DwzCallBackModel();
		post = postService.savePost(post);
		dm.setMessage("岗位新增成功!");
		dm.setStatusCode("200");
		dm.setCallbackType("closeCurrent");
		dm.setForwardUrl("/post.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("rel", rel);
		map.put("TblPost", post);
		return "post/post";
	}
	
	@RequestMapping(params = "tag=delete")
	public String delPost(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map, TblPost post, @RequestParam("rel") String rel) {
		if(log.isDebugEnabled()) {
			log.debug("------------ call listPost");
		}
		DwzCallBackModel dm = new DwzCallBackModel();
		if(post == null || post.getPostId() == null) {
			dm.setMessage("信息缺失，请重试!");
			dm.setStatusCode("300");
			dm.setCallbackType("forward");
			dm.setForwardUrl("/post.do?tag=list&rel=" + rel + "&num=" + Math.random());
			dm.setRel(rel);
			dm.setNavTabId(rel);
			PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
			return "post/postList";
		}
		if(postService.delPost(post) >0) {
			dm.setMessage("岗位删除成功!");
			dm.setStatusCode("200");
		} else {
			dm.setMessage("岗位删除失败，请重试或联系管理员!");
			dm.setStatusCode("300");
		}
		dm.setCallbackType("forward");
		dm.setForwardUrl("/post.do?tag=list&rel=" + rel + "&num=" + Math.random());
		dm.setRel(rel);
		dm.setNavTabId(rel);
		PageUtil.writeMsgToClient(response, JSON.toJSONString(dm));
		request.setAttribute("rel", rel);
		map.put("TblPost", post);
		return "post/postList";
	}
}
