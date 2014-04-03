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

import javax.crypto.spec.PSource;

import com.unknown.wiki.bean.Company;
import com.unknown.wiki.bean.CompanyJob;
import com.unknown.wiki.bean.Project;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Table;

public class ProjectDao implements Constant_Column,Constant_SQL{
	/**插入Project*/
	public static Project insertProjectUnknown(DataBaseDao dataBaseDao,Map<String, String> parameters){
		Project project = null;
		if(dataBaseDao != null){
			HashMap<String, String> queryMap = new HashMap<String, String>();
			if(parameters.containsKey(COLUMN_COMPANYID)){
				queryMap.put(COLUMN_COMPANYID, parameters.get(COLUMN_COMPANYID));
			}else if(parameters.containsKey(COLUMN_COMPANYNAME)){
				queryMap.put(COLUMN_COMPANYNAME, parameters.get(COLUMN_COMPANYNAME));
				parameters.remove(COLUMN_COMPANYNAME);
			}else{
				return null;
			}
			
			if(parameters.containsKey(COLUMN_JOBID)){
				queryMap.put(COLUMN_JOBID, parameters.get(COLUMN_JOBID));
			}else if(parameters.containsKey(COLUMN_JOBNAME)){
				queryMap.put(COLUMN_JOBNAME, parameters.get(COLUMN_JOBNAME));
				parameters.remove(COLUMN_JOBNAME);
			}else{
				return null;
			}
			
			CompanyJob companyJob = null;
			companyJob = CompanyJobDao.queryOrInsertCompanyJob(dataBaseDao, queryMap);
			if(companyJob!=null){
				parameters.put(COLUMN_COMPANYID, String.valueOf(companyJob.getCompanyId()));
				parameters.put(COLUMN_JOBID, String.valueOf(companyJob.getJobId()));
				project = insertProject(dataBaseDao, parameters);
			}
		}
		return project;
	}
	
	/**插入Project*/
	public static Project insertProject(DataBaseDao dataBaseDao,Map<String, String> parameters){
		Project project = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_PROJECT);
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
						sb.append(Constant_Table.TABLE_PROJECT);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							project = formatProject(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return project;
	}
	
	/**删除项目*/
	public static boolean deleteProject(DataBaseDao dataBaseDao,Map<String, String> parameters){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Constant_Table.TABLE_PROJECT);
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
	
	/**查询项目*/
	public static ArrayList<Project> queryProject(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<Project> result = new ArrayList<Project>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_PROJECT);
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
				while(resultSet.next()){
					result.add(formatProject(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新项目*/
	public static boolean updateProject(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Constant_Table.TABLE_PROJECT);
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
	
	private static Project formatProject(ResultSet resultSet) throws SQLException {
		Project project = new Project();
		project.setId(resultSet.getLong(COLUMN_ID));
		project.setCompanyId(resultSet.getLong(COLUMN_COMPANYID));
		project.setJobId(resultSet.getLong(COLUMN_JOBID));
		project.setCreateUserId(resultSet.getLong(COLUMN_CREATEUSERID));
		project.setBelongUserId(resultSet.getLong(COLUMN_BELONGUSERID));
		project.setUpdateUserId(resultSet.getLong(COLUMN_UPDATEUSERID));
		project.setCreateDate(resultSet.getString(COLUMN_CREATEDATE));
		project.setUpdateDate(resultSet.getString(COLUMN_UPDATEDATE));
		return project;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		HashMap<String, String> parameters = new HashMap<String,String>();

//		插入
		parameters.put(COLUMN_COMPANYNAME, "a公司_002");
		parameters.put(COLUMN_JOBNAME, "a工作_002");
		Project Project = ProjectDao.insertProjectUnknown(dataBaseDao, parameters);
		if(Project!=null){
			System.out.println(Project.toJsonString());
		}
		
		
/*		//删除
		parameters.put("id", "13");
		boolean result = ProjectDao.deleteProject(dataBaseDao, parameters);*/
		
		
		//查询
/*//		parameters.put(COLUMN_ID, "1");
		ArrayList<Project> arrayList = ProjectDao.queryProject(dataBaseDao, parameters);
		int size = arrayList.size();
		for(int i = 0;i<size;i++){
			System.out.println(arrayList.get(i).toJsonString());
		}*/
		
		
/*		//修改
		parameters.put(Constant_Column.COLUMN_ID, "5");
		HashMap<String, String> values = new HashMap<String, String>();
		values.put(Constant_Column.COLUMN_ENGLISHNAME, "修改后的英文名");
		boolean result = ProjectDao.updateProject(dataBaseDao, parameters, values);*/
	}

}
