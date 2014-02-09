package com.aming.sso.model;

import java.io.Serializable;

public class UserRoleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5021373860033300418L;
	private Integer userId;
	private Integer roleId;
	private String roleName;
	private boolean checked = false;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
