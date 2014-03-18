package com.unknown.wiki.bean;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.w_enum.Gender;

public class Person implements Constant_Column{
	private long id;
	private String name;
	private String EnglishName;
	private String birthday;
	private Gender gender;
	private int age;
	private String education;
	private String maritalStatus;
	private int workYear;
	private String liveAddress;
	private String belongAddress;
	private String otherLanguage;
	private String salary;
	private String companyNature;
	private String workStatus;
	private String hopeAddress;
	private String hopeJob;
	private String hopeVocation;
	private String hopeSalary;
	private boolean isHope;
	private long createUserId;
	private String createDate;
	private String createUser;
	private long updateUserId;
	private String updateUser;
	private String updateDate;
	private ArrayList<Contact> contactList;
	
	
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEnglishName() {
		return EnglishName;
	}



	public void setEnglishName(String englishName) {
		EnglishName = englishName;
	}



	public String getBirthday() {
		return birthday;
	}



	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}



	public Gender getGender() {
		return gender;
	}



	public void setGender(Gender gender) {
		this.gender = gender;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public String getEducation() {
		return education;
	}



	public void setEducation(String education) {
		this.education = education;
	}



	public String getMaritalStatus() {
		return maritalStatus;
	}



	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}



	public int getWorkYear() {
		return workYear;
	}



	public void setWorkYear(int workYear) {
		this.workYear = workYear;
	}



	public String getLiveAddress() {
		return liveAddress;
	}



	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}



	public String getBelongAddress() {
		return belongAddress;
	}



	public void setBelongAddress(String belongAddress) {
		this.belongAddress = belongAddress;
	}



	public String getOtherLanguage() {
		return otherLanguage;
	}



	public void setOtherLanguage(String otherLanguage) {
		this.otherLanguage = otherLanguage;
	}



	public String getSalary() {
		return salary;
	}



	public void setSalary(String salary) {
		this.salary = salary;
	}



	public String getCompanyNature() {
		return companyNature;
	}



	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}



	public String getHopeAddress() {
		return hopeAddress;
	}



	public void setHopeAddress(String hopeAddress) {
		this.hopeAddress = hopeAddress;
	}



	public String getHopeJob() {
		return hopeJob;
	}



	public void setHopeJob(String hopeJob) {
		this.hopeJob = hopeJob;
	}



	public String getHopeVocation() {
		return hopeVocation;
	}



	public void setHopeVocation(String hopeVocation) {
		this.hopeVocation = hopeVocation;
	}



	public String getHopeSalary() {
		return hopeSalary;
	}



	public void setHopeSalary(String hopeSalary) {
		this.hopeSalary = hopeSalary;
	}



	public boolean isHope() {
		return isHope;
	}



	public void setHope(boolean isHope) {
		this.isHope = isHope;
	}



	public long getCreateUserId() {
		return createUserId;
	}



	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}



	public String getCreateDate() {
		return createDate;
	}



	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}



	public String getCreateUser() {
		return createUser;
	}



	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}



	public long getUpdateUserId() {
		return updateUserId;
	}



	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}



	public String getUpdateUser() {
		return updateUser;
	}



	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}



	public String getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}



	public ArrayList<Contact> getContactList() {
		return contactList;
	}



	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_NAME, name);
		jsonObject.put(COLUMN_ENGLISHNAME, EnglishName);
		jsonObject.put(COLUMN_BIRTHDAY, birthday);
		jsonObject.put(COLUMN_GENDER, gender.ordinal());
		jsonObject.put(COLUMN_AGE, age);
		jsonObject.put(COLUMN_EDUCATION, education);
		jsonObject.put(COLUMN_MARITALSTATUS, maritalStatus);
		jsonObject.put(COLUMN_WORKYEAR, workYear);
		jsonObject.put(COLUMN_LIVEADDRESS, liveAddress);
		jsonObject.put(COLUMN_BELONGADDRESS, belongAddress);
		jsonObject.put(COLUMN_OTHERLANGUAGE, otherLanguage);
		jsonObject.put(COLUMN_SALARY, salary);
		jsonObject.put(COLUMN_COMPANYNATURE, companyNature);
		jsonObject.put(COLUMN_WORKSTATUS, workStatus);
		jsonObject.put(COLUMN_HOPEADDRESS, hopeAddress);
		jsonObject.put(COLUMN_HOPEJOB, hopeJob);
		jsonObject.put(COLUMN_HOPEVOCATION, hopeVocation);
		jsonObject.put(COLUMN_HOPESALARY, hopeSalary);
		jsonObject.put(COLUMN_ISHOPE, isHope);
		jsonObject.put(COLUMN_CREATEUSERID, createUserId);
		jsonObject.put(COLUMN_CREATEUSER, createUser);
		jsonObject.put(COLUMN_CREATEDATE, createDate);
		jsonObject.put(COLUMN_UPDATEUSERID, updateUserId);
		jsonObject.put(COLUMN_UPDATEUSER, updateUser);
		jsonObject.put(COLUMN_UPDATEDATE, updateDate);
		
		
		if(contactList!=null){
			int size = contactList.size();
			if(size > 0){
				JSONArray jsonArray = new JSONArray();
				for(int i=0;i<size;i++){
					jsonArray.add(contactList.get(i).toJsonString());
				}
				
				jsonObject.put(COLUMN_CONTACT, jsonArray);
			}
		}
		return jsonObject.toString();
	}
}
