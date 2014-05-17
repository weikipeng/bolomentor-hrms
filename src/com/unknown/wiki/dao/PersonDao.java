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

import com.unknown.wiki.bean.Person;
import com.unknown.wiki.bean.Person;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.w_enum.Gender;
import com.unknown.wiki.w_enum.Visible;

public class PersonDao implements Constant_Column,Constant_SQL,Constant_Table,Constant_Servlet{

	
	/**插入Person*/
	public static Person insertPerson(DataBaseDao dataBaseDao,Map<String, String> parameters){
		Person person = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_PERSON);
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
						sb.append(Constant_Table.TABLE_PERSON);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							person = formatPerson(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return person;
	}
	
	public static Person insertPersonJSONObject(DataBaseDao dataBaseDao,JSONObject personObject) {

		Person person = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			if(personObject.containsKey(COLUMN_ID)){
				personObject.remove(COLUMN_ID);
			}
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_INSERT);
			sb.append(Constant_Table.TABLE_PERSON);
			sb.append(SQL_SET);
			Iterator<String> iterator = personObject.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				sb.append(key);
				sb.append(SQL_EQ);
				sb.append(SQL_SINGLE_QUOTES);
				sb.append(personObject.optString(key));
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
						sb.append(Constant_Table.TABLE_PERSON);
						sb.append(SQL_WHERE);
						sb.append(COLUMN_ID);
						sb.append(" = ");
						sb.append(id);
						preparedStatement = connection.prepareStatement(sb.toString());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							person = formatPerson(resultSet);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return person;
	}

	/**删除人才*/
	public static boolean deletePerson(DataBaseDao dataBaseDao,Map<String, String> parameters){
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Constant_Table.TABLE_PERSON);
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
	
	public static boolean deletePersonJson(W_User user,DataBaseDao dataBaseDao, JSONObject personObject) {
		boolean result = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//删除
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_UPDATE);
			sb.append(TABLE_PERSON);
			sb.append(SQL_SET);
			
			sb.append(COLUMN_VISIBLE);
			sb.append(SQL_EQ);
			sb.append(Visible.INVISIBLE.ordinal());
			
			sb.append(SQL_WHERE);
			Iterator<String> iterator = personObject.keys();
			int count = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = personObject.getString(key);
				
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
					sb.append(personObject.get(key));
					sb.append(SQL_SINGLE_QUOTES);
				}
				
			}
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			if(sb.indexOf(SQL_WHERE)>0){
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
		}
		return result;
	}
	
	/**查询人才*/
	public static ArrayList<Person> queryPerson(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<Person> result = new ArrayList<Person>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_PERSON);
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
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					result.add(formatPerson(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static ArrayList<Person> queryPersonJSON(DataBaseDao dataBaseDao,JSONObject personObject) {
		ArrayList<Person> result = new ArrayList<Person>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(Constant_Table.TABLE_PERSON);
			Iterator<String> iterator = personObject.keys();
			int count = 0;
			while (iterator.hasNext()) {
				String key= iterator.next();
				if(count != 0){
					sb.append(SQL_AND);
				}else{
					sb.append(SQL_WHERE);
				}
				sb.append(key);
				sb.append(SQL_EQ);
				sb.append(SQL_SINGLE_QUOTES);
				sb.append(personObject.get(key));
				sb.append(SQL_SINGLE_QUOTES);
				count++;
			}
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					result.add(formatPerson(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static ArrayList<Person> queryPersonGrant(DataBaseDao dataBaseDao,W_User user, JSONObject parameters) {
		ArrayList<Person> result = new ArrayList<Person>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			int userId	 	= user.getId();
			int role	  	= user.getRole();
			
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_QUERY);
			sb.append(TABLE_PERSON);
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
			
//			if(ROLE_ADMIN_VALUE!=role){
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
			
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					result.add(formatPerson(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新人才*/
	public static boolean updatePerson(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_UPDATE);
			sb.append(Constant_Table.TABLE_PERSON);
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
			
			sb.append(SQL_SEMICOLON);
			
			System.out.println("执行的sql语句为			" +	sb.toString());
			
			if(sb.indexOf(SQL_WHERE)>0){
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
		}
		return isSuccess;
	}
	
	public static boolean updatePersonJSON(DataBaseDao dataBaseDao,JSONObject personObject) {
		boolean isSuccess = false;
		if(dataBaseDao != null){
			System.out.println("updatePerson 新接口-------------------");
			
			long id = personObject.optLong(COLUMN_ID,-1);
			personObject.remove(COLUMN_ID);
			if(id > 0){
				HashMap<String,String> parameters = new HashMap<String, String>();
				HashMap<String,String> values = new HashMap<String, String>();
				
				parameters.put(COLUMN_ID, String.valueOf(id));
				
				Iterator<String> keys = personObject.keys();
				while(keys.hasNext()){
					String key = keys.next();
					values.put(key, personObject.optString(key));
				}
				return updatePerson(dataBaseDao,parameters,values);
			}
			
		}
		return isSuccess;
	}
	
	private static Person formatPerson(ResultSet resultSet) throws SQLException {
		Person person = new Person();
		person.setId(resultSet.getLong(COLUMN_ID));
		person.setNetId(resultSet.getString(COLUMN_NETID));
		person.setName(resultSet.getString(COLUMN_NAME));
		person.setEnglishName(resultSet.getString(COLUMN_ENGLISHNAME));
		person.setBirthday(resultSet.getString(COLUMN_BIRTHDAY));
		person.setGender(Gender.values()[resultSet.getInt(COLUMN_GENDER)]);
		person.setAge(resultSet.getInt(COLUMN_AGE));
		person.setEducation(resultSet.getString(COLUMN_EDUCATION));
		person.setMaritalStatus(resultSet.getString(COLUMN_MARITALSTATUS));
		person.setWorkYear(resultSet.getInt(COLUMN_WORKYEAR));
		person.setLiveAddress(resultSet.getString(COLUMN_LIVEADDRESS));
		person.setBelongAddress(resultSet.getString(COLUMN_BELONGADDRESS));
		person.setOtherLanguage(resultSet.getString(COLUMN_OTHERLANGUAGE));
		person.setSalary(resultSet.getString(COLUMN_SALARY));
//		person.setPersonNature(resultSet.getString(COLUMN_PERSONNATURE));
		person.setWorkStatus(resultSet.getString(COLUMN_WORKSTATUS));
		person.setHopeAddress(resultSet.getString(COLUMN_HOPEADDRESS));
		person.setHopeJob(resultSet.getString(COLUMN_HOPEJOB));
		person.setHopeVocation(resultSet.getString(COLUMN_HOPEVOCATION));
		person.setHopeSalary(resultSet.getString(COLUMN_HOPESALARY));
		person.setHope(resultSet.getInt(COLUMN_ISHOPE) == 1);
		person.setVitae(resultSet.getString(COLUMN_VITAE));
		person.setCreateUserId(resultSet.getLong(COLUMN_CREATEUSERID));
		person.setCreateUser(resultSet.getString(COLUMN_CREATEUSER));
		person.setCreateDate(resultSet.getString(COLUMN_CREATEDATE));
		person.setUpdateUserId(resultSet.getLong(COLUMN_UPDATEUSERID));
		person.setUpdateUser(resultSet.getString(COLUMN_UPDATEUSER));
		person.setUpdateDate(resultSet.getString(COLUMN_UPDATEDATE));
		return person;
	}

	public static void main(String[] args) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		HashMap<String, String> parameters = new HashMap<String,String>();

/*//		插入
		parameters.put(COLUMN_NAME, "Person测试名字1");
		parameters.put(COLUMN_ENGLISHNAME, "Person测试英文名1");
		parameters.put(COLUMN_GENDER, "0");
//		parameters.put(COLUMN_OCCUPATION, "人事部门经理");
//		parameters.put(COLUMN_ISWORKING, "1");
		Person Person = PersonDao.insertPerson(dataBaseDao, parameters);
		if(Person!=null){
			System.out.println(Person.toJsonString());
		}*/
		
		
/*		//删除
		parameters.put("id", "13");
		boolean result = PersonDao.deletePerson(dataBaseDao, parameters);*/
		
		
		//查询
//		parameters.put(COLUMN_ID, "1");
		ArrayList<Person> arrayList = PersonDao.queryPerson(dataBaseDao, parameters);
		int size = arrayList.size();
		for(int i = 0;i<size;i++){
			System.out.println(arrayList.get(i).toJsonString());
		}
		
		
/*		//修改
		parameters.put(Constant_Column.COLUMN_ID, "5");
		HashMap<String, String> values = new HashMap<String, String>();
		values.put(Constant_Column.COLUMN_ENGLISHNAME, "修改后的英文名");
		boolean result = PersonDao.updatePerson(dataBaseDao, parameters, values);*/
	}
}
