package com.aming.sso.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.model.CommonModel;

@Component
public class AsyncService {
	protected final transient Log log = LogFactory.getLog(AsyncService.class);

	/**
	 * 
	 * @param cm
	 */
	@Async
	public void updateJxrwbSxrs(CommonModel cm, BaseDaoImpl dao) {
		//
	}
}
