package encryption;

import java.security.KeyFactory;  
import java.security.PrivateKey;  
import java.security.PublicKey;  
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.X509EncodedKeySpec;  
  
public class RSA_test {  
	  
    public static void main(String[] args) throws Exception {  
        String filepath="G:/tmp/";  
  
        //RSAEncrypt.genKeyPair(filepath);  
          
          
        System.out.println("--------------��Կ����˽Կ���ܹ���-------------------");  
        String plainText="ihep_��Կ����˽Կ����";  
        //��Կ���ܹ���  
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),plainText.getBytes());  
        String cipher=Base64.encode(cipherData);  
        //˽Կ���ܹ���  
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));  
        String restr=new String(res);  
        System.out.println("ԭ�ģ�"+plainText);  
        System.out.println("���ܣ�"+cipher);  
        System.out.println("���ܣ�"+restr);  
        System.out.println();  
          
        System.out.println("--------------˽Կ���ܹ�Կ���ܹ���-------------------");  
        plainText="ihep_˽Կ���ܹ�Կ����";  
        //˽Կ���ܹ���  
        cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),plainText.getBytes());  
        cipher=Base64.encode(cipherData);  
        //��Կ���ܹ���  
        res=RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), Base64.decode(cipher));  
        restr=new String(res);  
        System.out.println("ԭ�ģ�"+plainText);  
        System.out.println("���ܣ�"+cipher);  
        System.out.println("���ܣ�"+restr);  
        System.out.println();  
          
        System.out.println("---------------˽Կǩ������------------------");  
        String content="ihep_��������ǩ����ԭʼ����";  
        String signstr=RSASignature.sign(content,RSAEncrypt.loadPrivateKeyByFile(filepath));  
        System.out.println("ǩ��ԭ����"+content);  
        System.out.println("ǩ������"+signstr);  
        System.out.println();  
          
        System.out.println("---------------��ԿУ��ǩ��------------------");  
        System.out.println("ǩ��ԭ����"+content);  
        System.out.println("ǩ������"+signstr);  
          
        System.out.println("��ǩ�����"+RSASignature.doCheck(content, signstr, RSAEncrypt.loadPublicKeyByFile(filepath)));  
        System.out.println();  
          
    }  
}  
  
/** 
 * RSAǩ����ǩ�� 
 */  
 class RSASignature{  
      
    /** 
     * ǩ���㷨 
     */  
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";  
  
    /** 
    * RSAǩ�� 
    * @param content ��ǩ������ 
    * @param privateKey �̻�˽Կ 
    * @param encode �ַ������� 
    * @return ǩ��ֵ 
    */  
    public static String sign(String content, String privateKey, String encode)  
    {  
        try   
        {  
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decode(privateKey) );   
              
            KeyFactory keyf                 = KeyFactory.getInstance("RSA");  
            PrivateKey priKey               = keyf.generatePrivate(priPKCS8);  
  
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
  
            signature.initSign(priKey);  
            signature.update( content.getBytes(encode));  
  
            byte[] signed = signature.sign();  
              
            return Base64.encode(signed);  
        }  
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return null;  
    }  
      
    public static String sign(String content, String privateKey)  
    {  
        try   
        {  
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decode(privateKey) );   
            KeyFactory keyf = KeyFactory.getInstance("RSA");  
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);  
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
            signature.initSign(priKey);  
            signature.update( content.getBytes());  
            byte[] signed = signature.sign();  
            return Base64.encode(signed);  
        }  
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /** 
    * RSA��ǩ����� 
    * @param content ��ǩ������ 
    * @param sign ǩ��ֵ 
    * @param publicKey ����������̹�Կ 
    * @param encode �ַ������� 
    * @return ����ֵ 
    */  
    public static boolean doCheck(String content, String sign, String publicKey,String encode)  
    {  
        try   
        {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            byte[] encodedKey = Base64.decode(publicKey);  
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
  
          
            java.security.Signature signature = java.security.Signature  
            .getInstance(SIGN_ALGORITHMS);  
          
            signature.initVerify(pubKey);  
            signature.update( content.getBytes(encode) );  
          
            boolean bverify = signature.verify( Base64.decode(sign) );  
            return bverify;  
              
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return false;  
    }  
      
    public static boolean doCheck(String content, String sign, String publicKey)  
    {  
        try   
        {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            byte[] encodedKey = Base64.decode(publicKey);  
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
  
          
            java.security.Signature signature = java.security.Signature  
            .getInstance(SIGN_ALGORITHMS);  
          
            signature.initVerify(pubKey);  
            signature.update( content.getBytes() );  
          
            boolean bverify = signature.verify( Base64.decode(sign) );  
            return bverify;  
              
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return false;  
    }  
      
}  
