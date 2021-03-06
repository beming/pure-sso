package com.aming.sso.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblDepartment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_department")
public class TblDepartment implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8694756817777942516L;
	private Integer depId;
	private String depCode;
	private String depName;
	private Integer parentDepId;
	private Integer depLevel;
	private String depLevelCode;
	private Byte depStatus;
	private String depLinker;
	private String depPhone;
	private String depEmail;
	private String depTeacher;
	private String depRemark;

	// Constructors

	/** default constructor */
	public TblDepartment() {
	}

	/** minimal constructor */
	public TblDepartment(String depName, Integer parentDepId, Integer depLevel, Byte depStatus) {
		this.depName = depName;
		this.parentDepId = parentDepId;
		this.depLevel = depLevel;
		this.depStatus = depStatus;
	}

	/** full constructor */
	public TblDepartment(String depCode, String depName, Integer parentDepId, Integer depLevel, String depLevelCode, Byte depStatus, String depLinker,
			String depPhone, String depEmail, String depTeacher, String depRemark) {
		this.depCode = depCode;
		this.depName = depName;
		this.parentDepId = parentDepId;
		this.depLevel = depLevel;
		this.depLevelCode = depLevelCode;
		this.depStatus = depStatus;
		this.depLinker = depLinker;
		this.depPhone = depPhone;
		this.depEmail = depEmail;
		this.depTeacher = depTeacher;
		this.depRemark = depRemark;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "dep_id", unique = true, nullable = false)
	public Integer getDepId() {
		return this.depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	@Column(name = "dep_code", length = 32)
	public String getDepCode() {
		return this.depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	@Column(name = "dep_name", nullable = false, length = 32)
	public String getDepName() {
		return this.depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	@Column(name = "parent_dep_id", nullable = false)
	public Integer getParentDepId() {
		return this.parentDepId;
	}

	public void setParentDepId(Integer parentDepId) {
		this.parentDepId = parentDepId;
	}

	@Column(name = "dep_level", nullable = false)
	public Integer getDepLevel() {
		return this.depLevel;
	}

	public void setDepLevel(Integer depLevel) {
		this.depLevel = depLevel;
	}

	@Column(name = "dep_level_code", length = 32)
	public String getDepLevelCode() {
		return this.depLevelCode;
	}

	public void setDepLevelCode(String depLevelCode) {
		this.depLevelCode = depLevelCode;
	}

	@Column(name = "dep_status", nullable = false, precision = 2, scale = 0)
	public Byte getDepStatus() {
		return this.depStatus;
	}

	public void setDepStatus(Byte depStatus) {
		this.depStatus = depStatus;
	}

	@Column(name = "dep_linker", length = 32)
	public String getDepLinker() {
		return this.depLinker;
	}

	public void setDepLinker(String depLinker) {
		this.depLinker = depLinker;
	}

	@Column(name = "dep_phone", length = 32)
	public String getDepPhone() {
		return this.depPhone;
	}

	public void setDepPhone(String depPhone) {
		this.depPhone = depPhone;
	}

	@Column(name = "dep_email", length = 32)
	public String getDepEmail() {
		return this.depEmail;
	}

	public void setDepEmail(String depEmail) {
		this.depEmail = depEmail;
	}

	@Column(name = "dep_teacher", length = 1024)
	public String getDepTeacher() {
		return this.depTeacher;
	}

	public void setDepTeacher(String depTeacher) {
		this.depTeacher = depTeacher;
	}

	@Column(name = "dep_remark", length = 1024)
	public String getDepRemark() {
		return this.depRemark;
	}

	public void setDepRemark(String depRemark) {
		this.depRemark = depRemark;
	}

}