package com.aming.sso.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;
import com.sendtend.sso.aes.AESCoder;
import com.sendtend.sso.model.TokenModel;

public class UserSysModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4818589960288438211L;
	private Integer sys_id;
	private String sys_name;
	private String sys_url;
	private String token_key;
	private BigDecimal securitylevel;
	private Integer security_level;
	private String login_field;
	private String pwd_field;
	private String login_flag1;
	private String sysuser_id;
	private String sysuser_pwd;
	private String sysuser_type;
	private String sid;
	private Date sid_time;
	private String field1;
	private String token;
	public BigDecimal getSecuritylevel() {
		return securitylevel;
	}
	public void setSecuritylevel(BigDecimal securitylevel) {
		this.securitylevel = securitylevel;
	}
	public Integer getSecurity_level() {
		if(this.securitylevel != null) {
			return getSecuritylevel().intValue();
		}
		return this.security_level;
	}
	public void setSecurity_level(Integer security_level) {
		this.security_level = security_level;
	}
	public String getToken() {
		if(this.securitylevel != null && this.securitylevel.intValue()>=0) {
			TokenModel tm = new TokenModel();
			tm.setLevel(this.securitylevel.intValue());
			tm.setLoginname(this.sysuser_id);
			tm.setSessionTime(Calendar.getInstance(TimeZone.getTimeZone("Asia/Beijing")).getTime());
			Key key = AESCoder.keyStrToKey(this.token_key);
			/*
			 * 二级或者三级需要set pwd
			 */
			if(this.securitylevel.intValue() >= 2) {
				try {
					String pwd = new String(AESCoder.decrypt(AESCoder.parseHexStr2Byte(this.sysuser_pwd), key));
					tm.setLoginpwd(pwd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			tm.setUsertype(this.sysuser_type);
			/*
			 * 三级安全传sid
			 */
			if(this.securitylevel.intValue() >= 3) {
				tm.setSid(this.sid);
			}
			try {
				this.token = AESCoder.parseByte2HexStr(AESCoder.encrypt(JSON.toJSONString(tm).getBytes(), key));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return token;
	}
	public String getToken_key() {
		return token_key;
	}
	public void setToken_key(String token_key) {
		this.token_key = token_key;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Date getSid_time() {
		return sid_time;
	}
	public void setSid_time(Date sid_time) {
		this.sid_time = sid_time;
	}
	public Integer getSys_id() {
		return sys_id;
	}
	public void setSys_id(Integer sys_id) {
		this.sys_id = sys_id;
	}
	public String getSys_name() {
		return sys_name;
	}
	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}
	public String getSys_url() {
		return sys_url;
	}
	public void setSys_url(String sys_url) {
		this.sys_url = sys_url;
	}
	public String getLogin_field() {
		return login_field;
	}
	public void setLogin_field(String login_field) {
		this.login_field = login_field;
	}
	public String getPwd_field() {
		return pwd_field;
	}
	public void setPwd_field(String pwd_field) {
		this.pwd_field = pwd_field;
	}
	public String getLogin_flag1() {
		return login_flag1;
	}
	public void setLogin_flag1(String login_flag1) {
		this.login_flag1 = login_flag1;
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
	public String getSysuser_type() {
		return sysuser_type;
	}
	public void setSysuser_type(String sysuser_type) {
		this.sysuser_type = sysuser_type;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	@Override
	public String toString() {
		return "UserSysModel [sys_id=" + sys_id + ", sys_name=" + sys_name + ", sys_url=" + sys_url + ", token_key=" + token_key + ", securitylevel="
				+ securitylevel + ", login_field=" + login_field + ", pwd_field=" + pwd_field + ", login_flag1=" + login_flag1 + ", sysuser_id=" + sysuser_id
				+ ", sysuser_pwd=" + sysuser_pwd + ", sysuser_type=" + sysuser_type + ", sid=" + sid + ", sid_time=" + sid_time + ", field1=" + field1 + "]";
	}
}
