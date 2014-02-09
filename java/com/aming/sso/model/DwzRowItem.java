package com.aming.sso.model;

import java.io.Serializable;
import java.util.Date;

public class DwzRowItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -19901597460217826L;
	private Integer user_sys_id;
	private Integer user_id;
	private Integer sys_id;
	private Integer id;
	private String sysuserType;
	private String loginName;
	private String sysuserId;
	private String sysuserPwd;
	private String sysuser_type;
	private String login_name;
	private String sysuser_id;
	private String sysuser_pwd;
	private String field1;
	private String sid;
	private Date sid_time;
	public Date getSid_time() {
		return sid_time;
	}
	public void setSid_time(Date sid_time) {
		this.sid_time = sid_time;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public Integer getUser_sys_id() {
		return user_sys_id;
	}
	public void setUser_sys_id(Integer user_sys_id) {
		this.user_sys_id = user_sys_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getSys_id() {
		return sys_id;
	}
	public void setSys_id(Integer sys_id) {
		this.sys_id = sys_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSysuserType() {
		return sysuserType;
	}
	public void setSysuserType(String sysuserType) {
		this.sysuserType = sysuserType;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getSysuserId() {
		return sysuserId;
	}
	public void setSysuserId(String sysuserId) {
		this.sysuserId = sysuserId;
	}
	public String getSysuserPwd() {
		return sysuserPwd;
	}
	public void setSysuserPwd(String sysuserPwd) {
		this.sysuserPwd = sysuserPwd;
	}
	public String getSysuser_type() {
		return sysuser_type;
	}
	public void setSysuser_type(String sysuser_type) {
		this.sysuser_type = sysuser_type;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getSysuser_id() {
		return sysuser_id;
	}
	public void setSysuser_id(String sysuser_id) {
		this.sysuser_id = sysuser_id;
	}
	public String getSysuser_pwd() {
		return sysuser_pwd;
	}
	public void setSysuser_pwd(String sysuser_pwd) {
		this.sysuser_pwd = sysuser_pwd;
	}
	@Override
	public String toString() {
		return "DwzRowItem [user_sys_id=" + user_sys_id + ", user_id=" + user_id + ", sys_id=" + sys_id + ", id=" + id + ", sysuserType=" + sysuserType
				+ ", loginName=" + loginName + ", sysuserId=" + sysuserId + ", sysuserPwd=" + sysuserPwd + ", sysuser_type=" + sysuser_type + ", login_name="
				+ login_name + ", sysuser_id=" + sysuser_id + ", sysuser_pwd=" + sysuser_pwd + "]";
	}
}
