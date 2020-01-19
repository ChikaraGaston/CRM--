package com.jr.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jr.DBTool;
import com.jr.model.Client;

/**
 * Servlet implementation class ClientServlet
 */
@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//��ѯ���еĿͻ���Ϣ
		String sql="select * from pei_client";
		
		List<Client> list= DBTool.executeQueryWithSqlAndParams(sql,null,new Client());
		
//		System.out.println(list);
		
		//����ѯ���ݷ���session
		HttpSession session = request.getSession();
		session.setAttribute("list",list);
		
		//ҳ����ת
		response.sendRedirect("list.jsp");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
