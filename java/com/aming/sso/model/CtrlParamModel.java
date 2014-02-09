package com.aming.sso.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class CtrlParamModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6235000652478161948L;
	private String msg; 
	private String flag;
	
	@JSONField(name = "MSG")
	public String getMsg() {
		return msg;
	}
	@JSONField(name = "MSG")
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@JSONField(name = "FLAG")
	public String getFlag() {
		return flag;
	}
	@JSONField(name = "FLAG")
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
