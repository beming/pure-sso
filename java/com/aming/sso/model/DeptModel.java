package com.aming.sso.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeptModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1738295262312554125L;
	private Integer dep_id;
	private String dep_code;
	private String dep_level_code;
	private Integer dep_level;
	private Integer parent_dep_id;
	private String dep_name;
	private String dep_image;
	private BigDecimal dep_status;
	private String dep_addr;
	private String dep_linker;
	private String dep_phone;
	private String dep_mobile;
	private String dep_email;
	private String dep_teacher;
	private String dep_student;
	private String dep_remark;
	
	public String getDep_level_code() {
		return dep_level_code;
	}
	public void setDep_level_code(String dep_level_code) {
		this.dep_level_code = dep_level_code;
	}
	public Integer getDep_level() {
		return dep_level;
	}
	public void setDep_level(Integer dep_level) {
		this.dep_level = dep_level;
	}
	public Integer getParent_dep_id() {
		return parent_dep_id;
	}
	public void setParent_dep_id(Integer parent_dep_id) {
		this.parent_dep_id = parent_dep_id;
	}
	public Integer getDep_id() {
		return dep_id;
	}
	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}
	public String getDep_code() {
		return dep_code;
	}
	public void setDep_code(String dep_code) {
		this.dep_code = dep_code;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getDep_image() {
		return dep_image;
	}
	public void setDep_image(String dep_image) {
		this.dep_image = dep_image;
	}
	public BigDecimal getDep_status() {
		return dep_status;
	}
	public void setDep_status(BigDecimal dep_status) {
		this.dep_status = dep_status;
	}
	public String getDep_addr() {
		return dep_addr;
	}
	public void setDep_addr(String dep_addr) {
		this.dep_addr = dep_addr;
	}
	public String getDep_linker() {
		return dep_linker;
	}
	public void setDep_linker(String dep_linker) {
		this.dep_linker = dep_linker;
	}
	public String getDep_phone() {
		return dep_phone;
	}
	public void setDep_phone(String dep_phone) {
		this.dep_phone = dep_phone;
	}
	public String getDep_mobile() {
		return dep_mobile;
	}
	public void setDep_mobile(String dep_mobile) {
		this.dep_mobile = dep_mobile;
	}
	public String getDep_email() {
		return dep_email;
	}
	public void setDep_email(String dep_email) {
		this.dep_email = dep_email;
	}
	public String getDep_teacher() {
		return dep_teacher;
	}
	public void setDep_teacher(String dep_teacher) {
		this.dep_teacher = dep_teacher;
	}
	public String getDep_student() {
		return dep_student;
	}
	public void setDep_student(String dep_student) {
		this.dep_student = dep_student;
	}
	public String getDep_remark() {
		return dep_remark;
	}
	public void setDep_remark(String dep_remark) {
		this.dep_remark = dep_remark;
	}
}
