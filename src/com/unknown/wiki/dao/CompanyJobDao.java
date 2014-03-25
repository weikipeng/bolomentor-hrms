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

import com.unknown.wiki.bean.Company;
import com.unknown.wiki.bean.CompanyJob;
import com.unknown.wiki.bean.Job;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Table;

public class CompanyJobDao implements Constant_Column,Constant_SQL{
	
	/**查询或插入公司工作*/
	public static CompanyJob queryOrInsertCompanyJob(DataBaseDao dataBaseDao,Map<String, String> parameters){
		CompanyJob companyJob = null;
//		if(dataBaseDao != null){
//			HashMap<String, String> queryMap = new HashMap<String, String>();
//			if(parameters.containsKey(COLUMN_COMPANYNAME)){
//				Company company = null;
//				queryMap.put(COLUMN_NAME, parameters.get(COLUMN_COMPANYNAME));
//				ArrayList<Company> companyList = CompanyDao.queryCompany(dataBaseDao, queryMap);
//				if(companyList.size()>0){
//					company = companyList.get(0);
//				}
//				if(company == null){
//					return insertCompanyJobUnknown(dataBaseDao, parameters);
//				}
//				
//				parameters.remove(COLUMN_COMPANYNAME);
//				parameters.put(COLUMN_COMPANYID,String.valueOf(company.getId()));
//				
//			}else if(!parameters.containsKey(COLUMN_COMPANYID)){
//				return null;
//			}
//			
//			if(parameters.containsKey(COLUMN_JOBNAME)){
//				Job job = null;
//				queryMap.put(COLUMN_NAME, parameters.get(COLUMN_JOBNAME));
//				ArrayList<Job> jobList = JobDao.queryJob(dataBaseDao, queryMap);
//				if(jobList.size()>0){
//					job = jobList.get(0);
//				}
//				
//				if(job == null){
//					return insertCompanyJobUnknown(dataBaseDao, parameters);
//				}
//				parameters.remove(COLUMN_JOBNAME);
//				parameters.put(COLUMN_JOBID,String.valueOf(job.getId()));
//			}else if(!parameters.containsKey(COLUMN_JOBID)){
//				return null;
//			}
//			
//			ArrayList<CompanyJob> companyJobList = queryCompanyJob(dataBaseDao, parameters);
//			if(companyJobList.size()>0){
//				companyJob = companyJobList.get(0);
//			}else{
//				return insertCompanyJob(dataBaseDao, parameters);
//			}
//		}
		return companyJob;
	}
	
	/**插入工作*/
	public static CompanyJob insertCompanyJob(DataBaseDao dataBaseDao,Map<String, String> parameters){
		CompanyJob job = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_COMPANYJOB);
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
						sb.append("select * from ");
						sb.append(Constant_Table.TABLE_COMPANYJOB);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							job = formatJob(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return job;
	}
	
