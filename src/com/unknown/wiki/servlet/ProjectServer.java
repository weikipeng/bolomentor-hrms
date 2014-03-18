package com.unknown.wiki.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProjectServer
 */
@WebServlet(
		description = "project action", 
		urlPatterns = { "/project" }, 
		initParams = { 
				@WebInitParam(name = "testName", value = "testValue", description = "testDescription")
		})
public class ProjectServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String requestUri = request.getRequestURI();
		
		response.setContentType("text/html");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
//				out.println("<!DOCTYPE html>");
//				out.println("<html>");
//				out.println("<head>");
//				out.println("<title>Hello world</title>");
//				out.println("</head>");
//				out.println("<body>");
//				out.println("Hello World from frisrt servlet");
//				
//				out.println("<form action='" + requestUri + "' method='post'>");
//				out.println("<input type='text' name='name' />");
//				out.println("<input type='submit' value='submit' />");
//				out.println("</form>");
//				
//				out.println("</body>");
//				out.println("</html>");
		
		/*
				DataBaseDao dataBaseDao = new DataBaseDao();
				String sql = "select * from user";
				Connection connection = dataBaseDao.getConnection();
				String name = "";
				String pass = "";
				try {
					PreparedStatement preparedStatement =  (PreparedStatement) connection.prepareStatement(sql);
					ResultSet resultSet =  preparedStatement.executeQuery();
//					while (!resultSet.isLast()) {
					while (resultSet.next()) {
//						resultSet.next();
						name = resultSet.getString(2);
						pass = resultSet.getString(3);   
//						System.out.println("name -- " + name +"   pass -- "+pass);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				out.print(name);
				out.print("---"+pass);
				
				dataBaseDao.close();*/
		
		out.print("getB");
		
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
