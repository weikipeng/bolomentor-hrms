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

import com.unknown.wiki.bean.Company;
import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.dao.CompanyDao;
import com.unknown.wiki.dao.ContactDao;
import com.unknown.wiki.dao.DataBaseDao;
import com.unknown.wiki.dao.HRDao;
import com.unknown.wiki.dao.LoginDao;
import com.unknown.wiki.dao.RecordDao;
import com.unknown.wiki.tool.TimeUtil;
import com.unknown.wiki.w_enum.ContactType;
import com.unknown.wiki.w_enum.RecordType;
import com.unknown.wiki.w_enum.Visible;

/**
 * Servlet implementation class CompanyServer
 */
@WebServlet(description = "company server", urlPatterns = { "/company" })
public class CompanyServer extends HttpServlet implements Constant_Servlet,Constant_Table,Constant_Column{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyServer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
//		String action = request.getParameter(ACTION);
		response.setContentType("application/json;charset=utf-8");
//		response.setContentType("text/html;charset=utf-8");
//		response.setHeader("pragma", "no-cache");
//		response.setHeader("cache-control", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter printWriter = response.getWriter();
		
/*		HashMap<String, String> parameters = new HashMap<String, String>();
		Map<String,String[]> requestMap = request.getParameterMap();
		Iterator<Entry<String,String[]>> iterator =  requestMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, String[]> entry = iterator.next();
			String key = entry.getKey();
			String[] value = entry.getValue();
				int len = value.length;
				for(int i=0;i<len;i++){
					System.out.println(key + " ---- " + new String(value[i].getBytes("ISO-8859-1"),"utf-8"));
					System.out.println(key + " ---- " + value[i]);
				}
			if(!ACTION.equals(key)){
				parameters.put(key, new String(value[0].getBytes("ISO-8859-1"),"utf-8"));
			}
		}
		
		printWriter.print("{\"name\":\"我ai520\"}");
*/
		
		

/*		StringBuffer sb = new StringBuffer();
		String reqJson = null;
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		BufferedReader bufferedReader = request.getReader();
		while((reqJson = bufferedReader.readLine())!=null){
			sb.append(reqJson);
		}
		
		System.out.println("sb --->> "+sb.toString());
		System.out.println("------ --->> "+request.getParameter(Constant_Table.TABLE_COMPANY));
		System.out.println("getQueryString------ --->> "+request.getQueryString());
		
//		JSONObject tJsonObject = JSONObject.fromObject(sb.toString());
//		System.out.println("tJsonObject------>>"+tJsonObject.toString());
*/		
		
//		System.out.println("getQueryString------ --->> "+request.getQueryString());
//		JSONObject tJsonObject = JSONObject.fromObject(request.getParameter(Constant_Table.TABLE_COMPANY).toString());
//		System.out.println("tJsonObject------>>"+tJsonObject.toString());
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		Map<String,String[]> requestMap = request.getParameterMap();
		Iterator<Entry<String,String[]>> iterator =  requestMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, String[]> entry = iterator.next();
			String key = entry.getKey();
			String[] value = entry.getValue();
///*				int len = value.length;
//				for(int i=0;i<len;i++){
//					System.out.println(key + " ---- " + new String(value[i].getBytes("ISO-8859-1"),"utf-8"));
//				}*/
				int len = value.length;
				for(int i=0;i<len;i++){
					System.out.println(key + " ---- " + value[i]);
				}
			if(!ACTION.equals(key)){
//				parameters.put(key, new String(value[0].getBytes("ISO-8859-1"),"utf-8"));
				parameters.put(key, value[0]);
			}
		}
//		Map<String,String[]> requestMap = request.getParameterMap();
		
