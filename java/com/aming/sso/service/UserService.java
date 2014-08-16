package com.aming.sso.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.entity.TblMenu;
import com.aming.sso.entity.TblRole;
import com.aming.sso.entity.TblRoleMenu;
import com.aming.sso.entity.TblUser;
import com.aming.sso.model.PurviewModel;
import com.aming.sso.model.UserModel;
import com.sendtend.util.BeanTool;
import com.sendtend.util.MD5;
import com.sendtend.util.PageUtil;

@Service
@Transactional
public class UserService {
	protected final transient Log log = LogFactory.getLog(UserService.class);

	@Autowired
	private BaseDaoImpl entityDao;
	@Autowired
	private RoleService roleService;
	//@Autowired
	//private SSOSysService sysService;

	/**
	 */
	public TblUser findUserById(Integer userId) {
		if(userId == null) {
			return null;
		}
		return entityDao.findEntityById("userId", userId, TblUser.class);
	}

	/**
	 * 
	 * @param User
	 */
	public int updateUserPwd(TblUser user) {
		if(user == null || user.getUserId() == null) {
			return 0;
		}
		String sql = "update Tbl_User set passwd=:passwd where user_Id=:userId";
		return entityDao.executeSQL(sql, user);
	}
	
	/**
	 * Delete an existing User entity
	 * @param User
	 */
	public int deleteUser(TblUser user) {
		if(user == null || user.getUserId() == null) {
			return 0;
		}
		String sql = "delete from tbl_user_role where user_id<>1 and user_id=:userId";
		//entityDao.executeSQL(sql, user);
		//sql = "delete from tbl_user_sys where user_id<>1 and user_id=:userId";
		entityDao.executeSQL(sql, user);
		sql = "update Tbl_User set status=0 where user_id<>1 and user_Id=:userId";
		return entityDao.executeSQL(sql, user);
	}


	/**
	 * Save an existing User entity
	 * @param User
	 */
	public TblUser saveUser(TblUser user) {
		if(user == null) {
			return null;
		}
		TblUser existingUser = findUserById(user.getUserId());
		if(log.isDebugEnabled()) {
			log.debug("--------------user id 是否存在：" + (existingUser!=null));
		}
		if (existingUser != null) {
			if (existingUser != user) {
				try {
					//如果用户更新信息没有修改密码，保存原来密码
					if(user.getPasswd() == null) {
						user.setPasswd(existingUser.getPasswd());
					}
					BeanTool.copyProperties(existingUser, user);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			entityDao.update(existingUser);
			return existingUser;
		} else {
			//加密用户密码：
			user.setPasswd(new MD5().getMD5ofStr(user.getPasswd()));
			user = (TblUser)entityDao.save(user);
			return user;
		}
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public List<?> findUserByExample(TblUser user) {
		String sql = "from TblUser where loginName=? and passwd=?";
		Vector<String> v = new Vector<String>();
		v.add(user.getLoginName());
		v.add(user.getPasswd());
		return entityDao.createQuery(sql, v, 1, -1);
		//return entityDao.queryByExample(user, true, "userName");
	}
	
	/**
	 * 
	 * @param user
	 * @param nativeOrderField
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	public PageUtil listUsers(TblUser user, String nativeOrderField, Integer thePage, Integer pageSize) {
		PageUtil pu = new PageUtil();
		StringBuffer sql = new StringBuffer("select tu.*,tp.post_name");
		sql.append(" from tbl_user tu ");
		sql.append(" left join tbl_post tp on tu.post_id=tp.post_id where tu.user_id<>1 ");
		if(user != null) {
			if(user.getLoginName() != null) {
				user.setLoginName("%" + user.getLoginName() + "%");
				sql.append(" and tu.login_name like :loginName");
			}
			if(user.getUserName() != null) {
				user.setUserName("%" + user.getUserName() + "%");
				sql.append(" and tu.user_name like :userName");
			}
			if(user.getStatus() != null) {
				sql.append(" and tu.status=:status");
			}
		}
		if(nativeOrderField == null || nativeOrderField.isEmpty()) {
			nativeOrderField = "user_name";
		}
		sql.append(" order by tu." + nativeOrderField);
		if(log.isDebugEnabled()) {
			log.debug("-------------list user sql: " + sql.toString());
		}
		List<?> list = entityDao.queryNativeSQLAsBean(sql.toString(), user, UserModel.class, thePage, pageSize);
		pu.setDataSet(list);
		int count = new Integer(entityDao.createQuery("select count(o) from TblUser o", 1, -1).get(0).toString());
		pu.setTotalCount(count);
		return pu;
	}
	
	/**
	 * 岗位名称，部门名称，学院名称和校区名称
	 * 
	 * @param user
	 * @param nativeOrderField
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findUsersOtherProperties(TblUser user) {
		StringBuffer sql = new StringBuffer("select tp.post_id,tp.post_name");
		sql.append(" from tbl_user tu ");
		sql.append(" left join tbl_post tp on tu.post_id=tp.post_id where 1=1");
		if(user != null) {
			if(user.getUserId() != null) {
				sql.append(" and tu.user_id = :userId");
			}
			if(user.getStatus() != null) {
				sql.append(" and tu.status=:status");
			}
		}
		if(log.isDebugEnabled()) {
			log.debug("-------------list user sql: " + sql.toString());
		}
		List<?> list = entityDao.queryNativeSQL(sql.toString(), user, 1, -1);
		if(log.isDebugEnabled()) {
			log.debug("----------------find user other properties:" + list);
		}
		if(list != null && !list.isEmpty()) {
			return (Map<String, Object>) list.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblMenu> listUserMenu(TblUser user, Boolean isMenu) {
		if(user == null) {
			return null;
		}
		StringBuffer sql = new StringBuffer("select tm from TblMenu tm, TblRoleMenu trm, TblUserRole tur, TblUser tu " +
				"where tu.userId=tur.userId and tur.roleId=trm.roleId and trm.menuId=tm.menuId " +
				"and tu.userId=:userId");
		if(isMenu != null) {
			if(isMenu) {
				sql.append(" and tm.isMenu='1'");
			} else {
				sql.append(" and tm.isMenu<>'1'");
			}
		}
		sql.append(" order by tm.menuSort");
		List<?> list = entityDao.createQuery(sql.toString(), user, 1, -1);
		return (List<TblMenu>) list;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public PurviewModel listUserRight(TblUser user) {
		if(user == null) {
			return null;
		}
		PurviewModel pm = new PurviewModel();
		List<TblMenu> list = this.listUserMenu(user, true);
		pm.setMenuSet(list);
		list = this.listUserMenu(user, null);
		pm.setRightSet(list);
		//菜单全新
		List<TblRoleMenu> rmList = roleService.listUserRoleMenu(user);
		pm.setRoleMenuSet(rmList);
		List<TblRole> rList = roleService.listUserRole(user);
		pm.setRoleSet(rList);
		//List<UserSysModel> tsList = sysService.listUserSysByUser(user);
		//pm.setSysSet(tsList);
		return pm;
	}
	
	/**
	 * 
	 * @param user
	 * @param sid
	 * @return
	 */
	@Deprecated
	public int setSidByUser(TblUser user, String sid) {
		if(user == null || user.getUserId() == null) {
			return -1;
		}
		String sql = "update tbl_user_sys set sid='" + sid + "',sid_time=now() where user_id=:userId";
		if(log.isDebugEnabled()) {
			 log.debug("-------- update user sid:" + sql);
		}
		return entityDao.executeSQL(sql, user);
	}
}
