package com.unknown.wiki.bean;

import com.unknown.wiki.constant.Constant_Column;

import net.sf.json.JSONObject;

public class Project implements Constant_Column{
	private long id;
	private long companyId;
//	private String companyName;
	private long jobId;
//	private String jobName;
	private long createUserId;
//	private long createUserName;
	private long belongUserId;
	private long updateUserId;
//	private String belongUserName;
	private String createDate;
	private String updateDate;
//	private String jobStatus;
	
	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_COMPANYID, companyId);
		jsonObject.put(COLUMN_JOBID, jobId);
		jsonObject.put(COLUMN_CREATEUSERID, createUserId);
		jsonObject.put(COLUMN_BELONGUSERID, belongUserId);
		jsonObject.put(COLUMN_UPDATEUSERID, updateUserId);
		jsonObject.put(COLUMN_CREATEDATE, createDate);
		jsonObject.put(COLUMN_UPDATEDATE, updateDate);
		return jsonObject.toString();
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public long getCompanyId() {
		return companyId;
	}



	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}



	public long getJobId() {
		return jobId;
	}



	public void setJobId(long jobId) {
		this.jobId = jobId;
	}



	public long getCreateUserId() {
		return createUserId;
	}



	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}



	public long getBelongUserId() {
		return belongUserId;
	}



	public void setBelongUserId(long belongUserId) {
		this.belongUserId = belongUserId;
	}



	public String getCreateDate() {
		return createDate;
	}



	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}



	public String getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}



	public long getUpdateUserId() {
		return updateUserId;
	}



	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}
}
