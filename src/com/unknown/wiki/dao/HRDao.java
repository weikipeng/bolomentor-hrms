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
import com.unknown.wiki.bean.HR;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.w_enum.ContactType;
import com.unknown.wiki.w_enum.Visible;

public class HRDao implements Constant_Column,Constant_Servlet,Constant_Table,Constant_SQL{

	
	/**插入HR*/
	public static HR insertHR(DataBaseDao dataBaseDao,Map<String, String> parameters){
		HR hr = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_HR);
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
						sb.append(Constant_Table.TABLE_HR);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							hr = formatHR(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hr;
	}
	
	/**删除公司*/
	public static boolean deleteHR(DataBaseDao dataBaseDao,Map<String, String> parameters){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Constant_Table.TABLE_HR);
			sb.append(SQL_WHERE);
			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
			int count = 0;
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();

				if(count != 0){
					sb.append(SQL_AND);
				}
				sb.append(entry.getKey());
				sb.append("= '");
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
	
	/**查询公司*/
	public static ArrayList<HR> queryHR(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<HR> result = new ArrayList<HR>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_HR);
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
				sb.append("= '");
				sb.append(entry.getValue());
				sb.append(SQL_SINGLE_QUOTES);
				count++;
			}
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				HashMap<String, String> contactMap = new HashMap<String, String>();
				contactMap.put(Constant_Column.COLUMN_TYPE, String.valueOf(ContactType.HR.ordinal()));
				while(resultSet.next()){
					HR hr = formatHR(resultSet);
					result.add(hr);
					contactMap.put(Constant_Column.COLUMN_TYPEID, String.valueOf(hr.getId()));
					contactMap.put(COLUMN_VISIBLE, String.valueOf(Visible.VISIBLE.ordinal()));
					ArrayList<Contact> contactList = ContactDao.queryContact(dataBaseDao, contactMap);
					hr.setContactList(contactList);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新公司*/
	public static boolean updateHR(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Constant_Table.TABLE_HR);
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
					sb.append(SQL_AND);
				}else{
					sb.append(SQL_WHERE);
				}

				sb.append(entry.getKey());
				sb.append("= '");
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
	
	public static boolean InsertOrUpdateHR(DataBaseDao dataBaseDao,JSONObject hrObject) {
		boolean isSuccess = false;
		if(dataBaseDao != null){
			long id = -1;
			
			HashMap<String, String> queryMap = new HashMap<String, String>();
			HashMap<String, String> values = new HashMap<String, String>();
			
			if(hrObject.containsKey(COLUMN_ID)){
				id = hrObject.getLong(COLUMN_ID);
			}
			
			queryMap.put(COLUMN_ID, String.valueOf(id));
			
			HR hr = null;
			
			JSONArray contactArray = null;
			if(hrObject.containsKey(TABLE_CONTACT)){
				contactArray = hrObject.optJSONArray(TABLE_CONTACT);
				hrObject.remove(TABLE_CONTACT);
			}
			
			
			Iterator<String> iterator = hrObject.keys();
			while(iterator.hasNext()){
				String key = iterator.next();
				values.put(key, hrObject.optString(key));
			}
			
			if(id <0){
				hr = insertHR(dataBaseDao, values);
			}else{
				ArrayList<HR> hrList = queryHR(dataBaseDao, queryMap);
				if(hrList.size() <=0){
					hr = insertHR(dataBaseDao, values);
				}else{
					isSuccess = updateHR(dataBaseDao, queryMap, values);
				}
			}
			
			if(contactArray!=null && contactArray.size() > 0){
				int size = contactArray.size();
				for(int i=0;i<size;i++){
					JSONObject contactObject = contactArray.optJSONObject(i);
					contactObject.put(COLUMN_TYPEID, hr.getId());
					ContactDao.insertOrUpdateContact(dataBaseDao, contactObject);
				}
			}
			
			isSuccess = hr !=null;
		}
		return isSuccess;
	}

	private static HR formatHR(ResultSet resultSet) throws SQLException {
		HR hr = new HR();
		hr.setId(resultSet.getLong(COLUMN_ID));
		hr.setCompanyId(resultSet.getLong(COLUMN_COMPANYID));
		hr.setName(resultSet.getString(COLUMN_NAME));
		hr.setEnglishName(resultSet.getString(COLUMN_ENGLISHNAME));
		hr.setGender(resultSet.getString(COLUMN_GENDER));
		hr.setOccupation(resultSet.getString(COLUMN_OCCUPATION));
		hr.setIsWorking(resultSet.getInt(COLUMN_ISWORKING));
		return hr;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		HashMap<String, String> parameters = new HashMap<String,String>();
/*
//		插入
		parameters.put(COLUMN_NAME, "HR测试名字1");
		parameters.put(COLUMN_ENGLISHNAME, "HR测试英文名1");
		parameters.put(COLUMN_GENDER, "0");
		parameters.put(COLUMN_OCCUPATION, "人事部门经理");
		parameters.put(COLUMN_ISWORKING, "1");
		HR hr = HRDao.insertHR(dataBaseDao, parameters);
		if(hr!=null){
			System.out.println(hr.toJsonString());
		}
*/		
		
		//删除
/*		parameters.put("id", "6");
		boolean result = HRDao.deleteHR(dataBaseDao, parameters);*/
		
		
		/*//查询
		parameters.put(COLUMN_ID, "1");
		ArrayList<HR> arrayList = HRDao.queryHR(dataBaseDao, parameters);
		int size = arrayList.size();
		for(int i = 0;i<size;i++){
			System.out.println(arrayList.get(i).toJsonString());
		}
		*/
		
/*		//修改
		parameters.put(Constant_Column.COLUMN_ID, "5");
		HashMap<String, String> values = new HashMap<String, String>();
		values.put(Constant_Column.COLUMN_ENGLISHNAME, "修改后的英文名");
		boolean result = HRDao.updateHR(dataBaseDao, parameters, values);*/
	}
}
