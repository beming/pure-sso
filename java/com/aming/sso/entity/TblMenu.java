package com.aming.sso.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_menu")
public class TblMenu implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9161297022361295472L;
	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private String navcode;
	private String menuImg;
	private Integer menuSort;
	private Integer parentId;
	private Integer menuLevel;
	private Integer menuStatus;
	private Integer isMenu;
	private Integer topMenuFlag;
	private String menuField;
	private String menuRemark;

	// Constructors

	/** default constructor */
	public TblMenu() {
	}

	/** minimal constructor */
	public TblMenu(String menuName, Integer parentId) {
		this.menuName = menuName;
		this.parentId = parentId;
	}

	/** full constructor */
	public TblMenu(String menuName, String menuUrl, String navcode, String menuImg, Integer menuSort, Integer parentId, Integer menuLevel, Integer menuStatus,
			Integer isMenu, Integer topMenuFlag, String menuField, String menuRemark) {
		this.menuName = menuName;
		this.menuUrl = menuUrl;
		this.navcode = navcode;
		this.menuImg = menuImg;
		this.menuSort = menuSort;
		this.parentId = parentId;
		this.menuLevel = menuLevel;
		this.menuStatus = menuStatus;
		this.isMenu = isMenu;
		this.topMenuFlag = topMenuFlag;
		this.menuField = menuField;
		this.menuRemark = menuRemark;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "menu_id", unique = true, nullable = false)
	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Column(name = "menu_name", nullable = false, length = 32)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "menu_url", length = 32)
	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	@Column(name = "navcode", length = 32)
	public String getNavcode() {
		return this.navcode;
	}

	public void setNavcode(String navcode) {
		this.navcode = navcode;
	}

	@Column(name = "menu_img", length = 64)
	public String getMenuImg() {
		return this.menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}

	@Column(name = "menu_sort")
	public Integer getMenuSort() {
		return this.menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	@Column(name = "parent_id", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "menu_level")
	public Integer getMenuLevel() {
		return this.menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	@Column(name = "menu_status", precision = 1, scale = 0)
	public Integer getMenuStatus() {
		return this.menuStatus;
	}

	public void setMenuStatus(Integer menuStatus) {
		this.menuStatus = menuStatus;
	}

	@Column(name = "is_menu", precision = 1, scale = 0)
	public Integer getIsMenu() {
		return this.isMenu;
	}

	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}

	@Column(name = "top_menu_flag")
	public Integer getTopMenuFlag() {
		return this.topMenuFlag;
	}

	public void setTopMenuFlag(Integer topMenuFlag) {
		this.topMenuFlag = topMenuFlag;
	}

	@Column(name = "menu_field", length = 32)
	public String getMenuField() {
		return this.menuField;
	}

	public void setMenuField(String menuField) {
		this.menuField = menuField;
	}

	@Column(name = "menu_remark", length = 1024)
	public String getMenuRemark() {
		return this.menuRemark;
	}

	public void setMenuRemark(String menuRemark) {
		this.menuRemark = menuRemark;
	}

}