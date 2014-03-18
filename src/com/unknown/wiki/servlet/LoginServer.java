package com.unknown.wiki.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.unknown.wiki.bean.W_User;
import com.unknown.wiki.constant.Constant_Column;
import com.unknown.wiki.constant.Constant_Servlet;
import com.unknown.wiki.constant.Constant_Table;
import com.unknown.wiki.dao.DataBaseDao;
import com.unknown.wiki.dao.LoginDao;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet(
		urlPatterns = { "/login" }, 
		initParams = { 
				@WebInitParam(name = "login servlet", value = "login server")
		})
public class LoginServer extends HttpServlet implements Constant_Column,Constant_Servlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.setCharacterEncoding("UTF-8");
//		request.setCharacterEncoding("UTF-8");
//		
//		String requestUri = request.getRequestURI();
//		
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		
////		out.println("<!DOCTYPE html>");
////		out.println("<html>");
////		out.println("<head>");
////		out.println("<title>Hello world</title>");
////		out.println("</head>");
////		out.println("<body>");
////		out.println("Hello World from frisrt servlet");
////		
////		out.println("<form action='" + requestUri + "' method='post'>");
////		out.println("<input type='text' name='name' />");
////		out.println("<input type='submit' value='submit' />");
////		out.println("</form>");
////		
////		out.println("</body>");
////		out.println("</html>");
//
//		/*
//		DataBaseDao dataBaseDao = new DataBaseDao();
//		String sql = "select * from user";
//		Connection connection = dataBaseDao.getConnection();
//		String name = "";
//		String pass = "";
//		try {
//			PreparedStatement preparedStatement =  (PreparedStatement) connection.prepareStatement(sql);
//			ResultSet resultSet =  preparedStatement.executeQuery();
////			while (!resultSet.isLast()) {
//			while (resultSet.next()) {
////				resultSet.next();
//				name = resultSet.getString(2);
//				pass = resultSet.getString(3);   
////				System.out.println("name -- " + name +"   pass -- "+pass);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		out.print(name);
//		out.print("---"+pass);
//		
//		dataBaseDao.close();*/
//		
//		out.print("get ------ ");
//		
//		out.flush();
//		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		/*
//		String name = request.getParameter("name");
		Map<String,String[]> map = request.getParameterMap();
		Iterator<Entry<String, String[]>> ite = map.entrySet().iterator();
		while(ite.hasNext()){
			  Map.Entry<String, String[]> item = (Map.Entry<String, String[]>) ite.next();
			  String name = item.getKey();
			  String[] value = item.getValue();
			  System.out.println(name.toString()+"----------------"+value[0]);
		}*/
		
//		response.setContentType("text/html");
		response.setContentType("application/x-json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter(COLUMN_USERNAME);
		String password = request.getParameter(COLUMN_PASSWORD);
		
		DataBaseDao dataBaseDao = new DataBaseDao();
		
		W_User user = LoginDao.login(dataBaseDao,name, password);
		JSONObject jsonObject = new JSONObject();
		if(user == null){
			jsonObject.put(KEY_STATUS, 404);
			jsonObject.put(Constant_Table.TABLE_USER, null);
			jsonObject.put(KEY_MESSAGE, "用户名或密码错误！");
		}else{
			jsonObject.put(KEY_STATUS, 200);
			jsonObject.put(Constant_Table.TABLE_USER, user.toJsonObject());
			jsonObject.put(KEY_MESSAGE, "登录成功！");
		}
		
		dataBaseDao.close();
		
		out.print(jsonObject);
		
//		System.out.println(jsonObject);
		
//		out.println("<!DOCTYPE html>");
//		out.println("<html>");
//		out.println("<head>");
//		out.println("<title>Hello world</title>");
//		out.println("</head>");
//		out.println("<body>");
//		
//		out.println("Hello: " + name);
//		
//		out.println("</body>");
//		out.println("</html>");
		
		//out.print("测试");
		
		out.flush();
		out.close();
	}

}
