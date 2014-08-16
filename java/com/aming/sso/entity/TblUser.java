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
 * TblUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_user")
public class TblUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810823207483106857L;
	private Integer userId;
	private Integer depId;
	private Integer postId;
	private String loginName;
	private String userName;
	private String passwd;
	private Date chgPwdDate;
	private Integer status;
	private Boolean idType;
	@Override
	public String toString() {
		return "TblUser [userId=" + userId + ", depId=" + depId + ", postId=" + postId + ", loginName=" + loginName + ", userName=" + userName + ", passwd="
				+ passwd + ", chgPwdDate=" + chgPwdDate + ", status=" + status + ", idType=" + idType + ", idNum=" + idNum + ", sexy=" + sexy + ", birthday="
				+ birthday + ", phone=" + phone + ", mobile=" + mobile + ", addr=" + addr + ", postCode=" + postCode + ", email=" + email + ", lastLoginTime="
				+ lastLoginTime + ", remark=" + remark + "]";
	}

	private String idNum;
	private Integer sexy;
	private Date birthday;
	private String phone;
	private String mobile;
	private String addr;
	private String postCode;
	private String email;
	private Date lastLoginTime;
	private String remark;

	// Constructors

	/** default constructor */
	public TblUser() {
	}

	/** minimal constructor */
	public TblUser(String loginName, String userName, String passwd, Integer status) {
		this.loginName = loginName;
		this.userName = userName;
		this.passwd = passwd;
		this.status = status;
	}

	/** full constructor */
	public TblUser(Integer depId, Integer postId, String loginName, String userName, String passwd, Date chgPwdDate, Integer status, Boolean idType, String idNum,
			Integer sexy, Date birthday, String phone, String mobile, String addr, String postCode, String email, Date lastLoginTime, String remark) {
		this.depId = depId;
		this.postId = postId;
		this.loginName = loginName;
		this.userName = userName;
		this.passwd = passwd;
		this.chgPwdDate = chgPwdDate;
		this.status = status;
		this.idType = idType;
		this.idNum = idNum;
		this.sexy = sexy;
		this.birthday = birthday;
		this.phone = phone;
		this.mobile = mobile;
		this.addr = addr;
		this.postCode = postCode;
		this.email = email;
		this.lastLoginTime = lastLoginTime;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "dep_id")
	public Integer getDepId() {
		return this.depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	@Column(name = "post_id")
	public Integer getPostId() {
		return this.postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	@Column(name = "login_name", nullable = false, length = 32)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "user_name", nullable = false, length = 32)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "passwd", nullable = false, length = 32)
	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "chg_pwd_date", length = 13)
	public Date getChgPwdDate() {
		return this.chgPwdDate;
	}

	public void setChgPwdDate(Date chgPwdDate) {
		this.chgPwdDate = chgPwdDate;
	}

	@Column(name = "status", nullable = false, precision = 2, scale = 0)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "id_type", precision = 1, scale = 0)
	public Boolean getIdType() {
		return this.idType;
	}

	public void setIdType(Boolean idType) {
		this.idType = idType;
	}

	@Column(name = "id_num", length = 32)
	public String getIdNum() {
		return this.idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	@Column(name = "sexy", precision = 1, scale = 0)
	public Integer getSexy() {
		return this.sexy;
	}

	public void setSexy(Integer sexy) {
		this.sexy = sexy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length = 13)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "phone", length = 32)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mobile", length = 32)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "addr", length = 1024)
	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "post_code", length = 32)
	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "email", length = 32)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "last_login_time", length = 13)
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "remark", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}