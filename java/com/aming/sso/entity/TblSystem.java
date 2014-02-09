package com.aming.sso.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblSystem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_system")
public class TblSystem implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1990983314027267126L;
	private Integer sysId;
	private String sysName;
	private String sysImg;
	private Integer sysStatus;
	private String sysUrl;
	private String loginField;
	private String pwdField;
	private String loginFlag1;
	private String loginFlag2;
	private Integer securityLevel;
	private String tokenKey;
	private String sysRemark;

	// Constructors

	/** default constructor */
	public TblSystem() {
	}

	/** minimal constructor */
	public TblSystem(String sysName, String sysUrl) {
		this.sysName = sysName;
		this.sysUrl = sysUrl;
	}

	/** full constructor */
	public TblSystem(String sysName, String sysImg, Integer sysStatus, String sysUrl, String loginField, String pwdField, String loginFlag1, String loginFlag2,
			Integer securityLevel, String tokenKey, String sysRemark) {
		this.sysName = sysName;
		this.sysImg = sysImg;
		this.sysStatus = sysStatus;
		this.sysUrl = sysUrl;
		this.loginField = loginField;
		this.pwdField = pwdField;
		this.loginFlag1 = loginFlag1;
		this.loginFlag2 = loginFlag2;
		this.securityLevel = securityLevel;
		this.tokenKey = tokenKey;
		this.sysRemark = sysRemark;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sys_id", unique = true, nullable = false)
	public Integer getSysId() {
		return this.sysId;
	}

	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}

	@Column(name = "sys_name", nullable = false, length = 32)
	public String getSysName() {
		return this.sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	@Column(name = "sys_img", length = 64)
	public String getSysImg() {
		return this.sysImg;
	}

	public void setSysImg(String sysImg) {
		this.sysImg = sysImg;
	}

	@Column(name = "sys_status", precision = 1, scale = 0)
	public Integer getSysStatus() {
		return this.sysStatus;
	}

	public void setSysStatus(Integer sysStatus) {
		this.sysStatus = sysStatus;
	}

	@Column(name = "sys_url", nullable = false, length = 1024)
	public String getSysUrl() {
		return this.sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	@Column(name = "login_field", length = 32)
	public String getLoginField() {
		return this.loginField;
	}

	public void setLoginField(String loginField) {
		this.loginField = loginField;
	}

	@Column(name = "pwd_field", length = 32)
	public String getPwdField() {
		return this.pwdField;
	}

	public void setPwdField(String pwdField) {
		this.pwdField = pwdField;
	}

	@Column(name = "login_flag1", length = 32)
	public String getLoginFlag1() {
		return this.loginFlag1;
	}

	public void setLoginFlag1(String loginFlag1) {
		this.loginFlag1 = loginFlag1;
	}

	@Column(name = "login_flag2", length = 32)
	public String getLoginFlag2() {
		return this.loginFlag2;
	}

	public void setLoginFlag2(String loginFlag2) {
		this.loginFlag2 = loginFlag2;
	}

	@Column(name = "security_level", precision = 1, scale = 0)
	public Integer getSecurityLevel() {
		return this.securityLevel;
	}

	public void setSecurityLevel(Integer securityLevel) {
		this.securityLevel = securityLevel;
	}

	@Column(name = "token_key", length = 256)
	public String getTokenKey() {
		return this.tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	@Column(name = "sys_remark", length = 1024)
	public String getSysRemark() {
		return this.sysRemark;
	}

	public void setSysRemark(String sysRemark) {
		this.sysRemark = sysRemark;
	}

}