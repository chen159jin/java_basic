package sendMail;

import sendMail.util.MailSenderInfo;
import sendMail.util.SimpleMailSender;
//@Controller
//@RequestMapping("/system")
//@Scope("prototype")
public class sendMailTest {
	//@RequestMapping(value="/sendMail")
	//@ResponseBody
	 public String sendMail(){
		try {
		/*	String zh = request.getParameter("account");
			String email = request.getParameter("email");
			String ty = request.getParameter("ty");
			List list = this.getQzDao().execSqlQueryToArrays(
					"select useraccounttype,userid,userrealname from frmuser where useraccount='"
							+ zh + "' and useraccounttype ='" + ty + "'");
			if (list != null && list.size() > 0) {
				if (!email.contains(zh)) {
					this.writeJsMessage(response, "alert('��������ȷ������!');");
					return null;
				}
				Object[] obj = (Object[]) list.get(0);
				Random random = new Random();
				int x = random.nextInt(899999);
				x = x + 100000;
				String randomPwd = "" + x;
				// �������
				String pwd = randomPwd;
				if (GlobalNames.PASSWORD_MD5) {
					MacMD5 md = new MacMD5();
					pwd = md.CalcMD5(randomPwd);
				}
				this.getQzDao().execSqlUpdate(
						"update frmuser set userpasswd='" + pwd
								+ "' where useraccount='" + zh + "'");*/
				String dzxx = "jiaowu-key@zhbit.com";//��������
				String hz = "";
				String hzs = dzxx.split("@")[1];
				if (hzs.indexOf("qq") != -1) {
					hz = "smtp.qq.com";
				} else if (hzs.indexOf("126") != -1) {
					hz = "smtp.126.com";
				} else if (hzs.indexOf("163") != -1) {
					hz = "smtp.163.com";
				} else {
					hz = "smtp." + hzs;
				}
				// ��������----------------------------------------------------
				MailSenderInfo mailInfo = new MailSenderInfo();
				mailInfo.setMailServerHost(hz);
				mailInfo.setMailServerPort("25");
				mailInfo.setValidate(true);
				mailInfo.setUserName("jiaowu-key");//���������û���
				mailInfo.setPassword("jiaowu1234");// ���͵���������
				mailInfo.setFromAddress(dzxx);//��������
				mailInfo.setToAddress("email");//�ռ��˵�����
				mailInfo.setSubject("��������ѧ�麣ѧԺʦ����������"); // �ʼ�����
				//String ahref = randomPwd;
				/*String nr = "�װ���"
						+ obj[2].toString()
						+ "�����ã�<br><br>&nbsp;&nbsp;������ϵͳ��������Ϊ:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ ahref + "<br><br>&nbsp;&nbsp;Ϊ��ȷ�������ʺŰ�ȫ���뼰ʱ��¼ϵͳ��������"
						+ "<br><br>&nbsp;&nbsp;��Ǳ��˲����뼰ʱ����񴦷�ӳ��<br><br>";
				nr += "<font color='red'>����ֱ�ӻظ����ʼ�</font><br><div style='width: 500px' align='center'>��������ѧ�麣ѧԺ����</div>";
				*/
				mailInfo.setContent("�ʼ����ı�����");// �ʼ����ı�����
				// ---------------------------����
				// �ʼ�-------------------------------------------------
				SimpleMailSender mail = new SimpleMailSender();
				String[] fjpath = new String[2];
				mail.sendTextMailhtml(mailInfo, hz, "0", fjpath);
			//	this.writeJsMessage(response,"alert('���ͳɹ�!���¼���������ȡ����');window.close();");

		//	} else {
				//this.writeJsMessage(response, "alert('��������ȷ�Ľ�ְ����ѧ���˺�!');");
		//	}
		} catch (Exception e) {
			e.printStackTrace();
		//	this.writeJsMessage(response, "alert('����ʧ��');");
		}
		return null;
	}
}
