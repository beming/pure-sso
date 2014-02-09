package com.aming.sso.hibernate3.dao;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public interface EntityDao {
	public <T>T findEntityById(final String idName, final Object idValue, Class<T> clazz);
    public List<?> createQuery(final String queryString, final int thePage, final int pageSize);
    public List<?> createQuery(final String queryString, final Vector<?> vector, final int thePage, final int pageSize);
    public List<?> createQuery(final String queryString, final Object bean, final int thePage, final int pageSize);
    public List<Map<?,?>> queryNativeSQL(final String sqlString, final Object propertyBean, final int thePage, final int pageSize);
    public List<Map<?,?>> queryNativeSQL(final String sql, final Vector<?> v, final int thePage, final int pageSize);
    public List<?> queryNativeSQLAsList(final String sql, final Object bean, final int thePage, final int pageSize);
    public List<?> queryNativeSQLAsList(final String sql, final Vector<?> v, final int thePage, final int pageSize);
    public List<?> queryNativeSQLAsBean(final String sql, final Object bean, final Class<?> returnBean, final int thePage, final int pageSize);
    public List<?> queryNativeSQLAsBean(final String sql, final Vector<?> v, final Class<?> returnBean, final int thePage, final int pageSize);
    public Object save(final Object model);
    public void update(final Object model);
    public void delete(final Object model);
    public int executeSQL(final String sql);
    public int executeSQL(final String sql, final Object bean);
    public Long callProc(final String proc,final List<Object> paramList,final int outIndex, final int type);
    public Long callProc(final String proc,final List<String> paramList);
}
