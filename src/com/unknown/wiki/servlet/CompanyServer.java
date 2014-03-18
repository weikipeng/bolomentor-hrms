package com.unknown.wiki.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import org.apache.tomcat.util.http.fileupload.IOUtils;

import sun.swing.MenuItemLayoutHelper.ColumnAlignment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.unknown.wiki.bean.Company;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.dao.CompanyDao;
import com.unknown.wiki.dao.ContactDao;
import com.unknown.wiki.dao.DataBaseDao;
import com.unknown.wiki.dao.HRDao;
import com.unknown.wiki.dao.LoginDao;
import com.unknown.wiki.w_enum.ContactInfoType;
import com.unknown.wiki.w_enum.ContactType;

/**
 * Servlet implementation class CompanyServer
 */
@WebServlet(description = "company server", urlPatterns = { "/company" })
public class CompanyServer extends HttpServlet implements Constant_Servlet{
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
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
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
		boolean isLogin = LoginDao.isLogin(requestMap);
		if(isLogin){
			resultObject = doActions(requestMap);
		}else{
			resultObject = new JSONObject();
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE, "请登录！");			
		}
		
		printWriter.write(resultObject.toString());

		printWriter.flush();
		printWriter.close();
	}

	private JSONObject doActions(Map<String, String[]> requestMap) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		JSONObject resultObject = new JSONObject();
		String action = requestMap.containsKey(ACTION) ? requestMap.get(ACTION)[0]:null;
		System.out.println("action ------"+action+"------");
		JSONObject parameters = requestMap.containsKey(ACTION_DATA) ? JSONObject.fromObject(requestMap.get(ACTION_DATA)[0]):new JSONObject();
		
		if(ACTION_ADD.equals(action)){
//			Company company = CompanyDao.insertCompany(dataBaseDao, parameters);
			Company company = null;
			
			if(company!=null){
//				resp.setContentType("application/json; charset=utf-8");
//				resp.setHeader("pragma", "no-cache");
//				resp.setHeader("cache-control", "no-cache");

//				printWriter.write(company.toJsonString());
				resultObject.put(KEY_RESULT, RESULT_SUCCESS);
				resultObject.put(KEY_MESSAGE, "添加公司成功！");
			}else{
				resultObject.put(KEY_RESULT, RESULT_FAILED);
				resultObject.put(KEY_MESSAGE, "添加公司失败！");
			}

			
			
//			new String(request.getParameter("username").getBytes("ISO-8859-1"),"utf-8");
			
		}else if(ACTION_DELETE.equals(action)){
			boolean isSuccess = CompanyDao.deleteCompany(dataBaseDao, parameters);
			resultObject.put(KEY_RESULT, isSuccess?RESULT_SUCCESS:RESULT_FAILED);
			resultObject.put(KEY_MESSAGE, isSuccess?"删除公司成功！":"删除公司失败！");
			
		}else if(ACTION_QUERY.equals(action)){
			JSONObject userObject = LoginDao.getUserObject(requestMap);
			JSONArray jsonArray = new JSONArray();
			ArrayList<Company> arrayList = CompanyDao.queryCompany(dataBaseDao,userObject,parameters);
			int size = arrayList.size();
			HashMap<String , String> hrMeters = new HashMap<String, String>();
			HashMap<String , String> contactMeters = new HashMap<String, String>();
			for(int i=0;i<size;i++){
				Company company = arrayList.get(i);
				hrMeters.clear();
				contactMeters.clear();
				hrMeters.put(Constant_Column.COLUMN_COMPANYID, String.valueOf(company.getId()));
				company.setHrList(HRDao.queryHR(dataBaseDao, hrMeters));
				contactMeters.put(Constant_Column.COLUMN_TYPE, String.valueOf(ContactType.COMPANY.ordinal()));
				contactMeters.put(Constant_Column.COLUMN_TYPEID, String.valueOf(company.getId()));
				company.setContactList(ContactDao.queryContact(dataBaseDao, contactMeters));
				
				jsonArray.add(company.toJsonString());
			}
			resultObject.put(Constant_Table.TABLE_COMPANY, jsonArray);
			resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
			resultObject.put(KEY_MESSAGE,RESULT_SUCCESS);
		}else if(ACTION_UPDATE.equals(action)){
//			boolean isSuccess = CompanyDao.updateCompany(dataBaseDao, parameters);
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put(KEY_RESULT, isSuccess?RESULT_SUCCESS:RESULT_FAILED);
//			jsonObject.put(KEY_MESSAGE, isSuccess?"删除公司成功！":"删除公司失败！");
		}else if(null ==action || "".equals(action)){
			System.out.println("------执行新接口------");
			JSONObject dataObject = requestMap.containsKey(DATA) ? JSONObject.fromObject(requestMap.get(DATA)[0]):null;
			System.out.println("dataObject----->"+dataObject.toString());
			JSONObject companyObject = dataObject.optJSONObject(Constant_Table.TABLE_COMPANY);
			System.out.println("companyObject----->"+companyObject.toString());
			if(companyObject!=null){
				action = companyObject.optString(ACTION);
				System.out.println("action------执行新接口------"+action);
				if(ACTION_ADD.equals(action)){
					
				}else if(ACTION_DELETE.equals(action)){
					
				}else if(ACTION_QUERY.equals(action)){
					
				}else if(ACTION_UPDATE.equals(action)){
					String telephone = companyObject.optString(Constant_Column.COLUMN_TELEPHONE);
					boolean isSuccess = false;
					if(telephone!=null){
						isSuccess = ContactDao.insertOrUpdateContact(dataBaseDao,companyObject);
					}
					if(isSuccess){
						isSuccess = CompanyDao.updateCompany(dataBaseDao, companyObject);
					}
					resultObject.put(KEY_RESULT, isSuccess?RESULT_SUCCESS:RESULT_FAILED);
					resultObject.put(KEY_MESSAGE, isSuccess?"更新公司信息成功！":"更新公司信息失败！");
					
				}
			}
		}
		
		dataBaseDao.close();
		
		return resultObject;
	}

}
