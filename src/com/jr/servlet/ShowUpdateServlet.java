package com.jr.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jr.DBTool;
import com.jr.model.Client;


@WebServlet("/ShowUpdateServlet")
public class ShowUpdateServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int id=Integer.valueOf(request.getParameter("id")) ;
		
		String sql="select * from pei_client where id=?";
		
		List<Object> params=new ArrayList<Object>();
		params.add(id);

       List<Client> list=DBTool.executeQueryWithSqlAndParams(sql, params, new Client());
       
       
       //��ShowUpdateServlet ��ShowUpdatejsp��ֵ
       request.setAttribute("c", list.get(0));
       
       //����ת��
       request.getRequestDispatcher("update.jsp").forward(request, response);;
       
       
//       response.sendRedirect("");
       
		
		
	
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
