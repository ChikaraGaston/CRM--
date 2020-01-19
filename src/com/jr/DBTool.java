package com.jr;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class DBTool {
	/**
	 *  1    ����ҵ�����
	 *  2    ����ͨ����
	 *  3    ��������Է���ʹ��
	 *  4   ���Ի�����ֵ
	 * 
	 */
	
	
	//����
	private static final String DRVIER="com.mysql.jdbc.Driver";
	//���ݿ�����
	private static final String URL="jdbc:mysql://localhost:3306/wjsx";
	//�û�
	private static final String USER="root";
	//����
	private static final String PWD="123456";
	
	
	//����ȫ�ֵ����Ӷ���
	private static  Connection conn=null;
	
	
	/**
	   * �������ݿ�����
	 */
	private static void getConnection() {
		
		try {
			if(conn==null) {
				//��������
				Class.forName(DRVIER);
				
				//��������
				conn=  DriverManager.getConnection(URL,USER,PWD);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdateWithSqlAndParams(String sql,List<Object> params) {
		//���ô������ӷ���
		getConnection();
		
		
		try {
			//�������
			PreparedStatement pstmt= conn.prepareStatement(sql);
			//���ò���
			if(params!=null) {
				for(int i=0;i<params.size();i++) {
					pstmt.setObject(i+1, params.get(i));
				}
			}
			
			//ִ�����ݿ�
			int  rsult=pstmt.executeUpdate();
			return rsult;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	
	
	/**
	 * �ж������Ƿ����
	 * @param sql ������    select count(*) num
	 * @param params ����List�б�
	 * @return �������������
	 * 
	 *  
	 */
	public static boolean isExistWithSqlAndParams(String sql,List<Object> params) {
		//���ô������ӷ���
		getConnection();
		
		try {
			//�������
			PreparedStatement pstmt= conn.prepareStatement(sql);
			//���ò���
			if(params!=null) {
				for(int i=0;i<params.size();i++) {
					pstmt.setObject(i+1, params.get(i));
				}
			}
			
			//ִ�����ݿ�
			ResultSet rs=pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("num")>0;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private static ResultSet executeQuery(String sql,List<Object> params) {
		//���ô������ӷ���
		getConnection();
		
		try {
			//�������
			PreparedStatement pstmt= conn.prepareStatement(sql);
			//���ò���
			if(params!=null) {
				for(int i=0;i<params.size();i++) {
					pstmt.setObject(i+1, params.get(i));
				}
			}
			
			//ִ�����ݿ�
			ResultSet rs=pstmt.executeQuery();
			return rs;
		 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
		
		
	
	
	

	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> executeQueryWithSqlAndParams(String sql,List<Object> params,T  t) {
		//���ô������ӷ���
		getConnection();
		
		
		//ѭ�����������װ��T���б���
		ResultSet rs=executeQuery(sql, params);
		
		//����һ������
		List<T> list=new ArrayList<T>();
		
		//ͨ������Ķ��������ԭ������
		Class<?> c=t.getClass();
		
		
		try {
			//���������
			while(rs.next()) {
				
				//����һ������
				@SuppressWarnings("unchecked")
				T obj=(T)c.newInstance();
				
				//��ȡ��������������
				Field [] fields=c.getDeclaredFields();
				for(Field f:fields) {
					
					//��ȡ�������͵ļ�д java.lang.String
					String ftype=f.getType().getSimpleName();
					//��ȡ������
					String fname=f.getName();
					
					//ȫ�ֵ�value
					Object value=null;
					
					if(ftype.equalsIgnoreCase("string")) {
						  value=rs.getString(fname);
					}else if(ftype.equalsIgnoreCase("int")) {
						  value=rs.getInt(fname);
					}else if(ftype.equalsIgnoreCase("float")) {
						  value=rs.getFloat(fname);
					}else if(ftype.equalsIgnoreCase("boolean")) {
						  value=rs.getBoolean(fname);
					}else if(ftype.equalsIgnoreCase("date")) {
						  value=rs.getDate(fname);
					}
					
					//��ȡ������ֵ�Ž�obj
					//�ҵ���ǰ���Զ�Ӧ��set����
					Method[] methods=c.getDeclaredMethods();
					for(Method m:methods) {
						//��ȡ��������
						String mName=m.getName();
						//setname  getname setid getid
						//��ǰ�����ǲ���set+fname
						if(mName.equalsIgnoreCase("set"+fname)) {
							//���ø�set����
							m.invoke(obj, value);
						}
						
					}
				}
				//++++++++++++++++++++
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	 
		
	}
	
	
}
