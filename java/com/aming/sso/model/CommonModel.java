package com.aming.sso.model;

import java.io.Serializable;

public class CommonModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3435393527442336432L;
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
