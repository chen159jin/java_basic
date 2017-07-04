package printParse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public class WebToolUtils {

	/**

	 * ��ȡ����IP��ַ

	 * 

	 * @throws SocketException

	 */

	public static String getLocalIP() throws UnknownHostException,
			SocketException {

		if (isWindowsOS()) {

			return InetAddress.getLocalHost().getHostAddress();

		} else {

			return getLinuxLocalIp();

		}

	}

	/**

	 * �жϲ���ϵͳ�Ƿ���Windows

	 * 

	 * @return

	 */

	public static boolean isWindowsOS() {

		boolean isWindowsOS = false;

		String osName = System.getProperty("os.name");

		if (osName.toLowerCase().indexOf("windows") > -1) {

			isWindowsOS = true;

		}

		return isWindowsOS;

	}

	/**

	 * ��ȡ����Host����

	 */

	public static String getLocalHostName() throws UnknownHostException {

		return InetAddress.getLocalHost().getHostName();

	}

	/**

	 * ��ȡLinux�µ�IP��ַ

	 * 

	 * @return IP��ַ

	 * @throws SocketException

	 */

	private static String getLinuxLocalIp() throws SocketException {

		String ip = "";

		try {

			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {

				NetworkInterface intf = en.nextElement();

				String name = intf.getName();

				if (!name.contains("docker") && !name.contains("lo")) {

					for (Enumeration<InetAddress> enumIpAddr = intf
							.getInetAddresses(); enumIpAddr.hasMoreElements();) {

						InetAddress inetAddress = enumIpAddr.nextElement();

						if (!inetAddress.isLoopbackAddress()) {

							String ipaddress = inetAddress.getHostAddress()
									.toString();

							if (!ipaddress.contains("::")
									&& !ipaddress.contains("0:0:")
									&& !ipaddress.contains("fe80")) {

								ip = ipaddress;

								System.out.println(ipaddress);

							}

						}

					}

				}

			}

		} catch (SocketException ex) {

			System.out.println("��ȡip��ַ�쳣");

			ip = "127.0.0.1";

			ex.printStackTrace();

		}

		System.out.println("IP:" + ip);

		return ip;

	}

	/**

	 * ��ȡ�û���ʵIP��ַ����ʹ��request.getRemoteAddr();��ԭ�����п����û�ʹ���˴��������ʽ������ʵIP��ַ,

	 * 

	 * ���ǣ����ͨ���˶༶�������Ļ���X-Forwarded-For��ֵ����ֹһ��������һ��IPֵ�������ĸ������������û��˵���ʵIP�أ�

	 * ����ȡX-Forwarded-For�е�һ����unknown����ЧIP�ַ�����

	 * 

	 * �磺X-Forwarded-For��192.168.1.110, 192.168.1.120, 192.168.1.130,

	 * 192.168.1.100

	 * 

	 * �û���ʵIPΪ�� 192.168.1.110

	 * 

	 * @param request

	 * @return

	 */

	public static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("HTTP_CLIENT_IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("HTTP_X_FORWARDED_FOR");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

		}

		return ip;

	}

	/**

	 * ��ָ��URL����GET����������

	 * 

	 * @param url

	 *            ���������URL

	 * @param param

	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��

	 * @return URL ������Զ����Դ����Ӧ���

	 */

	public static String sendGet(String url) {
		
		String result = "";
		
		BufferedReader in = null;
		
		try {
			String urlNameString = url;
			
			URL realUrl = new URL(urlNameString);
			
			// �򿪺�URL֮�������
			URLConnection connection = realUrl.openConnection();
			
			// ����ͨ�õ���������
			connection.setRequestProperty("accept", "*/*");
			
			connection.setRequestProperty("connection", "Keep-Alive");
			
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible;MSIE 6.0; Windows NT 5.1;SV1)");
			
			// ����ʵ�ʵ�����
			connection.connect();
			
			// ��ȡ������Ӧͷ�ֶ�
			Map<String, List<String>> map = connection.getHeaderFields();
			
			// �������е���Ӧͷ�ֶ�
			for (String key : map.keySet()) {
				
				System.out.println(key + "--->" + map.get(key));
				
			}
			
			// ���� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(connection
					.getInputStream()));
			
			String line;
			
			while ((line = in.readLine()) != null) {
				
				result += line;
				
			}
			
		} catch (Exception e) {
			
			System.out.println("����GET��������쳣��" + e);
			
			e.printStackTrace();
			
		}
		// ʹ��finally�����ر�������
		finally {
			
			try {
				
				if (in != null) {
					
					in.close();
					
				}
				
			} catch (Exception e2) {
				
				e2.printStackTrace();
				
			}
			
		}
		
		return result;
		
	}

	/**

	 * ��ָ�� URL ����POST����������

	 * 

	 * @param url

	 *            ��������� URL

	 * @param param

	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��

	 * @return ������Զ����Դ����Ӧ���

	 */

	public static void sendPost(String pathUrl) {

		// PrintWriter out = null;

		// BufferedReader in = null;

		// String result = "";

		// try {

		// URL realUrl = new URL(url);

		// // �򿪺�URL֮�������

		// URLConnection conn = realUrl.openConnection();

		// // ����ͨ�õ���������

		// conn.setRequestProperty("accept", "*/*");

		// conn.setRequestProperty("connection", "Keep-Alive");

		// conn.setRequestProperty("user-agent",

		// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

		// // ����POST�������������������

		// conn.setDoOutput(true);

		// conn.setDoInput(true);

		// // ��ȡURLConnection�����Ӧ�������

		// out = new PrintWriter(conn.getOutputStream());

		// // �����������

		// out.print(param);

		// // flush������Ļ���

		// out.flush();

		// // ����BufferedReader����������ȡURL����Ӧ

		// in = new BufferedReader(

		// new InputStreamReader(conn.getInputStream()));

		// String line;

		// while ((line = in.readLine()) != null) {

		// result += line;

		// }

		// } catch (Exception e) {

		// System.out.println("���� POST ��������쳣��"+e);

		// e.printStackTrace();

		// }

		// //ʹ��finally�����ر��������������

		// finally{

		// try{

		// if(out!=null){

		// out.close();

		// }

		// if(in!=null){

		// in.close();

		// }

		// }

		// catch(IOException ex){

		// ex.printStackTrace();

		// }

		// }

		// return result;

		try {

			// ��������

			URL url = new URL(pathUrl);

			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();

			// //������������

			httpConn.setDoOutput(true);// ʹ�� URL ���ӽ������

			httpConn.setDoInput(true);// ʹ�� URL ���ӽ�������

			httpConn.setUseCaches(false);// ���Ի���

			httpConn.setRequestMethod("POST");// ����URL���󷽷�

			String requestString = "�ͷ���Ҫ��������ʽ���͵�����˵�����...";

			// ������������

			// ��������ֽ����ݣ������������ı��룬���������������˴����������ı���һ��

			byte[] requestStringBytes = requestString.getBytes("utf-8");

			httpConn.setRequestProperty("Content-length", ""
					+ requestStringBytes.length);

			httpConn.setRequestProperty("Content-Type",
					"   application/x-www-form-urlencoded");

			httpConn.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����

			httpConn
					.setRequestProperty("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

			httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");

			httpConn.setRequestProperty("Accept-Language",
					"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");

			httpConn
					.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0");

			httpConn.setRequestProperty("Upgrade-Insecure-Requests", "1");

			/*httpConn.setRequestProperty("account", name);

			httpConn.setRequestProperty("passwd", pwd);

			httpConn.setRequestProperty("phone", phone);

			httpConn.setRequestProperty("content", content);*/

			// �������������д������
			OutputStream outputStream = httpConn.getOutputStream();

			outputStream.write(requestStringBytes);

			outputStream.close();

			// �����Ӧ״̬

			int responseCode = httpConn.getResponseCode();

			if (HttpURLConnection.HTTP_OK == responseCode) {// ���ӳɹ�

				// ����ȷ��Ӧʱ��������

				StringBuffer sb = new StringBuffer();

				String readLine;

				BufferedReader responseReader;

				// ������Ӧ�����������������Ӧ������ı���һ��

				responseReader = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream(), "utf-8"));

				while ((readLine = responseReader.readLine()) != null) {

					sb.append(readLine).append("\n");

				}

				responseReader.close();

			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

	/**
	 * 
	 * ִ��һ��HTTP POST���󣬷���������Ӧ��HTML
	 * 
	 * @param url �����URL
	 * 
	 */

	public static void doPost(String url) {

		// ����Ĭ�ϵ�httpClientʵ��.

		CloseableHttpClient httpclient = HttpClients.createDefault();

		// ����httppost

		HttpPost httppost = new HttpPost(url);

		// ������������

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();

//		formparams.add(new BasicNameValuePair("account", name));
//
//		formparams.add(new BasicNameValuePair("passwd", pwd));
//
//		formparams.add(new BasicNameValuePair("phone", phone));
//
//		formparams.add(new BasicNameValuePair("content", content));

		UrlEncodedFormEntity uefEntity;

		try {

			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

			httppost.setEntity(uefEntity);

			System.out.println("executing request " + httppost.getURI());

			CloseableHttpResponse response = httpclient.execute(httppost);

			try {

				HttpEntity entity = response.getEntity();

				if (entity != null) {

					System.out
							.println("--------------------------------------");

					System.out.println("Response content: "
							+ EntityUtils.toString(entity, "UTF-8"));

					System.out
							.println("--------------------------------------");

				}

			} finally {

				response.close();

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			// �ر�����,�ͷ���Դ

			try {

				httpclient.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

}