/**
 * ˵����ͨ��ָ���ĳ��ȷ���������
 * ��д�ߣ��ƺ��
 * ���ڣ�Apr 11, 2008
 */
package com.qzdatasoft.comm.dao.util;

import java.util.UUID;

/**
 * ͨ��ָ���ĳ��ȷ���������
 * 
 * @author �ƺ��
 * 
 */
public class Sequence
{

    private static char[] mark = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
	    '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
	    'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
	    'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
	    'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
	    'Z' };

    private static Sequence sequence = null;

    public static Sequence getInstance()
    {
	if (sequence == null)
	    sequence = new Sequence();
	return sequence;
    }

    private Sequence()
    {

    }

    /**
     * ����һ��ָ�����ȵĲ��ظ�����,�˷������ظ��Ŀ�����<br>
     * ������������ظ��Ļ�����ȡ���Ʊ��
     * �ǵ����������ϲ� ������==
     *  <br>
     * ���÷���ΪSequence.getInstance().getSequence(12)
     * 
     * @param length
     *            ���г���
     * @return ָ�����ȵ�������
     */
    public String getSequence(int length)
    {

	if (length == 32)
	{
	    String uuid = UUID.randomUUID().toString().replace("-", "")
		    .toUpperCase();
	    return uuid;
	} else
	{
	    StringBuffer sf = new StringBuffer(length);
	    for (int i = 0; i < length; i++)
	    {
		sf.append(mark[(int) ((1 - Math.random()) * 62)]);
	    }
	    return sf.toString();
	}
    }
}
