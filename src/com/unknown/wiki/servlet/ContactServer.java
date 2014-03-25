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

import com.unknown.wiki.bean.Contact;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.dao.ContactDao;
import com.unknown.wiki.dao.DataBaseDao;

/**
 * Servlet implementation class ContactServer
 */
@WebServlet(description = "Contact server", urlPatterns = { "/contact" })
public class ContactServer extends HttpServlet implements Constant_Servlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactServer() {
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
		String action = request.getParameter(ACTION);
		
		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("pragma", "no-cache");
//		response.setHeader("cache-control", "no-cache");
		PrintWriter printWriter = response.getWriter();
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		Map<String,String[]> requestMap = request.getParameterMap();
		Iterator<Entry<String,String[]>> iterator =  requestMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, String[]> entry = iterator.next();
			String key = entry.getKey();
			String[] value = entry.getValue();
//				int len = value.length;
//				for(int i=0;i<len;i++){
//					System.out.println(key + " ---- " + new String(value[i].getBytes("ISO-8859-1"),"utf-8"));
//				}
			if(!ACTION.equals(key)){
				parameters.put(key, new String(value[0].getBytes("ISO-8859-1"),"utf-8"));
			}
		}
		DataBaseDao dataBaseDao = new DataBaseDao();
		
		if(ACTION_ADD.equals(action)){
			Contact contact = ContactDao.insertContact(dataBaseDao, parameters);
			if(contact!=null){
//				resp.setContentType("application/json; charset=utf-8");
//				resp.setHeader("pragma", "no-cache");
//				resp.setHeader("cache-control", "no-cache");

				printWriter.write(contact.toJsonString());
			}
			
//			new String(request.getParameter("username").getBytes("ISO-8859-1"),"utf-8");
			
		}else if(ACTION_DELETE.equals(action)){
			boolean isSuccess = ContactDao.deleteContact(dataBaseDao, parameters);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(KEY_RESULT, isSuccess?RESULT_SUCCESS:RESULT_FAILED);
			jsonObject.put(KEY_MESSAGE, isSuccess?"删除联系方式成功！":"删除联系方式失败！");
			printWriter.write(jsonObject.toString());
		}else if(ACTION_QUERY.equals(action)){
			JSONArray jsonArray = new JSONArray();
			ArrayList<Contact> arrayList = ContactDao.queryContact(dataBaseDao, parameters);
			int size = arrayList.size();
			for(int i=0;i<size;i++){
				Contact contact = arrayList.get(i);
				jsonArray.add(contact.toJsonString());
			}
			printWriter.write(jsonArray.toString());
		}else if(ACTION_UPDATE.equals(action)){
//			boolean isSuccess = CompanyDao.updateCompany(dataBaseDao, parameters);
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put(KEY_RESULT, isSuccess?RESULT_SUCCESS:RESULT_FAILED);
//			jsonObject.put(KEY_MESSAGE, isSuccess?"删除公司成功！":"删除公司失败！");
		}
		
		dataBaseDao.close();
		printWriter.flush();
		printWriter.close();
	
	}

}
