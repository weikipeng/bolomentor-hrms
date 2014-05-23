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

import com.unknown.wiki.bean.PersonRecord;
import com.unknown.wiki.bean.WorkExperience;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.w_enum.Visible;

public class WorkExperienceDao implements Constant_Column,Constant_SQL,Constant_Table,Constant_Servlet{
	
	/**插入新工作记录*/
	public static WorkExperience insertWorkExperience(DataBaseDao dataBaseDao,Map<String, String> parameters){
		WorkExperience workExperience = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_WORK_EXPERIENCE);
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
						sb.append(Constant_Table.TABLE_WORK_EXPERIENCE);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							workExperience = formatWorkExperience(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return workExperience;
	}
	
	/**插入工作记录*/
	public static WorkExperience insertWorkExperienceJSONObject(DataBaseDao dataBaseDao,JSONObject workExperienceObject){
		WorkExperience workExperience = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			if(workExperienceObject.containsKey(COLUMN_ID)){
				workExperienceObject.remove(COLUMN_ID);
			}
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_WORK_EXPERIENCE);
			sb.append(SQL_SET);
			Iterator<String> iterator = workExperienceObject.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				sb.append(key);
				sb.append(SQL_EQ);
				sb.append(SQL_SINGLE_QUOTES);
				sb.append(workExperienceObject.optString(key));
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
						sb.append(Constant_Table.TABLE_WORK_EXPERIENCE);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							workExperience = formatWorkExperience(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return workExperience;
	}
	
