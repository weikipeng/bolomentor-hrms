package com.unknown.wiki.bean;

import net.sf.json.JSONObject;

import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.w_enum.ContactInfoType;
import com.unknown.wiki.w_enum.ContactType;

public class Contact implements Constant_Column{
	private long id;
	private ContactType type;
	private long typeId;
	private ContactInfoType infoType;
	private String info;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ContactType getType() {
		return type;
	}
	public void setType(ContactType type) {
		this.type = type;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public ContactInfoType getInfoType() {
		return infoType;
	}
	public void setInfoType(ContactInfoType infoType) {
		this.infoType = infoType;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_TYPE, type.ordinal());
		jsonObject.put(COLUMN_TYPEID, typeId);
		jsonObject.put(COLUMN_INFOTYPE, infoType.ordinal());
		jsonObject.put(COLUMN_INFO, info);
		return jsonObject.toString();
	}
}
