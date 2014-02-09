package com.aming.sso.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TblUserSys entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_user_sys")
public class TblUserSys implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2555697990197354136L;
	private Integer userSysId;
	private Integer userId;
	private Integer sysId;
	private String sysuserId;
	private String sysuserPwd;
	private String sysuserType;
	private String sid;
	private Date sidTime;
	private String field1;

	// Constructors

	/** default constructor */
	public TblUserSys() {
	}

	/** minimal constructor */
	public TblUserSys(Integer userId, Integer sysId, String sysuserId) {
		this.userId = userId;
		this.sysId = sysId;
		this.sysuserId = sysuserId;
	}

	/** full constructor */
	public TblUserSys(Integer userId, Integer sysId, String sysuserId, String sysuserPwd, String sysuserType, String sid, Date sidTime, String field1) {
		this.userId = userId;
		this.sysId = sysId;
		this.sysuserId = sysuserId;
		this.sysuserPwd = sysuserPwd;
		this.sysuserType = sysuserType;
		this.sid = sid;
		this.sidTime = sidTime;
		this.field1 = field1;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_sys_id", unique = true, nullable = false)
	public Integer getUserSysId() {
		return this.userSysId;
	}

	public void setUserSysId(Integer userSysId) {
		this.userSysId = userSysId;
	}

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "sys_id", nullable = false)
	public Integer getSysId() {
		return this.sysId;
	}

	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}

	@Column(name = "sysuser_id", nullable = false, length = 32)
	public String getSysuserId() {
		return this.sysuserId;
	}

	public void setSysuserId(String sysuserId) {
		this.sysuserId = sysuserId;
	}

	@Column(name = "sysuser_pwd", length = 32)
	public String getSysuserPwd() {
		return this.sysuserPwd;
	}

	public void setSysuserPwd(String sysuserPwd) {
		this.sysuserPwd = sysuserPwd;
	}

	@Column(name = "sysuser_type", length = 256)
	public String getSysuserType() {
		return this.sysuserType;
	}

	public void setSysuserType(String sysuserType) {
		this.sysuserType = sysuserType;
	}

	@Column(name = "sid", length = 256)
	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "sid_time", length = 13)
	public Date getSidTime() {
		return this.sidTime;
	}

	public void setSidTime(Date sidTime) {
		this.sidTime = sidTime;
	}

	@Column(name = "field1", length = 128)
	public String getField1() {
		return this.field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

}