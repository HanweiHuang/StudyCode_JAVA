package com.harvey.OR_Mapping_Simulation.model;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;


public class Session {
	Map<String, String> cfs = new HashMap<String, String>();
	String tablename = "user";
	
	String[] methodNames;
	public Session(){
		cfs.put("id", "id");
		cfs.put("name", "name");
		cfs.put("username", "username");
		cfs.put("password","password");
		cfs.put("email","email");
		methodNames = new String[cfs.size()];
	}
	
	public void save(Student s) {
	
		String sql = createSql();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","");
			PreparedStatement ps = conn.prepareStatement(sql);
			
			for(int i=0;i<cfs.size();i++){
				String m = methodNames[i];
				Method method = s.getClass().getMethod(m);
				if(method.getReturnType().getName().equals("java.lang.String")){
					String mvalue = (String)method.invoke(s);
					ps.setString(i+1, mvalue);
				}
				if(method.getReturnType().getName().equals("int")){
					Integer mvalue = (Integer)method.invoke(s);
					ps.setInt(i+1, mvalue);
				}
				
				System.out.println(method.getReturnType().getName());
				//ps.setInt(i, s.getId());
				
			}
			
			ps.executeUpdate();
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String createSql() {
		String sql = "";
		String str1 = "";
		String str2 = "";
		
		for(int i=0;i<cfs.size();i++){
			str2 += "?,";
		}
		str2 = str2.substring(0,str2.length()-1);
		int index=0;
		for(String s:cfs.keySet()){
			str1 += s + ",";
			
			String value = cfs.get(s);
			String final_name = Character.toUpperCase(value.charAt(0)) + value.substring(1);
			//System.out.println(final_name);
			
			String methodname = "get"+ final_name;
			//System.out.println(methodname);
			methodNames[index] = methodname;
			index++;
		}
		str1 = str1.substring(0,str1.length()-1);
		
		sql = "insert into "+tablename+ "("+str1+") value (" +str2+ ")";
		System.out.println("sql:"+sql);
		return sql;
	}
}
