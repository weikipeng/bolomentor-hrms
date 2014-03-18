package com.unknown.wiki.bean;

import net.sf.json.JSONObject;

import com.unknown.wiki.constant.Constant_Column;

public class WorkHistory implements Constant_Column{
	private long id;
	private long personId;
	private String companyName;
	private String job;
	private String salary;
	private String startDate;
	private String endDate;
	private String leaveReason;

	
	
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



	public String getJob() {
		return job;
	}



	public void setJob(String job) {
		this.job = job;
	}



	public String getSalary() {
		return salary;
	}



	public void setSalary(String salary) {
		this.salary = salary;
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



	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_PERSONID, personId);
		jsonObject.put(COLUMN_COMPANYNAME, companyName);
		jsonObject.put(COLUMN_JOB, job);
		jsonObject.put(COLUMN_SALARY, salary);
		jsonObject.put(COLUMN_STARTDATE, startDate);
		jsonObject.put(COLUMN_ENDDATE, endDate);
		jsonObject.put(COLUMN_LEAVEREASON, leaveReason);
		
		return jsonObject.toString();
	}
}
