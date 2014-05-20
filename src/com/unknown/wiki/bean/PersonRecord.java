package com.unknown.wiki.bean;

import net.sf.json.JSONObject;

import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.w_enum.RecordType;

public class PersonRecord implements Constant_Column{
	private long id;
	private long personId;
	private int userId;
	private String date;
	private String content;
	private RecordType type;
	
	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_PERSONID, personId);
		jsonObject.put(COLUMN_USERID, userId);
		jsonObject.put(COLUMN_DATE, date);
		jsonObject.put(COLUMN_CONTENT, content);
		jsonObject.put(COLUMN_TYPE, type.ordinal());
		
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public RecordType getType() {
		return type;
	}

	public void setType(RecordType type) {
		this.type = type;
	}
}
