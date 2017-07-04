/**
 * ˵����Bean����������
 * ��д�ߣ�лƽ
 * ���ڣ�Nov 20, 2007
 *      Dec 10,2007  �Ľ�getProperty�����쳣�ķ���
 * ����ǿ�ǿƼ���Ȩ���С�
 * 2008-04-01 ������Բ����ڴ�������
 * 			  ����30���ԣ�set���ʹ���
 * 			  ���ӶԷ�POJO������ȡֵ֧��
 * 			  31 �Ƿ�ͬ��
 * 			  32 �Ƿ���ˣ�����ͨ��
 * 			  33 0 Ժϵδ�� 1Ժϵͨ�� 2 �쵼δ�� 3 �쵼ͨ��
 * 			  40 �û�����
 * 2008-04-19 �޸�����ȡֵ�쳣����
 */
package com.qzdatasoft.comm.dao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.hibernate.collection.PersistentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title:Bean����������
 * <p>
 * Description:Bean���������࣬��ʹ��������������Bean����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: ����ǿ�ǿƼ���չ���޹�˾
 * </p>
 * 
 */
public class BeanUtil
{
    private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * ����Bean����
     * 
     * @param target
     * @param source
     */
    public static void copyProperties(Object target, Object source)
    {
	try
	{
	    BeanUtils.copyProperties(target, source);
	} catch (Exception e)
	{
	    log.error(e.getMessage());
	}
    }

    private static String[] BOOLEANCN = { "��", "��" };

    /**
     * �ʺ�����
     */
    private static String[] ACCOUNTTYPE = { "ϵͳ����", "��ְ��", "ѧ��" };

    /**
     * Ժϵ��������
     */
    private static String[] YXSPTYPE = { "Ժϵδ��", "Ժϵͨ��", "�쵼δ��", "�쵼ͨ��" };

    /**
     * ͬ������
     */
    private static String[] TONGYITYPE = { "��ͬ��", "ͬ��" };

    /**
     * ��������
     */
    private static String[] SHENGPITYPE = { "δͨ��", "ͨ��", "δ��" };

    /**
     * �ƻ�״̬ ��zt��
     */
    private static String[] ZHIDAOJIHUATYPE = { "δ����", "������", "�����޸�",
	    "���ͨ������������", "����ͨ��" };

    /**
     * ���״̬ ��shzt��
     */
    private static String[] SHENHEZHUANGTAITYPE = { "δ����", "��˲�ͨ��", "���ͨ��",
	    "���" };

    /**
     * ����״̬��spzt��
     */
    private static String[] SHENPIZHUANGTAI = { "δ����", "������ͨ��", "����ͨ��", "����" };

    /**
     * ѧ�ڱ�־(xqbz)
     */
    private static String[] XUEQIBIAOZHI = { "����ѧ��", "��ǰѧ��", "�ſ�ѧ��" };

