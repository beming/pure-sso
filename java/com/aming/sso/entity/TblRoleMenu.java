package com.aming.sso.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblRoleMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_role_menu")
public class TblRoleMenu implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1201911522275281892L;
	private Integer roleMenuId;
	private Integer roleId;
	private Integer menuId;
	private Integer RSelect;
	private Integer RAdd;
	private Integer RUpdate;
	private Integer RDel;
	private Integer RExec;

	// Constructors

	/** default constructor */
	public TblRoleMenu() {
	}

	/** minimal constructor */
	public TblRoleMenu(Integer roleId, Integer menuId, Integer RSelect, Integer RAdd, Integer RUpdate, Integer RDel) {
		this.roleId = roleId;
		this.menuId = menuId;
		this.RSelect = RSelect;
		this.RAdd = RAdd;
		this.RUpdate = RUpdate;
		this.RDel = RDel;
	}

	/** full constructor */
	public TblRoleMenu(Integer roleId, Integer menuId, Integer RSelect, Integer RAdd, Integer RUpdate, Integer RDel, Integer RExec) {
		this.roleId = roleId;
		this.menuId = menuId;
		this.RSelect = RSelect;
		this.RAdd = RAdd;
		this.RUpdate = RUpdate;
		this.RDel = RDel;
		this.RExec = RExec;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_menu_id", unique = true, nullable = false)
	public Integer getRoleMenuId() {
		return this.roleMenuId;
	}

	public void setRoleMenuId(Integer roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	@Column(name = "role_id", nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "menu_id", nullable = false)
	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Column(name = "r_select", nullable = false, precision = 1, scale = 0)
	public Integer getRSelect() {
		return this.RSelect;
	}

	public void setRSelect(Integer RSelect) {
		this.RSelect = RSelect;
	}

	@Column(name = "r_add", nullable = false, precision = 1, scale = 0)
	public Integer getRAdd() {
		return this.RAdd;
	}

	public void setRAdd(Integer RAdd) {
		this.RAdd = RAdd;
	}

	@Column(name = "r_update", nullable = false, precision = 1, scale = 0)
	public Integer getRUpdate() {
		return this.RUpdate;
	}

	public void setRUpdate(Integer RUpdate) {
		this.RUpdate = RUpdate;
	}

	@Column(name = "r_del", nullable = false, precision = 1, scale = 0)
	public Integer getRDel() {
		return this.RDel;
	}

	public void setRDel(Integer RDel) {
		this.RDel = RDel;
	}

	@Column(name = "r_exec", precision = 1, scale = 0)
	public Integer getRExec() {
		return this.RExec;
	}

	public void setRExec(Integer RExec) {
		this.RExec = RExec;
	}

}