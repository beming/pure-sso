package com.aming.sso.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.entity.TblRole;
import com.aming.sso.entity.TblRoleMenu;
import com.aming.sso.entity.TblUser;
import com.aming.sso.entity.TblUserRole;
import com.sendtend.util.BeanTool;
import com.sendtend.util.PageUtil;

@Service
@Transactional
public class RoleService {
	protected final transient Log log = LogFactory.getLog(RoleService.class);

	@Autowired
	private BaseDaoImpl dao;

	/**
	 */
	@Transactional
	public TblRole findRoleById(Integer roleId) {
		if(roleId == null) {
			return null;
		}
		return dao.findEntityById("roleId", roleId, TblRole.class);
	}

	/**
	 * Delete an existing Role entity
	 * @param Role
	 */
	@Transactional
	public int deleteRole(TblRole role) {
		if(role == null || role.getRoleId() == null) {
			return 0;
		}
		String sql = "delete from Tbl_Role where role_id<>1 and role_Id=:roleId";
		int rst = dao.executeSQL(sql, role);
		sql = "delete from tbl_role_menu where role_id<>1 and role_id=:roleId";
		rst = dao.executeSQL(sql, role);
		return rst;
	}

	/**
	 * Save an existing Role entity
	 * @param Role
	 */
	@Transactional
	public TblRole saveRole(TblRole role) {
		if(role == null) {
			return null;
		}
		TblRole existingRole = findRoleById(role.getRoleId());

		if (existingRole != null) {
			if (existingRole != role) {
				try {
					BeanTool.copyProperties(existingRole, role);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			dao.update(existingRole);
			return existingRole;
		} else {
			role = (TblRole)dao.save(role);
			return role;
		}
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<TblRole> findRoleByExample(TblRole role) {
		return (List<TblRole>) dao.queryByExample(role, true, "roleId");
	}
	
	/**
	 * 
	 * @param role
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	@Transactional
	public PageUtil listRoles(TblRole role, Integer thePage, Integer pageSize) {
		PageUtil pu = new PageUtil();
		List<?> list = dao.queryByExample(role, true, "roleId");
		pu.setDataSet(list);
		int count = new Integer(dao.createQuery("select count(o) from TblRole o", 1, -1).get(0).toString());
		pu.setTotalCount(count);
		return pu;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<TblRole> listUserRole(TblUser user) {
		if(user == null) {
			return null;
		}
		String sql = "select tr from TblRole tr, TblUserRole tur, TblUser tu " +
				"where tr.roleId=tur.roleId and tur.userId=tu.userId and tu.userId=:userId";
		List<?> list = dao.createQuery(sql, user, 1, -1);
		return (List<TblRole>) list;
	}
	
	/**
	 * 
	 * @param ur
	 * @return
	 */
	@Transactional
	public TblUserRole saveUserRole(TblUserRole ur) {
		if(ur == null) {
			return null;
		}
		return (TblUserRole) dao.save(ur);
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@Transactional
	public int deleteUserRole(TblUser user) {
		if(user == null || user.getUserId() == null) {
			return -1;
		}
		String sql = "delete from Tbl_user_Role where user_id<>1 and user_id=:userId";
		return dao.executeSQL(sql, user);
	}
	
	/**
	 * 菜单权限
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<TblRoleMenu> listUserRoleMenu(TblUser user) {
		if(user == null) {
			return null;
		}
		String sql = "select trm from TblRoleMenu trm, TblUserRole tur, TblUser tu " +
				"where tu.userId=tur.userId and tur.roleId=trm.roleId and tu.userId=:userId";
		if(log.isDebugEnabled()) {
			log.debug("--------- listUserRoleMenu:" + sql);
		}
		List<?> list = dao.createQuery(sql, user, 1, -1);
		return (List<TblRoleMenu>) list;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<TblRoleMenu> listRoleMenuByRoleId(TblRole role) {
		if(role == null) {
			return null;
		}
		String sql = "select trm from TblRoleMenu trm where trm.roleId=:roleId";
		List<?> list = dao.createQuery(sql, role, 1, -1);
		return (List<TblRoleMenu>) list;
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@Transactional
	public int delRoleMenu(TblRole role) {
		if(role == null || role.getRoleId() == null) {
			return -1;
		}
		String sql = "delete from tbl_role_menu where role_id=:roleId";
		return dao.executeSQL(sql, role);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@Transactional
	public TblRoleMenu saveRoleMenu(TblRoleMenu rm) {
		if(rm == null) {
			return null;
		}
		log.info("-------------RoleMenu id:" + rm.getRoleMenuId());
		return (TblRoleMenu) dao.save(rm);
	}
}
