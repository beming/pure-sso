package com.aming.sso.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.model.GlobalModel;

@Service
public class CacheService {
	protected final transient Log log = LogFactory.getLog(CacheService.class);

	//@Autowired
	//private BaseDaoImpl dao;

	
	/**
	 * 
	 * @return
	 */
	public GlobalModel initGlobalModel() {
		log.info("-- initGlobalModel--");
		//List<Object> result = new ArrayList<Object>();
		/* 获取xxmc的相关信息 */
		GlobalModel gm = new GlobalModel();
		return gm;
	}
	
}
