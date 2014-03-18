package com.unknown.wiki.bean;

import net.sf.json.JSONObject;

public class W_User {
	private int id;
	private String name;
	private int role;
	
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
	public int getRole() {
		return role;
	}
	public void setRole(int type) {
		this.role = type;
	}
	
	public JSONObject toJsonObject(){
		JSONObject object = new JSONObject();
		
		object.put("id", id);
		object.put("name", name);
		object.put("role", role);
		
		return object;
	}
}
