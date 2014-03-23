package com.unknown.wiki.w_enum;

public enum ContactType {
	COMPANY("公司"),
	HR("HR"),
	PERSON("个人"),
	USER("用户");

	
	private ContactType(String desc) {
		text = desc;
	}
	
	private String text;

	public String text() {
		return text;
	}
	
//	public static ContactType getContactType(int id){
//		switch (id) {
//		case 0:
//			return ContactType.COMPANY;
//		case 1:
//			return ContactType.HR;
//		case 2:
//			return ContactType.PERSON;
//		default:
//			return ContactType.PERSON;
//		}
//	}
	
	public static void main(String[] args) {
		System.out.println("ContactType.COMPANY ---- " +ContactType.COMPANY);
		System.out.println("ContactType.COMPANY ---- " +ContactType.values()[0]);
		System.out.println("ContactType.COMPANY ---- " +ContactType.valueOf("PERSON"));
	}
}
