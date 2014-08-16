package com.aming.sso.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.entity.TblPost;
import com.sendtend.util.BeanTool;
import com.sendtend.util.PageUtil;

@Service
@Transactional
public class PostService {
	protected final transient Log log = LogFactory.getLog(PostService.class);

	@Autowired
	private BaseDaoImpl entityDao;

	/**
	 */
	public TblPost findPostById(Integer postId) {
		if(postId == null) {
			return null;
		}
		return entityDao.findEntityById("postId", postId, TblPost.class);
	}
	
	public List<?> findPostByExample(TblPost post) {
		return entityDao.queryByExample(post, true, "postName");
	}

	/**
	 * Delete an existing User entity
	 * @param User
	 */
	public int delPost(TblPost post) {
		if(post == null || post.getPostId() == null) {
			return 0;
		}
		String sql = "update tbl_post set post_status=0 where post_id=:postId";
		return entityDao.executeSQL(sql, post);
	}


	/**
	 * Save an existing User entity
	 * @param User
	 */
	public TblPost savePost(TblPost post) {
		if(post == null) {
			return null;
		}
		TblPost existingUser = this.findPostById(post.getPostId());

		if (existingUser != null) {
			if (existingUser != post) {
				try {
					BeanTool.copyProperties(existingUser, post);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			entityDao.update(existingUser);
			return existingUser;
		} else {
			post = (TblPost)entityDao.save(post);
			return post;
		}
	}
	
	public PageUtil listPost(TblPost post, String orderField, Integer thePage, Integer pageSize) {
		PageUtil pu = new PageUtil();
		if(orderField == null || orderField.isEmpty()) {
			orderField = "postName";
		}
		List<?> list = entityDao.queryByExample(post, true, orderField);
		pu.setDataSet(list);
		int count = new Integer(entityDao.createQuery("select count(o) from TblPost o", 1, -1).get(0).toString());
		pu.setTotalCount(count);
		return pu;
	}
}
