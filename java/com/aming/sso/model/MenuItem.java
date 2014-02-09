package com.aming.sso.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1168623272996065557L;
	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private String navcode;
	private Integer menuLevel;
	private Integer parentId;
	private String parentMenuName;
	private List<MenuItem> children = new ArrayList<MenuItem>();

	public MenuItem(Integer menuId, String name, String url, Integer menuLevel, Integer parentId, String navcode) {
		this.menuId = menuId;
		this.menuLevel = menuLevel;
		this.menuName = name;
		this.menuUrl = url;
		this.parentId = parentId;
		this.navcode = navcode;
	}
	
	public MenuItem(Integer menuId, String name, Integer menuLevel, Integer parentId, String navcode) {
		this.menuId = menuId;
		this.menuLevel = menuLevel;
		this.menuName = name;
		this.parentId = parentId;
		this.navcode = navcode;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	public void addChildMenu(MenuItem menu) {
		this.children.add(menu);
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

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getNavcode() {
		return navcode;
	}

	public void setNavcode(String navcode) {
		this.navcode = navcode;
	}
}
