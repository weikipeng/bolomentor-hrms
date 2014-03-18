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
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.w_enum.ContactInfoType;
import com.unknown.wiki.w_enum.ContactType;

public class ContactDao implements Constant_Column,Constant_Servlet{

	
	/**插入Contact*/
	public static Contact insertContact(DataBaseDao dataBaseDao,Map<String, String> parameters){
		Contact Contact = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("insert into ");
			sb.append(Constant_Table.TABLE_CONTACT);
			sb.append(" set ");
			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				sb.append(entry.getKey());
				sb.append("= '");
				sb.append(entry.getValue());
				sb.append("',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(";");
			
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
						sb.append("select * from ");
						sb.append(Constant_Table.TABLE_CONTACT);
						sb.append(" where ");
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
	
	/**删除公司*/
	public static boolean deleteContact(DataBaseDao dataBaseDao,Map<String, String> parameters){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Constant_Table.TABLE_CONTACT);
			sb.append(" where ");
			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
			int count = 0;
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();

				if(count != 0){
					sb.append(" and ");
				}
				sb.append(entry.getKey());
				sb.append("= '");
				sb.append(entry.getValue());
				sb.append("'");
				count ++;
			}
			sb.append(";");
			
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
	
	/**查询公司*/
	public static ArrayList<Contact> queryContact(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<Contact> result = new ArrayList<Contact>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ");
			sb.append(Constant_Table.TABLE_CONTACT);
			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
			int count = 0;
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				if(count != 0){
					sb.append(" and ");
				}else{
					sb.append(" where ");
				}
				sb.append(entry.getKey());
				sb.append("= '");
				sb.append(entry.getValue());
				sb.append("'");
				count++;
			}
//			if(count>0){
//				sb.deleteCharAt(sb.length()-1);
//			}
			sb.append(";");
			
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
	
	/**更新公司*/
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
				sb.append("= '");
				sb.append(entry.getValue());
				sb.append("',");
			}
			sb.deleteCharAt(sb.length()-1);
			
			iterator = parameters.entrySet().iterator();
			int count = 0;
			while(iterator.hasNext()){
				Entry<String, String> entry = iterator.next();
				if(count > 0){
					sb.append(" and ");
				}else{
					sb.append(" where ");
				}

				sb.append(entry.getKey());
				sb.append("= '");
				sb.append(entry.getValue());
				sb.append("'");
				count ++;
			}
			
			sb.append(";");
			
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
	}

	public static boolean insertOrUpdateContact(DataBaseDao dataBaseDao,JSONObject companyObject) {
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
}