	public static CompanyJob insertCompanyJobUnknown(DataBaseDao dataBaseDao,Map<String, String> parameters){
		CompanyJob companyJob = null;
//		if(dataBaseDao != null){
//			long companyId = -1;
//			HashMap<String, String> queryMap = new HashMap<String, String>();
//			if(parameters.containsKey(COLUMN_COMPANYNAME)){
//				queryMap.put(COLUMN_NAME, parameters.get(COLUMN_COMPANYNAME));
//				ArrayList<Company> companyList = CompanyDao.queryCompany(dataBaseDao, queryMap);
//				Company company = null;
//				if(companyList.size()>0){
//					company = companyList.get(0);
//				}else{
//					company = CompanyDao.insertCompany(dataBaseDao, queryMap);
//				}
//				
//				if(company == null){
//					return null;
//				}
//				
//				parameters.remove(COLUMN_COMPANYNAME);
//				parameters.put(COLUMN_COMPANYID, String.valueOf(company.getId()));
//			}else{
//				if(parameters.containsKey(COLUMN_COMPANYID)){
//					companyId = Long.parseLong(parameters.get(COLUMN_COMPANYID));
//					//是否应该判断存不存在？
//				}else{
//					return null;
//				}
//			}
//			
//			
//			int jobId = -1;
//			if(parameters.containsKey(COLUMN_JOBNAME)){
//				Job job = null;
////				HashMap<String, String> queryMap = new HashMap<String, String>();
//				queryMap.put(COLUMN_NAME, parameters.get(COLUMN_JOBNAME));
//				ArrayList<Job> jobList = JobDao.queryJob(dataBaseDao, queryMap);
//				if(jobList.size()>0){
//					job = jobList.get(0);
//				}else{
//					job = JobDao.insertJob(dataBaseDao, queryMap);
//				}
//				
//				if(job == null){
//					return null;
//				}
//				
//				jobId = job.getId();
//				parameters.remove(COLUMN_JOBNAME);
//				parameters.put(COLUMN_JOBID, String.valueOf(jobId));
//			}else{
//				if(parameters.containsKey(COLUMN_JOBID)){
//					jobId = Integer.parseInt(parameters.get(COLUMN_JOBID));
//				}else{
//					return null;
//				}
//			}
//			
//			return insertCompanyJob(dataBaseDao, parameters);
//		}
		return companyJob;
	}
	
	/**删除公司*/
	public static boolean deleteCompanyJob(DataBaseDao dataBaseDao,Map<String, String> parameters){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Constant_Table.TABLE_COMPANYJOB);
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
	public static ArrayList<CompanyJob> queryCompanyJob(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<CompanyJob> result = new ArrayList<CompanyJob>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ");
			sb.append(Constant_Table.TABLE_COMPANYJOB);
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
					result.add(formatJob(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新公司*/
	public static boolean updateJob(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Constant_Table.TABLE_COMPANYJOB);
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
	
	private static CompanyJob formatJob(ResultSet resultSet) throws SQLException {
		CompanyJob job = new CompanyJob();
		job.setCompanyId(resultSet.getLong(COLUMN_COMPANYID));
		job.setJobId(resultSet.getLong(COLUMN_JOBID));
		job.setSalary(resultSet.getString(COLUMN_SALARY));
		job.setNumber(resultSet.getInt(COLUMN_NUMBER));
		job.setStatus(resultSet.getString(COLUMN_STATUS));
		return job;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		HashMap<String, String> parameters = new HashMap<String,String>();

		//插入
/*		parameters.put(COLUMN_COMPANYNAME, "a公司_003");
		parameters.put(COLUMN_JOBNAME, "a工作_002");
		parameters.put(COLUMN_SALARY, "10万");
		parameters.put(COLUMN_NUMBER, "4");
		parameters.put(COLUMN_STATUS, "应聘中");
		CompanyJob job = CompanyJobDao.insertCompanyJobUnknown(dataBaseDao, parameters);
		if(job!=null){
			System.out.println(job.toJsonString());
		}*/
		
		
		
		//删除
		/*parameters.put(COLUMN_COMPANYID, "1");
		parameters.put(COLUMN_JOBID, "2");
		boolean result = CompanyJobDao.deleteCompanyJob(dataBaseDao, parameters);*/
		
		
/*		//查询
		ArrayList<CompanyJob> arrayList = CompanyJobDao.queryJob(dataBaseDao, parameters);
		int size = arrayList.size();
		for(int i = 0;i<size;i++){
			System.out.println(arrayList.get(i).toJsonString());
		}*/
		
		
		//修改
		parameters.put(Constant_Column.COLUMN_COMPANYID, "21653");
		parameters.put(Constant_Column.COLUMN_JOBID, "4");
		HashMap<String, String> values = new HashMap<String, String>();
		values.put(Constant_Column.COLUMN_STATUS, "应聘中_m");
		boolean result = CompanyJobDao.updateJob(dataBaseDao, parameters, values);
	}
}
