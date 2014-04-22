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

import com.unknown.wiki.bean.Record;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.w_enum.RecordType;
import com.unknown.wiki.w_enum.Visible;

public class RecordDao implements Constant_Column,Constant_SQL,Constant_Table,Constant_Servlet{
	
	/**插入记录*/
	public static Record insertRecord(DataBaseDao dataBaseDao,Map<String, String> parameters){
		Record record = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(TABLE_RECORD);
			sb.append(SQL_SET);
			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				sb.append(entry.getKey());
				sb.append(SQL_EQ);
				sb.append(SQL_SINGLE_QUOTES);
				sb.append(entry.getValue());
				sb.append(SQL_SINGLE_QUOTES);
				sb.append(SQL_COMMA);
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
						sb.append(Constant_Table.TABLE_RECORD);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(SQL_EQ);
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							record = formatRecord(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return record;
	}
	
	/**删除记录*/
	public static boolean deleteRecord(DataBaseDao dataBaseDao,Map<String, String> parameters){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Constant_Table.TABLE_RECORD);
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
	
	public static boolean deleteRecordJSON(W_User user,DataBaseDao dataBaseDao, JSONObject recordObject) {
		boolean result = false;
		
		if(dataBaseDao != null&&user!=null &&user.getRole()==127){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_UPDATE);
			sb.append(TABLE_RECORD);
			sb.append(SQL_SET);
			
			sb.append(COLUMN_VISIBLE);
			sb.append(SQL_EQ);
			sb.append(Visible.INVISIBLE.ordinal());
			
			sb.append(SQL_WHERE);
			Iterator<String> iterator = recordObject.keys();
			int count = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = recordObject.getString(key);
				
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
					sb.append(recordObject.get(key));
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
	
	/**查询记录*/
	public static ArrayList<Record> queryRecord(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<Record> result = new ArrayList<Record>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_RECORD);
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
					result.add(formatRecord(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**查询记录*/
	public static ArrayList<Record> queryRecordNew(DataBaseDao dataBaseDao,JSONObject userObject,JSONObject parameters){
		ArrayList<Record> result = new ArrayList<Record>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			String userId = userObject.optString(COLUMN_ID);
			String role	  = userObject.optString(COLUMN_ROLE);
			
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(TABLE_RECORD);
			Iterator<String> iterator = parameters.keys();
			int count = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				if(count != 0){
					sb.append(SQL_AND);
				}else{
					sb.append(SQL_WHERE);
				}
				sb.append(key);
				sb.append(SQL_EQ);
				sb.append(SQL_SINGLE_QUOTES);
				sb.append(parameters.getString(key));
				sb.append(SQL_SINGLE_QUOTES);
				count++;
			}
			
			if(!ROLE_ADMIN.equals(role)){
				if(sb.indexOf(SQL_WHERE)>0){
					sb.append(SQL_AND);
				}else{
					sb.append(SQL_WHERE);
				}
				
				sb.append(COLUMN_CREATEUSERID);
				sb.append(SQL_EQ);
				sb.append(SQL_SINGLE_QUOTES);
				sb.append(userId);
				sb.append(SQL_SINGLE_QUOTES);
			}
			
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					result.add(formatRecord(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新记录*/
	public static boolean updateRecord(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Constant_Table.TABLE_RECORD);
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
				sb.append(SQL_EQ);
				sb.append(SQL_SINGLE_QUOTES);
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
	
	private static Record formatRecord(ResultSet resultSet) throws SQLException {
		Record record = new Record();
		record.setId(resultSet.getLong(COLUMN_ID));
		record.setCompanyId(resultSet.getLong(COLUMN_COMPANYID));
		record.setType(RecordType.values()[resultSet.getInt(COLUMN_TYPE)]);
		record.setDate(resultSet.getString(COLUMN_DATE));
		record.setHrId(resultSet.getLong(COLUMN_HRID));
		record.setContent(resultSet.getString(COLUMN_CONTENT));
		record.setCreateUserId(resultSet.getInt(COLUMN_CREATEUSERID));
		return record;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		HashMap<String, String> parameters = new HashMap<String,String>();

		//插入
//		parameters.put("name", "维创科技有限记录2");
//		parameters.put("headhunter", "1");
//		parameters.put("isListing", "1");
//		Record record = RecordDao.insertRecord(dataBaseDao, parameters);
//		if(record!=null){
//			System.out.println(record.toJsonString());
//		}
		
		
		
		//删除
//		parameters.put("id", "15");
//		boolean result = RecordDao.deleteRecord(dataBaseDao, parameters);
		
		JSONObject userObject = new JSONObject();
		userObject.put(COLUMN_ID, "9527");
		userObject.put(COLUMN_ROLE, 127);
		
		JSONObject json = new JSONObject();
//		json.put(COLUMN_ID, null);
//		json.put(COLUMN_ENGLISHNAME, "Tecent");
		//查询
		ArrayList<Record> arrayList = RecordDao.queryRecordNew(dataBaseDao,userObject,json);
		int size = arrayList.size();
		for(int i = 0;i<size;i++){
			System.out.println(arrayList.get(i).toJsonString());
		}
		
		
		
//		//修改
//		parameters.put(Constant_Column.COLUMN_ID, "10");
//		HashMap<String, String> values = new HashMap<String, String>();
//		values.put(Constant_Column.COLUMN_ADDRESS, "维创新地址");
//		boolean result = RecordDao.updateRecord(dataBaseDao, parameters, values);
	}

	
//	public static boolean updateRecord(DataBaseDao dataBaseDao,JSONObject recordObject) {
//		boolean isSuccess = false;
//		if(dataBaseDao != null){
//			System.out.println("updateRecord 新接口");
//			HashMap<String,String> parameters = new HashMap<String, String>();
//			HashMap<String,String> values = new HashMap<String, String>();
//			
//			parameters.put(COLUMN_ID, recordObject.optString(COLUMN_ID));
//			
//			values.put(COLUMN_NAME, recordObject.optString(COLUMN_NAME));
//			values.put(COLUMN_ENGLISHNAME, recordObject.optString(COLUMN_ENGLISHNAME));
//			values.put(COLUMN_PROVINCE, recordObject.optString(COLUMN_PROVINCE));
//			values.put(COLUMN_CITY, recordObject.optString(COLUMN_CITY));
//			values.put(COLUMN_ADDRESS, recordObject.optString(COLUMN_ADDRESS));
//			values.put(COLUMN_INTENT, recordObject.optString(COLUMN_INTENT));
//			values.put(COLUMN_HEADHUNTER, recordObject.optString(COLUMN_HEADHUNTER));
//			values.put(COLUMN_NATURE, recordObject.optString(COLUMN_NATURE));
//			values.put(COLUMN_NUMBER, recordObject.optString(COLUMN_NUMBER));
//			values.put(COLUMN_ISLISTING, recordObject.optString(COLUMN_ISLISTING));
//			values.put(COLUMN_INTRODUCTION, recordObject.optString(COLUMN_INTRODUCTION));
//			
//			return updateRecord(dataBaseDao,parameters,values);
//		}
//		return isSuccess;
//	}

	public static boolean insertOrUpdateRecord(DataBaseDao dataBaseDao,JSONObject recordObject) {
		boolean isSuccess = false;
		if(dataBaseDao != null){
			HashMap<String, String> queryMap = new HashMap<String, String>();
			HashMap<String, String> values = new HashMap<String, String>();
			
			long id = -1;
			if(recordObject.containsKey(COLUMN_ID)){
				id = recordObject.optLong(COLUMN_ID,-1);
				recordObject.remove(COLUMN_ID);
			}
			
			Iterator<String> iterator = recordObject.keys();
			while(iterator.hasNext()){
				String key = iterator.next();
				values.put(key, recordObject.getString(key));
			}
			
			Record record = null;
			
			if(id <0){
				record = insertRecord(dataBaseDao, values);
			}else{
				queryMap.put(COLUMN_ID, String.valueOf(id));
				ArrayList<Record> recordList = queryRecord(dataBaseDao,queryMap);
				if(recordList.size()>0){
					record = recordList.get(0);
					updateRecord(dataBaseDao, queryMap, values);
				}else{
					record = insertRecord(dataBaseDao, values);
				}
			}
			
			isSuccess = record != null;
		}
		return isSuccess;
	}
}
