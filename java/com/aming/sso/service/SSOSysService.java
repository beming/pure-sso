package com.aming.sso.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.entity.TblSystem;
import com.aming.sso.entity.TblUser;
import com.aming.sso.entity.TblUserSys;
import com.aming.sso.model.DwzRowItem;
import com.aming.sso.model.UserSysModel;
import com.sendtend.sso.aes.AESCoder;
import com.sendtend.util.BeanTool;
import com.sendtend.util.PageUtil;

@Service
@Transactional
public class SSOSysService {
	protected final transient Log log = LogFactory.getLog(SSOSysService.class);

	@Autowired
	private BaseDaoImpl dao;

	/**
	 */
	@Transactional
	public TblSystem findSysById(Integer sysId) {
		if(sysId == null) {
			return null;
		}
		return dao.findEntityById("sysId", sysId, TblSystem.class);
	}
	
	/**
	 * 
	 * @param userSysId
	 * @return
	 */
	@Transactional
	public TblUserSys findUserSysById(Integer userSysId) {
		if(userSysId == null) {
			return null;
		}
		return dao.findEntityById("userSysId", userSysId, TblUserSys.class);
	}
	
	/**
	 * 
	 * @param sys
	 * @return
	 */
	@Transactional
	public List<?> findSysByExample(TblSystem sys) {
		return dao.queryByExample(sys, "sysName", true);
	}

	/**
	 * Delete an existing Sys entity
	 * @param Sys
	 */
	@Transactional
	public int deleteSys(TblSystem sys) {
		if(sys == null || sys.getSysId() == null) {
			return 0;
		}
		String sql = "delete from tbl_user_sys where sys_id=:sysId";
		dao.executeSQL(sql, sys);
		sql = "delete from tbl_system where sys_Id=:sysId";
		return dao.executeSQL(sql, sys);
	}


	/**
	 * Save an existing Sys entity
	 * @param Sys
	 */
	@Transactional
	public TblSystem saveSys(TblSystem sys) {
		if(sys == null) {
			return null;
		}
		TblSystem existingSys = this.findSysById(sys.getSysId());
		
		if (existingSys != null) {
			String key = existingSys.getTokenKey();
			if (existingSys != sys) {
				try {
					BeanTool.copyProperties(existingSys, sys);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			existingSys.setTokenKey(key);
			dao.update(existingSys);
			return existingSys;
		} else {
			//生成系统自己的AES 密钥
			byte[] keyByte = AESCoder.initSecretKey();
			String key = AESCoder.parseByte2HexStr(keyByte);
			sys.setTokenKey(key);
			sys = (TblSystem)dao.save(sys);
			return sys;
		}
	}
	
	/**
	 * 
	 * @param usersys
	 * @return
	 */
	@Transactional
	public TblUserSys saveUserSys(TblUserSys usersys) {
		if(usersys == null) {
			return null;
		}
		return (TblUserSys) dao.save(usersys);
	}
	
	/**
	 * 
	 * @param usersys
	 * @return
	 */
	@Transactional
	public int delUserSys(TblUserSys usersys) {
		if(usersys == null) {
			return -1;
		}
		String sql = "delete from tbl_user_sys where user_sys_id=:userSysId";
		return dao.executeSQL(sql, usersys);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional
	public int delUserSysByUserId(Integer userId) {
		TblUser user = new TblUser();
		user.setUserId(userId);
		String sql = "delete from tbl_user_sys where user_id=:userId";
		return dao.executeSQL(sql, user);
	}
	
	/**
	 * 
	 * @param sysId
	 * @return
	 */
	@Transactional
	public int delUserSysBySysId(Integer sysId) {
		TblSystem sys = new TblSystem();
		sys.setSysId(sysId);
		String sql = "delete from tbl_user_sys where sys_id=:sysId";
		return dao.executeSQL(sql, sys);
	}
	
	/**
	 * 
	 * @param usersys
	 * @return
	 */
	@Transactional
	public int delUserSysByUserIdAndSysId(TblUserSys usersys) {
		if(usersys == null) {
			return 0;
		}
		String sql = "delete from tbl_user_sys where sys_id=:sysId and user_id=:userId";
		return dao.executeSQL(sql, usersys);
	}
	
	/**
	 * 
	 * @param sys
	 * @param orderField
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	@Transactional
	public PageUtil listSys(TblSystem sys, String orderField, Integer thePage, Integer pageSize) {
		if(orderField == null || orderField.isEmpty()) {
			orderField = "sysName";
		}
		PageUtil pu = new PageUtil();
		List<?> list = dao.queryByExample(sys, orderField, true);
		pu.setDataSet(list);
		int count = new Integer(dao.createQuery("select count(o) from TblSystem o", 1, -1).get(0).toString());
		pu.setTotalCount(count);
		return pu;
	}
	
	/**
	 * 
	 * @param sys
	 * @param orderField
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	@Transactional
	public PageUtil listSysUser(TblSystem sys, Integer thePage, Integer pageSize) {
		PageUtil pu = new PageUtil();
		String select = "select t1.*, t2.login_name";
		String from = " from tbl_user_sys t1, tbl_user t2";
		String where = " where t1.user_id=t2.user_id";
		if(sys.getSysId() != null) {
			where = where + " and t1.sys_id=:sysId";
		}
		String order = " order by t2.login_name";
		StringBuffer sql = new StringBuffer();
		sql.append(select);
		sql.append(from);
		sql.append(where);
		sql.append(order);
		List<?> list = dao.queryNativeSQLAsBean(sql.toString(), sys, DwzRowItem.class, thePage, pageSize);
		sql = new StringBuffer("");
		sql.append("select count(t1.user_sys_id)");
		sql.append(from);
		sql.append(where);
		pu.setDataSet(list);
		Map<?, ?> cl = dao.queryNativeSQL(sql.toString(), sys, thePage, pageSize).get(0);
		int count = cl==null||cl.isEmpty()?0:new Integer(cl.get("count").toString());
		pu.setTotalCount(count);
		return pu;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@Transactional
	public List<UserSysModel> listUserSysByUser(TblUser user) {
		String sql = "select t1.sys_id,t1.sys_name,t1.sys_url,t1.login_field,t1.pwd_field,t1.login_flag1,t1.token_key,t1.security_level as securityLevel," +
				"t2.sysuser_id,t2.sysuser_pwd,t2.sysuser_type,t2.sid,t2.sid_time,t2.field1 " +
				"from tbl_system t1,tbl_user_sys t2 " +
				"where t1.sys_id=t2.sys_id and t1.sys_status='1' and t2.user_id=:userId";
		@SuppressWarnings("unchecked")
		List<UserSysModel> list = (List<UserSysModel>) dao.queryNativeSQLAsBean(sql, user, UserSysModel.class, 1, -1);
		return list;
	}
}
