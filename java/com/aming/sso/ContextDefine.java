package com.aming.sso;

import java.util.ArrayList;


public final class ContextDefine {
	public static String LOGIN_USER = "loginuser";
	public static String IDVeriPos = "idveripos";
	public static String LOGIN_ADMIN = "loginadmin";
	public static String PAGE_MESSAGE = "msg";
	//public static String COMMON_MODEL = "common_model";
	public static String GLOBAL_MODEL = "global_model";
	public static String CTRL_DATA_TAG = "ctrlData_tag";
	public static String OPENGETPWD = "openGetPwd";
	public static String GLOBAL_LOGIN_NUM = "global_login_num";
	public static String IDENTIFY_CODE = "identifyCode";
	public static String USEIDENTIFYCODE = "useIdentifyCode";
	public static String MEMCACHED_HOST = "memcached1.host";
	public static String MEMCACHED_PORT = "memcached1.port";
	public static String MEMCACHED_CACHETIME = "cacheTime";
	public static String TAG_MEMCACHED = "tagMemcached";
	public static String ENABLE_MEMCACHED = "enableMemcached";
	public static String HAD_INIT_MEMCACHED_SERVER = "hadInit";
	public static String R_SELECT = "list";
	public static String R_EDIT = "edit";
	public static String R_UPDATE = "update";
	public static String R_SAVE = "save";
	public static String R_DEL = "delete";
	public static String R_ADD = "add";
	
	public static String LOGIN_USER_RIGHT = "login_user_right";
	public static ArrayList<String> RIGHT_SELECT = new ArrayList<String>(){
		private static final long serialVersionUID = -5061234977867599950L;
	{add("list");add("query");add("find");add("select");add("toQuery");add("treeList");}};
	public static ArrayList<String> RIGHT_ADD = new ArrayList<String>(){
		private static final long serialVersionUID = 6712126398661381317L;
	{add("add");add("save");}};
	public static ArrayList<String> RIGHT_UPDATE = new ArrayList<String>(){
		private static final long serialVersionUID = 9006325380827416547L;
	{add("edit");add("update");add("chgPwd");add("editRoleMenu");add("userRole");add("grantUser");add("grantSys");}};
	public static ArrayList<String> RIGHT_DEL = new ArrayList<String>(){
		private static final long serialVersionUID = 8385100115720921292L;
	{add("del");add("delete");}};
}
