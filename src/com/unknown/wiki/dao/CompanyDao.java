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

import com.unknown.wiki.bean.Company;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.w_enum.Visible;

public class CompanyDao implements Constant_Column,Constant_SQL,Constant_Table,Constant_Servlet{
	
	/**插入公司*/
	public static Company insertCompany(DataBaseDao dataBaseDao,Map<String, String> parameters){
		Company company = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_COMPANY);
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
						sb.append(Constant_Table.TABLE_COMPANY);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							company = formatCompany(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return company;
	}
	
	/**插入公司*/
	public static Company insertCompanyJSONObject(DataBaseDao dataBaseDao,JSONObject companyObject){
		Company company = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_COMPANY);
			sb.append(SQL_SET);
			Iterator<String> iterator = companyObject.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				sb.append(key);
				sb.append(SQL_EQ);
				sb.append(SQL_SINGLE_QUOTES);
				sb.append(companyObject.optString(key));
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
						sb.append(Constant_Table.TABLE_COMPANY);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							company = formatCompany(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return company;
	}
	
//	/**删除公司*/
//	public static boolean deleteCompany(DataBaseDao dataBaseDao,Map<String, String> parameters){
//		boolean result = false;
//		if(dataBaseDao != null){
//			Connection connection = dataBaseDao.getConnection();
//			
//			//删除
//			StringBuffer sb = new StringBuffer();
//			sb.append("delete from ");
//			sb.append(Constant_Table.TABLE_COMPANY);
//			sb.append(SQL_WHERE);
//			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
//			int count = 0;
//			while (iterator.hasNext()) {
//				Entry<String, String> entry = iterator.next();
//
//				if(count != 0){
//					sb.append(SQL_AND);
//				}
//				sb.append(entry.getKey());
//				sb.append("= '");
//				sb.append(entry.getValue());
//				sb.append(SQL_SINGLE_QUOTES);
//				count ++;
//			}
//			sb.append(SQL_SEMICOLON);
//			
//			System.out.println("执行的sql语句为			" +	sb.toString());
//			try {
//				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
//				int deleteResult = preparedStatement.executeUpdate();
//				System.out.println("deleteResult --- " + deleteResult);
//				if(deleteResult>0){
//					result = true;
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
	
	/**删除公司*/
	public static boolean deleteCompanyJson(W_User user,DataBaseDao dataBaseDao,JSONObject companyObject){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_UPDATE);
			sb.append(TABLE_COMPANY);
			sb.append(SQL_SET);
			
			sb.append(COLUMN_VISIBLE);
			sb.append(SQL_EQ);
			sb.append(Visible.INVISIBLE.ordinal());
			
			sb.append(SQL_WHERE);
			Iterator<String> iterator = companyObject.keys();
			int count = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = companyObject.getString(key);
				
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
					sb.append(companyObject.get(key));
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
	
