package encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * ���ܴ������ļ���
 * @author Jin
 *
 */
public class AES {
	public static void main(String[] args) {
		//����������
		String str = "chenjin";
		//���룬����Ҫ��8�ı���
		String password = "12345678";
		byte[] result=encryp(str,password);
		System.out.println(result);
		byte[] dencryResult= decrypt(result, password);
		//System.out.println(dencryResult.toString());
		System.out.println(new String(dencryResult));
	}
	/**
	 * ����
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] encryp(String content,String password){
		try {
			//�����ṩ���Գƣ���Կ�������Ĺ��ܡ�
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			//��ָ�����������û��ṩ�����Դ��ʼ������Կ������
			kgen.init(128,new SecureRandom(password.getBytes()));
			SecretKey secretKey=kgen.generateKey();//����һ����Կ��
			byte[] enCondeFormat= secretKey.getEncoded();//����Կ��Ϊbyte����
			//�������� provider �޹صķ�ʽָ��һ����Կ��
			//����ʹ�ô���������һ���ֽ����鹹��һ�� SecretKey��������ͨ��һ�������� provider �ģ�SecretKeyFactory��
			SecretKeySpec key = new SecretKeySpec(enCondeFormat, "AES");
			//����Ϊ���ܺͽ����ṩ���빦�ܡ�
			//Ӧ�ó������ Cipher �� getInstance ��������������ת�� �����ƴ��ݸ�����������ָ���ṩ�ߵ����ƣ���ѡ���� 
			Cipher cipher=Cipher.getInstance("AES");//����������
			byte[] byteContent=content.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE,key);//��ʼ��
			byte[] result = cipher.doFinal(byteContent);//����
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ����
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password){
		try {
			KeyGenerator kegen=KeyGenerator.getInstance("AES");
			kegen.init(128,new SecureRandom(password.getBytes()));
			SecretKey secretKey = kegen.generateKey();
			byte[] enCodeFormat= secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher= Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE,key);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
}