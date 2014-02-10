package com.aming.sso.service;


import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import com.aming.sso.ContextDefine;
import com.aming.sso.ControlDefine;
import com.aming.sso.cache.AsyncCacheService;
import com.aming.sso.cache.CacheService;
import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.model.CommonModel;
import com.aming.sso.model.GlobalModel;
import com.sendtend.util.PropertiesParse;

@Service
@Component
@Transactional
public class InitContext implements ServletContextAware {
	protected final transient Log log = LogFactory.getLog(InitContext.class);
	
	@Autowired
	@Qualifier("entityDao") 
	private BaseDaoImpl dao;
	private ServletContext context;
	
	private byte[] lock = new byte[0]; // 特殊的instance变量
	
	public BaseDaoImpl getDao() {
		return dao;
	}

	public void setDao(BaseDaoImpl dao) {
		this.dao = dao;
	}

	//@PostConstruct
	@Required
	@Transactional
	public void init() {
		synchronized(lock){
			
			Properties prop = new Properties();
			/*
			 * =====================
			 * 配置数据
			 * =====================
			 */
			if(this.context.getAttribute(ControlDefine.CTRL_DATA) == null) {
				if(log.isInfoEnabled()) {
					log.info("------------开始初始化AJAX DATA--------------");
				}
				PropertiesParse parse = new PropertiesParse();
				parse.setPropFile("CtrlData.dat");
				prop = parse.parsePropFile();
				if(log.isInfoEnabled()) {
					log.info("-------properties:" + prop);
				}
				this.context.setAttribute(ControlDefine.CTRL_DATA, prop);
			}
			//缓存
			String addSessionFileStr = prop.getProperty(ControlDefine.ADD_SESSION_FILE);
			if(log.isInfoEnabled()) {
				log.info("-------addSessionFileStr:" + addSessionFileStr);
			}
			if(addSessionFileStr != null && !addSessionFileStr.isEmpty()) {
				String[] asfs = addSessionFileStr.split(",");
				String sid = null;
				String fn = null;
				for(String asf:asfs) {
					if(asf.length() > 1) {
						if(log.isInfoEnabled()) {
							log.info("-------asf:" + asf);
						}
						sid = asf.split(":")[0];
						fn = asf.split(":")[1];
						PropertiesParse parse = new PropertiesParse();
						parse.setPropFile(fn);
						prop = parse.parsePropFile();
						if(log.isInfoEnabled()) {
							log.info("-------properties:" + prop);
						}
						this.context.setAttribute(sid, prop);
					}
				}
			}
			if(log.isInfoEnabled()) {
				log.info("-------test dao:");
			}
			String sql = "select 1";
			dao.openSession();
			dao.queryNativeSQL(sql, null, 1, -1);
			if(log.isInfoEnabled()) {
				log.info("-------end of test dao:");
			}
			//调用缓存方法！
			CacheService cs = new CacheService();
			cs.initCache(this.context, dao);
			//调用异步执行缓存方法！
			AsyncCacheService as = new AsyncCacheService();
			as.initCache(this.context, dao);
			dao.closeSession();
		}
	}
	
	/**
	 * 
	 * @param tag
	 * @param context
	 * @return
	 */
	public static Properties getConfigData(String tag, ServletContext context) {
		if(tag == null || tag.length()<=0 || context == null) {
			return null;
		}
		return (Properties)context.getAttribute(tag);
	}
	
	/**
	 * 
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	/**
	 * 
	 * @return
	 */
	public static InitContext getInstance() {
		return new InitContext();
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	public GlobalModel getGlobalModel(HttpSession session) {
		return (GlobalModel)session.getServletContext().getAttribute(ContextDefine.GLOBAL_MODEL);
	}
	
	/**
	 * 
	 * @param session
	 * @param xh
	 * @return
	 */
	public CommonModel getCommonModel(HttpSession session, String xh) {
		return (CommonModel)session.getServletContext().getAttribute(xh);
	}
	
	/**
	 * 
	 * @param session
	 * @param xh
	 * @param cm
	 */
	public void setCommonModel(HttpSession session, String xh, CommonModel cm) {
		session.getServletContext().setAttribute(xh,cm);
	}
	
	/**
	 * 
	 * @param session
	 * @param ajaxDataTag ContextDefine
	 * @return
	 */
	public Object getCtrlDataByTag(HttpSession session, String tag) {
		Properties prop = (Properties)session.getServletContext().getAttribute(ContextDefine.CTRL_DATA_TAG);
		//log.info("--------------获取到的prop:" + prop);
		if(prop != null) {
			if(tag != null && tag.trim().length() > 0) {
				return prop.getProperty(tag);
			} else {
				return prop;
			}
		}
		return null;
	}
	
}
