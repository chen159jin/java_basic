package com.qzdatasoft.comm.dao.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor; 
import java.lang.reflect.Method; 
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qzdatasoft.comm.dao.impl.SqlParamer;
import com.qzdatasoft.comm.framework.execption.ServiceException;
import com.qzdatasoft.comm.util.BeanUtil;  

/**
 * <p>
 * Title: ConvertUtil
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: ����ǿ�ǿƼ���չ���޹�˾
 * </p>
 * <p>
 * author: ��Ȩ
 * </p>
 * <p>
 * Date: 2011-12-22
 * </p>
 * 
 * @version 1.0
 */
public class ConvertUtil {
	private static final Logger log = LoggerFactory.getLogger(ConvertUtil.class);

	static String selectAll = "select * from ? ";

	static String deleteAll = "delete from ? ";

	static String where = " where ? = ? ";

	static String order = "  order by " + " desc ";

	static String selectOneDto = selectAll + where;

	public static String getTableName(String classname) {

		int pos = classname.lastIndexOf(".");

		String dtoName = classname.substring(pos + 1);

		return dtoName.toLowerCase();

	}

	/**
	 * �����ӳ�䵽Class��
	 * 
	 * @param clz
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	protected static Object convertResultSet2Dto(Class clz, ResultSet rs) throws Exception {

		Object obj = clz.newInstance();

		ResultSetMetaData meta = rs.getMetaData();
		int iCount = meta.getColumnCount();
		for (int i = 1; i <= iCount; i++) {
			String colName = meta.getColumnName(i).toLowerCase();
			Object value = rs.getObject(i);
			if (value != null)
				BeanUtils.setProperty(obj, colName, value);
		}

		return obj;

	}

	// д������
	private static void write(Object obj, Object value, PropertyDescriptor descriptor) throws Exception {

		Method m = descriptor.getWriteMethod();
		m.invoke(obj, new Object[] { value });
	}

	/**
	 * ͨ�õ�ƴ�������ַ���
	 * 
	 * @param dto
	 * @return
	 */
	public static String createSql(Object dto) {

		StringBuffer cols = new StringBuffer();
		StringBuffer vals = new StringBuffer();
		Class clazz = dto.getClass();
		String column = "";
		try {
			column = getColumn(clazz);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		String tablename = getTableName(clazz.getName());

		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}

		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		// ��ѯ�������ԣ�Ȼ�����εõ������Ե�ֵ�����������Ϊnull����ô�Ͳ�������
		for (int i = 0; i < descriptors.length; i++) {
			PropertyDescriptor descriptor = descriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class") && !propertyName.equals("strPK")
					&& column.indexOf("," + propertyName + ",") != -1) {
				cols.append(propertyName);
				if (i < descriptors.length - 1)
					cols.append(",");

				Object value = null;
				Method m = descriptor.getReadMethod();
				try {
					value = m.invoke(dto, new Object[] {});
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException(e.getMessage());
				}
				String values = formatByUpdate(value, descriptor.getPropertyType());
				vals.append(values);
				if (i < descriptors.length - 1) {
					vals.append(",");
				}
			}

		}
		String strCols = cols.toString();
		String strVals = vals.toString();