//		JSONObject parameters = requestMap.containsKey(DATA) ? JSONObject.fromObject(requestMap.get(DATA)[0]):null;
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
			addCompany(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_DELETE.equals(action)){
			deleteCompany(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_QUERY.equals(action)){
			queryCompany(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_SINGLE.equals(action)){
			singleCompany(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_UPDATE.equals(action)){
			updateCompany(dataBaseDao,user,parameters,resultObject);
		}
		dataBaseDao.close();
		return resultObject;
	}

	private void singleCompany(DataBaseDao dataBaseDao, W_User user,JSONObject parameters, JSONObject resultObject) {
		JSONObject companyObject = parameters.getJSONObject(TABLE_COMPANY);
		
		if(companyObject != null){
			HashMap<String, String> queryMap = new HashMap<String, String>();
			String name = companyObject.optString(COLUMN_NAME, null);
			if(name == null || name.length() <=0){
				name = companyObject.optString(COLUMN_ENGLISHNAME,null);
				queryMap.put(COLUMN_ENGLISHNAME, name);
			}else{
				queryMap.put(COLUMN_NAME, name);
			}
			queryMap.put(COLUMN_VISIBLE, String.valueOf(Visible.VISIBLE.ordinal()));
			
			ArrayList<Company> companyList = CompanyDao.queryCompany(dataBaseDao,queryMap);
			int size = companyList.size();
			if(size >0){
				resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
				resultObject.put(KEY_MESSAGE,"该客户已经存在");
			}else{
				resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
				resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
			}
		}else{
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE,"传入参数错误！");
		}
	}

	private void queryCompany(DataBaseDao dataBaseDao, W_User user,JSONObject parameters, JSONObject resultObject) {
//		JSONObject userObject = LoginDao.getUserObject(requestMap);
		JSONArray jsonArray = new JSONArray();
		ArrayList<Company> arrayList = CompanyDao.queryCompanyGrant(dataBaseDao,user,parameters);
		int size = arrayList.size();
		HashMap<String , String> hrMeters = new HashMap<String, String>();
		HashMap<String , String> contactMeters = new HashMap<String, String>();
		HashMap<String , String> recordMeters = new HashMap<String, String>();
		for(int i=0;i<size;i++){
			Company company = arrayList.get(i);
			hrMeters.clear();
			contactMeters.clear();
			recordMeters.clear();
			
			//HR
			hrMeters.put(COLUMN_COMPANYID, String.valueOf(company.getId()));
			hrMeters.put(COLUMN_VISIBLE, String.valueOf(Visible.VISIBLE.ordinal()));
			company.setHrList(HRDao.queryHR(dataBaseDao, hrMeters));
			
			//Contact
			contactMeters.put(COLUMN_TYPE, String.valueOf(ContactType.COMPANY.ordinal()));
			contactMeters.put(COLUMN_TYPEID, String.valueOf(company.getId()));
			contactMeters.put(COLUMN_VISIBLE, String.valueOf(Visible.VISIBLE.ordinal()));
			company.setContactList(ContactDao.queryContact(dataBaseDao, contactMeters));
			
			//Record
			recordMeters.put(COLUMN_COMPANYID, String.valueOf(company.getId()));
			recordMeters.put(COLUMN_VISIBLE, String.valueOf(Visible.VISIBLE.ordinal()));
			recordMeters.put(COLUMN_TYPE, String.valueOf(RecordType.HISTORY.ordinal()));
			company.setRecordList(RecordDao.queryRecord(dataBaseDao, recordMeters));

			recordMeters.put(COLUMN_TYPE, String.valueOf(RecordType.PLAN.ordinal()));
			company.setRecordPlanList(RecordDao.queryRecord(dataBaseDao, recordMeters));

			if(ROLE_ADMIN_VALUE == user.getRole()){
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
			}
			
			jsonArray.add(company.toJsonString());
		}
		resultObject.put(Constant_Table.TABLE_COMPANY, jsonArray);
		resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
		resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
	}

	private void deleteCompany(DataBaseDao dataBaseDao, W_User user,JSONObject parameters, JSONObject resultObject) {
		JSONObject companyObject = parameters.getJSONObject(TABLE_COMPANY);
		
		if(companyObject != null){
			boolean isSuccess = CompanyDao.deleteCompanyJson(user,dataBaseDao,companyObject);
			if(isSuccess){
				resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
				resultObject.put(KEY_MESSAGE,"删除客户信息成功！");
			}else{
				resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
				resultObject.put(KEY_MESSAGE,"删除客户信息失败！");
			}
		}else{
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE,"传入参数错误！");
		}
	}

	private void addCompany(DataBaseDao dataBaseDao,W_User user,JSONObject parameters,JSONObject resultObject) {
		
////	Company company = CompanyDao.insertCompany(dataBaseDao, parameters);
//	Company company = null;
//	
//	if(company!=null){
////		resp.setContentType("application/json; charset=utf-8");
////		resp.setHeader("pragma", "no-cache");
////		resp.setHeader("cache-control", "no-cache");
//
////		printWriter.write(company.toJsonString());
//		resultObject.put(KEY_RESULT, RESULT_SUCCESS);
//		resultObject.put(KEY_MESSAGE, "添加公司成功！");
//	}else{
//		resultObject.put(KEY_RESULT, RESULT_FAILED);
//		resultObject.put(KEY_MESSAGE, "添加公司失败！");
//	}
//
//	
//	
////	new String(request.getParameter("username").getBytes("ISO-8859-1"),"utf-8");
		
		
//	JSONObject userObject = LoginDao.getUserObject(requestMap);
//	JSONArray jsonArray = new JSONArray();
		
		JSONObject companyObject = parameters.getJSONObject(TABLE_COMPANY);
		
		if(companyObject != null){
			HashMap<String, String> queryMap = new HashMap<String, String>();
			String name = companyObject.optString(COLUMN_NAME, null);
			if(name == null || name.length() <=0){
				name = companyObject.optString(COLUMN_ENGLISHNAME,null);
				queryMap.put(COLUMN_ENGLISHNAME, name);
			}else{
				queryMap.put(COLUMN_NAME, name);
			}
			ArrayList<Company> companyList = CompanyDao.queryCompany(dataBaseDao,queryMap);
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
				companyObject.put(COLUMN_CREATEDATE, TimeUtil.getTimeStamp());
				Company company = CompanyDao.insertCompanyJSONObject(dataBaseDao, companyObject);
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
	
	private void updateCompany(DataBaseDao dataBaseDao,W_User user,JSONObject parameters,JSONObject resultObject) {

//		JSONObject userObject = LoginDao.getUserObject(requestMap);
//		JSONArray jsonArray = new JSONArray();
		
		JSONObject queryObject = new JSONObject();
		
		JSONObject companyObject = parameters;
		companyObject.put(COLUMN_UPDATEUSERID, user.getId());
		companyObject.put(COLUMN_UPDATEDATE, TimeUtil.getTimeStamp());
		
		if(companyObject != null){
			queryObject.put(COLUMN_ID, companyObject.optLong(COLUMN_ID, -1));
			ArrayList<Company> arrayList = CompanyDao.queryCompanyGrant(dataBaseDao,user,queryObject);
			if(arrayList.size()>0){
				if(companyObject.containsKey(TABLE_HR)){
					JSONArray hrArray = companyObject.getJSONArray(TABLE_HR);
					companyObject.remove(TABLE_HR);
					
					for(int i=0;i<hrArray.size();i++){
						HRDao.InsertOrUpdateHR(dataBaseDao, hrArray.optJSONObject(i));
					}
					
					System.out.println("											");
					System.out.println("hrArray------"+hrArray.toString());
				}
				
				if(companyObject.containsKey(TABLE_RECORD)){
					JSONArray recordArray = companyObject.getJSONArray(TABLE_RECORD);
					companyObject.remove(TABLE_RECORD);
					for(int i=0;i<recordArray.size();i++){
						RecordDao.insertOrUpdateRecord(dataBaseDao, recordArray.optJSONObject(i));
					}
				}
				
				if(companyObject.containsKey(TABLE_RECORDPLAN)){
					JSONArray recordPlanArray = companyObject.getJSONArray(TABLE_RECORDPLAN);
					companyObject.remove(TABLE_RECORDPLAN);
					for(int i=0;i<recordPlanArray.size();i++){
						RecordDao.insertOrUpdateRecord(dataBaseDao, recordPlanArray.optJSONObject(i));
					}
				}
				
				if(companyObject.containsKey(TABLE_CONTACT)){
					JSONArray contactArray = companyObject.getJSONArray(TABLE_CONTACT);
					companyObject.remove(TABLE_CONTACT);
					for(int i=0;i<contactArray.size();i++){
						ContactDao.insertOrUpdateContact(dataBaseDao, contactArray.optJSONObject(i));
					}
				}
				
				boolean result = CompanyDao.updateCompanyJSON(dataBaseDao, companyObject);
				if(result){
					resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
					resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
				}else{
					resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
					resultObject.put(KEY_MESSAGE,"更新客户信息失败");
				}
				
			}else{
				resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
				resultObject.put(KEY_MESSAGE,"没有存在该客户的信息");
			}
		}else{
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE,"传入参数错误！");
		}
		
		
//		int size = arrayList.size();
//		HashMap<String , String> hrMeters = new HashMap<String, String>();
//		HashMap<String , String> contactMeters = new HashMap<String, String>();
//		for(int i=0;i<size;i++){
//			Company company = arrayList.get(i);
//			hrMeters.clear();
//			contactMeters.clear();
//			hrMeters.put(Constant_Column.COLUMN_COMPANYID, String.valueOf(company.getId()));
//			company.setHrList(HRDao.queryHR(dataBaseDao, hrMeters));
//			contactMeters.put(Constant_Column.COLUMN_TYPE, String.valueOf(ContactType.COMPANY.ordinal()));
//			contactMeters.put(Constant_Column.COLUMN_TYPEID, String.valueOf(company.getId()));
//			company.setContactList(ContactDao.queryContact(dataBaseDao, contactMeters));
//			
//			jsonArray.add(company.toJsonString());
//		}
//		resultObject.put(Constant_Table.TABLE_COMPANY, jsonArray);
//		resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
//		resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
//	}else if(null ==action || "".equals(action)){
//		System.out.println("------执行新接口------");
//		JSONObject dataObject = requestMap.containsKey(DATA) ? JSONObject.fromObject(requestMap.get(DATA)[0]):null;
//		System.out.println("dataObject----->"+dataObject.toString());
//		JSONObject companyObject = dataObject.optJSONObject(Constant_Table.TABLE_COMPANY);
//		System.out.println("companyObject----->"+companyObject.toString());
//		if(companyObject!=null){
//			action = companyObject.optString(ACTION);
//			System.out.println("action------执行新接口------"+action);
//			if(ACTION_ADD.equals(action)){
//				
//			}else if(ACTION_DELETE.equals(action)){
//				
//			}else if(ACTION_QUERY.equals(action)){
//				
//			}else if(ACTION_UPDATE.equals(action)){
//				String telephone = companyObject.optString(Constant_Column.COLUMN_TELEPHONE);
//				boolean isSuccess = false;
//				if(telephone!=null){
//					isSuccess = ContactDao.insertOrUpdateContactOld(dataBaseDao,companyObject);
//				}
//				if(isSuccess){
//					isSuccess = CompanyDao.updateCompany(dataBaseDao, companyObject);
//				}
//				resultObject.put(KEY_RESULT, isSuccess?RESULT_SUCCESS:RESULT_FAILED);
//				resultObject.put(KEY_MESSAGE, isSuccess?"更新公司信息成功！":"更新公司信息失败！");
//				
//			}
//		}
	}
}
