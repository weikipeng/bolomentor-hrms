package com.unknown.wiki.constant;

public interface  Constant_Servlet {
	public final String ACTION = "action";
	public final String DATA = "data";
	public final String ACTION_DATA = "action_data";
	
	public final String ACTION_ADD = "add";
	public final String ACTION_DELETE = "delete";
	public final String ACTION_UPDATE = "update";
	public final String ACTION_QUERY = "query";
	public final String ACTION_SINGLE = "single";

	public final String KEY_RESULT= "result";
	public final String KEY_MESSAGE= "message";
	public final String KEY_STATUS= "status";

	public final String RESULT_SUCCESS= "success";
	public final String RESULT_FAILED= "failed";
	public final String ROLE_ADMIN= "127";
	public final int ROLE_ADMIN_VALUE= Integer.parseInt(ROLE_ADMIN);
	
	public final int RESULT_CODE_FAILED = 404;
	public final int RESULT_CODE_SUCCESS = 200;
}
