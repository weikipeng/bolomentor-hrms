package com.unknown.wiki.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static final String PATTERN_TIMESTAMP = "yyyy年MM月dd日 HH:mm:ss";
	public static String getTimeStamp(){
		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_TIMESTAMP);
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(getTimeStamp());
	}
}
