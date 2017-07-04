/**
 * ˵�����ı������ڴ�����
 * ��д�ߣ�лƽ
 * ���ڣ�Aug 8, 2007
 * �޸���־��
 *    лƽ Aug 8, 2007 �������з������Ե�����ע��
 * ����ǿ�ǿƼ���Ȩ���С�
 */
package stringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
//import uk.ltd.getahead.dwr.WebContext;
//import uk.ltd.getahead.dwr.WebContextFactory;

/**
 * <p>
 * Title:�ı������ڴ�����
 * <p>
 * Description:ͨ�ù�����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: ����ǿ�ǿƼ���չ���޹�˾
 * </p>
 * 
 */
public class StringUtils {
	/**
	 * "����
	 */
	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();

	/**
	 * &����
	 */
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();

	/**
	 * <����
	 */
	private static final char[] LT_ENCODE = "&lt;".toCharArray();

	/**
	 * >����
	 */
	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	/**
	 * ժҪ�㷨
	 */
	private static MessageDigest digest = null;

	/**
	 * �������
	 */
	public static String xh = "";

	/**
	 * �����ַ���
	 */
	public static String code = "";
	/**
	 * �ܱ��볤��
	 */
	public static int len = 80;

	/**
	 * ����û������Ƿ��зǷ��ַ�
	 * 
	 * @param username
	 * @return boolean
	 */
	public static boolean validateUserName(String username) {
		Pattern p = Pattern.compile("^\\w+$");
		Matcher m = p.matcher(username);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * ���ַ�����ת�����ַ�������","�ָ����Ҽ����ַ�"'"��
	 * 
	 * @param values
	 * @return String
	 */
	public static String Array3String(String[] values) {
		StringBuffer result = new StringBuffer();
		if (values == null)
			return "";
		for (int i = 0; i < values.length; i++) {
			if (result.length() <= 0)
				result.append("'" + values[i] + "'");
			else
				result.append(",").append("'" + values[i] + "'");
		}
		return result.toString();
	}

	/**
	 * ���ַ�����ת�����ַ�������","�ָ�
	 * 
	 * @param values
	 * @return String
	 */
	public static String Array2String(String[] values) {
		StringBuffer result = new StringBuffer();
		if (values == null)
			return "";
		for (int i = 0; i < values.length; i++) {
			if (result.length() <= 0)
				result.append(values[i]);
			else
				result.append(",").append(values[i]);
		}
		return result.toString();
	}

	/**
	 * ����������ת�����ַ�������","�ָ�
	 * 
	 * @param values
	 * @return String
	 */
	public static String Array2String(Object[] values) {
		StringBuffer result = new StringBuffer();
		if (values == null)
			return "";
		for (int i = 0; i < values.length; i++) {
			if (result.length() <= 0)
				result.append(values[i].toString());
			else
				result.append(",").append(values[i].toString());
		}
		return result.toString();
	}

	/**
	 * ��LIST����ת�����ַ���
	 * 
	 * @param values
	 * @return String
	 */
	public static String Array2String(List values) {
		StringBuffer result = new StringBuffer();
		if (values == null)
			return "";
		for (int i = 0; i < values.size(); i++) {
			if (result.length() <= 0)
				result.append(values.get(i).toString());
			else
				result.append(",").append(values.get(i).toString());
		}
		return result.toString();
	}

	/**
	 * ���ı�ת����64λ����
	 * 
	 * @param txt
	 * @return String
	 */
	public static String base64Encode(String txt) {
		if (txt != null && txt.length() > 0) {
			txt = new BASE64Encoder().encode(txt.getBytes());
		}
		return txt;
	}

	/**
	 * ��BYTE��ʽ���ַ�ת��Ϊ64λ����
	 * 
	 * @param txt
	 * @return String
	 */
	public static String base64Encode(byte[] txt) {
		String encodeTxt = "";
		if (txt != null && txt.length > 0) {
			encodeTxt = new BASE64Encoder().encode(txt);
		}
		return encodeTxt;
	}

	/**
	 * ��64λ���뷽ʽת��Ϊ�ַ���
	 * 
	 * @param txt
	 * @return String
	 */
	public static String base64decode(String txt) {
		if (txt != null && txt.length() > 0) {
			byte[] buf;
			try {
				buf = new BASE64Decoder().decodeBuffer(txt);
				txt = new String(buf);
			} catch (IOException ex) {
			}
		}
		return txt;
	}

	/**
	 * ��64λ�����ַ���ת��Ϊbyte��ʽ
	 * 
	 * @param txt
	 * @return byte[]
	 */
	public static byte[] base64decodebyte(String txt) {
		byte[] buf = null;
		if (txt != null && txt.length() > 0) {
			try {
				buf = new BASE64Decoder().decodeBuffer(txt);
			} catch (IOException ex) {
			}
		}
		return buf;
	}

	/**
	 * �滻�ַ���
	 * 
	 * @param line
	 *            Դ�ַ���
	 * @param oldString
	 *            ��Ҫ�滻���ַ���
	 * @param newString
	 *            �滻Ϊ���ַ���
	 * @return String
	 */
	public static final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * �滻�ַ��������Դ�Сд
	 * 
	 * @param line
	 *            Դ�ַ���
	 * @param oldString
	 *            ��Ҫ�滻���ַ���
	 * @param newString
	 *            �滻Ϊ���ַ���
	 * @return String
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * �滻�ַ��������Դ�Сд������ס���滻��λ��
	 * 
	 * @param line
	 *            Դ�ַ���
	 * @param oldString
	 *            ��Ҫ�滻���ַ���
	 * @param newString
	 *            �滻Ϊ���ַ���
	 * @param count
	 *            ��¼���滻��λ����Ϣ
	 * @return String
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 0;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * �滻�ַ���������ס���滻��λ��
	 * 
	 * @param line
	 *            Դ�ַ���
	 * @param oldString
	 *            ��Ҫ�滻���ַ���
	 * @param newString
	 *            �滻Ϊ���ַ���
	 * @param count
	 *            ��¼���滻��λ����Ϣ
	 * @return String
	 */
	public static final String replace(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * �����е�HTML��ǽ���ת�� ����(ie, &lt;b&gt;&lt;table&gt;, etc)
	 * 
	 * @param in
	 * @return String
	 */
	public static final String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * ʹ��MD5�㷨�������ַ�����HASH�ֶ� Hashes a String using the Md5 algorithm and returns
	 * the result as a String of hexadecimal numbers. This method is
	 * synchronized to avoid excessive MessageDigest object creation. If calling
	 * this method becomes a bottleneck in your code, you may wish to maintain a
	 * pool of MessageDigest objects instead of using this method.
	 * <p>
	 * A hash is a one-way function -- that is, given an input, an output is
	 * easily computed. However, given the output, the input is almost
	 * impossible to compute. This is useful for passwords since we can store
	 * the hash and a hacker will then have a very hard time determining the
	 * original password.
	 * <p>
	 * In Jive, every time a user logs in, we simply take their plain text
	 * password, compute the hash, and compare the generated hash to the stored
	 * hash. Since it is almost impossible that two passwords will generate the
	 * same hash, we know if the user gave us the correct password or not. The
	 * only negative to this system is that password recovery is basically
	 * impossible. Therefore, a reset password method is used instead.
	 * 
	 * @param data
	 *            the String to compute the hash of.
	 * @return a hashed version of the passed-in String
	 */
	public synchronized static final String hash(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest. "
						+ "We will be unable to function normally.");
				nsae.printStackTrace();
			}
		}
		// Now, compute hash.
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}

	/**
	 * ��bytes����ת����16���Ʊ����ַ���
	 * 
	 * @param bytes
	 * @return String
	 */
	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * ��16���Ƶı���ת����bytes����
	 * 
	 * @param hex
	 * @return String
	 */
	public static final byte[] decodeHex(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = newByte;
			byteCount++;
		}
		return bytes;
	}

	/**
	 * ��16����charת�����ַ���
	 * 
	 * @param ch
	 * @return String
	 */
	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}

	/**
	 * ʹ��BreakIterator.wordInstance()���зִ�ת�� Converts a line of text into an
	 * array of lower case words using a BreakIterator.wordInstance().
	 * <p>
	 * 
	 * This method is under the Jive Open Source Software License and was
	 * written by Mark Imbriaco.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @return text broken up into an array of words.
	 */
	public static final String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		ArrayList wordList = new ArrayList();
		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);
		int start = 0;
		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary
				.next()) {
			String tmp = text.substring(start, end).trim();
			tmp = replace(tmp, "+", "");
			tmp = replace(tmp, "/", "");
			tmp = replace(tmp, "\\", "");
			tmp = replace(tmp, "#", "");
			tmp = replace(tmp, "*", "");
			tmp = replace(tmp, ")", "");
			tmp = replace(tmp, "(", "");
			tmp = replace(tmp, "&", "");
			if (tmp.length() > 0) {
				wordList.add(tmp);
			}
		}
		return (String[]) wordList.toArray(new String[wordList.size()]);
	}

	/**
	 * Array of numbers and letters of mixed case. Numbers appear in the list
	 * twice so that there is a more equal chance that a number will be picked.
	 * We can use the array to get a random number or letter by picking a random
	 * array index.
	 */
	/**
	 * ���ֺ��ַ�char[] random����������
	 */
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

	/**
	 * ʹ������ķ�ʽȡ�������ַ�������numbersAndLetters�еĶ���
	 * 
	 * @param length
	 * @return String
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[getRandom(71)];
		}
		return new String(randBuffer);
	}

	/**
	 * ���һ�������
	 * 
	 * @param maxRandom
	 *            �������ȡֵ����
	 * @return int
	 */
	public static final int getRandom(int maxRandom) {
		return (int) ((1 - Math.random()) * maxRandom);
	}

	/**
	 * Intelligently chops a String at a word boundary (whitespace) that occurs
	 * at the specified index in the argument or before. However, if there is a
	 * newline character before <code>length</code>, the String will be chopped
	 * there. If no newline or whitespace is found in <code>string</code> up to
	 * the index <code>length</code>, the String will chopped at
	 * <code>length</code>.
	 * <p>
	 * For example, chopAtWord("This is a nice String", 10) will return "This is
	 * a" which is the first word boundary less than or equal to 10 characters
	 * into the original String.
	 * 
	 * @param string
	 *            the String to chop.
	 * @param length
	 *            the index in <code>string</code> to start looking for a
	 *            whitespace boundary at.
	 * @return a substring of <code>string</code> whose length is less than or
	 *         equal to <code>length</code>, and that is chopped at whitespace.
	 */
	public static final String chopAtWord(String string, int length) {
		if (string == null) {
			return string;
		}

		char[] charArray = string.toCharArray();
		int sLength = string.length();
		if (length < sLength) {
			sLength = length;
		}

		// First check if there is a newline character before length; if so,
		// chop word there.
		for (int i = 0; i < sLength - 1; i++) {
			// Windows
			if (charArray[i] == '\r' && charArray[i + 1] == '\n') {
				return string.substring(0, i + 1);
			}
			// Unix
			else if (charArray[i] == '\n') {
				return string.substring(0, i);
			}
		}
		// Also check boundary case of Unix newline
		if (charArray[sLength - 1] == '\n') {
			return string.substring(0, sLength - 1);
		}

		// Done checking for newline, now see if the total string is less than
		// the specified chop point.
		if (string.length() < length) {
			return string;
		}

		// No newline, so chop at the first whitespace.
		for (int i = length - 1; i > 0; i--) {
			if (charArray[i] == ' ') {
				return string.substring(0, i).trim();
			}
		}

		// Did not find word boundary so return original String chopped at
		// specified length.
		return string.substring(0, length);
	}

	/**
	 * ��XML��������е��ַ�����ת���ַ�
	 * 
	 * @param string
	 * @return String
	 */
	public static final String escapeForXML(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * �������ַ�ת����ת���ַ�
	 * 
	 * @param string
	 * @return String
	 */
	public static final String escapeForSpecial(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * ��ת���ַ�ת����XML�еı�׼�ַ�
	 * 
	 * @param string
	 * @return String
	 */
	public static final String unescapeFromXML(String string) {
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		return replace(string, "&amp;", "&");
	}

	private static final char[] zeroArray = "0000000000000000".toCharArray();

	/**
	 * ָ���ַ������ȣ�����ļ�0
	 * 
	 * @param string
	 * @param length
	 * @return String
	 */
	public static final String zeroPadString(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		StringBuffer buf = new StringBuffer(length);
		buf.append(zeroArray, 0, length - string.length()).append(string);
		return buf.toString();
	}

	/**
	 * ������ת���ɺ��룬��15λ��¼������Ĳ�0
	 * 
	 * @param date
	 * @return String
	 */
	public static final String dateToMillis(Date date) {
		return zeroPadString(Long.toString(date.getTime()), 15);
	}

	/**
	 * ����������ת�����ַ���������ָ���ķָ���ָ�
	 * 
	 * @param c
	 *            ����
	 * @param spilt
	 *            �ָ���
	 * @return String
	 */
	public static final String collectionToString(Collection c, String spilt) {
		if (c == null) {
			return null;
		}
		if (spilt == null) {
			return null;
		}
		String ret = "";
		ArrayList a = new ArrayList(c);
		try {
			for (int i = 0; i < a.size(); i++) {
				String t = (String) a.get(i);
				if (i == a.size() - 1) {
					ret = ret + t;
				} else {
					ret = ret + t + spilt;
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����ָ���ĳ��ȹ���һ�����룬������û�а���0,o,l��I���������
	 * 
	 * @param length
	 * @return String
	 */
	public static String genPassword(int length) {
		if (length < 1) {
			return null;
		}
		String[] strChars = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
				"b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n",
				"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "a" };
		// û��0,o,l��I���������
		StringBuffer strPassword = new StringBuffer();
		int nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
		for (int i = 0; i < length; i++) {
			nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
			strPassword.append(strChars[nRand % (strChars.length - 1)]);
		}
		return strPassword.toString();
	}

	/**
	 * ����ָ���ĳ��ȹ���һ�����룬���������
	 * 
	 * @param length
	 * @return String
	 */
	public static String genNumPassword(int length) {
		if (length < 1) {
			return null;
		}
		String[] strChars = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		StringBuffer strPassword = new StringBuffer();
		int nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
		for (int i = 0; i < length; i++) {
			nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
			strPassword.append(strChars[nRand % (strChars.length - 1)]);
		}
		return strPassword.toString();
	}

	/**
	 * ����ָ���ĳ��ȹ���һ���ո�����ַ���
	 * 
	 * @param length
	 * @return String
	 */
	public static String genEmptyString(int length) {
		if (length < 1) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * ����URL���õ����URL�е�HTML����
	 * 
	 * @param str
	 * @return String
	 */
	public static String getHTML(String str) {
		// �жϸ�ҳ���Ƿ����
		if (urlExists(str)) {
			StringBuffer sb = new StringBuffer();
			try {
				URL urlObj = new URL(str);
				InputStream streamObj = urlObj.openStream();
				InputStreamReader readerObj = new InputStreamReader(streamObj);
				BufferedReader buffObj = new BufferedReader(readerObj);
				String strLine;
				while ((strLine = buffObj.readLine()) != null) {
					sb.append(strLine);
				}
				buffObj.close();
			} catch (Exception e) {
				return sb.toString();
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	/**
	 * ����һ�����ֵõ�����ASCII��
	 * 
	 * @param digit
	 * @return String
	 */
	public static String getAsciiString(int digit) {
		byte ret[] = new byte[1];
		ret[0] = (byte) digit;
		return new String(ret);
	}

	/**
	 * ����һ���ַ����õ�����ASCII��
	 * 
	 * @param s
	 * @return String
	 */
	public static int getAsciiNum(String s) {
		if (s.length() < 1) {
			return 0;
		}
		byte b = s.getBytes()[0];
		return b;
	}

	/**
	 * ��õ�ǰʱ�䣬��ת����yyyyMMddHHmmss��ʽ
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		return formatDateByFormatStr(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * ��ʽ������yyyy-MM-dd
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		return formatDateByFormatStr(date, "yyyy-MM-dd");
	}

	/**
	 * ��ʽ������ʱ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateTime(Date date) {
		return formatDateByFormatStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ��ʽ������yyyy/MM/dd
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate2(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy/MM/dd");
	}

	/**
	 * ��ʽ������MM-dd HH:mm
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate3(Date myDate) {
		return formatDateByFormatStr(myDate, "MM-dd HH:mm");
	}

	/**
	 * ��ʽ������yyyyMMdd
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate4(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyyMMdd");
	}

	/**
	 * ��ʽ������yyyy-MM-dd���Էֽ�������ʵ��
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate5(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy-MM-dd");
	}

	/**
	 * ��ʽ������ʱ��yyyy-MM-dd HH:mm
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate6(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy-MM-dd HH:mm");
	}

	/**
	 * ��ʽ������ʱ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate6a(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ��ʽ������yyyy��MM��dd��
	 * 
	 * @param myDate
	 * @return String
	 */
	public static String formatDate7(Date myDate) {
		return formatDateByFormatStr(myDate, "yyyy��MM��dd��");
	}

	/**
	 * ͨ����ʽ���ֶ�����ʽ������
	 * 
	 * @param myDate
	 *            ���������
	 * @param formatStr
	 *            ��Ҫ��ʽ������ʽ����yyyy-M-D
	 * @return ��ʽ�������������
	 */
	public static String formatDateByFormatStr(Date myDate, String formatStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		return formatter.format(myDate);
	}

	/**
	 * ͨ����ʽ���ֶ�����ʽ������
	 * 
	 * @param myDate
	 *            ���������
	 * @param formatStr
	 *            ��Ҫ��ʽ������ʽ����yyyy-M-D
	 * @return ��ʽ�������������
	 */
	public static String formatDateByFormatStr(Object myDate, String formatStr) {
		if (myDate == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		return formatter.format(myDate);
	}

	/**
	 * ͨ����ʽ������ʽ��float
	 * 
	 * @param f
	 *            �����ֵ
	 * @param formatStr
	 *            ��Ҫ��ʽ������ʽ����####.##
	 * @return String
	 */
	public static String formatDouble(float f, String formatStr) {
		DecimalFormat format = new DecimalFormat(formatStr);
		return format.format(f);
	}

	/**
	 * ͨ����ʽ������ʽ��Object
	 * 
	 * @param f
	 *            �����ֵ
	 * @param formatStr
	 *            ��Ҫ��ʽ������ʽ����####.##
	 * @return String
	 */
	public static String formatDouble(Object f, String formatStr) {
		if (f == null)
			return "";
		DecimalFormat format = new DecimalFormat(formatStr);
		return format.format(f);
	}

	/**
	 * ͨ����ʽ������ʽ��double
	 * 
	 * @param d
	 *            �����ֵ
	 * @param formatStr
	 *            ��Ҫ��ʽ������ʽ����####.##
	 * @return String
	 */
	public static String formatDouble(double d, String formatStr) {
		DecimalFormat format = new DecimalFormat(formatStr);
		return format.format(d);
	}

	/**
	 * ��ʽ��ΪRSS�ļ���Ҫ��rfc822�淶��ʽ��ʱ��
	 * 
	 * @param myDate
	 *            Date
	 * @return String
	 */
	public static String formatDateForRss(Date myDate) {
		SimpleDateFormat sdfTemp = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss z", Locale.US);
		SimpleTimeZone aZone = new SimpleTimeZone(8, "GMT");
		sdfTemp.setTimeZone(aZone);
		return sdfTemp.format(myDate);
	}

	/**
	 * ��������ת����long
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return String
	 */
	public static long Date2Long(int year, int month, int date) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date);
		return cld.getTime().getTime();
	}

	/**
	 * ��������ʱ����ת����long
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return long
	 */
	public static long Time2Long(int year, int month, int date, int hour,
			int minute, int second) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date, hour, minute, second);
		return cld.getTime().getTime();
	}

	/**
	 * ��һ��long�͵�ʱ���л����
	 * 
	 * @param t
	 * @return int
	 */
	public static int getYear(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.YEAR);
	}

	/**
	 * ��long��ʱ���л����
	 * 
	 * @param t
	 * @return int
	 */
	public static int getMonth(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MONTH) + 1;
	}

	/**
	 * ��long��ʱ���л����
	 * 
	 * @param t
	 * @return int
	 */
	public static int getDay(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ��long��ʱ���л��Сʱ
	 * 
	 * @param t
	 * @return int
	 */
	public static int getHour(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * ��long��ʱ���л�÷�
	 * 
	 * @param t
	 * @return int
	 */
	public static int getMinute(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MINUTE);
	}

	/**
	 * ��long��ʱ���л����
	 * 
	 * @param t
	 * @return int
	 */
	public static int getSecond(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.SECOND);
	}

	/**
	 * ��Date�л����
	 * 
	 * @param date
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.YEAR);
	}

	/**
	 * ��Date�л����
	 * 
	 * @param date
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MONTH) + 1;
	}

	/**
	 * ��Date�л����
	 * 
	 * @param date
	 * @return int
	 */
	public static int getDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ��Date�л��Сʱ
	 * 
	 * @param date
	 * @return int
	 */
	public static int getHour(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * ��Date�л�÷���
	 * 
	 * @param date
	 * @return int
	 */
	public static int getMinute(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MINUTE);
	}

	/**
	 * ��Date�л����
	 * 
	 * @param date
	 * @return int
	 */
	public static int getSecond(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.SECOND);
	}

	/**
	 * ��õ�ǰ���
	 * 
	 * @return int
	 */
	public static int getYear() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.YEAR);
	}

	/**
	 * ��õ�ǰ�·�
	 * 
	 * @return int
	 */
	public static int getMonth() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.MONTH) + 1;
	}

	/**
	 * ��õ�ǰ����
	 * 
	 * @return int
	 */
	public static int getDay() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * �����Ķ��Ż���Ӣ�Ķ���
	 * 
	 * @param text
	 * @return String
	 */
	public static String replaceComma(String text) {
		if (text != null) {
			text = replace(text, "��", ",");
		}
		return text;
	}

	/**
	 * ��\n���б�ǻ���<br>
	 * 
	 * @param text
	 * @return String
	 */
	public static String replaceBr(String text) {
		if (text != null) {
			text = replace(text, "\n", "<BR>");
		}
		return text;
	}

	/**
	 * ���ϵͳʱ�������
	 * 
	 * @return int
	 */
	public static long getLongTime() {
		return System.currentTimeMillis();
	}

	/**
	 * ��õ�ǰʱ�������ڼ�
	 * 
	 * @param dt
	 * @return String
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
		Calendar cal = Calendar.getInstance();

		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];

	}

	/**
	 * ���һ���ַ�����null���ǿյ�
	 * 
	 * @param param
	 * @return boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.length() == 0 || param.trim().equals("") || param
				.trim().equalsIgnoreCase("null")) ? true : false;
	}

	/**
	 * ���ַ���ȥ���ո�����ַ���Ϊ���򷵻�""
	 * 
	 * @param param
	 * @return String
	 */
	public static String notNull(String param) {
		return param == null ? "" : param.trim();
	}

	/**
	 * ���ַ���ת����Boolean������ַ�����ͷΪ1,y,Y,t,T������Ϊ��true
	 * 
	 * @param param
	 * @return boolean
	 */
	public static boolean parseBoolean(String param) {
		if (nullOrBlank(param)) {
			return false;
		}
		switch (param.charAt(0)) {
		case '1':
		case 'y':
		case 'Y':
		case 't':
		case 'T':
			return true;
		}
		return false;
	}

	/**
	 * dateToString(Date inDate) ��������ת�����ַ���"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param inDate
	 *            Date
	 * @return String
	 */
	public static String dateToString(Date inDate) {
		String outDateStr = "";
		if (inDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			outDateStr = formatter.format(inDate);
		}
		return outDateStr;
	}

	/**
	 * ��������ת�����ַ���"yyyy-MM-dd"
	 * 
	 * @param inDate
	 *            Date ��Ҫת��������ʱ��
	 * @return outDateStr String
	 */
	public static String dateToSimpleStr(Date inDate) {
		String outDateStr = "";
		if (inDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			outDateStr = formatter.format(inDate);
		}
		return outDateStr;
	}

	/**
	 * ���ַ���"yyyy-MM-dd HH:mm:ss"ת����������
	 * 
	 * @param s
	 *            String ��Ҫת��������ʱ���ַ���
	 * @return theDate Date
	 */
	public static Date stringToDateWithTime(String s) {
		Date theDate = new Date();
		try {
			if (s != null) {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				theDate = dateFormatter.parse(s);
			} else {
				theDate = null;
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return theDate;
	}

	/**
	 * ���ַ���"yyyy-MM-dd"ת����������
	 * 
	 * @param s
	 *            String ��Ҫת��������ʱ���ַ���
	 * @return theDate Date
	 */
	public static Date stringToDate(String s) {
		Date theDate = new Date();
		try {
			if (s != null) {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(
						"yyyy-MM-dd");
				theDate = dateFormatter.parse(s);
			} else {
				theDate = null;
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return theDate;
	}

	/**
	 * Date +/- int = �µ�Date
	 * 
	 * @param inDate
	 *            Date ԭ����
	 * @param AddDateInt
	 *            int Ҫ�Ӽ�������
	 * @return ReturnDate Date �µ�Date
	 */
	public static Date dateAddInt(Date inDate, int AddDateInt) {
		Calendar currentC = Calendar.getInstance();
		currentC.setTime(inDate);
		currentC.add(Calendar.DAY_OF_YEAR, AddDateInt);
		return currentC.getTime();
	}

	/**
	 * Date +/- int = �µ�Date
	 * 
	 * @param inDate
	 *            Date ԭ����,�ַ�����
	 * @param AddDateInt
	 *            int Ҫ�Ӽ�������
	 * @return ReturnDate Date �µ�Date
	 */
	public static Date dateAddInt(String inDate, int AddDateInt) {
		try {
			Date date = new Date();
			if (inDate != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
				date = formatter.parse(inDate);
			}
			Calendar currentC = Calendar.getInstance();
			currentC.setTime(date);
			currentC.add(Calendar.DAY_OF_YEAR, AddDateInt);
			return currentC.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * ������ڼ� <br>
	 * ������0--������6
	 * 
	 * @param inDate
	 * @return dayOfWeek int ������ڼ�
	 */
	public static int dayOfWeek(Date inDate) {
		int dayOfWeek = 0;
		Calendar theCalendar = new GregorianCalendar();
		String DateStr = dateToString(inDate);
		theCalendar.set(Integer.parseInt(DateStr.substring(0, 4)),
				Integer.parseInt(DateStr.substring(5, 7)) - 1,
				Integer.parseInt(DateStr.substring(8, 10)));
		dayOfWeek = theCalendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 7) {
			dayOfWeek = 0;
		}
		return dayOfWeek;
	}

	public static int dayOfWeek1(Date inDate) {
		int dayOfWeek = 0;
		Calendar theCalendar = new GregorianCalendar();
		String DateStr = dateToString(inDate);
		theCalendar.set(Integer.parseInt(DateStr.substring(0, 4)),
				Integer.parseInt(DateStr.substring(5, 7)) - 1,
				Integer.parseInt(DateStr.substring(8, 10)));
		dayOfWeek = theCalendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 2) {
			dayOfWeek = 0;
		}
		return dayOfWeek;
	}

	/**
	 * ������ڼ� <br>
	 * ������0--������6 ������������ַ�����
	 * 
	 * @param inDate
	 * @return dayOfWeek int ������ڼ�
	 */
	public static int dayOfWeek(String inDate) {
		int dayOfWeek = 0;
		Calendar theCalendar = new GregorianCalendar();
		String DateStr = inDate;
		theCalendar.set(Integer.parseInt(DateStr.substring(0, 4)),
				Integer.parseInt(DateStr.substring(5, 7)) - 1,
				Integer.parseInt(DateStr.substring(8, 10)));
		dayOfWeek = theCalendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 7) {
			dayOfWeek = 0;
		}
		return dayOfWeek;
	}

	/**
	 * minusDate �����������ڵ��������
	 * 
	 * @param beginDate
	 *            ��ʼ����
	 * @param endDate
	 *            ��ʼ����
	 * @return result long
	 */
	public static long minusDate(Date beginDate, Date endDate) {
		long result = (beginDate.getTime() - endDate.getTime())
				/ (1000 * 60 * 60 * 24);
		return result;
	}

	/**
	 * ��������ʱ���м����Сʱ
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return long
	 */
	public static long minusHour(Date beginDate, Date endDate) {
		long result = (beginDate.getTime() - endDate.getTime())
				/ (1000 * 60 * 60);
		return result;
	}

	/**
	 * ��������ʱ���м���ķ���
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return long
	 */
	public static long minusMinute(Date beginDate, Date endDate) {
		long result = (beginDate.getTime() - endDate.getTime()) / (1000 * 60);
		return result;
	}

	/**
	 * �������ռ������䣬����
	 * 
	 * @param brithday
	 *            ���յ��ַ���(yyyy-mm-dd)
	 * @return age int
	 */
	public static int getAge(String brithday) {
		int age = 0;
		// try {
		// Calendar birth = Calendar.getInstance();
		// int year = Integer.parseInt(brithday.substring(0, 4));
		// int month = Integer.parseInt(brithday.substring(5, 7)) - 1;
		// int day = Integer.parseInt(brithday.substring(8, 10));
		// birth.set(year, month, day);
		// Calendar today = Calendar.getInstance();
		// if (today.get(Calendar.MONTH) > birth.get(Calendar.MONTH)
		// || (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH))
		// && today.get(Calendar.DATE) >= birth.get(Calendar.DATE))
		// age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		// else
		// age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) - 1;
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// return age;
		try {
			String[] brithStr = brithday.split("-");
			Calendar birthday = new GregorianCalendar(
					Integer.parseInt(brithStr[0]),
					Integer.parseInt(brithStr[1]),
					Integer.parseInt(brithStr[2]));
			Calendar today = new GregorianCalendar();
			age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
			// ȡ�����꣬��������ջ�û������ô�������1
			today.add(Calendar.YEAR, age);
			if (birthday.before(today)) {
				age--;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return age;

	}

	/**
	 * �������ַ���yyyy-mm-ddת��Ϊlong��
	 * 
	 * @param dateStr
	 *            �����ַ���yyyy-mm-dd
	 * @return long theDay
	 */
	public static long dateStrToLong(String dateStr) {
		long theDate = getLongTime();
		Date thisDate = stringToDate(dateStr);
		if (thisDate != null) {
			theDate = thisDate.getTime();
		}
		return theDate;
	}

	/**
	 * �����ַ���yyyy-MM-dd HH:mm:ssת��Ϊlong��
	 * 
	 * @param dateStr
	 *            �����ַ���yyyy-MM-dd HH:mm:ss
	 * @return long theDay
	 */
	public static long dateStrWithTimeToLong(String dateStr) {
		long theDate = getLongTime();
		Date thisDate = stringToDateWithTime(dateStr);
		if (thisDate != null) {
			theDate = thisDate.getTime();
		}
		return theDate;
	}

	/**
	 * long������ת����yyyy-MM-dd��ʽ
	 * 
	 * @param theDateLong
	 * @return String
	 */
	public static String longToDateStr(long theDateLong) {
		String dateStr = "1970-01-01";
		try {
			dateStr = Integer.toString(getYear(theDateLong)) + "-"
					+ getMonth(theDateLong) + "-" + getDay(theDateLong);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * long������ת����yyyy-MM-dd HH:mm:ss��ʽ
	 * 
	 * @param theDateLong
	 * @return String DateStr yyyy-MM-dd HH:mm:ss
	 */
	public static String longToDateWithTimeStr(long theDateLong) {
		String dateStr = "1970-01-01 00:00:00";
		try {
			dateStr = Integer.toString(getYear(theDateLong)) + "-"
					+ getMonth(theDateLong) + "-" + getDay(theDateLong) + " "
					+ getHour(theDateLong) + ":" + getMinute(theDateLong) + ":"
					+ getSecond(theDateLong);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * ��ת����
	 * 
	 * @param src
	 *            Դ����
	 * @return ת���������
	 */
	public static int[] ArrayConvert(int[] src) {
		if (src == null) {
			return null;
		}
		int length = src.length;
		int temp = 0;
		// ��ת
		for (int i = 0; i < length / 2; i++) {
			temp = src[i];
			src[i] = src[length - i - 1];
			src[length - i - 1] = temp;
		}
		return src;
	}

	/**
	 * ����ת���ַ�תABCD
	 * 
	 * @param i
	 * @return ABCD
	 */
	public static String getColName(int iCol) {
		int zs = iCol / 26;//
		int ys = iCol % 26;//
		String cCol = "";
		String tmp = "";
		if (ys == 0) {
			tmp = "Z";
		} else {
			tmp = String.valueOf((char) (ys + 64));
		}
		if (zs > 1 || (zs == 1 && ys > 0))//
		{
			if (ys == 0) {
				zs = zs - 1;
			}
			cCol = getColName(zs) + cCol + tmp;
		} else {//
			if (ys == 0) {
				cCol += "Z";
			} else {
				cCol += String.valueOf((char) (ys + 64));
			}
		}
		return cCol;
	}

	/**
	 * ȥ���ַ����еĿո񡢻س������з����Ʊ��
	 * 
	 * @param inStr
	 * @return String
	 */
	public static String replaceBlank(String inStr) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(inStr);
		return m.replaceAll("");
	}

	/**
	 * ȥ���ַ����еĻس������з����Ʊ�����������ո�
	 * 
	 * @param inStr
	 * @return String
	 */
	public static String replaceBlank2(String inStr) {
		Pattern p = Pattern.compile("\\[s\\s]|\t|\r|\n");
		Matcher m = p.matcher(inStr);
		return m.replaceAll("");
	}

	/**
	 * ��URL����UTF-8����
	 * 
	 * @param inStr
	 * @return url
	 */
	public static String encodeUrlByUTF8(String inStr) {
		try {
			return java.net.URLEncoder.encode(inStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return inStr;
	}

	/**
	 * ��Unicodeת����String
	 * 
	 * @param str
	 * @return String
	 */
	public static String UnicodeToString(String str) {
		String res = null;
		StringBuffer sb = new StringBuffer();
		try {
			while (str.length() > 0) {
				if (str.startsWith("\\u")) {
					int x = Integer.parseInt(str.substring(2, 6), 16);
					sb.append((char) x);
					str = str.substring(6);
				} else {
					sb.append(str.charAt(0));
					str = str.substring(1);
				}
			}
			res = sb.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return res;
	}

	/**
	 * ��Stringת��ΪUnicode
	 * 
	 * @param szOrg
	 * @return String
	 */
	public static String StringToUnicode(String szOrg) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < szOrg.length(); i++) {
			// if (szOrg.substring(i, i + 1).matches("[\u4e00-\u9fa5]+"))
			// sb.append("\\u" + Integer.toHexString(szOrg.charAt(i)));
			// else
			// sb.append(szOrg.charAt(i));
			if (szOrg.charAt(i) > 128) {
				sb.append("\\u" + Integer.toHexString(szOrg.charAt(i)));
			} else
				sb.append(szOrg.charAt(i));
		}
		return sb.toString();
	}

	/**
	 * ����һ��10λ������ID
	 * 
	 * @param userId
	 *            ,�ɴ���һ������
	 * @return
	 */
	public static String generateID(String userId) {
		if (userId == null || "".equals(userId))
			userId = "1";
		int iUserId = Integer.parseInt(userId);
		Date d = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(toValue(1900 + d.getYear()))
				.append(toValue(d.getMonth() + 1)).append(toValue(d.getDate()));
		sb.append(toValue(d.getMinutes())).append(toValue(d.getSeconds()));
		sb.append(iUserId).append(Math.round(Math.random() * 100));
		sb.append(Math.round(Math.random() * 100));
		return formatString(sb.toString());
	}

	private static String toValue(int value) {
		String strRet = String.valueOf(value);
		if (value < 10) {
			strRet = "0" + Integer.toString(value);
		}
		return strRet;
	}

	private static String formatString(String value) {
		if (value.length() < 9) {
			for (int i = 0; i < 9 - value.length(); i++) {
				value = "1" + value;
			}
		}
		if (value.length() > 9) {
			value = value.substring(value.length() - 9, value.length());
		}
		return value;
	}

	/**
	 * ���URL�Ƿ����
	 * 
	 * @param URLName
	 * @return boolean
	 */
	public static boolean urlExists(String URLName) {
		// try {
		// URL u = new URL(URLName);
		// URLConnection c = u.openConnection();
		// c.connect();
		// System.out.println(c.getDate());
		// if (c.getContentType() == null || c.getExpiration() == 0)
		// return false;
		// // System.out.println("��������: "+c.getContentType());
		// // System.out.println("���ݳ���: "+c.getContentLength());
		// // System.out.println("��������: "+new Date(c.getDate()));
		// // System.out.println("����޸�����: "+new Date(c.getLastModified()));
		// // System.out.println("��ֹ����: "+new Date(c.getExpiration()));
		//
		// return true;
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName)
					.openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * ����ظ���List�еĶ���
	 * 
	 * @param args
	 */
	public static List emptyList(List list) {
		Set<Object> set = new HashSet<Object>();
		for (Object o : list) {
			set.add(o);
		}
		List li = new ArrayList<Object>();
		for (Object o : set) {
			li.add(o);
		}
		list.removeAll(list);
		return li;
	}

	/**
	 * ��1,2,3,4,6,8,9,10,13ת���� 1-4,6,8-10,13
	 * 
	 * @param str
	 * @return
	 */
	public static String convertKkzc(String str) {
		StringBuffer stringBuffer = new StringBuffer();
		String[] strlist = str.split(",");
		stringBuffer.append(strlist[0]);
		if (strlist.length > 1) {
			for (int i = 0; i < strlist.length; i++) {
				String m = "";
				String n = "";
				int j = i + 1;
				for (; j < strlist.length; j++) {
					if (Integer.parseInt(strlist[j]) == Integer
							.parseInt(strlist[j - 1]) + 1) {
						m = strlist[j];
						continue;
					} else {
						n = strlist[j];
						break;
					}
				}
				i = j - 1;
				if (!"".equals(m)) {
					stringBuffer.append("-").append(m);
				}
				if (!"".equals(n)) {
					stringBuffer.append(",").append(n);
				}
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * ��1-4,6,8-10,13ת����1,2,3,4,6,8,9,10,13
	 * 
	 * @param str
	 * @return
	 */
	public static String reserveKkzc(String str) {
		if (StringUtils.nullOrBlank(str)) {
			return "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		String[] strlist = str.split(",");

		for (int i = 0; i < strlist.length; i++) {
			if (StringUtils.nullOrBlank(strlist[i])) {
				continue;
			}

			if (strlist[i].indexOf("-") <= 0) {
				stringBuffer.append(strlist[i]);
				stringBuffer.append(",");
				continue;
			}

			String[] strList2 = strlist[i].split("-");

			for (int j = Integer.parseInt(strList2[0]); j <= Integer
					.parseInt(strList2[1]); ++j) {
				stringBuffer.append(j);
				stringBuffer.append(",");
			}
		}

		if (stringBuffer.toString().endsWith(",")) {
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		}
		return stringBuffer.toString();
	}

	public static String clearRegx(String str, String regx) {
		if (nullOrBlank(str) || regx == null) {
			return str;
		}
		String r = str.substring(str.length() - 1, str.length());
		if (r.equals(regx)) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static void main(String[] args) {
		// System.out.println(reserveKkzc("1-4,6,8-10,13"));
		System.out.println(clearRegx(",", ","));
	}

	/**
	 * ��Ŀͻ��˿�IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
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
	 * ����1,2,3,4��ת���ɡ�'1','2','3','4'��
	 * 
	 * @param request
	 * @return
	 */
	public static String getsringDou(String str) {
		String[] zhstr = str.split(",");
		String returnstring = "";
		for (int i = 0; i < zhstr.length; i++) {
			if (i == 0) {
				returnstring = returnstring + "'" + zhstr[i] + "'";
			} else {
				returnstring = returnstring + ",'" + zhstr[i] + "'";
			}
		}
		return returnstring;
	}

	/**
	 * ���ݱ�־λȥ��˫�ܡ�flag=0,1,2��˫�ܱ�־,0 ȫ�� 1 ���� 2˫��
	 * 
	 * @param request
	 * @return
	 */
	public static String getDsKKzc(String str, String flag) {

		// jx0408.setKkzc(kkzcmx.substring(1,kkzcmx.length()-1));
		String[] kkzclist = str.split(",");
		StringBuffer kkzcnew = new StringBuffer("");
		for (int i = 0; i < kkzclist.length; i++) {
			if (kkzclist[i].indexOf("-") > -1) {
				kkzcnew.append(kkzclist[i]).append(",");

			} else {
				if ("1".equals(flag)) {
					if (Integer.parseInt(kkzclist[i]) % 2 != 0) {
						kkzcnew.append(kkzclist[i]).append(",");
					}
				} else if ("2".equals(flag)) {
					if (Integer.parseInt(kkzclist[i]) % 2 == 0) {
						kkzcnew.append(kkzclist[i]).append(",");
					}
				} else {
					kkzcnew.append(kkzclist[i]).append(",");
				}

			}

		}

		return kkzcnew.toString().substring(0, kkzcnew.length() - 1);
	}

	/**
	 * ���л�����
	 * 
	 * @param o
	 *            Ҫ���л��Ķ���
	 * @return ���л�����ַ���
	 * @throws IOException
	 */
	//public static String objectToString(Object o) throws IOException {
	/*	ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bs);

		oos.writeObject(o);

		byte[] b = bs.toByteArray();
		return Base64.encodeBytes(b);*/
//	}

	/**
	 * �����л�����
	 * 
	 * @param <E>
	 * @param byteString
	 *            ���л�����ַ���
	 * @return �����л���Ķ���
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	//public static <E> E stringToObject(String byteString) throws IOException,
	//		ClassNotFoundException {
	/*
		byte[] b = Base64.decode(byteString);

		ByteArrayInputStream bi = new ByteArrayInputStream(b);
		ObjectInputStream bis = new ObjectInputStream(bi);
		return (E) bis.readObject();
	*/
	//}

	/**
	 * ������ת��IN SQL
	 * 
	 * @param param
	 * @return
	 */
	public String getInSql(String[] param) {
		String sql = "";
		for (int i = 0; param != null && i < param.length; i++) {
			sql += "'" + param[i] + "'";
			if (i < param.length - 1) {
				sql += ",";
			}
		}
		return sql;
	}

	/**
	 * �����Ķ��Ż���Ӣ�Ķ���
	 * 
	 * @param text
	 * @return String
	 */
	public static String replaceString(String text) {
		if (text != null) {
			text = replace(text, "'", "''");
		}
		return text;
	}

	/**
	 * ����������ܵ��ַ���
	 */
	private static void getCode() {
		int zs = 0;
		for (int i = 0; i < len; i++) {
			int j = (int) (Math.random() * 3 + 1);
			xh = xh + j;
			zs = zs + j;
		}
		// �����ܳ���������֤��
		Random random = new Random();
		for (int i = 0; i < zs; i++) {
			boolean b = random.nextBoolean();
			if (b) { // �ַ���
				// int choice = random.nextBoolean() ? 65 : 97; ȡ��65��д��ĸ����97Сд��ĸ
				int randomrum = 65 + random.nextInt(57);
				if (randomrum > 90 && randomrum < 97) {
					randomrum = randomrum + 6;
				}
				code += (char) (randomrum);// ȡ�ô�д��ĸ
			} else { // ����
				code += String.valueOf(random.nextInt(10));
			}
		}
	}

	/**
	 * �ַ�������
	 * 
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		String sstr = "";
		if ("".equals(xh)) {
			getCode();
		} else {
			String sxh = xh;
			String scode = code;
			for (int i = 0; i < str.length(); i++) {
				if (i < len) {
					sstr = sstr
							+ str.substring(i, i + 1)
							+ scode.substring(0,
									Integer.parseInt(sxh.substring(i, i + 1)));
					scode = scode.substring(
							Integer.parseInt(sxh.substring(i, i + 1)),
							scode.length());
				} else {
					sstr = sstr + str.substring(i, str.length());
					i = str.length();
				}
			}
		}
		return sstr;
	}

	/**
	 * �ַ�������
	 * 
	 * @param str
	 * @return
	 */
	public static String decrypt(String str) {
		String encoded = str;
		if ("".equals(xh) || xh == null) {
			return "false";
		}
		String sesscode = code;
		String sessxh = xh;
		String dstr = "";
		String dcode = "";
		for (int x = 0; x < len; x++) {
			dstr = dstr + encoded.substring(0, 1);
			encoded = encoded.substring(1, encoded.length());
			int j = Integer.parseInt(sessxh.substring(x, x + 1));
			String sesscodepart = sesscode.substring(0, j);
			sesscode = sesscode.substring(j, sesscode.length());
			if (encoded.length() >= sesscodepart.length()) {
				try {

					dcode = dcode + encoded.substring(0, sesscodepart.length());
					encoded = encoded.substring(sesscodepart.length(),
							encoded.length());
				} catch (Exception e) {
					e.printStackTrace();
					return "false";

				}
			} else {
				dstr = dstr + encoded.substring(0, encoded.length());
				break;
			}
			if (encoded.length() == 0) {
				break;
			}
			if (x == (len - 1)) {
				dstr = dstr + encoded.substring(0, encoded.length());
			}
		}
		if (code.indexOf(dcode) == -1) {
			return "false";
		}
		return dstr;
	}

	/**
	 * У��Ƿ��ؼ���
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIllegally(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		String[] rexgs = { "exec", "execute", "insert", "create", "drop",
				"table", "grant", "column_name", "delete", "update",
				"truncate", "declare", "frmuser", "frmfunctioncell",
				"frmfunctioncellurl", "frmsecondfunctionuser",
				"frmrolefunction", "all_tables", "dba_tables", "user_tables",
				"tabs", "user_tab_columns", "user_tab_cols", "table_name" };

		boolean bool = false;
		for (int a = 0; a < rexgs.length; a++) {
			if (str.toLowerCase().indexOf(rexgs[a]) > -1) {
				bool = true;
				break;
			}
		}
		return bool;
	}

	/**
	 * �����ַ��滻
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceSpecial(String str) {
		if (str != null) {
			str = str.replaceAll("&#59;", ";").replaceAll("&amp;", "&")
					.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
					.replaceAll("&#97;nd", "and").replaceAll("&#111;r", "or")
					.replaceAll("&#108;ike", "like")
					.replaceAll("&#103;rant", "grant")
					.replaceAll("&#117;nion", "union")
					.replaceAll("&#119;here", "where")
					.replaceAll("&#102;rom", "from")
					.replaceAll("&#114;estore", "restore")
					.replaceAll("&#109;aster.", "master.")
					.replaceAll("&#101;xists", "exists")
					.replaceAll("&#97;lter", "alter")
					.replaceAll("&#116;runcate", "truncate")
					.replaceAll("&#114;ename", "rename")
					.replaceAll("&#99;reate", "create")
					.replaceAll("&#100;rop", "drop")
					.replaceAll("&#100;elete", "delete")
					.replaceAll("&#117;pdate", "update")
					.replaceAll("&#105;nsert", "insert")
					.replaceAll("&#115;elect", "select")
					.replaceAll("&#101;xec", "exec");
		}
		return str;
	}

}
