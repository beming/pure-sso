package com.aming.sso.cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.aming.sso.ControlDefine;
import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.service.InitContext;

@Service
@Component
public class AsyncCacheService {
	protected final transient Log log = LogFactory.getLog(AsyncCacheService.class);

	//@Autowired
	//private BaseDaoImpl dao;
	
	/**
	 * 
	 * @param cm
	 */
	@Async
	public void initCache(ServletContext context, BaseDaoImpl dao) {
		if(log.isDebugEnabled()) {
			log.debug("-----------一进入异步初始化方法");
		}
		Properties prop = InitContext.getConfigData(ControlDefine.CTRL_DATA, context);
		if(prop != null && !prop.isEmpty()) {
			String classAndMethodStr = prop.getProperty(ControlDefine.ASYNC_CACHE_CLASS_METHOD);
			if(classAndMethodStr == null || classAndMethodStr.isEmpty()) {
				if(log.isDebugEnabled()) {
					log.debug("-----------一没有配置异步初始化方法");
				}
				return;
			}
			String[] classAndMethods = classAndMethodStr.split(",");
			if(classAndMethods.length <= 0) {
				return;
			}
			//dao.openSession();
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
					try {
						Class<?> cObj = Class.forName(clazz);
						Object obj = cObj.newInstance();
						//只能由该名字的唯一一个方法，不能有重名方法
						Method m = cObj.getMethod(methodName);
						m.invoke(obj, args);
		            } catch (ClassNotFoundException e) {
						log.warn("--------该类没有找到，忽略缓存，或请配置CtrlData.dat");
					} catch (InstantiationException e) {
						log.warn("--------实例化出错，请在配置类中加上构造函数");
					} catch (IllegalAccessException e) {
						log.warn("--------访问出错，请检查类或方法的定义是否为public");
					} catch (NoSuchMethodException e) {
						log.warn("--------调用的方法没有找到，请检查配置CtrlData.dat，配置正确的方法名");
					} catch (SecurityException e) {
						log.warn("--------权限有误，请检查文件权限");
					} catch (IllegalArgumentException e) {
						log.warn("--------参数异常，改方法只能由一个参数：ServletContext");
					} catch (InvocationTargetException e) {
						log.warn("--------InvocationTargetException：" + e.getMessage());
					} catch (Exception ex) {
						ex.printStackTrace();
						log.warn("--------异步 缓存最后的Exception：" + ex.getMessage());
					}
				}
			}
			//dao.closeSession();
		}
	}
}