	public static boolean insertOrUpdateWorkExperience(DataBaseDao dataBaseDao,JSONObject recordObject) {
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
			
			WorkExperience record = null;
			
			if(id <0){
				record = insertWorkExperience(dataBaseDao, values);
			}else{
				queryMap.put(COLUMN_ID, String.valueOf(id));
				ArrayList<WorkExperience> recordList = queryWorkExperience(dataBaseDao,queryMap);
				if(recordList.size()>0){
					record = recordList.get(0);
					updateWorkExperience(dataBaseDao, queryMap, values);
				}else{
					record = insertWorkExperience(dataBaseDao, values);
				}
			}
			
			isSuccess = record != null;
		}
		return isSuccess;
	}
	
	/**删除工作记录*/
	public static boolean deleteWorkExperienceJson(W_User user,DataBaseDao dataBaseDao,JSONObject workExperienceObject){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_UPDATE);
			sb.append(TABLE_WORK_EXPERIENCE);
			sb.append(SQL_SET);
			
			sb.append(COLUMN_VISIBLE);
			sb.append(SQL_EQ);
			sb.append(Visible.INVISIBLE.ordinal());
			
			sb.append(SQL_WHERE);
			Iterator<String> iterator = workExperienceObject.keys();
			int count = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = workExperienceObject.getString(key);
				
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
					sb.append(workExperienceObject.get(key));
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
	
	/**查询工作记录*/
	public static ArrayList<WorkExperience> queryWorkExperience(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<WorkExperience> result = new ArrayList<WorkExperience>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_WORK_EXPERIENCE);
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
					result.add(formatWorkExperience(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**查询工作记录*/
	public static ArrayList<WorkExperience> queryWorkExperienceGrant(DataBaseDao dataBaseDao,W_User user,JSONObject parameters){
		ArrayList<WorkExperience> result = new ArrayList<WorkExperience>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			int userId	 	= user.getId();
			int role	  	= user.getRole();
			
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(TABLE_WORK_EXPERIENCE);
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
			
			if(ROLE_ADMIN_VALUE!=role){
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
					result.add(formatWorkExperience(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新工作记录*/
	public static boolean updateWorkExperience(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_UPDATE);
			sb.append(Constant_Table.TABLE_WORK_EXPERIENCE);
			sb.append(SQL_SET);
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
			
			if(count == 0) return false;
			
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
	
	private static WorkExperience formatWorkExperience(ResultSet resultSet) throws SQLException {
		WorkExperience workExperience = new WorkExperience();
		workExperience.setId(resultSet.getLong(COLUMN_ID));
		workExperience.setPersonId(resultSet.getLong(COLUMN_PERSONID));
		workExperience.setCompanyName(resultSet.getString(COLUMN_COMPANYNAME));
		workExperience.setJobTitle(resultSet.getString(COLUMN_JOBTITLE));
		workExperience.setJobLevel(resultSet.getString(COLUMN_JOBLEVEL));
		workExperience.setSalary(resultSet.getString(COLUMN_SALARY));
		workExperience.setJobInfo(resultSet.getString(COLUMN_JOBINFO));
		workExperience.setStartDate(resultSet.getString(COLUMN_STARTDATE));
		workExperience.setEndDate(resultSet.getString(COLUMN_ENDDATE));
		workExperience.setLeaveReason(resultSet.getString(COLUMN_LEAVEREASON));
		
		return workExperience;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		JSONArray idArray = new JSONArray();
		for(int i=0;i<10;i++){
			idArray.add(i);
		}
		JSONObject workExperienceObject = new JSONObject();
//		workExperienceObject.put(COLUMN_ID,idArray);
		workExperienceObject.put(COLUMN_ID,1);
		deleteWorkExperienceJson(null,dataBaseDao,workExperienceObject);
		
		
//		DataBaseDao dataBaseDao = new DataBaseDao();
//		HashMap<String, String> parameters = new HashMap<String,String>();
//
//		//插入
////		parameters.put("name", "维创科技有限工作记录2");
////		parameters.put("headhunter", "1");
////		parameters.put("isListing", "1");
////		WorkExperience workExperience = WorkExperienceDao.insertWorkExperience(dataBaseDao, parameters);
////		if(workExperience!=null){
////			System.out.println(workExperience.toJsonString());
////		}
//		
//		
//		
//		//删除
////		parameters.put("id", "15");
////		boolean result = WorkExperienceDao.deleteWorkExperience(dataBaseDao, parameters);
//		
//		JSONObject userObject = new JSONObject();
//		userObject.put(COLUMN_ID, "9527");
//		userObject.put(COLUMN_ROLE, 127);
//		
//		JSONObject json = new JSONObject();
////		json.put(COLUMN_ID, null);
////		json.put(COLUMN_ENGLISHNAME, "Tecent");
//		//查询
//		ArrayList<WorkExperience> arrayList = WorkExperienceDao.queryWorkExperienceGrant(dataBaseDao,null,json);
//		int size = arrayList.size();
//		for(int i = 0;i<size;i++){
//			System.out.println(arrayList.get(i).toJsonString());
//		}
//		
//		
//		
////		//修改
////		parameters.put(Constant_Column.COLUMN_ID, "10");
////		HashMap<String, String> values = new HashMap<String, String>();
////		values.put(Constant_Column.COLUMN_ADDRESS, "维创新地址");
////		boolean result = WorkExperienceDao.updateWorkExperience(dataBaseDao, parameters, values);
	}
	
	public static boolean updateWorkExperienceJSON(DataBaseDao dataBaseDao,JSONObject workExperienceObject) {
		boolean isSuccess = false;
		if(dataBaseDao != null){
			System.out.println("updateWorkExperience 新接口-------------------");
			
			long id = workExperienceObject.optLong(COLUMN_ID,-1);
			workExperienceObject.remove(COLUMN_ID);
			if(id > 0){
				HashMap<String,String> parameters = new HashMap<String, String>();
				HashMap<String,String> values = new HashMap<String, String>();
				
				parameters.put(COLUMN_ID, String.valueOf(id));
				
				Iterator<String> keys = workExperienceObject.keys();
				while(keys.hasNext()){
					String key = keys.next();
					values.put(key, workExperienceObject.optString(key));
				}
				return updateWorkExperience(dataBaseDao,parameters,values);
			}
			
		}
		return isSuccess;
	}
}
