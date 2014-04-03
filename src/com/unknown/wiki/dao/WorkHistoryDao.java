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

import com.unknown.wiki.bean.WorkHistory;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Table;

public class WorkHistoryDao implements Constant_Column,Constant_SQL{
	
	/**插入公司*/
	public static WorkHistory insertWorkHistory(DataBaseDao dataBaseDao,Map<String, String> parameters){
		WorkHistory workHistory = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_WORKHISTORY);
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
						sb.append(Constant_Table.TABLE_WORKHISTORY);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							workHistory = formatWorkHistory(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return workHistory;
	}
	
	/**删除公司*/
	public static boolean deleteWorkHistory(DataBaseDao dataBaseDao,Map<String, String> parameters){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			if(parameters.isEmpty()) return false;
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Constant_Table.TABLE_WORKHISTORY);
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
	public static ArrayList<WorkHistory> queryWorkHistory(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<WorkHistory> result = new ArrayList<WorkHistory>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_WORKHISTORY);
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
//			if(count>0){
//				sb.deleteCharAt(sb.length()-1);
//			}
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					result.add(formatWorkHistory(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新公司*/
	public static boolean updateWorkHistory(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Constant_Table.TABLE_WORKHISTORY);
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
	
	private static WorkHistory formatWorkHistory(ResultSet resultSet) throws SQLException {
		WorkHistory workHistory = new WorkHistory();
		workHistory.setId(resultSet.getLong(COLUMN_ID));
		workHistory.setPersonId(resultSet.getLong(COLUMN_PERSONID));
		workHistory.setCompanyName(resultSet.getString(COLUMN_COMPANYNAME));
		workHistory.setJob(resultSet.getString(COLUMN_JOB));
		workHistory.setStartDate(resultSet.getString(COLUMN_STARTDATE));
		workHistory.setEndDate(resultSet.getString(COLUMN_ENDDATE));
		workHistory.setSalary(resultSet.getString(COLUMN_SALARY));
		workHistory.setLeaveReason(resultSet.getString(COLUMN_LEAVEREASON));
		
		return workHistory;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		HashMap<String, String> parameters = new HashMap<String,String>();

		//插入
//		parameters.put("name", "维创科技有限公司2");
//		parameters.put("headhunter", "1");
//		parameters.put("isListing", "1");
//		WorkHistory workHistory = WorkHistoryDao.insertWorkHistory(dataBaseDao, parameters);
//		if(workHistory!=null){
//			System.out.println(workHistory.toJsonString());
//		}
		
		
		
		//删除
//		parameters.put("id", "15");
//		boolean result = WorkHistoryDao.deleteWorkHistory(dataBaseDao, parameters);
		
		
		//查询
		/*ArrayList<WorkHistory> arrayList = WorkHistoryDao.queryWorkHistory(dataBaseDao, parameters);
		int size = arrayList.size();
		for(int i = 0;i<size;i++){
			System.out.println(arrayList.get(i).toJsonString());
		}*/
		
		
		//修改
		parameters.put(Constant_Column.COLUMN_ID, "10");
		HashMap<String, String> values = new HashMap<String, String>();
		values.put(Constant_Column.COLUMN_ADDRESS, "维创新地址");
		boolean result = WorkHistoryDao.updateWorkHistory(dataBaseDao, parameters, values);
	}

	
	public static boolean updateWorkHistory(DataBaseDao dataBaseDao,JSONObject companyObject) {
		boolean isSuccess = false;
		if(dataBaseDao != null){
			System.out.println("updateWorkHistory 新接口");
			HashMap<String,String> parameters = new HashMap<String, String>();
			HashMap<String,String> values = new HashMap<String, String>();
			
			parameters.put(COLUMN_ID, companyObject.optString(COLUMN_ID));
			
			values.put(COLUMN_NAME, companyObject.optString(COLUMN_NAME));
			values.put(COLUMN_ENGLISHNAME, companyObject.optString(COLUMN_ENGLISHNAME));
			values.put(COLUMN_PROVINCE, companyObject.optString(COLUMN_PROVINCE));
			values.put(COLUMN_CITY, companyObject.optString(COLUMN_CITY));
			values.put(COLUMN_ADDRESS, companyObject.optString(COLUMN_ADDRESS));
			values.put(COLUMN_INTENT, companyObject.optString(COLUMN_INTENT));
			values.put(COLUMN_HEADHUNTER, companyObject.optString(COLUMN_HEADHUNTER));
			values.put(COLUMN_NATURE, companyObject.optString(COLUMN_NATURE));
			values.put(COLUMN_NUMBER, companyObject.optString(COLUMN_NUMBER));
			values.put(COLUMN_ISLISTING, companyObject.optString(COLUMN_ISLISTING));
			values.put(COLUMN_INTRODUCTION, companyObject.optString(COLUMN_INTRODUCTION));
			
			return updateWorkHistory(dataBaseDao,parameters,values);
		}
		return isSuccess;
	}

}
