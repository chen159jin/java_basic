package encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MD5 {   
	 private static Map users = new HashMap();   

	 public static void main(String[] args){  

	        String userName = "zyg";   
	        String password = "123";   
	        registerUser(userName,password);   

	        userName = "changong";   
	        password = "456";   
	        registerUser(userName,password);   

	        String loginUserId = "zyg";   
	        String pwd = "123";   
	        try {   
	            if(loginValid(loginUserId,pwd)){   
	                System.out.println("��ӭ��½������");   
	            }else{   
	                System.out.println("����������������룡����");   
	            }   
	        } catch (NoSuchAlgorithmException e) {   
	            e.printStackTrace();   
	        } catch (UnsupportedEncodingException e) {   
	            e.printStackTrace();   
	        }    
	    }   
	 /**  
     * ע���û�  
     *   
     * @param userName  
     * @param password  
     */  
    public static void registerUser(String userName,String password){   
        String encryptedPwd = null;   
        try {   
            encryptedPwd = MD5.getEncryptedPwd(password);   

            System.out.println("���ܺ���û�����"+encryptedPwd);
            users.put(userName, encryptedPwd);   

        } catch (NoSuchAlgorithmException e) {   
            e.printStackTrace();   
        } catch (UnsupportedEncodingException e) {   
            e.printStackTrace();   
        }   
    }   

    /**  
     * ��֤��½  
     *   
     * @param userName  
     * @param password  
     * @return  
     * @throws UnsupportedEncodingException   
     * @throws NoSuchAlgorithmException   
     */  
    public static boolean loginValid(String userName,String password)    
                throws NoSuchAlgorithmException, UnsupportedEncodingException{   
        String pwdInDb = (String)users.get(userName); 
        System.out.println(pwdInDb);
        if(null!=pwdInDb){ // ���û�����   
                return MD5.validPassword(password, pwdInDb);   
        }else{   
            System.out.println("�����ڸ��û�������");   
            return false;   
        }   
    }   

    private static final String HEX_NUMS_STR="0123456789ABCDEF";   
    private static final Integer SALT_LENGTH = 12;   

    /**   
     * ��16�����ַ���ת�����ֽ�����   
     * @param hex   
     * @return   
     */  
    public static byte[] hexStringToByte(String hex) {   
        int len = (hex.length() / 2);   
        byte[] result = new byte[len];   
        char[] hexChars = hex.toCharArray();   
        for (int i = 0; i < len; i++) {   
            int pos = i * 2;   
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4    
                            | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));   
        }   
        return result;   
    }   


    /**  
     * ��ָ��byte����ת����16�����ַ���  
     * @param b  
     * @return  
     */  
    public static String byteToHexString(byte[] b) {   
        StringBuffer hexString = new StringBuffer();   
        for (int i = 0; i < b.length; i++) {   
            String hex = Integer.toHexString(b[i] & 0xFF);   
            if (hex.length() == 1) {   
                hex = '0' + hex;   
            }   
            hexString.append(hex.toUpperCase());   
        }   
        return hexString.toString();   
    }   

    /**  
     * ��֤�����Ƿ�Ϸ�  
     * @param password  
     * @param passwordInDb  
     * @return  
     * @throws NoSuchAlgorithmException  
     * @throws UnsupportedEncodingException  
     */  
    public static boolean validPassword(String password, String passwordInDb)   
            throws NoSuchAlgorithmException, UnsupportedEncodingException {   
        //��16�����ַ�����ʽ����ת�����ֽ�����   
        byte[] pwdInDb = hexStringToByte(passwordInDb);   
        //�����α���   
        byte[] salt = new byte[SALT_LENGTH];   
        //���δ����ݿ��б���Ŀ����ֽ���������ȡ����   
        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);   
        //������ϢժҪ����   
        MessageDigest md = MessageDigest.getInstance("MD5");   
        //�������ݴ�����ϢժҪ����   
        md.update(salt);   
        //����������ݴ�����ϢժҪ����   
        md.update(password.getBytes("UTF-8"));   
        //��������������ϢժҪ   
        byte[] digest = md.digest();   
        //����һ���������ݿ��п�����ϢժҪ�ı���   
        byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];   
        //ȡ�����ݿ��п������ϢժҪ   
        System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);   
        //�Ƚϸ�������������ɵ���ϢժҪ�����ݿ�����ϢժҪ�Ƿ���ͬ   
        if (Arrays.equals(digest, digestInDb)) {   
            //������ȷ���ؿ���ƥ����Ϣ   
            return true;   
        } else {   
            //�����ȷ���ؿ��ƥ����Ϣ   
            return false;   
        }   
    }   


    /**  
     * ��ü��ܺ��16������ʽ����  
     * @param password  
     * @return  
     * @throws NoSuchAlgorithmException  
     * @throws UnsupportedEncodingException  
     */  
    public static String getEncryptedPwd(String password)   
            throws NoSuchAlgorithmException, UnsupportedEncodingException {   
        //�������ܺ�Ŀ����������   
        byte[] pwd = null;   
        //�����������   
        SecureRandom random = new SecureRandom();   
        //�������������   
        byte[] salt = new byte[SALT_LENGTH];   
        //������������α�����   
        random.nextBytes(salt);   

        //������ϢժҪ����   
        MessageDigest md = null;   
        //������ϢժҪ   
        md = MessageDigest.getInstance("MD5");   
        //�������ݴ�����ϢժҪ����   
        md.update(salt);   
        //����������ݴ�����ϢժҪ����   
        md.update(password.getBytes("UTF-8"));   
        //�����ϢժҪ���ֽ�����   
        byte[] digest = md.digest();   

        //��ΪҪ�ڿ�����ֽ������д���Σ����Լ����ε��ֽڳ���   
        pwd = new byte[digest.length + SALT_LENGTH];   
        //���ε��ֽڿ��������ɵļ��ܿ����ֽ������ǰ12���ֽڣ��Ա�����֤����ʱȡ����   
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);   
        //����ϢժҪ���������ܿ����ֽ�����ӵ�13���ֽڿ�ʼ���ֽ�   
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);   
        //���ֽ������ʽ���ܺ�Ŀ���ת��Ϊ16�����ַ�����ʽ�Ŀ���   
        return byteToHexString(pwd);   
    }   
}  
