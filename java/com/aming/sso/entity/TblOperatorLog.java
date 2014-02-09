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
 * TblOperatorLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_operator_log")
public class TblOperatorLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6555006268890632288L;
	private Integer operatorId;
	private Integer operatorUser;
	private String operatorIp;
	private String operatorLevel;
	private Date operatorTime;
	private String operatorContent;

	// Constructors

	/** default constructor */
	public TblOperatorLog() {
	}

	/** full constructor */
	public TblOperatorLog(Integer operatorUser, String operatorIp, String operatorLevel, Date operatorTime, String operatorContent) {
		this.operatorUser = operatorUser;
		this.operatorIp = operatorIp;
		this.operatorLevel = operatorLevel;
		this.operatorTime = operatorTime;
		this.operatorContent = operatorContent;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "operator_id", unique = true, nullable = false)
	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	@Column(name = "operator_user", nullable = false)
	public Integer getOperatorUser() {
		return this.operatorUser;
	}

	public void setOperatorUser(Integer operatorUser) {
		this.operatorUser = operatorUser;
	}

	@Column(name = "operator_ip", nullable = false, length = 32)
	public String getOperatorIp() {
		return this.operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	@Column(name = "operator_level", nullable = false, length = 32)
	public String getOperatorLevel() {
		return this.operatorLevel;
	}

	public void setOperatorLevel(String operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "operator_time", nullable = false, length = 13)
	public Date getOperatorTime() {
		return this.operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	@Column(name = "operator_content", nullable = false, length = 1024)
	public String getOperatorContent() {
		return this.operatorContent;
	}

	public void setOperatorContent(String operatorContent) {
		this.operatorContent = operatorContent;
	}

}