package com.unknown.wiki.bean;

import java.util.ArrayList;

import com.unknown.wiki.constant.Constant_Column;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HR implements Constant_Column{
	private long id;
	private long companyId;
	private String name;
	private String EnglishName;
	private String gender;
	private String occupation;
	private int isWorking;
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


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getOccupation() {
		return occupation;
	}


	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public int getIsWorking() {
		return isWorking;
	}


	public void setIsWorking(int isWorking) {
		this.isWorking = isWorking;
	}


	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_COMPANYID, companyId);
		jsonObject.put(COLUMN_NAME, name);
		jsonObject.put(COLUMN_ENGLISHNAME, EnglishName);
		jsonObject.put(COLUMN_GENDER, gender);
		jsonObject.put(COLUMN_OCCUPATION, occupation);
		jsonObject.put(COLUMN_ISWORKING, isWorking);
		
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


	public long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}


	public ArrayList<Contact> getContactList() {
		return contactList;
	}


	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}
	
	
}
