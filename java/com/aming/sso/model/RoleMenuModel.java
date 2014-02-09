package com.aming.sso.model;

import java.io.Serializable;

public class RoleMenuModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2366342731547390172L;
	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private Integer parentId;
	private String parentMenu;
	private Integer isMenu;
	private boolean checked = false;
	private boolean RSelect = false;
	private boolean RAdd = false;
	private boolean RUpdate = false;
	private boolean RDel = false;
	private boolean RExec = false;
	public boolean isRExec() {
		return RExec;
	}
	public void setRExec(boolean rExec) {
		RExec = rExec;
	}
	public boolean getRSelect() {
		return RSelect;
	}
	public void setRSelect(boolean rSelect) {
		RSelect = rSelect;
	}
	public boolean getRAdd() {
		return RAdd;
	}
	public void setRAdd(boolean rAdd) {
		RAdd = rAdd;
	}
	public boolean getRUpdate() {
		return RUpdate;
	}
	public void setRUpdate(boolean rUpdate) {
		RUpdate = rUpdate;
	}
	public boolean getRDel() {
		return RDel;
	}
	public void setRDel(boolean rDel) {
		RDel = rDel;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
	}
	public Integer getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	@Override
	public String toString() {
		return "RoleMenuModel [menuId=" + menuId + ", menuName=" + menuName + ", menuUrl=" + menuUrl + ", parentId=" + parentId + ", parentMenu=" + parentMenu
				+ ", isMenu=" + isMenu + ", checked=" + checked + ", RSelect=" + RSelect + ", RAdd=" + RAdd + ", RUpdate=" + RUpdate + ", RDel=" + RDel + "]";
	}
}