		String createsql = "INSERT INTO " + tablename + "(" + deleteMark(strCols) + ") VALUES("
				+ deleteMark(strVals) + ")";
		return createsql;
	}
	
	/**
	 * ͨ�õ�ƴ�������ַ���(����ģʽ)
	 * 
	 * @param dto
	 * @return
	 */
	public static Map createSqlParam(Object obj) {

		Map<String,Object> tvlMap = new HashMap<String, Object>(); 
		List<Object> param = new ArrayList<Object>();
		StringBuffer cols = new StringBuffer();
		StringBuffer vals = new StringBuffer();
		 
		Class clazz = obj.getClass();
		String column = "";
		try {
			column = getColumn(clazz);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		String tablename = getTableName(clazz.getName());
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new ServiceException(ex.getMessage());
		}

		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		// ��ѯ�������ԣ�Ȼ�����εõ������Ե�ֵ�����������Ϊnull����ô�Ͳ�������
		for (int i = 0; i < descriptors.length; i++) {
			PropertyDescriptor descriptor = descriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class") && !propertyName.equals("strPK")
					&& column.indexOf("," + propertyName + ",") != -1) {
				cols.append(propertyName);
				if (i < descriptors.length - 1){
					cols.append(",");
				}
				vals.append("?");
				if (i < descriptors.length - 1) {
					vals.append(",");
				}
				Object value = null;
				Method m = descriptor.getReadMethod();
				try {
					value = m.invoke(obj, new Object[] {});
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException(e.getMessage());
				}
				param.add(value);
			}

		}
		String strCols = cols.toString();
		String strVals = vals.toString();

		String createsql = "INSERT INTO " + tablename + "(" + deleteMark(strCols) + ") VALUES("
				+ deleteMark(strVals) + ")";
		tvlMap.put("sql", createsql);
		tvlMap.put("parameter", param);
		return tvlMap;
	}

	 
	/**
	 * ����SQl��ƴ�ӣ�����Ҫ�������������ܸ��� ���µ�object ������ݿ��в�ѯ������object��Ȼ������������޸ģ��������ܱ�֤���ݲ���ʧ
	 * 
	 * @param o
	 * @return
	 */
	protected static Map updateSql(Object o) {
		StringBuffer sets = new StringBuffer();
		// StringBuffer parameter = new StringBuffer();
		Class clazz = o.getClass();
		String tablename = getTableName(clazz.getName());
		BeanInfo info = null; 
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		Map pk = getPK(clazz);
		String column = getColumn(clazz);
		Map map = new HashMap<String, String>();
		Set set = pk.keySet();
		for (Object obj : set) {
			map.put((String) obj, null);
		}
		for (int i = 0; i < descriptors.length; i++) {
			PropertyDescriptor descriptor = descriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class") && !propertyName.equals("strPK")
					&& column.indexOf("," + propertyName + ",") != -1) {
				Object value = null;
				Method m = descriptor.getReadMethod();
				try {
					value = m.invoke(o, new Object[] {});
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException(e.getMessage());
				}
				String values = formatByUpdate(value, descriptor.getPropertyType());
				if (map.keySet().contains(propertyName)) {
					map.put(propertyName, values);
				}else{
					sets.append(propertyName + "= ");
					sets.append(values); 
					if (i < descriptors.length) {
						sets.append(","); 
					}
				}
			}

		}
		try {
			StringBuffer sb = new StringBuffer("update ");
			sb.append(tablename).append(" set ");
			if (sets != null && sets.length() > 1) {
				sb.append(sets.substring(0, sets.length() - 1).toString());
			} else {
				sb.append(sets.toString());
			}
			sb.append(" where ");
			int i = 1;
			for (Object k : map.keySet()) {
				sb.append(k).append(" = ");
				String type = pk.get(k).toString();
				sb.append(ConvertUtil.formatByStr(map.get(k).toString(), type)).append(" ");
				i++;
				if (i == map.keySet().size())
					sb.append(" and ");
			}
			Map map1 = new HashMap<String, String>();
			map1.put("sql", sb.toString()); 
			map1.put("parameter", "");
			return map1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	/**
	 * ����SQl��ƴ�ӣ�����Ҫ�������������ܸ��� ���µ�object ������ݿ��в�ѯ������object��<br>
	 * Ȼ������������޸ģ��������ܱ�֤���ݲ���ʧ(����ģʽ)
	 * 
	 * @param o
	 * @return
	 */
	protected static Map updateSqlParam(Object o) {
		StringBuffer sets = new StringBuffer();
		List<Object> lstParam = new ArrayList<Object>();
		Class clazz = o.getClass();
		String tablename = getTableName(clazz.getName());
		BeanInfo info = null; 
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		Map pk = getPK(clazz);
		String column = getColumn(clazz);
		Map<String,Object> map = new HashMap<String, Object>();
		Set set = pk.keySet();
		for (Object obj : set) {
			map.put((String) obj, null);
		}
		for (int i = 0; i < descriptors.length; i++) {
			PropertyDescriptor descriptor = descriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class") && !propertyName.equals("strPK")
					&& column.indexOf("," + propertyName + ",") != -1) {
				Object value = null;
				Method m = descriptor.getReadMethod();
				try {
					value = m.invoke(o, new Object[] {});
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
					throw new ServiceException(e.getMessage());
				} 
				if (map.keySet().contains(propertyName)) {
					map.put(propertyName, value);
				}else{
					sets.append(propertyName + "=? ");
					lstParam.add(value);
					if (i < descriptors.length) {
						sets.append(","); 
					}
				}
			}

		}
		try {
			StringBuffer sb = new StringBuffer("UPDATE ");
			sb.append(tablename).append(" SET ");
			if (sets != null && sets.length() > 1) {
				sb.append(sets.substring(0, sets.length() - 1).toString());
			} else {
				sb.append(sets.toString());
			}
			sb.append(" WHERE ");
			int i = 1;
			for (Object k : map.keySet()) {
				sb.append(k).append(" =?"); 
				lstParam.add(map.get(k));
				i++;
				if (i == map.keySet().size())
					sb.append(" AND ");
			}
			Map map1 = new HashMap();
			map1.put("sql", sb.toString());
			// map1.put("parameter", parameter.toString());
			map1.put("parameter", lstParam);
			return map1;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	

	/**
	 * ����SQl��ƴ�ӣ�����Ҫ�������������ܸ���(ֻ����ֵ��ΪNULL������) ���µ�object
	 * ������ݿ��в�ѯ������object��Ȼ������������޸ģ��������ܱ�֤���ݲ���ʧ
	 * 
	 * @param o
	 * @return
	 */
	protected static Map updateSqlNotNull(Object o) {
		StringBuffer sets = new StringBuffer(); 
		Class clazz = o.getClass();
		String tablename = getTableName(clazz.getName());
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		Map<String, String> pk = getPK(clazz);
		String column = getColumn(clazz);
		Map<String, String> map = new HashMap<String, String>();
		Set set = pk.keySet();
		for (Object obj : set) {
			map.put((String) obj, null);
		}
		for (int i = 0; i < descriptors.length; i++) {
			PropertyDescriptor descriptor = descriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class") && !propertyName.equals("strPK")
					&& column.indexOf("," + propertyName + ",") != -1) {
				Object value = null;
				Method m = descriptor.getReadMethod();
				try {
					value = m.invoke(o, new Object[] {});
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException(e.getMessage());
				}
				String values = formatByUpdate(value, descriptor.getPropertyType()); 
				if (map.keySet().contains(propertyName)) {
					map.put(propertyName, values);
				}else{
					//ֵ��Ϊ��ʱ
					if(value != null ){
						sets.append(propertyName + "= ");
						sets.append(values);
						if (i < descriptors.length) {
							sets.append(","); 
						}
					}
				}				
				
			}

		}
		try {
			StringBuffer sb = new StringBuffer("update ");
			sb.append(tablename).append(" set ");
			if (sets != null && sets.length() > 1) {
				sb.append(sets.substring(0, sets.length() - 1).toString());
			} else {
				sb.append(sets.toString());
			}
			sb.append(" where ");
			int i = 1;
			for (Object k : map.keySet()) {
				sb.append(k).append(" = ");
				String type = pk.get(k).toString();
				sb.append(ConvertUtil.formatByStr(map.get(k).toString(), type)).append(" ");
				i++;
				if (i == map.keySet().size())
					sb.append(" and ");
			}
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("sql", sb.toString()); 
			map1.put("parameter", "");
			return map1;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	/**
	 * ����SQl��ƴ�ӣ�����Ҫ�������������ܸ���(ֻ����ֵ��ΪNULL������) ���µ�object<br>
	 * ������ݿ��в�ѯ������object��Ȼ������������޸ģ��������ܱ�֤���ݲ���ʧ������ģʽ��
	 * @param 
	 * ����ֵ�а���sql��parameter
	 * @return
	 */
	protected static Map updateSqlNotNullParam(Object o) {
		StringBuffer sets = new StringBuffer(); 
		List<Object> lstParam = new ArrayList<Object>();
		Class clazz = o.getClass();
		String tablename = getTableName(clazz.getName());
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException ex) {
			ex.printStackTrace();
			throw new ServiceException(ex.getMessage());
		}
		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		Map<String, String> pk = getPK(clazz);
		String column = getColumn(clazz);
		Map<String, Object> map = new HashMap<String, Object>();
		Set set = pk.keySet();
		for (Object obj : set) {
			map.put((String) obj, null);
		}
		//���ò���
		for (int i = 0; i < descriptors.length; i++) {
			PropertyDescriptor descriptor = descriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class") && !propertyName.equals("strPK")
					&& column.indexOf("," + propertyName + ",") != -1) {
				Object value = null;
				Method m = descriptor.getReadMethod();
				try {
					value = m.invoke(o, new Object[] {});
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException(e.getMessage());
				} 
				//����
				if (map.keySet().contains(propertyName)) {
					map.put(propertyName, value);
				}else{
					//ֵ��Ϊ��ʱ
					if(value != null ){
						sets.append(propertyName + "=?");
						lstParam.add(value);
						sets.append(",");
					}
				}
				
				
			}

		}
		//WHERE��������
		try {
			StringBuffer sb = new StringBuffer("UPDATE ");
			sb.append(tablename.toUpperCase()).append(" SET ");
			if (sets != null && sets.length() > 1) {
				sb.append(sets.substring(0, sets.length() - 1).toString());
			} else {
				sb.append(sets);
			}
			sb.append(" WHERE ");
			int i = 1;
			for (Object k : map.keySet()) {
				sb.append(k).append(" =? ");
				lstParam.add(map.get(k));
				i++;
				if (i == map.keySet().size()){
					sb.append(" AND ");
				}
					
			}
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("sql", sb.toString()); 
			map1.put("parameter", lstParam);
			return map1;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	// ��������ɾ��
	protected static String deleteSqlByPK(Class clazz, Object[] o) {
		String tablename = getTableName(clazz.getName());
		StringBuffer sb = new StringBuffer("delete ");
		sb.append(tablename).append(" where ");
		try {
			Map pk = getPK(clazz);
			Map<String, String> map = new HashMap<String, String>();
			Set set = pk.keySet();
			for (Object obj : set) {
				map.put((String) obj, null);
			}
			int i = 1;
			for (Object k : map.keySet()) {
				sb.append(k).append(" = ");
				String type = pk.get(k).toString();
				sb.append(ConvertUtil.formatByStr(o[i - 1].toString(), type)).append(" ");
				i++;
				if (i == map.keySet().size()){
					sb.append(" and ");
				}
					
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	/**
	 * ����ɾ�����ɣ�����ģʽ��
	 * @param clazz
	 * @param o
	 * @return
	 */
	protected static String deleteSqlByPkParam(Class clazz) {
		String tablename = getTableName(clazz.getName());
		StringBuffer sb = new StringBuffer("delete ");
		sb.append(tablename).append(" where ");
		try {
			Map pk = getPK(clazz);
			Map<String, String> map = new HashMap<String, String>();
			Set set = pk.keySet();
			for (Object obj : set) {
				map.put((String) obj, null);
			}
			int i = 1;
			for (Object k : map.keySet()) {
				sb.append(k).append(" = ");
				sb.append("?");
				i++;
				if (i == map.keySet().size()){
					sb.append(" and ");
				}
					
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * �����������õ���ѯsql
	 * 
	 * @param clazz
	 * @param o
	 * @return
	 */
	protected static String findByPrimaryKeySql(Class clazz, Object[] o) {
		String classname = clazz.getName();
		String tablename = ConvertUtil.getTableName(classname);

		SqlParamer sqlParamer = new SqlParamer(selectOneDto);
		sqlParamer.setName(tablename);
		Map pk = getPK(clazz);
		Set set = pk.keySet();
		if (set.size() > 0) {
			// ˵������������
			if (o.length < set.size()) {
				throw new RuntimeException("����������������������ĸ���������ڵ������������ĸ��� ");
			}
		}
		int i = 0;
		for (Object s : set) {
			sqlParamer.setName(s.toString());
			String type = (String) pk.get(s.toString());
			if (type.equals("int") || type.equals("Integer")) {
				sqlParamer.setInt((Integer.valueOf(o[i].toString())));
			} else if (type.equals("") || type.equals("double")) {
				sqlParamer.setObject(o[i]);
			} else {
				sqlParamer.setString((String) o[i]);
			}
			i++;
		}

		String sql = sqlParamer.toString();
		return sql;
	}
	
	/**
	 * ���ݼ�ֵ���õ���ѯsql ��������ʽ
	 * 
	 * @param clazz
	 * @param o
	 * @return
	 */
	protected static Map<String,Object> findByPrimaryKeySqlParam(Class clazz, Object[] o) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> param = new ArrayList<Object>();
		String classname = clazz.getName();
		String tablename = ConvertUtil.getTableName(classname);
		StringBuffer sql = new StringBuffer("SELECT * FROM ");
		sql.append(tablename);
		sql.append(" WHERE ");
		Map pk = getPK(clazz);//����
		Set set = pk.keySet();
		int i = 0;
		for (Object obj : set) {
			
			sql.append(obj);
			sql.append(" =? ");
			param.add(o[i]);
			if(i < set.size() - 1){
				sql.append(" AND ");
			}
			i++;
		}
		map.put("sql", sql);
		map.put("parameter", param);
		return map;
	}
	
	/**
	 * ���ݼ�ֵ���õ���ѯsql ��������ʽ
	 * 
	 * @param clazz
	 * @param o
	 * @return
	 */
	protected static Map<String,Object> findByKeyValueSqlParam(Class clazz,Map object) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> param = new ArrayList<Object>();
		String classname = clazz.getName();
		String tablename = ConvertUtil.getTableName(classname);
		StringBuffer sql = new StringBuffer("SELECT * FROM ");
		sql.append(tablename);		
		Set set = object.keySet();
		int i = 0;
		for (Object obj : set) {
			if(i == 0){
				sql.append(" WHERE "); 
			}
			sql.append(obj);
			sql.append(" =? ");
			param.add(object.get(obj));
			if(i < set.size() - 1){
				sql.append(" AND ");
			}
			i++;
		}
		map.put("sql", sql);
		map.put("parameter", param);
		return map;
	}

	/**
	 * ƴ��ɾ��SQL���������������ֵ��������ܳ�����ɾ
	 * 
	 * @param dto
	 * @return
	 */
	public static String deleteSql(Object dto) {
		Class clazz = dto.getClass();
		String tablename = getTableName(clazz.getName());
		StringBuffer sb = new StringBuffer("delete ");
		sb.append(tablename).append(" where ");
		try {
			Map pk = getPK(clazz);
			Set set = pk.keySet();

			int i = 1;
			for (Object k : set) {
				sb.append(k).append(" = ");
				String value = (String) BeanUtil.getProperty(dto, (String) k);
				if (value == null || "".equals(value)) {
					throw new RuntimeException("ɾ������ʱҪ���������");
				}
				sb.append("'").append(value).append("'");
				i++;
				if (i <= set.size())
					sb.append(" and ");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		// Class clazz = dto.getClass();
		// String tablename = getTableName(clazz.getName());
		// BeanInfo info = null;
		// try
		// {
		// info = Introspector.getBeanInfo(clazz);
		// } catch (IntrospectionException ex)
		// {
		//
		// ex.printStackTrace();
		// throw new ServiceException(ex.getMessage());
		// }
		// String column = "";
		// try
		// {
		// column = getColumn(clazz);
		// } catch (Exception e)
		// {
		// throw new RuntimeException(e.getMessage());
		// }
		// StringBuffer sb = new StringBuffer("delete ");
		// String str = null;
		// sb.append(tablename).append(" where ");
		// PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		// for (int i = 0; i < descriptors.length; i++)
		// {
		//
		// PropertyDescriptor descriptor = descriptors[i];
		// String propertyName = descriptor.getName();
		// if (!propertyName.equals("class")
		// && !propertyName.trim().equals("strPK")
		// && column.indexOf("," + propertyName + ",") != -1)
		// {
		//
		// Object value = null;
		// Method m = descriptor.getReadMethod();
		// try
		// {
		// value = m.invoke(dto, new Object[] {});
		// } catch (IllegalArgumentException e)
		// {
		// e.printStackTrace();
		// throw new ServiceException(e.getMessage());
		// } catch (IllegalAccessException e)
		// {
		// e.printStackTrace();
		// throw new ServiceException(e.getMessage());
		// } catch (InvocationTargetException e)
		// {
		// e.printStackTrace();
		// throw new ServiceException(e.getMessage());
		// }
		// if (value != null)
		// {
		// String values = formatByUpdate(value, descriptor
		// .getPropertyType());
		// if (!StringUtils.nullOrBlank(values))
		// {
		// sb.append(" ").append(propertyName).append(" =");
		// sb.append(values);
		// sb.append(" and");
		// str = sb.toString().substring(0,
		// sb.toString().length() - 3);
		//
		// }
		// }
		//
		// }
		//
		// }
		// return str;
	}

	/**
	 * �õ�������е����ݵ�sql
	 */
	protected static String findAllSql(Class clazz) {
		String classname = clazz.getName();
		String tablename = ConvertUtil.getTableName(classname);
		try {
			Map pk = getPK(clazz);
			StringBuffer sb = new StringBuffer();

			Set set = pk.keySet();
			for (Object obj : set) {
				sb.append(obj).append(",");
			}
			SqlParamer sqlParamer;
			String order = " order by  " + sb.toString().substring(0, sb.toString().length() - 1) + " desc";
			if (sb.toString() != null && !sb.toString().equals("")) {
				sqlParamer = new SqlParamer(selectAll + order);
			} else {
				sqlParamer = new SqlParamer(selectAll);
			}

			sqlParamer.setName(tablename);
			String sql = sqlParamer.toString();
			return sql;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * �ж�����ĸ�ʽ�������ո񣬺�ʱ������
	 * 
	 * @param value
	 * @param propertyType
	 * @return
	 */
	protected static String format(Object value, Class propertyType) {
		if (value != null
				&& (propertyType.equals(java.util.Date.class) || propertyType.equals(java.sql.Date.class) || propertyType
						.equals(java.sql.Timestamp.class))) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String strdate = df.format(value);
			StringBuffer sb = new StringBuffer();
			sb.append("to_date('").append(strdate);
			sb.append("','yyyy-MM-dd hh:mi:ss')");
			return sb.toString();
		} else if (value == null) {
			return "NULL";
		} else if (value.equals("")) {
			{
				return " ";
			}
		} else {
			return value.toString();
		}
	}

	// ��������ƴ��sqlʹ�ã�����������ͣ�����ʱ�������
	protected static String formatByUpdate(Object value, Class propertyType) {
		if (propertyType.equals(java.sql.Clob.class)) {
			// StringBuffer sb = new StringBuffer();
			//
			// try
			// {
			//
			// Reader reader = ((Clob) value).getCharacterStream();
			// char[] charbuf = new char[4096];
			// for (int i = reader.read(charbuf); i > 0; i = reader
			// .read(charbuf))
			// {
			// sb.append(charbuf, 0, i);
			// }
			// } catch (Exception e)
			// {
			// e.printStackTrace();
			// log.error(e.getMessage());
			// throw new RuntimeException(e.getMessage());
			// }
			// String ss = sb.toString();
			// ss = ss.replaceAll("'", "''");
			// ss= "to_clob("+"'" + ss + "')";
			return "?";
		} else if (value != null
				&& (propertyType.equals(java.util.Date.class) || propertyType.equals(java.sql.Date.class) || propertyType
						.equals(java.sql.Timestamp.class))) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String strdate = df.format(value);
			StringBuffer sb = new StringBuffer();
			sb.append("to_date('").append(strdate);
			sb.append("','yyyy-MM-dd hh:mi:ss')");
			return sb.toString();
		} else if (value == null || value.equals("NULL") || value.equals("null")) {
			return "NULL";
		} else if (propertyType.equals(java.lang.String.class)) {
			String ss = value.toString();
			ss = ss.replaceAll("'", "''");
			return "'" + ss + "'";

		} else {
			return value.toString();
		}
	}

	/**
	 * ���ַ����ӡ���
	 * 
	 * @param str
	 * @return
	 */
	public static String formatByStr(String str) {
		if (isInteger(str)) {
			return str;
		} else {
			str = str.replaceAll("'", "''");
			return "'" + str + "'";
		}
	}

	/**
	 * ���ַ����ӡ������ų��������
	 * 
	 * @param str
	 * @return
	 */
	public static String formatByStr(String str, String type) {
		if (type.equals("int") || type.equals("long") || type.equals("double") || type.equals("float")) {
			return str;
		} else if (str.startsWith("'")) {
			return str;
		} else {
			str = str.replaceAll("'", "''");
			return "'" + str + "'";
		}
	}

	/**
	 * �ж��Ƿ�������
	 * @param str
	 * @return
	 */
	private static boolean isInteger(String str) {
		try {
			Long.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ����ת��
	 * 
	 * @param clazz
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Object convertRequest2Dto(Class clazz, HttpServletRequest request) throws Exception {

		Object dto = clazz.newInstance();

		BeanInfo info = null;

		info = Introspector.getBeanInfo(clazz);

		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();

		for (int i = 0; i < descriptors.length; i++) {

			PropertyDescriptor descriptor = descriptors[i];
			String propertyName = descriptor.getName();

			String val = request.getParameter(propertyName);
			val = ConvertUtil.codeformat(val);

			if (val != null) {
				Object value = TypeUtil.cast(val, descriptor.getPropertyType());
				write(dto, value, descriptor);

			}

		}

		return dto;

	}

	/**
	 * �õ��������ֶ�
	 * 
	 */
	public static Map getPK(Class clazz) {
		try {
			Object o = clazz.newInstance();
			BeanInfo info = null;

			info = Introspector.getBeanInfo(clazz);

			PropertyDescriptor[] descriptors = info.getPropertyDescriptors();

			for (int i = 0; i < descriptors.length; i++) {

				PropertyDescriptor descriptor = descriptors[i];
				String propertyName = descriptor.getName();

				if (propertyName.equals("strPK")) {
					Object value = null;
					Method m = descriptor.getReadMethod();
					value = m.invoke(o, new Object[] {});
					return (Map) value;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}

	}

	/**
	 * �õ����ݿ����е�Ԫ��
	 * @param clazz
	 * @return
	 */
	public static String getColumn(Class clazz) {
		try {
			Object o = clazz.newInstance();
			BeanInfo info = null;

			info = Introspector.getBeanInfo(clazz);

			PropertyDescriptor[] descriptors = info.getPropertyDescriptors();

			for (int i = 0; i < descriptors.length; i++) {
				PropertyDescriptor descriptor = descriptors[i];
				String propertyName = descriptor.getName();

				if (propertyName.equals("column")) {
					Object value = null;
					Method m = descriptor.getReadMethod();
					value = m.invoke(o, new Object[] {});
					return (String) value;
				}
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public static String deleteMark(String str) {
		if (str != null && !str.equals("") && str.length() > 2) {
			String last = str.substring(str.length() - 1);
			if (last.equals(",")) {
				str = str.substring(0, str.length() - 1);
			}
		}
		return str;
	}

	// �����������
	public static String codeformat(String s) {

		String ss = s;
		if (ss != null) {
			ss = ss.replaceAll("\r|\n", "");
		}
		while ((s != null) && (ss.indexOf("'") > -1)) {
			ss = ss.substring(0, ss.indexOf("'")) + "" + ss.substring(ss.indexOf("'") + 1);
		}

		return ss;
	}

	// ��ԭʼ��sql ��ƴ�ӳ�oracleҪ�� ��ҳ��SQL
	protected static String getSqlPagination(String sql) {

		StringBuffer sb = new StringBuffer();
		if (sql.startsWith("f") || sql.startsWith("F")) {
			sql = "select * " + sql;
		}
		sb.append("select * from (select row_.*, rownum rownum_ from (");
		sb.append(sql);
		sb.append(") row_ where rownum <= ?)where rownum_ > ?");
		return sb.toString();
	}

	/**
	 * �Ѳ���list��� ������ʽ
	 */
	public static Object[] convertList2Array(List list) {
		if (list != null && list.size() > 0) {
			Object[] obj = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				obj[i] = list.get(i);
			}
			return obj;
		}
		return null;
	}

	/**
	 * �ж���from��ͷ���������Զ����� select *
	 * 
	 */

	public static String valBeginning(String sql) {
		sql = sql.trim();
		if (sql.startsWith("f") || sql.startsWith("F")) {
			StringBuffer sb = new StringBuffer();
			sb.append("select * ");
			sb.append(sql);
			return sb.toString();
		} else {
			return sql;
		}
	}


}
