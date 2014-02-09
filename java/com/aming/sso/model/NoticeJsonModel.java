package com.aming.sso.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class NoticeJsonModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8317330017965180261L;
	private String ggbt;
	private String fbdw;
	private String fbsj;
	private String yxqx;
	private String ggzw;
	private String xh;
	private String gglbdm;
	private String sfzd;
	@JSONField(name = "GGBT")
	public String getGgbt() {
		return ggbt;
	}
	@JSONField(name = "GGBT")
	public void setGgbt(String ggbt) {
		this.ggbt = ggbt;
	}
	@JSONField(name = "FBDW")
	public String getFbdw() {
		return fbdw;
	}
	@JSONField(name = "FBDW")
	public void setFbdw(String fbdw) {
		this.fbdw = fbdw;
	}
	@JSONField(name = "FBSJ")
	public String getFbsj() {
		return fbsj;
	}
	@JSONField(name = "FBSJ")
	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}
	@JSONField(name = "YXQX")
	public String getYxqx() {
		return yxqx;
	}
	@JSONField(name = "YXQX")
	public void setYxqx(String yxqx) {
		this.yxqx = yxqx;
	}
	@JSONField(name = "GGZW")
	public String getGgzw() {
		return ggzw;
	}
	@JSONField(name = "GGZW")
	public void setGgzw(String ggzw) {
		this.ggzw = ggzw;
	}
	@JSONField(name = "XH")
	public String getXh() {
		return xh;
	}
	@JSONField(name = "XH")
	public void setXh(String xh) {
		this.xh = xh;
	}
	@JSONField(name = "GGLBDM")
	public String getGglbdm() {
		return gglbdm;
	}
	@JSONField(name = "GGLBDM")
	public void setGglbdm(String gglbdm) {
		this.gglbdm = gglbdm;
	}
	@JSONField(name = "SFZD")
	public String getSfzd() {
		return sfzd;
	}
	@JSONField(name = "SFZD")
	public void setSfzd(String sfzd) {
		this.sfzd = sfzd;
	}
	
}
