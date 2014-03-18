package com.unknown.wiki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

import com.mysql.jdbc.PreparedStatement;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Table;

public class LoginDao {
	public static final W_User login(DataBaseDao dataBaseDao,String name ,String password){

//		Job job = null;
//		if(dataBaseDao != null){
//			Connection connection = dataBaseDao.getConnection();
//			
//			//插入
//			StringBuffer sb = new StringBuffer();
//			sb.append("insert into ");
//			sb.append(Constant_Table.TABLE_JOB);
//			sb.append(" set ");
//			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
//			while (iterator.hasNext()) {
//				Entry<String, String> entry = iterator.next();
//				sb.append(entry.getKey());
//				sb.append("= '");
//				sb.append(entry.getValue());
//				sb.append("',");
//			}
//			sb.deleteCharAt(sb.length()-1);
//			sb.append(";");
//			
//			System.out.println("执行的sql语句为			" +	sb.toString());
//			
//			try {
//				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
//				int insertResult = preparedStatement.executeUpdate();
//				System.out.println("insertResult	" +	insertResult);
//				if(insertResult>0){
//					ResultSet resultSet = preparedStatement.getGeneratedKeys();
//					if(resultSet.next()){
//						System.out.println("id --- " + resultSet.getString(1) );
//						
//						long id = resultSet.getLong(1);
//						sb = new StringBuffer();
//						sb.append("select * from ");
//						sb.append(Constant_Table.TABLE_JOB);
//						sb.append(" where ");
//						sb.append(COLUMN_ID);
//						sb.append(" = ");
//						sb.append(id);
//						preparedStatement = connection.prepareStatement(sb.toString());
//						resultSet = preparedStatement.executeQuery();
//						if(resultSet.next()){
//							job = formatJob(resultSet);
//						}
//					}
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return job;

		W_User user = null;
		if(dataBaseDao!=null){
			String sql = "select * from user where name = ? and password = ?";
			Connection connection = dataBaseDao.getConnection();
			System.out.println("name -- " + name +"   pass -- "+password);
			try {
				PreparedStatement preparedStatement =  (PreparedStatement) connection.prepareStatement(sql);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, password);
				ResultSet resultSet =  preparedStatement.executeQuery();
				if(resultSet.next()) {
					user = new W_User();
					user.setId(Integer.parseInt(resultSet.getString("id")));
//				String pass = resultSet.getString(3);   
					user.setName(resultSet.getString("name"));
					user.setRole(Integer.parseInt(resultSet.getString("role")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return user;
	}
	
	public static final boolean isLogin(Map<String,String[]> requestMap){
		boolean result = false;
//		JSONObject userObject = requestMap.containsKey(Constant_Table.TABLE_USER) ? JSONObject.fromObject(requestMap.get(Constant_Table.TABLE_USER)[0]):null;
		JSONObject userObject = getUserObject(requestMap);
		if(userObject != null){
			int userId = userObject.optInt(Constant_Column.COLUMN_ID); 
			int role = userObject.optInt(Constant_Column.COLUMN_ROLE); 
			
			if(userId  >0 || role >0){
				result = true;
			}
		}
		
		return result;
	}
	
	public static final JSONObject getUserObject(Map<String,String[]> requestMap){
		return requestMap.containsKey(Constant_Table.TABLE_USER) ? JSONObject.fromObject(requestMap.get(Constant_Table.TABLE_USER)[0]):null;
	}
	
}
