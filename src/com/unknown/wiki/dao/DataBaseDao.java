package com.unknown.wiki.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import com.mysql.jdbc.PreparedStatement;

/*
 * 数据库连接管理类
 */
public class DataBaseDao {
	private Connection mConnection;
	private final String url = "jdbc:mysql://127.0.0.1:3306/hrms";
	private String user = "root";
	private String password = "root";
	public static final String TABLE_USER = "user";
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
		
		try {
			mConnection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean close(){
		if(mConnection!=null){
			try {
				if(!mConnection.isClosed()){
					mConnection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			mConnection = null;
		}
		return true;
	}
	
	public Connection getConnection(){
		return mConnection;
	}
	
	public static void main(String[] args) {
//		DataBaseDao dataBaseDao = new DataBaseDao();
//		String sql = "select * from user";
//		Connection connection = dataBaseDao.getConnection();
//		try {
//			PreparedStatement preparedStatement =  (PreparedStatement) connection.prepareStatement(sql);
//			ResultSet resultSet =  preparedStatement.executeQuery();
////			while (!resultSet.isLast()) {
//			while (resultSet.next()) {
////				resultSet.next();
//				String name = resultSet.getString(2);
//				String pass = resultSet.getString(3);   
//				System.out.println("name -- " + name +"   pass -- "+pass);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		dataBaseDao.close();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("test","test");
		System.out.println(jsonObject);
	}
}

