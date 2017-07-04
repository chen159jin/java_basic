package sendMail.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class SimpleMailSender  {    
	/**   
	  * ���ı���ʽ�����ʼ�   
	  * @param mailInfo �����͵��ʼ�����Ϣ   
	 * @throws Exception  
	  */    
	    public void sendTextMail(MailSenderInfo mailInfo,String hz,String sffj,String[] fjpath) throws Exception  {    
	    	Properties props = new Properties();
	        props.put("mail.smtp.host", hz);
	        props.put("mail.smtp.auth", "true");
	        //�������ʼ��Ự
	        Session session = Session.getInstance(props);
	        //������Ϣ��
	        MimeMessage message = new MimeMessage(session); 
	        //������ַ
	        Address address = new InternetAddress(mailInfo.getFromAddress());
	        message.setFrom(address);
	        //�ռ���ַ
	        Address toAddress = new InternetAddress(mailInfo.getToAddress());
	        message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
	        
	        //����
	        message.setSubject(mailInfo.getSubject());
	        	Multipart mp = new MimeMultipart(); 
	        	MimeBodyPart text=new MimeBodyPart();
	        	text.setText(mailInfo.getContent());
	        	mp.addBodyPart(text); 
		        for(int i=0;i<fjpath.length;i++){
		        	MimeBodyPart mbp=new MimeBodyPart(); 
		        	FileDataSource fds=new FileDataSource(fjpath[i]); 
			        mbp.setDataHandler(new DataHandler(fds)); 
			        mbp.setFileName(MimeUtility.encodeText(fds.getName())); 
			        mp.addBodyPart(mbp); 
		        }
		        message.setContent(mp);
	        
	        Transport transport = session.getTransport("smtp");
	        transport.connect(hz, mailInfo.getFromAddress(), mailInfo.getPassword());
	        //����
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();   
	    }  
	    
	    /**   
		  * ��html��ʽ�����ʼ�   
		  * @param mailInfo �����͵��ʼ�����Ϣ   
		 * @throws Exception  
		  */    
		    public void sendTextMailhtml(MailSenderInfo mailInfo,String hz,String sffj,String[] fjpath) throws Exception  {    
		    	Properties props = new Properties();
		        props.put("mail.smtp.host", hz);
		        props.put("mail.smtp.auth", "true");
		        //�������ʼ��Ự
		        Session session = Session.getInstance(props);
		        //������Ϣ��
		        MimeMessage message = new MimeMessage(session); 
		        //������ַ
		        Address address = new InternetAddress(mailInfo.getFromAddress());
		        message.setFrom(address);
		        //�ռ���ַ
		        Address toAddress = new InternetAddress(mailInfo.getToAddress());
		        message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
		        
		        //����
		        message.setSubject(mailInfo.getSubject());
	        	Multipart mp = new MimeMultipart(); 
	        	MimeBodyPart text=new MimeBodyPart();
	        	text.setContent(mailInfo.getContent(),"text/html;charset=gb2312");
	        	mp.addBodyPart(text); 
		        message.setContent(mp);
		        Transport transport = session.getTransport("smtp");
		        transport.connect(hz, mailInfo.getFromAddress(), mailInfo.getPassword());
		        //����
		        transport.sendMessage(message, message.getAllRecipients());
		        transport.close();   
		    }  
	}   
