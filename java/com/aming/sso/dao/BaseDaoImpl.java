package com.aming.sso.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import oracle.jdbc.OracleTypes;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BinaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Aming
 * 
 */
@Repository
public class BaseDaoImpl implements BaseDao {

	@Autowired
    @Qualifier("sessionFactory")  
	private SessionFactory sessionFactory;
	
	private Session session = null;

	public void openSession() {
		this.session = this.sessionFactory.openSession();
	}
	
	public Session getSession() {
		if(this.session != null) {
			return this.session;
		} else if(this.sessionFactory != null ) {
			this.session = this.sessionFactory.getCurrentSession();
			try {
				this.session.createQuery("select 1").list();
				return this.session;
			} catch (Exception ex) {
				//this.session = this.sessionFactory.openSession();
				ex.printStackTrace();
			}
			this.session = this.sessionFactory.openSession();
			return this.session;
		}
		return this.sessionFactory.getCurrentSession();
	}
	
	public void closeSession() {
		if(this.session != null) {
			this.session.clear();
			this.session.close();
			this.session = null;
		}
	}
	
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public Criteria createCriteria(Class<?> clazz) {
		return getSession().createCriteria(clazz);
	}
	
	public List<?> createQuery(final String queryString, final int thePage, final int pageSize) {
		Query query = getSession().createQuery(queryString);
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		List<?> rows = query.list();
		return rows;
	}

	public List<?> createQuery(final String queryString, final Vector<?> vector, final int thePage, final int pageSize) {
		Query query = getSession().createQuery(queryString);
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		for (int i = 0; vector != null && i < vector.size(); i++) {
			query.setParameter(i, vector.get(i));
		}
		List<?> rows = query.list();
		return rows;
	}

	public List<?> createQuery(final String queryString, final Object bean, final int thePage, final int pageSize) {
		Query query = getSession().createQuery(queryString);
		query.setProperties(bean);
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		List<?> rows = query.list();
		return rows;
	}

	public List<?> queryByExample(Object obj, boolean asc, String... orderField) {
		Criteria criteria = getSession().createCriteria(obj.getClass())
			     .add( Example.create(obj).enableLike().ignoreCase());
		if(orderField != null && orderField.length > 0) {
			for(String order : orderField) {
				if(asc) {
					criteria.addOrder(Order.asc(order));
				} else {
					criteria.addOrder(Order.desc(order));
				}
			}
		}
		return criteria.list();
	}
	
	public Object save(final Object model) {
		getSession().save(model);
		return model;
	}

	public void update(final Object model) {
		getSession().update(model);
	}

	public void delete(final Object model) {
		getSession().delete(model);
	}
	
