package com.unknown.wiki.bean;

import com.unknown.wiki.constant.Constant_Column;

import net.sf.json.JSONObject;

public class Job implements Constant_Column{
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_NAME, name);
		return jsonObject.toString();
	}
}
