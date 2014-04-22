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

import com.unknown.wiki.bean.Job;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Table;

/**工作操作类*/
public class JobDao implements Constant_Column,Constant_SQL{
	/**插入工作*/
	public static Job insertJob(DataBaseDao dataBaseDao,Map<String, String> parameters){
		Job job = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_JOB);
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
						sb.append(Constant_Table.TABLE_JOB);
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
	
	/**删除工作*/
	public static boolean deleteJob(DataBaseDao dataBaseDao,Map<String, String> parameters){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Constant_Table.TABLE_JOB);
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
	
	/**查询工作*/
	public static ArrayList<Job> queryJob(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<Job> result = new ArrayList<Job>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_JOB);
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
				sb.append(SQL_EQ);sb.append(SQL_SINGLE_QUOTES);
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
	
	/**更新工作*/
	public static boolean updateJob(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Constant_Table.TABLE_JOB);
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
	
	private static Job formatJob(ResultSet resultSet) throws SQLException {
		Job job = new Job();
		job.setId(resultSet.getInt(COLUMN_ID));
		job.setName(resultSet.getString(COLUMN_NAME));
		return job;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		HashMap<String, String> parameters = new HashMap<String,String>();

/*		//插入
		parameters.put("name", "aJob002");
		Job job = JobDao.insertJob(dataBaseDao, parameters);
		if(job!=null){
			System.out.println(job.toJsonString());
		}*/
		
		
		
		//删除
		/*parameters.put("id", "3");
		boolean result = JobDao.deleteJob(dataBaseDao, parameters);*/
		
		
		//查询
/*		ArrayList<Job> arrayList = JobDao.queryJob(dataBaseDao, parameters);
		int size = arrayList.size();
		for(int i = 0;i<size;i++){
			System.out.println(arrayList.get(i).toJsonString());
		}*/
		
		
		//修改
		/*parameters.put(Constant_Column.COLUMN_ID, "1");
		HashMap<String, String> values = new HashMap<String, String>();
		values.put(Constant_Column.COLUMN_NAME, "aJob001_n");
		boolean result = JobDao.updateJob(dataBaseDao, parameters, values);*/
	}
}
