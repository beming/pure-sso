package com.aming.sso.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_role")
public class TblRole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5673795206289418008L;
	private Integer roleId;
	private String roleName;
	private Byte roleStatus;
	private String roleRemark;

	// Constructors

	/** default constructor */
	public TblRole() {
	}

	/** minimal constructor */
	public TblRole(String roleName, Byte roleStatus) {
		this.roleName = roleName;
		this.roleStatus = roleStatus;
	}

	/** full constructor */
	public TblRole(String roleName, Byte roleStatus, String roleRemark) {
		this.roleName = roleName;
		this.roleStatus = roleStatus;
		this.roleRemark = roleRemark;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name", nullable = false, length = 32)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_status", nullable = false, precision = 2, scale = 0)
	public Byte getRoleStatus() {
		return this.roleStatus;
	}

	public void setRoleStatus(Byte roleStatus) {
		this.roleStatus = roleStatus;
	}

	@Column(name = "role_remark", length = 1024)
	public String getRoleRemark() {
		return this.roleRemark;
	}

	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}

}