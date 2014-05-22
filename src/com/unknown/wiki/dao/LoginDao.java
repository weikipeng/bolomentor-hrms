package com.unknown.wiki.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import com.unknown.wiki.bean.Contact;
import com.unknown.wiki.bean.HR;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.w_enum.ContactType;

public class LoginDao implements Constant_Column,Constant_Servlet,Constant_SQL,Constant_Table{
	public static final W_User login(DataBaseDao dataBaseDao,String name ,String password){

//		Job job = null;
//		if(dataBaseDao != null){
//			Connection connection = dataBaseDao.getConnection();
//			
//			//插入
//			StringBuffer sb = new StringBuffer();
//			sb.append(SQL_INSERT);
//			sb.append(Constant_Table.TABLE_JOB);
//			sb.append(" set ");
//			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
//			while (iterator.hasNext()) {
//				Entry<String, String> entry = iterator.next();
//				sb.append(entry.getKey());
//				sb.append(SQL_EQ);sb.append(SQL_SINGLE_QUOTES);
//				sb.append(entry.getValue());
//				sb.append(SQL_SINGLE_QUOTES);sb.append(SQL_COMMA);
//			}
//			sb.deleteCharAt(sb.length()-1);
//			sb.append(SQL_SEMICOLON);
//			
//			System.out.println("执行的sql语句为			" +	sb.toString());
//			
//			try {
//				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
//				int insertResult = preparedStatement.executeUpdate();
//				System.out.println("insertResult	" +	insertResult);
//				if(insertResult>0){
//					ResultSet resultSet = preparedStatement.getGeneratedKeys();
//					if(resultSet.next()){
//						System.out.println("id --- " + resultSet.getString(1) );
//						
//						long id = resultSet.getLong(1);
//						sb = new StringBuffer();
//						sb.append(SQL_QUERY);
//						sb.append(Constant_Table.TABLE_JOB);
//						sb.append(SQL_WHERE);
//						sb.append(COLUMN_ID);
//						sb.append(" = ");
//						sb.append(id);
//						preparedStatement = connection.prepareStatement(sb.toString());
//						resultSet = preparedStatement.executeQuery();
//						if(resultSet.next()){
//							job = formatJob(resultSet);
//						}
//					}
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return job;

		W_User user = null;
		if(dataBaseDao!=null){
//			String sql = "select * from user where name = ? and password = ?";
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(TABLE_USER);
			sb.append(SQL_WHERE);
			sb.append(COLUMN_NAME);
			sb.append(SQL_EQ);
			sb.append(SQL_QUESTIONMARK);
			sb.append(SQL_AND);
			sb.append(COLUMN_PASSWORD);
			sb.append(SQL_EQ);
			sb.append(SQL_QUESTIONMARK);
			sb.append(SQL_SEMICOLON);
			
			
			Connection connection = dataBaseDao.getConnection();
			System.out.println("name -- " + name +"   pass -- "+password);
			try {
				PreparedStatement preparedStatement =  (PreparedStatement) connection.prepareStatement(sb.toString());
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, password);
				ResultSet resultSet =  preparedStatement.executeQuery();
				if(resultSet.next()) {
					user = formatUser(resultSet);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return user;
	}
	
	public static final W_User isLogin(DataBaseDao dataBaseDao,Map<String,String[]> requestMap){
		W_User user = null;
//		JSONObject userObject = requestMap.containsKey(Constant_Table.TABLE_USER) ? JSONObject.fromObject(requestMap.get(Constant_Table.TABLE_USER)[0]):null;
		if(dataBaseDao!=null){
			JSONObject userObject = getUserObject(requestMap);
			if(userObject != null){
				int userId = userObject.optInt(Constant_Column.COLUMN_ID); 
//				int role = userObject.optInt(Constant_Column.COLUMN_ROLE); 
				if(userId>0){
					HashMap<String,String> queryMap = new HashMap<String, String>();
					queryMap.put(COLUMN_ID, String.valueOf(userId));
					ArrayList<W_User> userList = queryUser(dataBaseDao,queryMap);
					
					if(userList.size()>0){
						user = userList.get(0);
					}
				}
			}
		}
		
		return user;
	}
	
	private static ArrayList<W_User> queryUser(DataBaseDao dataBaseDao,HashMap<String, String> parameters) {
		ArrayList<W_User> result = new ArrayList<W_User>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_USER);
			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
			int count = 0;
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				if(count != 0){
					sb.append(SQL_AND);
				}else{
					sb.append(SQL_WHERE);
				}
				sb.append(entry.getKey());
				sb.append(SQL_EQ);
				sb.append(SQL_SINGLE_QUOTES);
				sb.append(entry.getValue());
				sb.append(SQL_SINGLE_QUOTES);
				count++;
			}
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
//				HashMap<String, String> contactMap = new HashMap<String, String>();
//				contactMap.put(Constant_Column.COLUMN_TYPE, String.valueOf(ContactType.HR.ordinal()));
				while(resultSet.next()){
					W_User hr = formatUser(resultSet);
					result.add(hr);
//					contactMap.put(Constant_Column.COLUMN_TYPEID, String.valueOf(hr.getId()));
//					ArrayList<Contact> contactList = ContactDao.queryContact(dataBaseDao, contactMap);
//					hr.setContactList(contactList);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static final JSONObject getUserObject(Map<String,String[]> requestMap){
		return requestMap.containsKey(Constant_Table.TABLE_USER) ? JSONObject.fromObject(requestMap.get(Constant_Table.TABLE_USER)[0]):null;
	}
	
	private static W_User formatUser(ResultSet resultSet) throws SQLException {
		W_User user = new W_User();
		user.setId(Integer.parseInt(resultSet.getString(COLUMN_ID)));
		user.setName(resultSet.getString(COLUMN_NAME));
		user.setRole(Integer.parseInt(resultSet.getString(COLUMN_ROLE)));
		user.setNickName(resultSet.getString(COLUMN_NICKNAME));
		user.setAvatar(resultSet.getString(COLUMN_AVATAR));
		return user;
	}

	public static ArrayList<W_User> queryUser(DataBaseDao dataBaseDao,W_User user) {
		ArrayList<W_User> result = new ArrayList<W_User>();
		
//		if(dataBaseDao != null&&user!=null&&user.getId()>0 &&ROLE_ADMIN_VALUE == user.getRole()){
		if(dataBaseDao != null&&user!=null&&user.getId()>0 ){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_USER);
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					W_User hr = formatUser(resultSet);
					result.add(hr);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