	/**查询公司*/
	public static ArrayList<Company> queryCompany(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<Company> result = new ArrayList<Company>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_COMPANY);
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
					result.add(formatCompany(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**查询公司*/
//	public static ArrayList<Company> queryCompanyGrantOld(DataBaseDao dataBaseDao,JSONObject userObject,JSONObject parameters){
//		ArrayList<Company> result = new ArrayList<Company>();
//		if(dataBaseDao != null){
//			Connection connection = dataBaseDao.getConnection();
//			
//			String userId = userObject.optString(COLUMN_ID);
//			String role	  = userObject.optString(COLUMN_ROLE);
//			
//			
//			StringBuffer sb = new StringBuffer();
//			sb.append(SQL_QUERY);
//			sb.append(TABLE_COMPANY);
//			Iterator<String> iterator = parameters.keys();
//			int count = 0;
//			while (iterator.hasNext()) {
//				String key = iterator.next();
//				if(count != 0){
//					sb.append(SQL_AND);
//				}else{
//					sb.append(SQL_WHERE);
//				}
//				sb.append(key);
//				sb.append(SQL_EQ);
//				sb.append(SQL_SINGLE_QUOTES);
//				sb.append(parameters.getString(key));
//				sb.append(SQL_SINGLE_QUOTES);
//				count++;
//			}
//			
//			if(!ROLE_ADMIN.equals(role)){
//				if(sb.indexOf(SQL_WHERE)>0){
//					sb.append(SQL_AND);
//				}else{
//					sb.append(SQL_WHERE);
//				}
//				
//				sb.append(COLUMN_CREATEUSERID);
//				sb.append(SQL_EQ);
//				sb.append(SQL_SINGLE_QUOTES);
//				sb.append(userId);
//				sb.append(SQL_SINGLE_QUOTES);
//			}
//			
//			sb.append(SQL_SEMICOLON);
//			
//			System.out.println("执行的sql语句为			" +	sb.toString());
//			
//			try {
//				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
//				ResultSet resultSet = preparedStatement.executeQuery();
//				while(resultSet.next()){
//					result.add(formatCompany(resultSet));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
	
	/**查询公司*/
	public static ArrayList<Company> queryCompanyGrant(DataBaseDao dataBaseDao,W_User user,JSONObject parameters){
		ArrayList<Company> result = new ArrayList<Company>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			int userId	 	= user.getId();
			int role	  	= user.getRole();
			
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(TABLE_COMPANY);
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
					result.add(formatCompany(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新公司*/
	public static boolean updateCompany(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Constant_Table.TABLE_COMPANY);
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
	
	private static Company formatCompany(ResultSet resultSet) throws SQLException {
		Company company = new Company();
		company.setId(resultSet.getLong(COLUMN_ID));
		
		company.setCreateUserId(resultSet.getInt(COLUMN_CREATEUSERID));
		company.setCreateDate(resultSet.getString(COLUMN_CREATEDATE));
		
		company.setUpdateUserId(resultSet.getInt(COLUMN_UPDATEUSERID));
		company.setUpdateDate(resultSet.getString(COLUMN_UPDATEDATE));
		
		company.setName(resultSet.getString(COLUMN_NAME));
		company.setEnglishName(resultSet.getString(COLUMN_ENGLISHNAME));
		company.setProvince(resultSet.getString(COLUMN_PROVINCE));
		company.setCity(resultSet.getString(COLUMN_CITY));
		company.setAddress(resultSet.getString(COLUMN_ADDRESS));
		company.setIntent(resultSet.getString(COLUMN_INTENT));
		company.setHeadHunter(resultSet.getInt(COLUMN_HEADHUNTER));
		company.setNature(resultSet.getString(COLUMN_NATURE));
		company.setNumber(resultSet.getString(COLUMN_NUMBER));
		company.setIsListing(resultSet.getInt(COLUMN_ISLISTING));
		company.setIntroduction(resultSet.getString(COLUMN_INTRODUCTION));	
		
		return company;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		JSONArray idArray = new JSONArray();
		for(int i=0;i<10;i++){
			idArray.add(i);
		}
		JSONObject companyObject = new JSONObject();
//		companyObject.put(COLUMN_ID,idArray);
		companyObject.put(COLUMN_ID,1);
		deleteCompanyJson(null,dataBaseDao,companyObject);
		
		
//		DataBaseDao dataBaseDao = new DataBaseDao();
//		HashMap<String, String> parameters = new HashMap<String,String>();
//
//		//插入
////		parameters.put("name", "维创科技有限公司2");
////		parameters.put("headhunter", "1");
////		parameters.put("isListing", "1");
////		Company company = CompanyDao.insertCompany(dataBaseDao, parameters);
////		if(company!=null){
////			System.out.println(company.toJsonString());
////		}
//		
//		
//		
//		//删除
////		parameters.put("id", "15");
////		boolean result = CompanyDao.deleteCompany(dataBaseDao, parameters);
//		
//		JSONObject userObject = new JSONObject();
//		userObject.put(COLUMN_ID, "9527");
//		userObject.put(COLUMN_ROLE, 127);
//		
//		JSONObject json = new JSONObject();
////		json.put(COLUMN_ID, null);
////		json.put(COLUMN_ENGLISHNAME, "Tecent");
//		//查询
//		ArrayList<Company> arrayList = CompanyDao.queryCompanyGrant(dataBaseDao,null,json);
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
////		boolean result = CompanyDao.updateCompany(dataBaseDao, parameters, values);
	}

	
	public static boolean updateCompany(DataBaseDao dataBaseDao,JSONObject companyObject) {
		boolean isSuccess = false;
		if(dataBaseDao != null){
			System.out.println("updateCompany 新接口");
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
			
			return updateCompany(dataBaseDao,parameters,values);
		}
		return isSuccess;
	}
}
