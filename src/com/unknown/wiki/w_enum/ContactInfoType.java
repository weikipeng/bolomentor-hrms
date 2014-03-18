package com.unknown.wiki.w_enum;

public enum ContactInfoType {
	MOBILE("手机"),
	QQ("QQ"),
	COMPANY_TEL("公司电话"),
	HOME_TEL("家庭电话"),
	COMPANY_EMAIL("公司邮箱"),
	PRIVATE_EMAIL("私人邮箱"),
	OTHER("其他联系方式");
	
//	手机
//	家庭电话
//	公司电话
//	私人邮箱
//	公司邮箱
//	其他联系方式
	
	private ContactInfoType(String desc) {
		text = desc;
	}
	
	private String text;

	public String text() {
		return text;
	}
	
//	public static ContactInfoType getType(int id){
//		switch (id) {
//		case 0:
//			return MOBILE;
//		case 1:
//			return QQ;
//		case 2:
//			return COMPANY_TEL;
//		case 3:
//			return HOME_TEL;
//		case 4:
//			return COMPANY_EMAIL;
//		case 5:
//			return PRIVATE_EMAIL;
//		case 6:
//			return OTHER;
//		default:
//			return MOBILE;
//		}
//	}
	
	public static void main(String[] args) {
		System.out.println(ContactInfoType.MOBILE.ordinal());
		System.out.println(ContactInfoType.COMPANY_EMAIL.text());
		System.out.println(ContactInfoType.valueOf(ContactInfoType.class, "QQ"));
	}
}
