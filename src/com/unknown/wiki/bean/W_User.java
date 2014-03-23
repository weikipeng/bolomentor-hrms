package com.unknown.wiki.bean;

import com.unknown.wiki.constant.Constant_Column;

import net.sf.json.JSONObject;

public class W_User implements Constant_Column{
	private int id;
	private String name;
	private int role;
	private String nickName;
	private String avatar;
	
	
	
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



	public void setRole(int role) {
		this.role = role;
	}



	public String getNickName() {
		return nickName;
	}



	public void setNickName(String nickName) {
		this.nickName = nickName;
	}



	public String getAvatar() {
		return avatar;
	}



	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	public JSONObject toJsonObject(){
		JSONObject object = new JSONObject();
		
		object.put(COLUMN_ID, id);
		object.put(COLUMN_NAME, name);
		object.put(COLUMN_ROLE, role);
		object.put(COLUMN_NICKNAME, nickName);
		object.put(COLUMN_AVATAR, avatar);
		
		
		return object;
	}
}