	public List<?> queryByExample(Object obj, String orderField, boolean asc) {
		Criteria criteria = getSession().createCriteria(obj.getClass())
			     .add( Example.create(obj).enableLike().ignoreCase());
		if(orderField != null && !orderField.isEmpty()) {
			if(asc) {
				criteria.addOrder(Order.asc(orderField));
			} else {
				criteria.addOrder(Order.desc(orderField));
			}
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Map<?, ?>> queryNativeSQL(final String sql, final Object bean, final int thePage, final int pageSize) {
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		;
		if (bean != null)
			query.setProperties(bean);
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		List<Map<?, ?>> rows = query.list();
		return rows;
	}

	public List<Map<?, ?>> queryNativeSQL(final String sql, final Vector<?> v, final int thePage, final int pageSize) {
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		;
		for (int i = 0; v != null && i < v.size(); i++) {
			query.setParameter(i, v.get(i));
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		@SuppressWarnings("unchecked")
		List<Map<?, ?>> rows = query.list();
		return rows;
	}

	/**
	 * 
	 * @param sql
	 * @param v
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	public List<Map<?, ?>> queryGSXKNoticeSQL(final String sql, final Vector<?> v, final int thePage, final int pageSize) {
		Query query = getSession().createSQLQuery(sql).addScalar("GGZW", BinaryType.INSTANCE).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		;
		for (int i = 0; v != null && i < v.size(); i++) {
			query.setParameter(i, v.get(i));
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		@SuppressWarnings("unchecked")
		List<Map<?, ?>> rows = query.list();
		return rows;
	}

	public List<?> queryNativeSQLAsList(final String sql, final Object bean, final int thePage, final int pageSize) {
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST);
		;
		if (bean != null)
			query.setProperties(bean);
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		List<?> rows = (List<?>) query.list();
		return rows;
	}

	/**
	 * return map
	 */
	public List<?> queryNativeSQLAsList(final String sql, final Vector<?> v, final int thePage, final int pageSize) {
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST);
		;
		for (int i = 0; v != null && i < v.size(); i++) {
			query.setParameter(i, v.get(i));
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		List<?> rows = (List<?>) query.list();
		return rows;
	}

	/**
	 * 
	 * @param sql
	 *            :select xh as \"xh\" from xxx, as 字段需要用双引号引住，因为 oracle会自动转为
	 *            大写，导致无法set到bean的property中。
	 * @param obj
	 * @param returnBean
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	public List<?> queryNativeSQLAsBean(final String sql, final Object bean, final Class<?> returnBean, final int thePage, final int pageSize) {
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(returnBean));
		if (bean != null)
			query.setProperties(bean);
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		List<?> rows = query.list();
		return rows;
	}

	public List<?> queryNativeSQLAsBean(final String sql, final Vector<?> v, final Class<?> returnBean, final int thePage, final int pageSize) {
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(returnBean));
		;
		for (int i = 0; v != null && i < v.size(); i++) {
			query.setParameter(i, v.get(i));
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((thePage - 1) * pageSize);
		List<?> rows = query.list();
		return rows;
	}

	/**
	 * 
	 */
	public Long callProc(final String proc, final List<Object> paramList, final int outIndex, final int type) {
		Long result = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			// conn = getSession().connection();
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			conn.setAutoCommit(false);
			cstmt = conn.prepareCall(proc);
			for (int i = 0; paramList != null && i < paramList.size(); i++) {
				if (i + 1 == outIndex) {
					cstmt.setInt(i + 1, (Integer.parseInt(paramList.get(i).toString())));
				} else {
					cstmt.setString(i + 1, paramList.get(i).toString());
				}
			}
			cstmt.registerOutParameter(outIndex, type);
			cstmt.execute();
			result = new Long(cstmt.getInt(outIndex));
			conn.commit();
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception ex) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ex) {
				}
			}
		}
		return result;
	}

	/**
	 * 
	 */
	public Long callProc(final String proc, final List<String> paramList) {
		Long result = new Long(-1);
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			conn.setAutoCommit(false);
			cstmt = conn.prepareCall(proc);
			for (int i = 0; paramList != null && i < paramList.size(); i++) {
				cstmt.setString(i + 1, paramList.get(i));
			}
			cstmt.execute();
			conn.commit();
			result = new Long(1);
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception ex) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ex) {
				}
			}
		}
		return result;
	}

	public int executeSQL(final String sql) {
		Query query = getSession().createSQLQuery(sql);
		return query.executeUpdate();
	}

	/**
	 * 
	 */
	public int executeSQL(final String sql, final Object bean) {
		Query query = getSession().createSQLQuery(sql);
		if (bean != null) {
			if (bean instanceof Vector) {
				Vector<?> v = (Vector<?>) bean;
				for (int i = 0; v != null && i < v.size(); i++) {
					query.setParameter(i, v.get(i));
				}
			} else {
				query.setProperties(bean);
			}
		}
		return query.executeUpdate();
	}

	/**
	 * 
	 */
	public int executeSQL(final String sql, final Vector<?> v) {
		Query query = getSession().createSQLQuery(sql);
		for (int i = 0; v != null && i < v.size(); i++) {
			query.setParameter(i, v.get(i));
		}
		return query.executeUpdate();
	}

	public Long executePureSQL(final List<String> lstSQL) {
		Long result = new Long(-1);
		Connection conn = null;
		java.sql.Statement cstmt = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			conn.setAutoCommit(false);
			cstmt = conn.createStatement();
			for (int i = 0; lstSQL != null && i < lstSQL.size(); i++) {
				cstmt.addBatch(lstSQL.get(i));
			}

			cstmt.executeBatch();
			conn.commit();
			result = new Long(1);
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception ex) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ex) {
				}
			}
		}
		return result;
	}

	@Override
	public <T> T findEntityById(final String idName, final Object idValue, final Class<T> clazz) {
		final DetachedCriteria query = DetachedCriteria.forClass(clazz);
		Criteria criteria = query.getExecutableCriteria(getSession());
		criteria.add(Restrictions.eq(idName, idValue));
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public <T> T findEntityByProp(final String prop, final Object value, final Class<T> clazz) {
		final DetachedCriteria query = DetachedCriteria.forClass(clazz);
		Criteria criteria = query.getExecutableCriteria(getSession());
		criteria.add(Restrictions.eq(prop, value));
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 */
	public List<Map<String, String>> callProcWithReturnList(final String proc, final List<Object> paramList, final int outIdx) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			conn.setAutoCommit(false);
			cstmt = conn.prepareCall(proc);
			for (int i = 0; paramList != null && i < paramList.size(); i++) {
				if (i + 1 == outIdx) {
					continue;
				}
				cstmt.setString(i + 1, paramList.get(i).toString());
			}
			cstmt.registerOutParameter(outIdx, OracleTypes.CURSOR); // -10
			cstmt.execute();
			ResultSet rs = (ResultSet) cstmt.getObject(outIdx);
			ResultSetMetaData rmd = rs.getMetaData();
			int colNum = rmd.getColumnCount();
			String[] colNames = new String[colNum];
			for (int i = 0; i < colNum; i++) {
				colNames[i] = rmd.getColumnLabel(i + 1);
			}
			HashMap<String, String> map = null;
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
				result.add(map);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception ex) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ex) {
				}
			}
		}
		return result;
	}
}
