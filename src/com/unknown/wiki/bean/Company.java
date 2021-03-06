package com.unknown.wiki.bean;

import java.util.ArrayList;

import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Table;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Company implements Constant_Column,Constant_Table{
	private long id;
	private int createUserId;
	private String createDate;
	private int updateUserId;
	private String updateDate;
	private String name;
	private String EnglishName;
	private String province;
	private String city;
	private String address;
	private String intent;
	private int headHunter;
	private String nature;
	/**50以下；50-150；150-500；500-1000；1000-5000；5000-10000；10000以上*/
	private String number;
	private int isListing;
	private String introduction;
	private ArrayList<HR> hrList;
	private ArrayList<Contact> contactList;
	private ArrayList<Record> recordList;
	private ArrayList<Record> recordPlanList;

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



	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getIntent() {
		return intent;
	}



	public void setIntent(String intent) {
		this.intent = intent;
	}



	public int getHeadHunter() {
		return headHunter;
	}



	public void setHeadHunter(int headHunter) {
		this.headHunter = headHunter;
	}



	public String getNature() {
		return nature;
	}



	public void setNature(String nature) {
		this.nature = nature;
	}



	public String getNumber() {
		return number;
	}



	public void setNumber(String number) {
		this.number = number;
	}



	public int getIsListing() {
		return isListing;
	}



	public void setIsListing(int isListing) {
		this.isListing = isListing;
	}



	public String getIntroduction() {
		return introduction;
	}



	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}



	public ArrayList<HR> getHrList() {
		return hrList;
	}



	public void setHrList(ArrayList<HR> hrList) {
		this.hrList = hrList;
	}



	public ArrayList<Contact> getContactList() {
		return contactList;
	}



	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}



	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_CREATEUSERID, createUserId);
		jsonObject.put(COLUMN_CREATEDATE, createDate);
		jsonObject.put(COLUMN_UPDATEUSERID, updateUserId);
		jsonObject.put(COLUMN_UPDATEDATE, updateDate);
		jsonObject.put(COLUMN_NAME, name);
		jsonObject.put(COLUMN_ENGLISHNAME, EnglishName);
		jsonObject.put(COLUMN_PROVINCE, province);
		jsonObject.put(COLUMN_CITY, city);
		jsonObject.put(COLUMN_ADDRESS, address);
		jsonObject.put(COLUMN_INTENT, intent);
		jsonObject.put(COLUMN_HEADHUNTER, headHunter);
		jsonObject.put(COLUMN_NATURE, nature);
		jsonObject.put(COLUMN_NUMBER, number);
		jsonObject.put(COLUMN_ISLISTING, isListing);
		jsonObject.put(COLUMN_INTRODUCTION, introduction);
		
		if(hrList!=null){
			int size = hrList.size();
			if(size > 0){
				JSONArray hrArray = new JSONArray();
				for(int i=0;i<size;i++){
					hrArray.add(hrList.get(i).toJsonString());
				}
				
				jsonObject.put(COLUMN_HR, hrArray);
			}
		}
		
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
		
		if(recordList!=null){
			int size = recordList.size();
			if(size > 0){
				JSONArray jsonArray = new JSONArray();
				for(int i=0;i<size;i++){
					jsonArray.add(recordList.get(i).toJsonString());
				}
				
				jsonObject.put(TABLE_RECORD, jsonArray);
			}
		}
		
		if(getRecordPlanList()!=null){
			int size = getRecordPlanList().size();
			if(size > 0){
				JSONArray jsonArray = new JSONArray();
				for(int i=0;i<size;i++){
					jsonArray.add(getRecordPlanList().get(i).toJsonString());
				}
				
				jsonObject.put(TABLE_RECORDPLAN, jsonArray);
			}
		}
		
		return jsonObject.toString();
	}

	public ArrayList<Record> getRecordList() {
		return recordList;
	}

	public void setRecordList(ArrayList<Record> recordList) {
		this.recordList = recordList;
	}

	public ArrayList<Record> getRecordPlanList() {
		return recordPlanList;
	}

	public void setRecordPlanList(ArrayList<Record> recordPlanList) {
		this.recordPlanList = recordPlanList;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
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

	public int getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}
}
