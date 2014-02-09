package com.aming.sso.model;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 提取公用部分，放到application中
 * 
 * @author Administrator
 *
 */
public class GlobalModel implements Serializable {
	protected final transient Log log = LogFactory.getLog(GlobalModel.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -1911964522929215876L;
	private String msg;
	private String flag; //成功与否标志
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
