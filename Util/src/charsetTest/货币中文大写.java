package charsetTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class �������Ĵ�д {
	public static void main(String[] args) {
		System.out.println(ChineseCurrency(123000080.123));
	}
	
	public static String ChineseCurrency(double num){
		String s = new DecimalFormat("#.00").format(num);
		System.out.println(s);
		char[] digit = { '��', 'Ҽ', '��', '��', '��', '��', '½', '��', '��', '��' };
		String unit = "Ǫ��ʰ��Ǫ��ʰ��Ǫ��ʰ��Ǫ��ʰԪ�Ƿ�";
		String str=s.replaceAll("\\.", "");
//		System.out.println(str);
		//char[] strs=str.toCharArray();
		int l = unit.length();
		StringBuffer sb=new StringBuffer(unit.substring(l-str.length(), l));
		System.out.println(sb.toString());
		for (int i = str.length()-1; i >=0 ; i--) {
			System.out.println(str.charAt(i));
			sb = sb.insert(i, digit[(str.charAt(i)- 0x30)]);
		}
		System.out.println(sb.toString());
		s=sb.toString();
		//��ʾ�滻��ʰ����ۡ���Ǫ���ֶ�        //�������������ϵ�                    //ƥ�����ס�������Ԫ���ַ��滻�ɶ�Ӧ��ֵ
		s = s.replaceAll("��[ʰ��Ǫ]", "��").replaceAll("��{2,}", "��").replaceAll("��([����Ԫ])", "$1").replaceAll("��[�Ƿ�]", "");
		if (s.endsWith("��"))
			s += "���";
		if (!s.contains("��") && !s.contains("��") && s.contains("Ԫ"))
			s += "��";
		if (s.contains("��") && !s.contains("��") && !s.contains("��"))
			s = s.replace("Ԫ", "Ԫ��");
		return s;
	}
	
	/*Ҽ�ڷ�Ǫ�����������ʰ��ԪҼ�Ƿ���
	 * public static String ChineseCurrency(double num){
		char[] str={'��', 'Ҽ', '��', '��', '��', '��', '½', '��', '��', '��'};
		String unit = "Ǫ��ʰ��Ǫ��ʰ��Ǫ��ʰ��Ǫ��ʰԪ�Ƿ�";
	//	BigDecimal bg = new BigDecimal(num).setScale(2, RoundingMode.UP);//��������
	//	double n= bg.doubleValue();
		String[] numStr=String.valueOf(num).split("\\.");
		if(numStr.length==2){
			
		}
		char[] nums = String.valueOf(numStr).toCharArray(); //������ת��������
		StringBuilder strBu=new StringBuilder("");
		for (int i = nums.length; i > 0; i--) {
			strBu.append(str[Integer.parseInt(nums[i] + "")]);
//			System.out.println(nums[i]);
		}
		System.out.println(strBu);
		return null;
	}*/
	
}
