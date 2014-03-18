package com.unknown.wiki.bean;

import com.unknown.wiki.constant.Constant_Column;

import net.sf.json.JSONObject;

/**公司工作职位*/
public class CompanyJob implements Constant_Column{
	private long companyId;
	private long jobId;
	private String salary;
	private int number;
	private String status;
	
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



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getSalary() {
		return salary;
	}



	public void setSalary(String salary) {
		this.salary = salary;
	}



	public int getNumber() {
		return number;
	}



	public void setNumber(int number) {
		this.number = number;
	}



	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_COMPANYID, companyId);
		jsonObject.put(COLUMN_JOBID, jobId);
		jsonObject.put(COLUMN_SALARY, salary);
		jsonObject.put(COLUMN_NUMBER, number);
		jsonObject.put(COLUMN_STATUS,status);
		return jsonObject.toString();
	}
}
