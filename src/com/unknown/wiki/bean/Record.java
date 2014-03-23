package com.unknown.wiki.bean;

import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.w_enum.RecordType;

import net.sf.json.JSONObject;

/**记录*/
public class Record implements Constant_Column{
	private long id;
	private long companyId;
	private RecordType type;
	private long hrId;
	private String date;
	private int createUserId;
	private String content;
	
	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(COLUMN_ID, id);
		jsonObject.put(COLUMN_COMPANYID, companyId);
		jsonObject.put(COLUMN_TYPE, type.ordinal());
		jsonObject.put(COLUMN_HRID, hrId);
		jsonObject.put(COLUMN_DATE, date);
		jsonObject.put(COLUMN_CREATEUSERID, createUserId);
		jsonObject.put(COLUMN_CONTENT, content);
		
		return jsonObject.toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getHrId() {
		return hrId;
	}

	public void setHrId(long hrId) {
		this.hrId = hrId;
	}

	public RecordType getType() {
		return type;
	}

	public void setType(RecordType type) {
		this.type = type;
	}
}