    /**
     * ���ظö����ֵ
     * 
     * @param opObj
     *            Դ����
     * @param objProperty
     *            ��������
     * @param propertyType
     *            ���� <br>
     *            0 ���� <br>
     *            1 �ַ� <br>
     *            2 ������(�Ƿ�)����0��1ת�����ǣ��� <br>
     *            10 ����yyyy-MM-dd <br>
     *            11 ���ڼ�ʱ��yyyy-MM-dd HH:mm:ss <br>
     *            12 ���� yyyyMM <br>
     *            13 ������ yyyyMMdd <br>
     *            14 ������ʱ���� yyyyMMddhhmmss <br>
     *            20 Ǯ��2λС�� <br>
     *            30 ��������<br>
     *            31 �Ƿ�ͬ��<br>
     *            32 �Ƿ���ˣ�����ͨ��<br>
     *            33 Ժϵδ�� 1Ժϵͨ�� 2 �쵼δ�� 3 �쵼ͨ��<br>
     *            34 �ƻ�״̬ ��zt��<br>
     *            35 ���״̬ ��shzt��<br>
     *            36 ����״̬��spzt��<br>
     *            37 ѧ�ڱ�־(xqbz)<br>
     *            40 �û�����
     * @return Object ȡֵ����
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getProperty(Object opObj, String objProperty,
	    String propertyType)
    {
	try
	{
	    if (propertyType.equals("99"))
		return objProperty;
	    // �ж��Ƿ��Ƕ���󷵻�
	    if (opObj.getClass().isArray())
	    {
		Object[] objs = (Object[]) opObj;
		if (objProperty.indexOf(".") <= 0)
		    return getObjPropertyByType(objs[Integer
			    .parseInt(objProperty)], propertyType);
		else
		    return getObjPropertyByType(PropertyUtils.getProperty(
			    objs[Integer.parseInt(objProperty.substring(0,
				    objProperty.indexOf(".")))], objProperty
				    .substring(objProperty.indexOf(".") + 1)),
			    propertyType);
	    } else
		return getObjPropertyByType(PropertyUtils.getProperty(opObj,
			objProperty), propertyType);
	} catch (Exception e)
	{
	    // log.error(opObj.toString() + "����ȡֵ�쳣��" + objProperty + "���Բ�������");
	    // e.printStackTrace();
	    return "";
	}
    }

    /**
     * ���ظö����ֵ
     * 
     * @param opObj
     * @param objProperty
     * @return Object ȡֵ����
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getProperty(Object opObj, String objProperty)
    {
	try
	{
	    // �ж��Ƿ��Ƕ���󷵻�
	    if (opObj.getClass().isArray())
	    {
		Object[] objs = (Object[]) opObj;
		// ������ǵ��������Ӧ����һ����ţ�����0��1��2
		if (objProperty.indexOf(".") <= 0)
		    return objs[Integer.parseInt(objProperty)];
		// ����ǵ��������ȡ������������
		else
		    return PropertyUtils.getProperty(objs[Integer
			    .parseInt(objProperty.substring(0, objProperty
				    .indexOf(".")))], objProperty
			    .substring(objProperty.indexOf(".") + 1));
	    } else
		return PropertyUtils.getProperty(opObj, objProperty);
	} catch (Exception e)
	{
	    // log.error(opObj.toString() + "����ȡֵ�쳣��" + objProperty + "���Բ�������");
	    // e.printStackTrace();
	    return "";
	}
    }

    /**
     * ����ֵ
     * 
     * @param opObj
     *            ��������
     * @param objProperty
     *            ���������
     * @param valueObject
     *            ֵ����
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static void setProperty(Object opObj, String objProperty,
	    Object valueObject) throws IllegalAccessException,
	    InvocationTargetException, NoSuchMethodException
    {
	// �ж��Ƿ��Ƕ���󷵻�
	if (opObj.getClass().isArray())
	{
	    Object[] objs = (Object[]) opObj;
	    // �����������CLASSTYPE���������õ����Ե�һ���ֶαȽ�
	    if (objProperty.indexOf(".") > 0)
		BeanUtils.setProperty(objs[Integer.parseInt(objProperty
			.substring(0, objProperty.indexOf(".")))], objProperty
			.substring(objProperty.indexOf(".") + 1), valueObject);
	} else
	{
	    BeanUtils.setProperty(opObj, objProperty, valueObject);
	}
    }

    /**
     * ��ActionForm������ֵ���Ƶ�Model
     * 
     * @param actionForm
     * @param model
     */
    public static void actionFormToModel(ActionForm actionForm, Object model)
    {
	Class modelClass = model.getClass();
	Class afClass = actionForm.getClass();
	try
	{
	    Method[] modelMethod = modelClass.getMethods();
	    for (int i = 0; i < modelMethod.length; i++)
	    {
		String setMethodName = modelMethod[i].getName();
		if (setMethodName.indexOf("set") == 0)
		{
		    String getMethodName = setMethodName.replaceFirst("set",
			    "get");
		    Object value[] = { afClass.getMethod(getMethodName, null)
			    .invoke(actionForm, null) };
		    modelMethod[i].invoke(model, value);
		}
	    }
	} catch (Exception e)
	{
	    log.error(actionForm.getClass() + "���󿽱��쳣" + e.getMessage());
	}
    }

    /**
     * ��Model������ֵ���Ƶ�ActionForm
     * 
     * @param actionForm
     * @param model
     */
    public static void modelToActionForm(Object model, ActionForm actionForm)
    {
	Class modelClass = model.getClass();
	Class afClass = actionForm.getClass();
	try
	{
	    Method[] modelMethod = modelClass.getMethods();
	    for (int i = 0; i < modelMethod.length; i++)
	    {
		String setMethodName = modelMethod[i].getName();

		if (setMethodName.indexOf("set") == 0)
		{
		    String getMethodName = setMethodName.replaceFirst("set",
			    "get");
		    Object value[] = { modelClass
			    .getMethod(getMethodName, null).invoke(model, null) };
		    Class type[] = { modelClass.getMethod(getMethodName, null)
			    .getReturnType() };

		    afClass.getMethod(setMethodName, type).invoke(actionForm,
			    value);
		}
	    }
	} catch (Exception e)
	{
	    log.error(actionForm.getClass() + "���󿽱��쳣" + e.getMessage());
	}
    }

    /**
     * ��һ�����л��Ķ��󻺴���ļ�
     * 
     * @param f
     *            ������ļ�·��
     * @param obj
     *            ����
     * @throws Exception
     */
    public static void serializableObj(String f, Object obj) throws Exception
    {
	File file = new File(f);
	FileOutputStream os = new FileOutputStream(file);
	ObjectOutputStream oos = new ObjectOutputStream(os);
	oos.writeObject(obj);
	oos.close();
	os.close();
    }

