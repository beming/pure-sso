package com.aming.sso.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class JsonModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4859971648047607860L;
	private String campusId;
	private String campusName;
	private String menuId;
	private String menuName;
	private String userId;
	private String userName;
	private String loginName;
	private String roleId;
	private String roleName;
	private String depId;
	private String depName;
	private String postId;
	private String postName;
	@JSONField(name = "loginName")
	public String getLoginName() {
		return loginName;
	}
	@JSONField(name = "loginName")
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	@JSONField(name = "campusId")
	public String getCampusId() {
		return campusId;
	}
	@JSONField(name = "campusId")
	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}
	@JSONField(name = "campusName")
	public String getCampusName() {
		return campusName;
	}
	@JSONField(name = "campusName")
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	@JSONField(name = "menuId")
	public String getMenuId() {
		return menuId;
	}
	@JSONField(name = "menuId")
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	@JSONField(name = "menuName")
	public String getMenuName() {
		return menuName;
	}
	@JSONField(name = "menuName")
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	@JSONField(name = "userId")
	public String getUserId() {
		return userId;
	}
	@JSONField(name = "userId")
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@JSONField(name = "userName")
	public String getUserName() {
		return userName;
	}
	@JSONField(name = "userName")
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@JSONField(name = "roleId")
	public String getRoleId() {
		return roleId;
	}
	@JSONField(name = "roleId")
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@JSONField(name = "roleName")
	public String getRoleName() {
		return roleName;
	}
	@JSONField(name = "roleName")
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@JSONField(name = "depId")
	public String getDepId() {
		return depId;
	}
	@JSONField(name = "depId")
	public void setDepId(String depId) {
		this.depId = depId;
	}
	@JSONField(name = "depName")
	public String getDepName() {
		return depName;
	}
	@JSONField(name = "depName")
	public void setDepName(String depName) {
		this.depName = depName;
	}
	@JSONField(name = "postId")
	public String getPostId() {
		return postId;
	}
	@JSONField(name = "postId")
	public void setPostId(String postId) {
		this.postId = postId;
	}
	@JSONField(name = "postName")
	public String getPostName() {
		return postName;
	}
	@JSONField(name = "postName")
	public void setPostName(String postName) {
		this.postName = postName;
	}	
}
