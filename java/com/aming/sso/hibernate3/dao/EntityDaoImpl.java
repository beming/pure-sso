package com.aming.sso.hibernate3.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * @author Aming
 *
 */
public class EntityDaoImpl extends HibernateDaoSupport implements EntityDao {
	
	public List<?> createQuery(final String queryString, final int thePage, final int pageSize) {
		return (List<?>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createQuery(queryString);
				query.setMaxResults(pageSize);
				query.setFirstResult((thePage - 1) * pageSize);
				List<?> rows = query.list();
				return rows;
			}
		});
	}
	
	public List<?> createQuery(final String queryString, final Vector<?> vector, final int thePage, final int pageSize) {
		return (List<?>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createQuery(queryString);
				query.setMaxResults(pageSize);
				query.setFirstResult((thePage - 1) * pageSize);
				for(int i=0; vector != null && i<vector.size(); i++) {
					query.setParameter(i, vector.get(i));
				}
				List<?> rows = query.list();
				return rows;
			}
		});
	}
	
	public List<?> createQuery(final String queryString, final Object bean, final int thePage, final int pageSize) {
		return (List<?>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createQuery(queryString);
				query.setProperties(bean);
				query.setMaxResults(pageSize);
				query.setFirstResult((thePage - 1) * pageSize);
				List<?> rows = query.list();
				return rows;
			}
		});
	}

	public Object save(final Object model) {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				session.save(model);
				//session.merge(model);
				return model;
			}
		});
	}

	public void update(final Object model) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				session.update(model);
				return model;
			}
		});
	}

	public void delete(final Object model) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				session.delete(model);
				return new Boolean(true);
			}
		});
	}

	public List<Map<?,?>> queryNativeSQL(final String sql, final Object bean, final int thePage, final int pageSize) {
		return (List<Map<?,?>>) getHibernateTemplate().execute(new HibernateCallback<List<Map<?,?>>>() {
			public List<Map<?,?>> doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); ;
				if(bean != null)
					query.setProperties(bean);
				query.setMaxResults(pageSize);
				query.setFirstResult((thePage - 1) * pageSize);
				List<Map<?,?>> rows = query.list();
				return rows;
			}
		});
	}
	
	public List<Map<?,?>> queryNativeSQL(final String sql, final Vector<?> v, final int thePage, final int pageSize) {
		return (List<Map<?,?>>) getHibernateTemplate().execute(new HibernateCallback<List<Map<?,?>>>() {
			public List<Map<?,?>> doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); ;
				for(int i=0; v != null && i<v.size(); i++) {
					query.setParameter(i, v.get(i));
				}
				query.setMaxResults(pageSize);
				query.setFirstResult((thePage - 1) * pageSize);
				List<Map<?,?>> rows = query.list();
				return rows;
			}
		});
	}
	
	public List<?> queryNativeSQLAsList(final String sql, final Object bean, final int thePage, final int pageSize) {
		return (List<?>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST); ;
				if(bean != null)
					query.setProperties(bean);
				query.setMaxResults(pageSize);
				query.setFirstResult((thePage - 1) * pageSize);
				List<?> rows = (List<?>)query.list();
				if(rows.size()>0) {
					if(rows.get(0) instanceof List || rows.get(0) instanceof ArrayList) {
						return rows.get(0);
					}
				}
				return rows;
			}
		});
	}
	
	/**
	 * return map
	 */
	public List<?> queryNativeSQLAsList(final String sql, final Vector<?> v, final int thePage, final int pageSize) {
		return (List<?>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST); ;
				for(int i=0; v != null && i<v.size(); i++) {
					query.setParameter(i, v.get(i));
				}
				query.setMaxResults(pageSize);
				query.setFirstResult((thePage - 1) * pageSize);
				List<?> rows = (List<?>)query.list();
				if(rows.size()>0) {
					if(rows.get(0) instanceof List || rows.get(0) instanceof ArrayList) {
						return rows.get(0);
					}
				}
				return rows;
			}
		});
	}
	
	/**
	 * 
	 * @param sql :select xh as \"xh\" from xxx, as 字段需要用双引号引住，因为 oracle会自动转为 大写，导致无法set到bean的property中。
	 * @param obj
	 * @param returnBean
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	public List<?> queryNativeSQLAsBean(final String sql, final Object bean, final Class<?> returnBean, final int thePage, final int pageSize) {
		return (List<?>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(returnBean)); ;
				if(bean != null)
					query.setProperties(bean);
				query.setMaxResults(pageSize);
				query.setFirstResult((thePage - 1) * pageSize);
				List<?> rows = query.list();
				return rows;
			}
		});
	}
	
	public List<?> queryNativeSQLAsBean(final String sql, final Vector<?> v, final Class<?> returnBean, final int thePage, final int pageSize) {
		return (List<?>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(returnBean)); ;
				for(int i=0; v != null && i<v.size(); i++) {
					query.setParameter(i, v.get(i));
				}
				query.setMaxResults(pageSize);
				query.setFirstResult((thePage - 1) * pageSize);
				List<?> rows = query.list();
				return rows;
			}
		});
	}
	
	/**
	 * 
	 */
	public Long callProc(final String proc,final List<Object> paramList,final int outIndex, final int type) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Long result = null;
                Connection conn = null;
                CallableStatement cstmt = null;
                try {
                    //conn = session.connection();
                	conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
                    conn.setAutoCommit(false);
                    cstmt = conn.prepareCall(proc);
                    for (int i=0; paramList != null && i<paramList.size(); i++) {
                    	if(i+1 == outIndex) {
                    		cstmt.setInt(i+1,(Integer.parseInt(paramList.get(i).toString())));
                    	} else {
                    		cstmt.setString(i+1, paramList.get(i).toString());
                    	}
                    }
                    cstmt.registerOutParameter(outIndex,type);
                    cstmt.execute();
                    result = new Long(cstmt.getInt(outIndex));
                    conn.commit();
                } catch (Exception ex) {
                    try {
                        conn.rollback();
                    } catch (SQLException e1) {
                        logger.error(e1);
                        e1.printStackTrace();
                    }
                    ex.printStackTrace();
                } finally {
                    if(cstmt != null) {
                    	try {
                    		cstmt.close();
                    	}catch(Exception ex) {}
                    }
                    if(conn != null) {
                    	try {
                    		conn.close();
                    	}catch(Exception ex) {}
                    }
                }
                return result;
			}
		});
	}
	
	/**
	 * 
	 */
	public Long callProc(final String proc,final List<String> paramList) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Long result = new Long(-1);
                Connection conn = null;
                CallableStatement cstmt = null;
                try {
                    conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
                    conn.setAutoCommit(false);
                    cstmt = conn.prepareCall(proc);
                    for (int i=0; paramList != null && i<paramList.size(); i++) {
                    	cstmt.setString(i+1, paramList.get(i));
                    }
                    cstmt.execute();
                    conn.commit();
                    result = new Long(1);
                } catch (Exception ex) {
                    try {
                        conn.rollback();
                    } catch (SQLException e1) {
                        logger.error(e1);
                        e1.printStackTrace();
                    }
                    ex.printStackTrace();
                } finally {
                    if(cstmt != null) {
                    	try {
                    		cstmt.close();
                    	}catch(Exception ex) {}
                    }
                    if(conn != null) {
                    	try {
                    		conn.close();
                    	}catch(Exception ex) {}
                    }
                }
                return result;
			}
		});
	}
	
	public int executeSQL(final String sql) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createSQLQuery(sql); ;
				return query.executeUpdate();
			}
		});
	}
	/**
	 * 
	 */
	public int executeSQL(final String sql, final Object bean) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Query query = session.createSQLQuery(sql);
				if(bean != null) {
					query.setProperties(bean);
				}
				return query.executeUpdate();
			}
		});
	}
	
	public Long executePureSQL(final List<String> lstSQL) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				Long result = new Long(-1);
                Connection conn = null;
                java.sql.Statement cstmt = null;
                try {
                    conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
                    conn.setAutoCommit(false);
                    cstmt = conn.createStatement();
                    for (int i=0; lstSQL != null && i<lstSQL.size(); i++) {
                    	cstmt.addBatch(lstSQL.get(i));
                    	logger.debug(lstSQL.get(i));
                    }
                    
                    cstmt.executeBatch();
                	conn.commit();
                	result = new Long(1);
                } catch (Exception ex) {
                    try {
                        conn.rollback();
                    } catch (SQLException e1) {
                        logger.error(e1);
                        e1.printStackTrace();
                    }
                    ex.printStackTrace();
                } finally {
                    if(cstmt != null) {
                    	try {
                    		cstmt.close();
                    	}catch(Exception ex) {}
                    }
                    if(conn != null) {
                    	try {
                    		conn.close();
                    	}catch(Exception ex) {}
                    }
                }
                return result;
			}
		});
	}

	@Override
	public <T> T findEntityById(final String idName, final Object idValue, final Class<T> clazz) {
		return (T) getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				final DetachedCriteria query = DetachedCriteria  
			            .forClass(clazz);  
			    Criteria criteria = query.getExecutableCriteria(getSession());  
			    criteria.add(Restrictions.eq(idName, idValue));
			    List<T> list = criteria.list();
			    if(list != null && list.size() >0) {
			    	return  list.get(0);
			    }
				return null;
			}
		});
	}
}
