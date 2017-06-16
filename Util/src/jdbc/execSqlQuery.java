package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import assistPackage.ObjectMapper;
import assistPackage.Student;

public class execSqlQuery {
	/**
	 * ����ѯ�����Ľ����װ�������list������
	 * @param conn  ���ݿ�����
	 * @param sql	sql���
	 * @param arr	sql����еģ�
	 * @param mapperʵ����Ľ�������
	 * @return      List<Object[]>
	 */
	public static List<Object[]> TemplateResultToArray(Connection conn, String sql,String[] arr) {
		List<Object[]> list=new ArrayList<Object[]>();  
		ResultSet rs = null;
		PreparedStatement pstm = null;
		try {
			pstm=conn.prepareStatement(sql);
			System.out.println(sql);
			for (int i = 0; i < arr.length; i++) {
				pstm.setObject(i+1, arr[i]);
			}
			rs=pstm.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			while(rs.next()){
				int columnNum = rsMeta.getColumnCount();
				Object[] obj = new String[columnNum];
				Object fieldValue = null;
				for (int i = 1; i <= columnNum; i++) {
				//fieldValue = rs.getString(rsMeta.getColumnName(i));
					fieldValue = rs.getObject(i);
					if (fieldValue == null) {
						fieldValue = "";
					}
					obj[i - 1] = fieldValue;
				}
				list.add(obj); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (rs != null)
				rs.close();
				if (pstm != null)
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * ����ѯ�����Ľ����װ��Map��list������
	 * @param conn  ���ݿ�����
	 * @param sql	sql���
	 * @param arr	sql����еģ�
	 * @param mapperʵ����Ľ�������
	 * @return      List<Map>
	 */
	public static List<Map<String,Object>> TemplateResultToMap(Connection conn, String sql,String[] arr) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();  
		ResultSet rs = null;
		PreparedStatement pstm = null;
		try {
			pstm=conn.prepareStatement(sql);
			System.out.println(sql);
			for (int i = 0; i < arr.length; i++) {
				pstm.setObject(i+1, arr[i]);
			}
			rs=pstm.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			while(rs.next()){
				int columnNum = rsmd.getColumnCount();
				Map<String,Object> map = new HashMap<String, Object>();
				//Object[] obj = new String[columnNum];
				for (int i = 1; i <= columnNum; i++) {
					//��ȡָ���еı�Ŀ¼����
					String label=rsmd.getColumnLabel(i);
					//�� Java ��������� Object ����ʽ��ȡ�� ResultSet ����ĵ�ǰ����ָ���е�ֵ
					Object object= rs.getObject(i);
					//�����ݿ��е��ֶ�����ֵ��ӦΪһ��map�����е�һ����ֵ��
					map.put(label.toLowerCase(), object);
				}
				list.add(map); 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
				try {
					if (rs != null)
					rs.close();
					if (pstm != null)
						pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
	}
	/**
	 * ����ѯ�����Ľ����װ��ʵ�����list������
	 * @param conn   ���ݿ�����
	 * @param sql    sql���
	 * @param mapper ʵ����Ľ������� 
	 * @return
	 */
	public static List<Object> Result(Connection conn, String sql, ObjectMapper mapper) {
		List<Object> list=new ArrayList<Object>();  
		Object o=null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				o=mapper.mapping(rs);
				list.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
		
	}
	/**
	 * ����ѯ�����Ľ����װ��ʵ�����list������
	 * @param conn  ���ݿ�����
	 * @param sql	sql���
	 * @param arr	sql����еģ�
	 * @param mapperʵ����Ľ�������
	 * @return
	 */
	public static List<Object> TemplateResult(Connection conn, String sql, String[] arr,ObjectMapper mapper) {
		List<Object> list=new ArrayList<Object>();  
		Object o=null;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(sql);
			System.out.println(sql);
			 for(int i=0;i<arr.length;i++){
	                pstm.setObject(i+1, arr[i]); 
	            }
			rs=pstm.executeQuery();
			while(rs.next()){
				o=mapper.mapping(rs);
				list.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)
				rs.close();
				if(pstm!=null)
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public static void main(String[] args) {
		Connection conn=null;
		String sql = "select xs0101id, xh,xm from xs0101 where xs0101id in (?,?)";
		String[] sqls = {"200660950231","200810540111"};
		//String sql2 = "select xs0101id, xh,xm from xs0101 where xh in ('200660950231','200810540111')";
		try {
			conn=JdbcConn.getConnection("oracle", "localhost:1521:orcl", "hebsydx","hebsydx");
			//List<Object> list = TemplateResult(conn, sql, sqls, new UsersDAOObjectMapper());
			//List<Object> list = Result(conn, sql2, new UsersDAOObjectMapper());
			/*for (int i = 0; i < list.size(); i++) {
				Student stu=(Student)list.get(i);
				System.out.println(stu.toString());
			}*/
			/*
			 * ��������
			 */
			/*List<Object[]> list = TemplateResultToArray(conn, sql, sqls);
			for (int i = 0; i < list.size(); i++) {
				String[] stu=(String[])list.get(i);
				System.out.println(Arrays.toString(stu));
			}*/
			/*
			 * ����map
			 */
			List<Map<String, Object>> list = TemplateResultToMap(conn, sql, sqls);
			for (int i = 0; i < list.size(); i++) {
				Map<?, ?> map = list.get(i);
				Iterator<?> it = map.entrySet().iterator();
				while(it.hasNext()){
					Entry<?, ?> entry = (Entry<?, ?>) it.next();
					System.out.print(entry.getKey()+":"+entry.getValue().toString());
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcConn.closeConn(conn);
		}
	}
}
class UsersDAOObjectMapper implements ObjectMapper{  
    public Object mapping(ResultSet rs){ 
        Student u=new Student();      
            try{
                u.setId(rs.getString("xs0101id"));  
                u.setXh(rs.getString("xh"));  
                u.setXm(rs.getString("xm"));  
            }catch(Exception ex){  
                ex.printStackTrace();  
            }  
  
        return u;  
    }  
}