package com.aming.sso.cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aming.sso.ControlDefine;
import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.service.InitContext;

@Service
@Component
public class CacheService {
	protected final transient Log log = LogFactory.getLog(CacheService.class);

	//@Autowired
	//private BaseDaoImpl dao;
	/**
	 * 
	 * @param cm
	 */
	@Required
	@Transactional
	public void initCache(ServletContext context, BaseDaoImpl dao) {
		if(log.isDebugEnabled()) {
			log.debug("-----------一进入实时启动初始化方法");
		}
		Properties prop = InitContext.getConfigData(ControlDefine.CTRL_DATA, context);
		if(log.isDebugEnabled()) {
			log.debug("-----------一finish call to get properties!");
		}
		if(prop != null && !prop.isEmpty()) {
			if(log.isDebugEnabled()) {
				log.debug("-----------一got properties!" + prop.toString());
			}
			String classAndMethodStr = prop.getProperty(ControlDefine.CACHE_CLASS_METHOD);
			if(log.isDebugEnabled()) {
				log.debug("-----------一classAndMethodStr:" + classAndMethodStr);
			}
			if(classAndMethodStr == null || classAndMethodStr.isEmpty()) {
				if(log.isDebugEnabled()) {
					log.debug("-----------一没有配置实时启动初始化方法");
				}
				return;
			}
			if(log.isDebugEnabled()) {
				log.debug("-----------一ready to split by , ");
			}
			String[] classAndMethods = classAndMethodStr.split(",");
			if(log.isDebugEnabled()) {
				log.debug("-----------一classAndMethods length: " + classAndMethods.length);
			}
			if(classAndMethods.length <= 0) {
				return;
			}
			if(log.isDebugEnabled()) {
				log.debug("-----------一ready to open the database session ");
			}
			//dao.openSession();
			if(log.isDebugEnabled()) {
				log.debug("-----------一finish to open the database session ");
			}
			Object[] args = new Object[2];
			args[0] = context;
			args[1] = dao;
			for(String classAndMethod:classAndMethods) {
				if(classAndMethod.length()<5) {
					if(log.isDebugEnabled()) {
						log.debug("-----------一配置数据异常,跳过:" + classAndMethod);
					}
					continue;
				}
				String clazz = classAndMethod.substring(0, classAndMethod.indexOf(".class"));
				String methodName = classAndMethod.substring(classAndMethod.indexOf(".class") + 7);
				if(clazz != null && clazz.length()>1 && methodName != null && methodName.length()>1) {
					if(log.isDebugEnabled()) {
						log.debug("------- cache Class name:" + clazz + ", the init method name: " + methodName);
					}
					//MANUL TO OPEN HIBERNATE SESSION
					try {
						Class<?> cObj = Class.forName(clazz);
						Object obj = cObj.newInstance();
						//只能由该名字的唯一一个方法，不能有重名方法
						Method[] ms = cObj.getMethods();
						//Method m = cObj.getMethod(methodName);
						for(Method method:ms) {
							if(method.getName().equals(methodName)) {
								method.invoke(obj, args);
							}
						}
		            } catch (ClassNotFoundException e) {
						log.warn("--------该类没有找到，忽略缓存，或请配置CtrlData.dat");
					} catch (InstantiationException e) {
						log.warn("--------实例化出错，请在配置类中加上构造函数");
					} catch (IllegalAccessException e) {
						log.warn("--------访问出错，请检查类或方法的定义是否为public");
					} catch (SecurityException e) {
						log.warn("--------权限有误，请检查文件权限");
					} catch (IllegalArgumentException e) {
						log.warn("--------参数异常，改方法只能由一个参数：ServletContext");
					} catch (InvocationTargetException e) {
						log.warn("--------InvocationTargetException：" + e.getMessage());
					} catch (Exception ex) {
						ex.printStackTrace();
						log.warn("--------同步缓存最后的Exception：" + ex.getMessage());
					}
				}
			}
			//dao.closeSession();
		}
	}
}
