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
	private BaseDaoImpl entityDao;

	/**
	 */
	public TblRole findRoleById(Integer roleId) {
		if(roleId == null) {
			return null;
		}
		return entityDao.findEntityById("roleId", roleId, TblRole.class);
	}

	/**
	 * Delete an existing Role entity
	 * @param Role
	 */
	public int deleteRole(TblRole role) {
		if(role == null || role.getRoleId() == null) {
			return 0;
		}
		String sql = "delete from Tbl_Role where role_id<>1 and role_Id=:roleId";
		int rst = entityDao.executeSQL(sql, role);
		sql = "delete from tbl_role_menu where role_id<>1 and role_id=:roleId";
		rst = entityDao.executeSQL(sql, role);
		return rst;
	}

	/**
	 * Save an existing Role entity
	 * @param Role
	 */
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
			entityDao.update(existingRole);
			return existingRole;
		} else {
			role = (TblRole)entityDao.save(role);
			return role;
		}
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblRole> findRoleByExample(TblRole role) {
		return (List<TblRole>) entityDao.queryByExample(role, true, "roleId");
	}
	
	/**
	 * 
	 * @param role
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	public PageUtil listRoles(TblRole role, Integer thePage, Integer pageSize) {
		PageUtil pu = new PageUtil();
		List<?> list = entityDao.queryByExample(role, true, "roleId");
		pu.setDataSet(list);
		int count = new Integer(entityDao.createQuery("select count(o) from TblRole o", 1, -1).get(0).toString());
		pu.setTotalCount(count);
		return pu;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblRole> listUserRole(TblUser user) {
		if(user == null) {
			return null;
		}
		String sql = "select tr from TblRole tr, TblUserRole tur, TblUser tu " +
				"where tr.roleId=tur.roleId and tur.userId=tu.userId and tu.userId=:userId";
		List<?> list = entityDao.createQuery(sql, user, 1, -1);
		return (List<TblRole>) list;
	}
	
	/**
	 * 
	 * @param ur
	 * @return
	 */
	public TblUserRole saveUserRole(TblUserRole ur) {
		if(ur == null) {
			return null;
		}
		return (TblUserRole) entityDao.save(ur);
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	public int deleteUserRole(TblUser user) {
		if(user == null || user.getUserId() == null) {
			return -1;
		}
		String sql = "delete from Tbl_user_Role where user_id<>1 and user_id=:userId";
		return entityDao.executeSQL(sql, user);
	}
	
	/**
	 * 菜单权限
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblRoleMenu> listUserRoleMenu(TblUser user) {
		if(user == null) {
			return null;
		}
		String sql = "select trm from TblRoleMenu trm, TblUserRole tur, TblUser tu " +
				"where tu.userId=tur.userId and tur.roleId=trm.roleId and tu.userId=:userId";
		if(log.isDebugEnabled()) {
			log.debug("--------- listUserRoleMenu:" + sql);
		}
		List<?> list = entityDao.createQuery(sql, user, 1, -1);
		return (List<TblRoleMenu>) list;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblRoleMenu> listRoleMenuByRoleId(TblRole role) {
		if(role == null) {
			return null;
		}
		String sql = "select trm from TblRoleMenu trm where trm.roleId=:roleId";
		List<?> list = entityDao.createQuery(sql, role, 1, -1);
		return (List<TblRoleMenu>) list;
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	public int delRoleMenu(TblRole role) {
		if(role == null || role.getRoleId() == null) {
			return -1;
		}
		String sql = "delete from tbl_role_menu where role_id=:roleId";
		return entityDao.executeSQL(sql, role);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public TblRoleMenu saveRoleMenu(TblRoleMenu rm) {
		if(rm == null) {
			return null;
		}
		log.info("-------------RoleMenu id:" + rm.getRoleMenuId());
		return (TblRoleMenu) entityDao.save(rm);
	}
}
