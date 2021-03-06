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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.unknown.wiki.bean.Contact;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.w_enum.ContactInfoType;
import com.unknown.wiki.w_enum.ContactType;
import com.unknown.wiki.w_enum.Visible;

public class ContactDao implements Constant_Column,Constant_Servlet,Constant_SQL,Constant_Table{

	
	/**插入联系方式*/
	public static Contact insertContact(DataBaseDao dataBaseDao,Map<String, String> parameters){
		Contact Contact = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_CONTACT);
			sb.append(" set ");
			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				sb.append(entry.getKey());
				sb.append(SQL_EQ);sb.append(SQL_SINGLE_QUOTES);
				sb.append(entry.getValue());
				sb.append(SQL_SINGLE_QUOTES);sb.append(SQL_COMMA);
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
				int insertResult = preparedStatement.executeUpdate();
				System.out.println("insertResult	" +	insertResult);
				if(insertResult>0){
					ResultSet resultSet = preparedStatement.getGeneratedKeys();
					if(resultSet.next()){
						System.out.println("id --- " + resultSet.getString(1) );
						
						long id = resultSet.getLong(1);
						sb = new StringBuffer();
						sb.append(SQL_QUERY);
						sb.append(Constant_Table.TABLE_CONTACT);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							Contact = formatContact(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Contact;
	}
	
	/**删除联系方式*/
	public static boolean deleteContact(DataBaseDao dataBaseDao,Map<String, String> parameters){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Constant_Table.TABLE_CONTACT);
			sb.append(SQL_WHERE);
			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
			int count = 0;
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();

				if(count != 0){
					sb.append(SQL_AND);
				}
				sb.append(entry.getKey());
				sb.append(SQL_EQ);sb.append(SQL_SINGLE_QUOTES);
				sb.append(entry.getValue());
				sb.append(SQL_SINGLE_QUOTES);
				count ++;
			}
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
				int deleteResult = preparedStatement.executeUpdate();
				System.out.println("deleteResult --- " + deleteResult);
				if(deleteResult>0){
					result = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**查询联系方式*/
	public static ArrayList<Contact> queryContact(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<Contact> result = new ArrayList<Contact>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_CONTACT);
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
//			if(count>0){
//				sb.deleteCharAt(sb.length()-1);
//			}
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					result.add(formatContact(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新联系方式*/
	public static boolean updateContact(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Constant_Table.TABLE_CONTACT);
			sb.append(" set ");
			Iterator<Entry<String, String>> iterator = values.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				sb.append(entry.getKey());
				sb.append(SQL_EQ);sb.append(SQL_SINGLE_QUOTES);
				sb.append(entry.getValue());
				sb.append(SQL_SINGLE_QUOTES);sb.append(SQL_COMMA);
			}
			sb.deleteCharAt(sb.length()-1);
			
			iterator = parameters.entrySet().iterator();
			int count = 0;
			while(iterator.hasNext()){
				Entry<String, String> entry = iterator.next();
				if(count > 0){
					sb.append(SQL_AND);
				}else{
					sb.append(SQL_WHERE);
				}

				sb.append(entry.getKey());
				sb.append(SQL_EQ);sb.append(SQL_SINGLE_QUOTES);
				sb.append(entry.getValue());
				sb.append(SQL_SINGLE_QUOTES);
				count ++;
			}
			
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
				int updateResult = preparedStatement.executeUpdate();
				System.out.println("updateResult	" +	updateResult);
				if(updateResult>0){
					isSuccess = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isSuccess;
	}
	
	private static Contact formatContact(ResultSet resultSet) throws SQLException {
		Contact Contact = new Contact();
		Contact.setId(resultSet.getLong(COLUMN_ID));
		Contact.setType(ContactType.values()[resultSet.getInt(COLUMN_TYPE)]);
		Contact.setTypeId(resultSet.getLong(COLUMN_TYPEID));
		Contact.setInfoType(ContactInfoType.values()[resultSet.getInt(COLUMN_INFOTYPE)]);
		Contact.setInfo(resultSet.getString(COLUMN_INFO));
		return Contact;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		HashMap<String, String> parameters = new HashMap<String,String>();

//		插入
/*		parameters.put(COLUMN_TYPE, String.valueOf(ContactType.HR.ordinal()));
		parameters.put(COLUMN_TYPEID, "1");
		parameters.put(COLUMN_INFOTYPE, String.valueOf(ContactInfoType.COMPANY_EMAIL.ordinal()));
		parameters.put(COLUMN_INFO, "gongsiyoux@email.com");
		Contact Contact = ContactDao.insertContact(dataBaseDao, parameters);
		if(Contact!=null){
			System.out.println(Contact.toJsonString());
		}*/
		
		
		//删除
		/*parameters.put("id", "6");
		boolean result = ContactDao.deleteContact(dataBaseDao, parameters);*/
		
		
		//查询
/*//		parameters.put(COLUMN_ID, "1");
		ArrayList<Contact> arrayList = ContactDao.queryContact(dataBaseDao, parameters);
		int size = arrayList.size();
		for(int i = 0;i<size;i++){
			System.out.println(arrayList.get(i).toJsonString());
		}*/
		
		
	/*	//修改
		parameters.put(Constant_Column.COLUMN_ID, "5");
		HashMap<String, String> values = new HashMap<String, String>();
		values.put(Constant_Column.COLUMN_INFO, "修改后的info");
		boolean result = ContactDao.updateContact(dataBaseDao, parameters, values);*/
		
		JSONObject contactObject = new JSONObject();
		contactObject.put(COLUMN_ID, "1");
		
		W_User user = new W_User();
		user.setRole(127);
		
		
//		deleteContactJSON(user,dataBaseDao,contactObject);
		deleteHRContact(user,dataBaseDao,1);
	}

	public static boolean insertOrUpdateContactOld(DataBaseDao dataBaseDao,JSONObject companyObject) {
		boolean isSuccess = false;
		if(dataBaseDao != null){
			String companyId = companyObject.optString(COLUMN_ID);
			if(companyId == null || "".equals(companyId)){
				return false;
			}
			String info = companyObject.optString(COLUMN_TELEPHONE);
			
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put(COLUMN_TYPE, String.valueOf(ContactType.COMPANY.ordinal()));
			parameters.put(COLUMN_TYPEID,companyId);
			parameters.put(COLUMN_INFOTYPE,String.valueOf(ContactInfoType.COMPANY_TEL.ordinal()));
			
			ArrayList<Contact> contactList = queryContact(dataBaseDao, parameters);
			Contact contact = null;
			if(contactList.size()>0){
				contact = contactList.get(0);
			}
			
			HashMap<String, String> values = new HashMap<String, String>();
			values.putAll(parameters);
			values.put(COLUMN_INFO, info);
			
			if(contact!=null){
				if(contact.getInfo().equals(info)){
					return true;
				}else{
					updateContact(dataBaseDao, parameters, values);
				}
			}else{
				contact = insertContact(dataBaseDao, values);
				return contact == null;
			}
		}
		return isSuccess;
	}
	
	public static boolean insertOrUpdateContact(DataBaseDao dataBaseDao,JSONObject contactObject) {
		boolean isSuccess = false;
		if(dataBaseDao != null){
			long id = -1;
			
			HashMap<String, String> queryMap = new HashMap<String, String>();
			HashMap<String, String> values = new HashMap<String, String>();
			
			if(contactObject.containsKey(COLUMN_ID)){
				id = contactObject.getLong(COLUMN_ID);
				contactObject.remove(COLUMN_ID);
			}

			queryMap.put(COLUMN_ID, String.valueOf(id));
			
			Contact contact = null;
			
			Iterator<String> iterator = contactObject.keys();
			while(iterator.hasNext()){
				String key = iterator.next();
				values.put(key, contactObject.optString(key));
			}

			if(id <0){
				contact = insertContact(dataBaseDao, values);
			}else{
				ArrayList<Contact> contactList = queryContact(dataBaseDao, queryMap);
				if(contactList.size() <=0){
					contact = insertContact(dataBaseDao, values);
				}else{
					contact = contactList.get(0);
					isSuccess = updateContact(dataBaseDao, queryMap, values);
				}
			}
			
			isSuccess = contact!=null;
		}
		return isSuccess;
	}

	public static boolean deleteContactJSON(W_User user,DataBaseDao dataBaseDao, JSONObject contactObject) {
		boolean result = false;
		
		if(dataBaseDao != null&&user!=null &&user.getRole()==127){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_UPDATE);
			sb.append(TABLE_CONTACT);
			sb.append(SQL_SET);
			
			sb.append(COLUMN_VISIBLE);
			sb.append(SQL_EQ);
			sb.append(Visible.INVISIBLE.ordinal());
			
			sb.append(SQL_WHERE);
			Iterator<String> iterator = contactObject.keys();
			int count = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = contactObject.getString(key);
				
				if(count != 0){
					sb.append(SQL_AND);
				}
				count ++;
				
				if(value.trim().startsWith(SQL_LEFT_BRACKET)){
					JSONArray valueArray = JSONArray.fromObject(value);
					if(valueArray!=null){
						int size = valueArray.size();
						if(size > 0){
							sb.append(key);
							sb.append(SQL_IN);
							sb.append(SQL_OPEN_PARENTHESIS);
							
							for(int j = 0;j<size;j++){
								if(j!=0){
									sb.append(SQL_COMMA);
								}
								sb.append(SQL_SINGLE_QUOTES);
								sb.append(valueArray.getInt(j));
								sb.append(SQL_SINGLE_QUOTES);
							}
							
							sb.append(SQL_CLOSE_PARENTHESIS);
						}
					}
				}else{
					sb.append(key);
					sb.append(SQL_EQ);
					sb.append(SQL_SINGLE_QUOTES);
					sb.append(contactObject.get(key));
					sb.append(SQL_SINGLE_QUOTES);
				}
				
			}
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
				int deleteResult = preparedStatement.executeUpdate();
				System.out.println("deleteResult --- " + deleteResult);
				if(deleteResult>0){
					result = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean deleteHRContact(W_User user,DataBaseDao dataBaseDao, long id) {
		boolean result = false;
		
		if(dataBaseDao != null&&user!=null &&user.getRole()==127){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_UPDATE);
			sb.append(TABLE_CONTACT);
			sb.append(SQL_SET);
			
			sb.append(COLUMN_VISIBLE);
			sb.append(SQL_EQ);
			sb.append(Visible.INVISIBLE.ordinal());
			
			sb.append(SQL_WHERE);
			sb.append(COLUMN_TYPE);
			sb.append(SQL_EQ);
			sb.append(String.valueOf(ContactType.HR.ordinal()));
			sb.append(SQL_AND);
			sb.append(COLUMN_TYPEID);
			sb.append(SQL_EQ);
			sb.append(String.valueOf(id));
				
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
				int deleteResult = preparedStatement.executeUpdate();
				System.out.println("deleteResult --- " + deleteResult);
				if(deleteResult>0){
					result = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
