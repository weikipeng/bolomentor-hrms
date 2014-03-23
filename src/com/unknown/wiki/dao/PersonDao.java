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

import com.unknown.wiki.bean.Person;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_SQL;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.w_enum.Gender;

public class PersonDao implements Constant_Column,Constant_SQL{

	
	/**插入Person*/
	public static Person insertPerson(DataBaseDao dataBaseDao,Map<String, String> parameters){
		Person person = null;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("insert into ");
			sb.append(Constant_Table.TABLE_PERSON);
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
	
	/**删除公司*/
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
	public static ArrayList<Person> queryPerson(DataBaseDao dataBaseDao,Map<String, String> parameters){
		ArrayList<Person> result = new ArrayList<Person>();
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ");
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
					result.add(formatPerson(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**更新公司*/
	public static boolean updatePerson(DataBaseDao dataBaseDao,Map<String, String> parameters,Map<String, String> values){
		boolean isSuccess = false;
		if(dataBaseDao != null){
			Connection connection = dataBaseDao.getConnection();
			
			//插入
			StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Constant_Table.TABLE_PERSON);
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
	
	private static Person formatPerson(ResultSet resultSet) throws SQLException {
		Person person = new Person();
		person.setId(resultSet.getLong(COLUMN_ID));
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
		person.setCompanyNature(resultSet.getString(COLUMN_COMPANYNATURE));
		person.setWorkStatus(resultSet.getString(COLUMN_WORKSTATUS));
		person.setHopeAddress(resultSet.getString(COLUMN_HOPEADDRESS));
		person.setHopeJob(resultSet.getString(COLUMN_HOPEJOB));
		person.setHopeVocation(resultSet.getString(COLUMN_HOPEVOCATION));
		person.setHopeSalary(resultSet.getString(COLUMN_HOPESALARY));
		person.setHope(resultSet.getInt(COLUMN_ISHOPE) == 1);
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
