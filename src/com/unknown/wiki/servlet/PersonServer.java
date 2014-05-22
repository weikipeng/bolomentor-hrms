package com.unknown.wiki.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.unknown.wiki.bean.Person;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.dao.ContactDao;
import com.unknown.wiki.dao.DataBaseDao;
import com.unknown.wiki.dao.LoginDao;
import com.unknown.wiki.dao.PersonDao;
import com.unknown.wiki.dao.PersonRecordDao;
import com.unknown.wiki.tool.TimeUtil;
import com.unknown.wiki.w_enum.ContactType;
import com.unknown.wiki.w_enum.RecordType;
import com.unknown.wiki.w_enum.Visible;

/**
 * Servlet implementation class PersonServer
 */
@WebServlet(description = "person server", urlPatterns = { "/person" })
public class PersonServer extends HttpServlet implements Constant_Servlet,Constant_Table,Constant_Column{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter printWriter = response.getWriter();
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		Map<String,String[]> requestMap = request.getParameterMap();
		Iterator<Entry<String,String[]>> iterator =  requestMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, String[]> entry = iterator.next();
			String key = entry.getKey();
			String[] value = entry.getValue();
				int len = value.length;
				for(int i=0;i<len;i++){
					System.out.println(key + " ---- " + value[i]);
				}
			if(!ACTION.equals(key)){
				parameters.put(key, value[0]);
			}
		}
		JSONObject resultObject = null;
		
		resultObject = doActions(requestMap);
		
		System.out.println("返回------>"+resultObject.toString());
		
		printWriter.write(resultObject.toString());

		printWriter.flush();
		printWriter.close();
	
	}

	
	private JSONObject doActions(Map<String, String[]> requestMap) {
		JSONObject resultObject = new JSONObject();
		DataBaseDao dataBaseDao = new DataBaseDao();
		W_User user = LoginDao.isLogin(dataBaseDao,requestMap);
		if(user == null){
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE, "请登录！");
			return resultObject;
		}
		
		String action = requestMap.containsKey(ACTION) ? requestMap.get(ACTION)[0]:null;
		System.out.println("action ------"+action+"------");
		JSONObject parameters = requestMap.containsKey(ACTION_DATA) ? JSONObject.fromObject(requestMap.get(ACTION_DATA)[0]):new JSONObject();
		
		if(ACTION_ADD.equals(action)){
			addPerson(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_DELETE.equals(action)){
			deletePerson(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_QUERY.equals(action)){
			queryPerson(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_SINGLE.equals(action)){
			singlePerson(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_UPDATE.equals(action)){
			updatePerson(dataBaseDao,user,parameters,resultObject);
		}
		dataBaseDao.close();
		return resultObject;
	}
	
	private void addPerson(DataBaseDao dataBaseDao,W_User user,JSONObject parameters,JSONObject resultObject) {
		
		JSONObject personObject = parameters;
		
		if(personObject != null){
			HashMap<String, String> queryMap = new HashMap<String, String>();
			String name = personObject.optString(COLUMN_NAME, null);
			if(name == null || name.length() <=0){
				name = personObject.optString(COLUMN_ENGLISHNAME,null);
				queryMap.put(COLUMN_ENGLISHNAME, name);
			}else{
				queryMap.put(COLUMN_NAME, name);
			}
			queryMap.put(COLUMN_VISIBLE, String.valueOf(Visible.VISIBLE.ordinal()));
			ArrayList<Person> personList = PersonDao.queryPerson(dataBaseDao,queryMap);
			if(personList.size()<=0){
				JSONArray contactArray = null;
				if(personObject.containsKey(TABLE_CONTACT)){
					contactArray = personObject.getJSONArray(TABLE_CONTACT);
					personObject.remove(TABLE_CONTACT);
				}
				
				JSONArray recordArray = null;
				if(personObject.containsKey(TABLE_PERSON_RECORD)){
					recordArray = personObject.getJSONArray(TABLE_PERSON_RECORD);
					personObject.remove(TABLE_PERSON_RECORD);
				}
				
				
				personObject.put(COLUMN_CREATEUSERID, user.getId());
				personObject.put(COLUMN_UPDATEUSERID, user.getId());
				String timeStamp = TimeUtil.getTimeStamp();
				personObject.put(COLUMN_CREATEDATE, timeStamp);
				personObject.put(COLUMN_UPDATEDATE, timeStamp);
				Person person = PersonDao.insertPersonJSONObject(dataBaseDao, personObject);
				if(person!=null){
					//contact
					if(contactArray!=null && contactArray.size()>0){
						for(int i=0;i<contactArray.size();i++){
							JSONObject contactObject = contactArray.optJSONObject(i);
							contactObject.put(COLUMN_TYPEID, person.getId());
							ContactDao.insertOrUpdateContact(dataBaseDao, contactObject);
						}
						
						System.out.println("											");
						System.out.println("contactArray------"+contactArray.toString());
					}
					
					if(recordArray!=null){
						for(int i=0;i<recordArray.size();i++){
							JSONObject recordObject =  recordArray.optJSONObject(i);
							recordObject.put(COLUMN_PERSONID, person.getId());
							PersonRecordDao.insertOrUpdateRecord(dataBaseDao,recordObject);
						}
					}
					
					resultObject.put(TABLE_PERSON, person.toJsonString());
					resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
					resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
				}else{
					resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
					resultObject.put(KEY_MESSAGE,"传入参数错误！");
				}
			}else{
				resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
				resultObject.put(KEY_MESSAGE,"已经存在该人才！");
			}
			
			
		}else{
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE,"传入参数错误！");
		}
	}

	private void singlePerson(DataBaseDao dataBaseDao, W_User user,JSONObject parameters, JSONObject resultObject) {
		JSONObject personObject = parameters;
		
		if(personObject != null){
			HashMap<String, String> queryMap = new HashMap<String, String>();
			String name = personObject.optString(COLUMN_NAME, null);
			if(name == null || name.length() <=0){
				name = personObject.optString(COLUMN_ENGLISHNAME,null);
				queryMap.put(COLUMN_ENGLISHNAME, name);
			}else{
				queryMap.put(COLUMN_NAME, name);
			}
			queryMap.put(COLUMN_VISIBLE, String.valueOf(Visible.VISIBLE.ordinal()));
			
			ArrayList<Person> personList = PersonDao.queryPerson(dataBaseDao,queryMap);
			int size = personList.size();
			if(size >0){
				resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
				resultObject.put(KEY_MESSAGE,"该人才已经存在");
			}else{
				resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
				resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
			}
		}else{
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE,"传入参数错误！");
		}
	}

	private void queryPerson(DataBaseDao dataBaseDao, W_User user,JSONObject parameters, JSONObject resultObject) {
		JSONArray jsonArray = new JSONArray();
//		ArrayList<Person> arrayList = PersonDao.queryPersonGrant(dataBaseDao,user,parameters);
		ArrayList<Person> arrayList = PersonDao.queryPersonJSON(dataBaseDao,parameters);
		int size = arrayList.size();
		HashMap<String , String> contactMeters = new HashMap<String, String>();
		HashMap<String , String> recordMeters = new HashMap<String, String>();
		for(int i=0;i<size;i++){
			Person person = arrayList.get(i);
			contactMeters.clear();
			recordMeters.clear();
			
			//Contact
			contactMeters.put(COLUMN_TYPE, String.valueOf(ContactType.PERSON.ordinal()));
			contactMeters.put(COLUMN_TYPEID, String.valueOf(person.getId()));
			contactMeters.put(COLUMN_VISIBLE, String.valueOf(Visible.VISIBLE.ordinal()));
			person.setContactList(ContactDao.queryContact(dataBaseDao, contactMeters));
			
			//Record
			recordMeters.put(COLUMN_PERSONID, String.valueOf(person.getId()));
			recordMeters.put(COLUMN_VISIBLE, String.valueOf(Visible.VISIBLE.ordinal()));
			person.setRecordList(PersonRecordDao.queryPersonRecord(dataBaseDao, recordMeters));
			
			jsonArray.add(person.toJsonString());
		}
		
//		if(ROLE_ADMIN_VALUE == user.getRole()){
			System.out.println("												");
			System.out.println("ROLE_ADMIN_VALUE												"+ROLE_ADMIN_VALUE);
			System.out.println("user.getRole()												"+user.getRole());
			ArrayList<W_User> userList = LoginDao.queryUser(dataBaseDao,user);
			JSONArray userArray = new JSONArray();
			if(userList.size()>0){
				int tSize = userList.size();
				for(int j=0;j<tSize;j++){
					userArray.add(userList.get(j).toJsonObject());
				}
			}
			resultObject.put(TABLE_USER,userArray);
//		}
		
		resultObject.put(Constant_Table.TABLE_PERSON, jsonArray);
		resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
		resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
	}
	
	private void deletePerson(DataBaseDao dataBaseDao, W_User user,JSONObject parameters, JSONObject resultObject) {
		JSONObject personObject = parameters.getJSONObject(TABLE_PERSON);
		
		if(personObject != null){
			boolean isSuccess = PersonDao.deletePersonJson(user,dataBaseDao,personObject);
			if(isSuccess){
				resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
				resultObject.put(KEY_MESSAGE,"删除人才信息成功！");
			}else{
				resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
				resultObject.put(KEY_MESSAGE,"删除人才信息失败！");
			}
		}else{
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE,"传入参数错误！");
		}
	}
	
	private void updatePerson(DataBaseDao dataBaseDao,W_User user,JSONObject parameters,JSONObject resultObject) {
		JSONObject queryObject = new JSONObject();
		
		JSONObject personObject = parameters;
		personObject.put(COLUMN_UPDATEUSERID, user.getId());
		personObject.put(COLUMN_UPDATEDATE, TimeUtil.getTimeStamp());
		
		if(personObject != null){
			queryObject.put(COLUMN_ID, personObject.optLong(COLUMN_ID, -1));
			ArrayList<Person> arrayList = PersonDao.queryPersonGrant(dataBaseDao,user,queryObject);
			if(arrayList.size()>0){
				if(personObject.containsKey(TABLE_PERSON_RECORD)){
					JSONArray recordArray = personObject.getJSONArray(TABLE_PERSON_RECORD);
					personObject.remove(TABLE_PERSON_RECORD);
					for(int i=0;i<recordArray.size();i++){
						PersonRecordDao.insertOrUpdateRecord(dataBaseDao, recordArray.optJSONObject(i));
					}
				}
				
				if(personObject.containsKey(TABLE_CONTACT)){
					JSONArray contactArray = personObject.getJSONArray(TABLE_CONTACT);
					personObject.remove(TABLE_CONTACT);
					for(int i=0;i<contactArray.size();i++){
						ContactDao.insertOrUpdateContact(dataBaseDao, contactArray.optJSONObject(i));
					}
				}
				
				boolean result = PersonDao.updatePersonJSON(dataBaseDao, personObject);
				if(result){
					resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
					resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
				}else{
					resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
					resultObject.put(KEY_MESSAGE,"更新人才信息失败");
				}
				
			}else{
				resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
				resultObject.put(KEY_MESSAGE,"没有存在该人才的信息");
			}
		}else{
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE,"传入参数错误！");
		}
	}
}
