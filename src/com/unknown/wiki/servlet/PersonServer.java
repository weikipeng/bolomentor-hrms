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

import com.unknown.wiki.bean.Person;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.dao.PersonDao;
import com.unknown.wiki.dao.ContactDao;
import com.unknown.wiki.dao.DataBaseDao;
import com.unknown.wiki.dao.HRDao;
import com.unknown.wiki.dao.LoginDao;
import com.unknown.wiki.dao.RecordDao;
import com.unknown.wiki.tool.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
//			deletePerson(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_QUERY.equals(action)){
//			queryPerson(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_SINGLE.equals(action)){
//			singlePerson(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_UPDATE.equals(action)){
//			updatePerson(dataBaseDao,user,parameters,resultObject);
		}
		dataBaseDao.close();
		return resultObject;
	}
private void addPerson(DataBaseDao dataBaseDao,W_User user,JSONObject parameters,JSONObject resultObject) {
		
		JSONObject companyObject = parameters;
		
		if(companyObject != null){
			HashMap<String, String> queryMap = new HashMap<String, String>();
			String name = companyObject.optString(COLUMN_NAME, null);
			if(name == null || name.length() <=0){
				name = companyObject.optString(COLUMN_ENGLISHNAME,null);
				queryMap.put(COLUMN_ENGLISHNAME, name);
			}else{
				queryMap.put(COLUMN_NAME, name);
			}
			ArrayList<Person> companyList = PersonDao.queryPerson(dataBaseDao,queryMap);
			if(companyList.size()<=0){
				JSONArray hrArray = null;
				if(companyObject.containsKey(TABLE_HR)){
					hrArray = companyObject.getJSONArray(TABLE_HR);
					companyObject.remove(TABLE_HR);
				}
				
				
				JSONArray contactArray = null;
				if(companyObject.containsKey(TABLE_CONTACT)){
					contactArray = companyObject.getJSONArray(TABLE_CONTACT);
					companyObject.remove(TABLE_CONTACT);
				}
				
				JSONArray recordArray = null;
				if(companyObject.containsKey(TABLE_RECORD)){
					recordArray = companyObject.getJSONArray(TABLE_RECORD);
					companyObject.remove(TABLE_RECORD);
				}
				
				JSONArray recordPlanArray = null;
				if(companyObject.containsKey(TABLE_RECORDPLAN)){
					recordPlanArray = companyObject.getJSONArray(TABLE_RECORDPLAN);
					companyObject.remove(TABLE_RECORDPLAN);
				}
				
				
				companyObject.put(COLUMN_CREATEUSERID, user.getId());
				companyObject.put(COLUMN_UPDATEUSERID, user.getId());
				String timeStamp = TimeUtil.getTimeStamp();
				companyObject.put(COLUMN_CREATEDATE, timeStamp);
				companyObject.put(COLUMN_UPDATEDATE, timeStamp);
				Person company = PersonDao.insertPersonJSONObject(dataBaseDao, companyObject);
				if(company!=null){
					//HR
					if(hrArray!=null && hrArray.size() > 0){
						for(int i=0;i<hrArray.size();i++){
							JSONObject hrObject = hrArray.optJSONObject(i);
							hrObject.put(COLUMN_COMPANYID, company.getId());
							HRDao.InsertOrUpdateHR(dataBaseDao,hrObject);
						}
						System.out.println("											");
						System.out.println("hrArray------"+hrArray.toString());
					}
					
					//contact
					if(contactArray!=null && contactArray.size()>0){
						for(int i=0;i<contactArray.size();i++){
							JSONObject contactObject = contactArray.optJSONObject(i);
							contactObject.put(COLUMN_TYPEID, company.getId());
							ContactDao.insertOrUpdateContact(dataBaseDao, contactObject);
						}
						
						System.out.println("											");
						System.out.println("contactArray------"+contactArray.toString());
					}
					
					if(recordArray!=null){
						for(int i=0;i<recordArray.size();i++){
							JSONObject recordObject =  recordArray.optJSONObject(i);
							recordObject.put(COLUMN_COMPANYID, company.getId());
							RecordDao.insertOrUpdateRecord(dataBaseDao,recordObject);
						}
					}
					
					if(recordPlanArray!=null){
						for(int i=0;i<recordPlanArray.size();i++){
							JSONObject recordPlanObject = recordPlanArray.optJSONObject(i);
							recordPlanObject.put(COLUMN_COMPANYID, company.getId());
							RecordDao.insertOrUpdateRecord(dataBaseDao,recordPlanObject);
						}
					}
					
					resultObject.put(TABLE_COMPANY, company.toJsonString());
					resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
					resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
				}else{
					resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
					resultObject.put(KEY_MESSAGE,"传入参数错误！");
				}
			}else{
				resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
				resultObject.put(KEY_MESSAGE,"已经存在该客户！");
			}
			
			
		}else{
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE,"传入参数错误！");
		}
	}
}
