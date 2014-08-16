package com.aming.sso.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.entity.TblDepartment;
import com.aming.sso.model.DeptModel;
import com.sendtend.util.BeanTool;
import com.sendtend.util.PageUtil;

@Service
@Transactional
public class DeptService {
	protected final transient Log log = LogFactory.getLog(DeptService.class);

	@Autowired
	private BaseDaoImpl entityDao;

	/**
	 */
	public TblDepartment findDeptById(Integer depId) {
		if(depId == null) {
			return null;
		}
		return entityDao.findEntityById("depId", depId, TblDepartment.class);
	}

	public List<?> findDeptsByExample(TblDepartment dept) {
		return entityDao.queryByExample(dept, true, "depName");
	}
	
	/**
	 * Delete an existing Dept entity
	 * @param Dept
	 */
	public int deleteDept(TblDepartment dept) {
		if(dept == null || dept.getDepId() == null) {
			return 0;
		}
		String sql = "update tbl_department set dep_status=0 where dep_id=:depId";
		return entityDao.executeSQL(sql, dept);
	}


	/**
	 * Save an existing Dept entity
	 * @param Dept
	 */
	public TblDepartment saveDept(TblDepartment dept) {
		if(dept == null) {
			return null;
		}
		TblDepartment existingDept = findDeptById(dept.getDepId());

		if (existingDept != null) {
			if (existingDept != dept) {
				try {
					BeanTool.copyProperties(existingDept, dept);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			entityDao.update(existingDept);
			return existingDept;
		} else {
			dept = (TblDepartment)entityDao.save(dept);
			return dept;
		}
	}
	
	public PageUtil listDepts(TblDepartment dept, String nativeOrderField, Integer thePage, Integer pageSize) {
		PageUtil pu = new PageUtil();
		if(nativeOrderField == null || nativeOrderField.isEmpty()) {
			nativeOrderField = "dep_name";
		}
		StringBuffer sql = new StringBuffer("select td.* ");
		sql.append("from tbl_department td ");
		sql.append("where 1=1");
		if(dept.getDepName() != null && !dept.getDepName().isEmpty()) {
			sql.append(" and td.dep_name=:depName");
		}
		if(dept.getDepStatus() != null) {
			sql.append(" and td.dep_status=:depStatus");
		}
		sql.append(" order by td." + nativeOrderField);
		List<?> list = entityDao.queryNativeSQLAsBean(sql.toString(), dept, DeptModel.class, thePage, pageSize);
		pu.setDataSet(list);
		int count = new Integer(entityDao.createQuery("select count(o) from TblDepartment o", 1, -1).get(0).toString());
		pu.setTotalCount(count);
		return pu;
	}

}
