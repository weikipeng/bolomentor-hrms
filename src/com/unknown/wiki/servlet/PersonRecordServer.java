package com.unknown.wiki.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.dao.DataBaseDao;
import com.unknown.wiki.dao.LoginDao;
import com.unknown.wiki.dao.PersonRecordDao;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class PersonRecordServer
 */
@WebServlet(description = "person record server", urlPatterns = { "/person_record" })
public class PersonRecordServer extends HttpServlet implements Constant_Column,Constant_Table,Constant_Servlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonRecordServer() {
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
		// TODO Auto-generated method stub
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
			
		}else if(ACTION_DELETE.equals(action)){
			deleteRecord(dataBaseDao,user,parameters,resultObject);
		}else if(ACTION_QUERY.equals(action)){
			
		}else if(ACTION_SINGLE.equals(action)){
			
		}else if(ACTION_UPDATE.equals(action)){
			
		}
		dataBaseDao.close();
		return resultObject;
	}

	private void deleteRecord(DataBaseDao dataBaseDao, W_User user,JSONObject parameters, JSONObject resultObject) {
		if(parameters != null){
			boolean isSuccess = PersonRecordDao.deleteRecordJSON(user,dataBaseDao,parameters);
			if(isSuccess){
				resultObject.put(KEY_STATUS, RESULT_CODE_SUCCESS);
				resultObject.put(KEY_MESSAGE,"删除记录成功！");
			}else{
				resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
				resultObject.put(KEY_MESSAGE,"删除记录失败！");
			}
		}else{
			resultObject.put(KEY_STATUS, RESULT_CODE_FAILED);
			resultObject.put(KEY_MESSAGE,"传入参数错误！");
		}
	}

}
