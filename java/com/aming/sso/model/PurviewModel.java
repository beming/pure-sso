package com.aming.sso.model;

import java.io.Serializable;
import java.util.List;

import com.aming.sso.entity.TblMenu;
import com.aming.sso.entity.TblRole;
import com.aming.sso.entity.TblRoleMenu;

public class PurviewModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2737447247301970033L;
	private List<UserSysModel> sysSet;
	private List<TblMenu> menuSet;
	private List<TblMenu> rightSet;
	private List<TblRole> roleSet;
	private List<TblRoleMenu> roleMenuSet;
	public List<UserSysModel> getSysSet() {
		return sysSet;
	}
	public void setSysSet(List<UserSysModel> sysSet) {
		this.sysSet = sysSet;
	}
	public List<TblMenu> getMenuSet() {
		return menuSet;
	}
	public void setMenuSet(List<TblMenu> menuSet) {
		this.menuSet = menuSet;
	}
	public List<TblMenu> getRightSet() {
		return rightSet;
	}
	public void setRightSet(List<TblMenu> rightSet) {
		this.rightSet = rightSet;
	}
	public List<TblRole> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(List<TblRole> roleSet) {
		this.roleSet = roleSet;
	}
	public List<TblRoleMenu> getRoleMenuSet() {
		return roleMenuSet;
	}
	public void setRoleMenuSet(List<TblRoleMenu> roleMenuSet) {
		this.roleMenuSet = roleMenuSet;
	}
	@Override
	public String toString() {
		return "PurviewModel [sysSet=" + sysSet + ", menuSet=" + menuSet + ", rightSet=" + rightSet + ", roleSet=" + roleSet + ", roleMenuSet=" + roleMenuSet
				+ "]";
	}
}
