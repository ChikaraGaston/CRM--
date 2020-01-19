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


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		//��ȡ���û���
		 String name=request.getParameter("username");
		
		 //��ȡ���û�����
		 String pwd=request.getParameter("pwd");
		 
		//��ȡ���ֻ���
		 String tel=request.getParameter("tel");
		 
		 //��ȡ������
		 String email=request.getParameter("email");
		 
		 //�������ݿ�
		 String sql="insert into pei_user (username,pwd,tel,email) values(?,?,?,?)";
		 
		 List<Object> param = new ArrayList<Object>();
		 param.add(name);
		 param.add(pwd);
		 param.add(tel);
		 param.add(email);
		 
//		 System.out.println(param);
		 
		 int num=DBTool.executeUpdateWithSqlAndParams(sql, param);
		 if(num>0){
//			 System.out.println("ע��ɹ�");
			 response.sendRedirect("LoginCheckServlet?username="+name+"&pwd="+pwd);
		 }else {
//			 System.out.println("ע��ʧ��");
			 response.sendRedirect("faild.jsp");
			 
		 }
		 
	}

}
