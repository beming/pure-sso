package com.aming.sso.service;


import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import com.aming.sso.ContextDefine;
import com.aming.sso.model.CommonModel;
import com.aming.sso.model.GlobalModel;
import com.sendtend.util.PropertiesParse;

public class InitContext implements ServletContextAware {
	protected final transient Log log = LogFactory.getLog(InitContext.class);
	
	@Autowired
	private CacheService cacheService;
	private ServletContext context;
	private byte[] lock = new byte[0]; // 特殊的instance变量
	
	/**
	 * 
	 */
	public void init() {
		//log.debug("------------------------fc you -------------------");
		synchronized(lock){
			Properties prop = new Properties();
			/*
			 * =====================
			 * 配置数据
			 * =====================
			 */
			if(this.context.getAttribute(ContextDefine.CTRL_DATA_TAG) == null) {
				if(log.isInfoEnabled()) {
					log.info("------------开始初始化AJAX DATA--------------");
				}
				PropertiesParse parse = new PropertiesParse();
				parse.setPropFile("CtrlData.dat");
				prop = parse.parsePropFile();
				if(log.isInfoEnabled()) {
					log.info("-------properties:" + prop);
				}
				this.context.setAttribute(ContextDefine.CTRL_DATA_TAG, prop);
			}
			/*
			 * =====================
			 * 初始化全局参数
			 * =====================
			 */
			if(this.context.getAttribute(ContextDefine.GLOBAL_MODEL) == null) {
				if(log.isInfoEnabled()) {
					log.info("------------开始初始化全局变量--------------");
				}
				GlobalModel gm = cacheService.initGlobalModel();
				this.context.setAttribute(ContextDefine.GLOBAL_MODEL, gm);
			}
		}
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
