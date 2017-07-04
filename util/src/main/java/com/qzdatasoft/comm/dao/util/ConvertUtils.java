package com.qzdatasoft.comm.dao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import oracle.sql.CLOB;


public class ConvertUtils
{
    /**
     * ��Դ����ת��ΪString
     * @param o Դ����
     * @param defValue Ĭ��ֵ,ת��ʧ�ܽ����ظ�ֵ
     * @return ת�����Stringֵ,���ʧ�ܽ�����Ĭ��ֵ
     */
    public static String toString(Object o, String defValue)
    {
        if (null == o)
        {
            return defValue;
        }
        
        try
        {
            return o.toString();
        }
        catch (Exception e)
        {
            return defValue;
        }
    }
    
    /**
     * ��Դ����ת��ΪString
     * @param o Դ����
     * @return ת�����Stringֵ,���ʧ�ܽ����ؿմ�
     */
    public static String toString(Object o)
    {
        return toString(o, "");
    }
    
    /**
     * ��Դ����ת��Ϊdouble
     * @param o Դ����
     * @param defValue Ĭ��ֵ,ת��ʧ�ܽ����ظ�ֵ
     * @return ת�����doubleֵ,���ʧ�ܽ�����Ĭ��ֵ
     */
    public static double toDouble(Object o, double defValue)
    {
        if (null == o)
        {
            return defValue;
        }
        
        try
        {
            if (Boolean.class.isInstance(o))
            {
                boolean b = ((Boolean) o).booleanValue();
                return (b ? 1.0 : 0.0);   // �����boolֵ,true����1,false����0
            }
            
            return Double.parseDouble(o.toString());
        }
        catch (NumberFormatException e)
        {
            return defValue;
        }
    }
    
    /**
     * ��Դ����ת��Ϊdouble
     * @param o Դ����
     * @return ת�����doubleֵ,���ʧ�ܽ�����0
     */
    public static double toDouble(Object o)
    {
        return toDouble(o, 0.0);
    }
    
    /**
     * ��Դ����ת��Ϊfloat
     * @param o Դ����
     * @param defValue Ĭ��ֵ,ת��ʧ�ܽ����ظ�ֵ
     * @return ת�����floatֵ,���ʧ�ܽ�����Ĭ��ֵ
     */
    public static float toFloat(Object o, float defValue)
    {
        if (null == o)
        {
            return defValue;
        }
        
        try
        {
            if (Boolean.class.isInstance(o))
            {
                boolean b = ((Boolean) o).booleanValue();
                return (b ? 1.0f : 0.0f);   // �����boolֵ,true����1,false����0
            }
            
            return Float.parseFloat(o.toString());
        }
        catch (NumberFormatException e)
        {
            return defValue;
        }
    }
    
    /**
     * ��Դ����ת��Ϊfloat
     * @param o Դ����
     * @return ת�����floatֵ,���ʧ�ܽ�����0
     */
    public static float toFloat(Object o)
    {
        return toFloat(o, 0.0f);
    }
    
    /**
     * ��Դ����ת��Ϊint
     * @param o Դ����
     * @param defValue Ĭ��ֵ,ת��ʧ�ܽ����ظ�ֵ
     * @return ת�����intֵ,���ʧ�ܽ�����Ĭ��ֵ
     */
    public static int toInt(Object o, int defValue) {
        if (null == o) {
            return defValue;
        }
        
        double dbValue = ConvertUtils.toDouble(o);
        return (int) dbValue;
    }
    
    /**
     * ��Դ����ת��Ϊint
     * @param o Դ����
     * @return ת�����intֵ,���ʧ�ܽ�����Ĭ��ֵ0
     */
    public static int toInt(Object o)
    {
        return toInt(o, 0);
    }
    
    /**
     * ��Դ����ת��boolֵ
     * @param o Դ����
     * @param defValue ���Դ����Ϊ��,�򷵻ش�Ĭ��ֵ
     * @return ת�����������¹������ת��
     *         ���ת�����ֲ�Ϊ0,�򷵻�true,����ת���ַ����ٽ��бȽ�,
     *         ����ַ�����ֵΪtrue(���Դ�Сд),�򷵻�true,���򷵻�false
     */
    public static boolean toBool(Object o, boolean defValue)
    {
        if (null == o)
        {
            return defValue;
        }
        
        int intValue = ConvertUtils.toInt(o);
        if (intValue != 0)
        {
            return true;
        }
        
        String strValue = ConvertUtils.toString(o);
        return Boolean.parseBoolean(strValue);
    }
    
    /**
     * ��Դ����ת��boolֵ
     * @param o Դ����
     * @return ת�����������¹������ת��
     *         ���Դ����Ϊnull,�򷵻�false,����:
     *         ���ת�����ֲ�Ϊ0,�򷵻�true,����ת���ַ����ٽ��бȽ�,
     *         ����ַ�����ֵΪtrue(���Դ�Сд),�򷵻�true,���򷵻�false
     */
    public static boolean toBool(Object o)
    {
        return toBool(o, false);
    }
    
    /**
     * ���ַ���ת���������ַ���,����.7��ת����0.7
     * @param o Դ�ַ���
     * @param defaultVal Ĭ��ֵ
     * @param retSrcString ���Ϊtrue,��ת��ʧ��ʱ������Դ�ַ���,���򷵻�Ĭ��ֵ
     * @return
     */
    public static String toNumberString(String o, String defaultVal, boolean retSrcString)
    {
        try
        {
            if (null == o)
            {
                if (retSrcString) return o;
                else return defaultVal;
            }
            
            return toString(Double.parseDouble(o));
        }
        catch(NumberFormatException e)
        {
            if (retSrcString) return o;
            else return defaultVal;
        }
    }
    
    
    /**
     * ����ѧ��ѧ�ڣ�ת��Ϊ������ѯ�ַ���
     * 
     * @param xnxq01id
     * @return
     */
    public static String toFqcxToString(String xnxq01id){
        if(xnxq01id==null || "".equals(xnxq01id)){
            return "";
        }
        return "partition(p"+xnxq01id.replaceAll("-","_")+")";
    }
    
    /**
     * ���ַ���ת���������ַ���,����.7��ת����0.7
     * @param o Դ�ַ���
     * @return ת������ַ���,���ʧ�ܽ�����Դ�ַ���
     */
    public static String toNumberString(String o)
    {
        return toNumberString(o, "", true);
    }
    
    public static void main(String[] args)
    {
        System.out.println(toInt("23s"));
    }
    
    public static String clobToString(CLOB clob) throws SQLException, IOException {
	     String reString = "";
	     if( clob == null || clob.getCharacterOutputStream() == null )
	      return "";
	     Reader is = clob.getCharacterStream();// �õ���
	     BufferedReader br = new BufferedReader(is);
	     String s = br.readLine();
	     StringBuffer sb = new StringBuffer();
	     while (s != null) {// ִ��ѭ�����ַ���ȫ��ȡ����ֵ��StringBuffer��StringBufferת��STRING
	      sb.append(s);
	      s = br.readLine();
	     }
	     reString = sb.toString();
	     return reString;
	    }
}
