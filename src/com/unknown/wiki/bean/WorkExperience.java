package com.unknown.wiki.bean;

import net.sf.json.JSONObject;

import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Table;

public class WorkExperience implements Constant_Column,Constant_Table{
	private long id;
	private long personId;
	private String companyName;
	private String jobTitle;
	private String jobLevel;
	private String salary;
	private String jobInfo;
	private String startDate;
	private String endDate;
	private String leaveReason;
	
	
	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_PERSONID, personId);
		jsonObject.put(COLUMN_COMPANYNAME, companyName);
		jsonObject.put(COLUMN_JOBTITLE, jobTitle);
		jsonObject.put(COLUMN_JOBLEVEL, jobLevel);
		jsonObject.put(COLUMN_SALARY, salary);
		jsonObject.put(COLUMN_JOBINFO, jobInfo);
		jsonObject.put(COLUMN_STARTDATE, startDate);
		jsonObject.put(COLUMN_ENDDATE, endDate);
		jsonObject.put(COLUMN_LEAVEREASON, leaveReason);
		
		return jsonObject.toString();
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobLevel() {
		return jobLevel;
	}
	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getJobInfo() {
		return jobInfo;
	}
	public void setJobInfo(String jobInfo) {
		this.jobInfo = jobInfo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
}