    /**
     * ��һ���ļ�ת����һ������
     * 
     * @param f
     *            �ļ�·��
     * @return Object
     * @throws Exception
     */
    public static Object unSerializableObj(String f) throws Exception
    {
	File file = new File(f);
	FileInputStream is = new FileInputStream(file);
	ObjectInputStream ois = new ObjectInputStream(is);
	Object obj = ois.readObject();
	ois.close();
	is.close();
	return obj;
    }

    /**
     * ��ʽ���ַ���
     * 
     * @param o
     * @param propertyType
     * @return
     */
    private static Object getObjPropertyByType(Object o, String propertyType)
	    throws Exception
    {
	switch (Integer.parseInt(propertyType))
	{
	    case 0:// ����
	    {
		if (o == null)
		    return "";
		return o;
	    }
	    case 1:// �ַ�
	    {
		if (o == null)
		    return "";
		return o;
	    }
	    case 2:// ������
	    {
		if (o == null)
		    return BOOLEANCN[0];
		else
		    return BOOLEANCN[Integer.parseInt(o.toString())];
	    }
	    case 10:// ����yyyy-MM-dd
		return StringUtils.formatDateByFormatStr(o, "yyyy-MM-dd");
	    case 11:// ���ڼ�ʱ��yyyy-MM-dd HH:mm:ss
		return StringUtils.formatDateByFormatStr(o,
			"yyyy-MM-dd HH:mm:ss");
	    case 12:// ���� yyyyMM
		return StringUtils.formatDateByFormatStr(o, "yyyyMM");
	    case 13:// ������ yyyyMMdd
		return StringUtils.formatDateByFormatStr(o, "yyyyMMdd");
	    case 14:// ������ʱ���� yyyyMMddhhmmss
		return StringUtils.formatDateByFormatStr(o, "yyyyMMddhhmmss");
	    case 20:// ����2λС��
		return StringUtils.formatDouble(o, "#.00");
	    case 30:// ��������
	    {
		if (o == null)
		    return "";
		if (o.getClass().toString().indexOf("PersistentSet") > 0)
		{
		    try
		    {
			PersistentSet set = (PersistentSet) o;
			if (set.isEmpty())
			    return "";
			else
			{
			    StringBuffer sb = new StringBuffer();
			    Iterator it = set.iterator();
			    while (it.hasNext())
			    {
				if (sb.length() <= 0)
				    sb.append(it.next().toString());
				else
				    sb.append(",").append(it.next().toString());
			    }
			    return sb.toString();
			}
		    } catch (Exception ex)
		    {
			ex.printStackTrace();
		    }
		}
		return "";
	    }
	    case 31: // �Ƿ�ͬ��
	    {
		if (o == null)
		    return TONGYITYPE[0];
		else
		    return TONGYITYPE[Integer.parseInt(o.toString())];
	    }
	    case 32: // �Ƿ���ˣ�����ͨ��
	    {
		if (o == null)
		    return SHENGPITYPE[2];
		else
		    return SHENGPITYPE[Integer.parseInt(o.toString())];
	    }
	    case 33: // 0 Ժϵδ�� 1Ժϵͨ�� 2 �쵼δ�� 3 �쵼ͨ��
	    {
		if (o == null)
		    return YXSPTYPE[0];
		else
		    return YXSPTYPE[Integer.parseInt(o.toString())];
	    }
	    case 34: // �ƻ�״̬ ��zt��
	    {
		if (o == null)
		    return ZHIDAOJIHUATYPE[0];
		else
		    return ZHIDAOJIHUATYPE[Integer.parseInt(o.toString())];
	    }
	    case 35: // ���״̬ ��shzt��
	    {
		if (o == null)
		    return SHENHEZHUANGTAITYPE[0];
		else
		    return SHENHEZHUANGTAITYPE[Integer.parseInt(o.toString())];
	    }
	    case 36: // ����״̬��spzt��
	    {
		if (o == null)
		    return SHENPIZHUANGTAI[0];
		else
		    return SHENPIZHUANGTAI[Integer.parseInt(o.toString())];
	    }
	    case 37: // ѧ�ڱ�־(xqbz)
	    {
		if (o == null)
		    return XUEQIBIAOZHI[0];
		else
		    return XUEQIBIAOZHI[Integer.parseInt(o.toString())];
	    }
	    case 40://
	    {
		if (o == null)
		    return ACCOUNTTYPE[0];
		else
		    return ACCOUNTTYPE[Integer.parseInt(o.toString())];
	    }
	}

	return "";
    }

    /**
     * ���һ�������������
     * 
     * @param className
     * @return List
     * @throws ClassNotFoundException
     */
    public static List getClassAllProperty(String className)
	    throws ClassNotFoundException
    {
	Class c = Class.forName(className);
	Field[] f = c.getDeclaredFields();
	List ls = new ArrayList();
	for (int i = 0; i < f.length; i++)
	{
	    if (f[i].getType().getName().length() > 10)
		ls.add(f[i].getType().getName());
	}
	return ls;
    }
}
