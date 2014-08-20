package com.aming.sso.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblPost entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_post")
public class TblPost implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6600250972372326953L;
	private Integer postId;
	private Integer depId;
	private String postName;
	private Byte postStatus;
	private String postRemark;

	// Constructors

	/** default constructor */
	public TblPost() {
	}

	/** minimal constructor */
	public TblPost(Integer depId, String postName, Byte postStatus) {
		this.depId = depId;
		this.postName = postName;
		this.postStatus = postStatus;
	}

	/** full constructor */
	public TblPost(Integer depId, String postName, Byte postStatus, String postRemark) {
		this.depId = depId;
		this.postName = postName;
		this.postStatus = postStatus;
		this.postRemark = postRemark;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "post_id", unique = true, nullable = false)
	public Integer getPostId() {
		return this.postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	@Column(name = "dep_id", nullable = false)
	public Integer getDepId() {
		return this.depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	@Column(name = "post_name", nullable = false, length = 32)
	public String getPostName() {
		return this.postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Column(name = "post_status", nullable = false, precision = 2, scale = 0)
	public Byte getPostStatus() {
		return this.postStatus;
	}

	public void setPostStatus(Byte postStatus) {
		this.postStatus = postStatus;
	}

	@Column(name = "post_remark", length = 1024)
	public String getPostRemark() {
		return this.postRemark;
	}

	public void setPostRemark(String postRemark) {
		this.postRemark = postRemark;
	}

}