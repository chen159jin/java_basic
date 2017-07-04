/**
 * ˵�����ļ�IO����������
 * ��д�ߣ�лƽ
 * ���ڣ�Nov 20, 2007
 * ����ǿ�ǿƼ���Ȩ���С�
 */
package io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.apache.struts.upload.FormFile;



//import com.qzdatasoft.comm.generic.Sequence;
import com.qzdatasoft.comm.dao.util.Sequence;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import stringUtil.StringUtils;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * <p>
 * Title:�ļ�IO����������
 * <p>
 * Description:�ļ�IO���������࣬��ʹ���������������ļ�����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: ����ǿ�ǿƼ���չ���޹�˾
 * </p>
 * 
 */
public class FileIO {
	/**
	 * readFile(String filePath) ͨ���ļ�·�����ֽڷ�ʽ��ȡ�ļ�����
	 * 
	 * @param filePath
	 *            String
	 * @return String
	 */
	public static String readFile(String filePath) {
		String info = "";
		try {
			File f = new File(filePath);
			if (f.exists()) {
				FileInputStream bw = new FileInputStream(f);
		int len = bw.available();//�ɻ�õĳ��� �������紫����ܻ���Ϊ��������������ȷ
				byte[] str = new byte[len];
				if (bw.read(str) == -1) {
					info = "";
				} else {
					info = new String(str);
				}
				bw.close();
				bw = null;
			}
			f = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * readFile(String filePath, String charset) ͨ���ļ�·�����ֽڷ�ʽ����ָ���ı����ȡ�ļ�����
	 * 
	 * @param filePath
	 *            String
	 * @param charset
	 *            String
	 * @return String
	 */
	public static String readFile(String filePath, String charset) {
		String info = "";
		try {
			File f = new File(filePath);
			if (f.exists()) {
				FileInputStream bw = new FileInputStream(f);
				int len = bw.available();
				byte[] str = new byte[len];
				if (bw.read(str) == -1) {
					info = "";
				} else {
					info = new String(str, charset);
				}
				bw.close();
				bw = null;
			}
			f = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * writeFile(String msg, String filePath) ���ֽڵķ�ʽд�ļ� �Ƽ�ʹ��
	 * 
	 * @param msg
	 *            String
	 * @param filePath
	 *            String
	 */
	public static void writeFile(String msg, String filePath) {
		FileWriter fw = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			fw = new FileWriter(filePath);
			fw.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����ķ�ʽд�ļ�
	 * 
	 * @param msg
	 * @param filePath
	 */
	public static void writeFile2(String msg, String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream wf = new FileOutputStream(filePath);
			wf.write(msg.getBytes("utf-8"));
			wf.close();
			file = null;
			wf = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * makeDir(String directoryName) ����������һ��Ŀ¼
	 * 
	 * @param directoryName
	 *            String
	 * @return boolean
	 */
	public static boolean makeDir(String directoryName) {
		boolean flag = false;
		try {
			File aFile = new File(directoryName);
			aFile.mkdir();
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * cutFile(String sourceDirectoryName,String sourceFileName, String
	 * DestDirectoryName,String DestFileName) �ѱ����ļ�cut����һ��Ŀ¼
	 * 
	 * @param sourceDirectoryName
	 *            String
	 * @param sourceFileName
	 *            String
	 * @param DestDirectoryName
	 *            String
	 * @param DestFileName
	 *            String
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean cutFile(String sourceDirectoryName,
			String sourceFileName, String DestDirectoryName, String DestFileName)
			throws java.io.IOException {
		boolean flag = false;
		try {
			File aFile = new File(sourceDirectoryName + sourceFileName);
			aFile.renameTo(new File(DestDirectoryName + DestFileName));
			aFile.delete();
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * cutFiles(String sourceDirectoryName, String DestDirectoryName,Vector
	 * fileNames) �ѱ��ض���ļ�cut����һ��Ŀ¼
	 * 
	 * @param sourceDirectoryName
	 *            String
	 * @param destDirectoryName
	 *            String
	 * @param fileNames
	 *            Vector
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean cutFiles(String sourceDirectoryName,
			String destDirectoryName, Vector fileNames)
			throws java.io.IOException {
		boolean flag = false;
		try {
			File af = new File(destDirectoryName);
			af.mkdirs();
			for (int i = 0; i < fileNames.size(); i++) {
				cutFile(sourceDirectoryName, (String) fileNames.elementAt(i),
						destDirectoryName, (String) fileNames.elementAt(i));
			}
			/*
			 * File af1 = new File(sourceDirectoryName); af1.delete();
			 */
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * copyFile(File src,File dest) �ѱ���һ���ļ�copy����һ���ط�
	 * 
	 * @param src
	 *            File
	 * @param dest
	 *            File
	 * @throws IOException
	 */
	public static void copyFile(File src, File dest) throws java.io.IOException {
		int length = (int) src.length();
		// �ֿ鿽��ת���ĸ���
		int block = 20000000;
		int blocknum = length / block;
		int blockmod = length % block;
		int totalblock = 0;
		if (blockmod == 0) {
			totalblock = blocknum;
		} else {
			totalblock = blocknum + 1;
		}
		FileOutputStream outfile = null;
		FileInputStream infile = null;
		try {
			int starthead = 0;
			outfile = new FileOutputStream(dest);
			infile = new FileInputStream(src);
			for (int m = 0; m < totalblock && length != 0; m++) {
				if ((m + 1) * block > length) {
					byte[] TempAttach = new byte[blockmod];
					infile.read(TempAttach, starthead, blockmod);
					outfile.write(TempAttach, starthead, blockmod);
					outfile.close();
					infile.close();
				} else {
					byte[] TempAttach = new byte[block];
					infile.read(TempAttach, starthead, block);
					outfile.write(TempAttach, starthead, block);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			outfile.close();
			infile.close();
		}
	}

	/**
	 * copyDirectory(String srcDir,String destDir) �ѱ���һ��Ŀ¼�������ļ�copy����һ��Ŀ¼��
	 * 
	 * @param srcDir
	 *            String
	 * @param destDir
	 *            String
	 */
	public static void copyDirectory(String srcDir, String destDir) {
		File srcDirFile = new File(srcDir);
		File destDirFile = new File(destDir);
		if (srcDirFile.exists()) {
			if (destDirFile.exists() == false) {
				destDirFile.mkdir();
			}
			File[] files = srcDirFile.listFiles();
			int i = files.length;
			int a = 0;
			for (a = 0; a < i && i > 0; a++) {
				try {
					copyFile(
							files[a],
							new File(
									destDir
											+ File.separator
											+ files[a]
													.getCanonicalPath()
													.substring(
															files[a].getCanonicalPath()
																	.lastIndexOf(
																			File.separator) + 1)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * seperateBasicFilename(String wholeFilename) ��������·�����л�ȡ������·����Ϣ���ļ���
	 * 
	 * @param wholeFilename
	 *            String
	 * @return String
	 */
	public static String seperateBasicFilename(String wholeFilename) {
		String fg = new String(File.separator);
		int index = wholeFilename.lastIndexOf(fg);
		if (index < 0) {
			return wholeFilename;
		}
		String filename = wholeFilename.substring(index + 1);
		return filename;
	}

	/**
	 * seperateSimpleFilename(String wholeFilename) ��������·�����л�ȡ������·����Ϣû����չ�����ļ���
	 * 
	 * @param wholeFilename
	 *            String
	 * @return String
	 */
	public static String seperateSimpleFilename(String wholeFilename) {
		String filename = seperateBasicFilename(wholeFilename);
		String fg = new String(".");
		int index = filename.lastIndexOf(fg);
		if (index < 0) {
			return wholeFilename;
		}
		String simplefilename = filename.substring(0, index);
		return simplefilename;
	}

	/**
	 * getFileExt(String fileName) ���ļ�����ȡ���ļ���չ��
	 * 
	 * @param fileName
	 *            String
	 * @return String
	 */
	public static String getFileExt(String fileName) {
		String fileExt = "";
		int index = fileName.lastIndexOf(".");
		fileExt = fileName.substring(index, fileName.length());
		return fileExt;
	}

	/**
	 * getRealFilePath(ServletContext context,String filePath)
	 * ����һ���ļ������·�����������ľ���·������ҪServletContext��Ϣ
	 * 
	 * @param context
	 *            ServletContext
	 * @param filePath
	 *            String
	 * @return String
	 */
	public static String getRealFilePath(ServletContext context, String filePath) {
		String realFilePath = context.getRealPath(filePath);
		return realFilePath;
	}

	/**
	 * deleteFile(String filePath)
	 * 
	 * @param realFilePath
	 *            String
	 * @return boolean ɾ���ļ���Ŀ¼
	 */
	public static boolean deleteFile(String realFilePath) {
		boolean flag = false;
		try {
			File aFile = new File(realFilePath);
			flag = aFile.delete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * @param args
	 */
	// ɾ���ļ����µ������ļ�
	public static boolean deleteAllFile(String folderFullPath) {
		boolean ret = false;
		File file = new File(folderFullPath);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					String filePath = fileList[i].getPath();
					deleteAllFile(filePath);
				}
			}
			if (file.isFile()) {
				file.delete();
			}
		}
		return ret;
	}

	/**
	 * @param args
	 */
	// ɾ���ļ����µ������ļ�
	public static String getAllFristFile(String folderFullPath) {
		String path = "";
		File file = new File(folderFullPath);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					path = fileList[i].getPath();

				}
			}
			if (file.isFile()) {
				file.delete();
			}
		}
		return path;
	}

	/**
	 * ��ѹ������. ��ZIP_FILENAME�ļ���ѹ��ZIP_DIRĿ¼��.
	 * 
	 * @throws Exception
	 */
	public static void upZipFile(String path, String mbpath) throws Exception {
		String baseDir = mbpath;
		ZipFile zfile = new ZipFile(path);
		Enumeration zList = zfile.entries();
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			ze = (ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				System.out.println("Dir: " + ze.getName() + " skipped..");
				continue;
			}
			System.out.println("Extracting: " + ze.getName() + "\t"
					+ ze.getSize() + "\t" + ze.getCompressedSize());

			// ��ZipEntryΪ�����õ�һ��InputStream����д��OutputStream��
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					getRealFileName(baseDir, ze.getName())));
			InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
			System.out.println("Extracted: " + ze.getName());
		}
		zfile.close();

	}

	/**
	 * ������Ŀ¼������һ�����·������Ӧ��ʵ���ļ���.
	 * 
	 * @param baseDir
	 *            ָ����Ŀ¼
	 * @param absFileName
	 *            ���·������������ZipEntry�е�name
	 * @return java.io.File ʵ�ʵ��ļ�
	 */
	public static File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
			if (!ret.exists())
				ret.mkdirs();
			ret = new File(ret, dirs[dirs.length - 1]);
			return ret;
		}
		return ret;
	}

	/**
	 * checkFilesFolder(String realFilePath) ����ļ�·���Ƿ����
	 * 
	 * @param realFilePath
	 *            String
	 * @return boolean
	 */
	public static boolean checkFilesFolder(String realFilePath) {
		boolean flag = false;
		try {
			File aFile = new File(realFilePath);
			flag = aFile.exists();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * isAllowFile(String flag, String fileName) ���ͼ���ļ����Ƿ�Ϸ�--*.jpg;*.gif;*.gif
	 * 
	 * @param flag
	 *            String
	 * @param fileName
	 *            String
	 * @return boolean
	 */
	public static boolean isAllowFile(String flag, String fileName) {
		boolean isAllow = false;
		if (flag.equals("pic")) {
			fileName = fileName.toLowerCase();
			if (fileName.endsWith(".jpg") || fileName.endsWith(".gif")
					|| fileName.endsWith(".gif")) {
				isAllow = true;
			}
		}
		return isAllow;
	}

	/**
	 * creatFile(InputStream stream, String outPutRealPath) ���ļ�������ʽ�����ļ�
	 * 
	 * @param stream
	 *            InputStream
	 * @param outPutRealPath
	 *            String
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean creatFile(InputStream stream, String outPutRealPath)
			throws java.io.IOException {
		boolean flag = false;
		OutputStream bos = null;
		try {
			bos = new FileOutputStream(outPutRealPath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stream.close();
			bos.close();
		}
		return flag;
	}

	/**
	 * shrinkImage(String inRealPath, String outRealPath, int outWidth, int
	 * outHeigh) ��ͼ���ļ�����ָ����������ţ����С��ָ����ߣ��򿽱�
	 * 
	 * @param inRealPath
	 *            String
	 * @param outRealPath
	 *            String
	 * @param outWidth
	 *            int
	 * @param outHeigh
	 *            int
	 * @return boolean
	 */
	public static boolean imageOperate(String inRealPath, String outRealPath,
			int outWidth, int outHeigh) {
		boolean opFlag = false;
		try {
			File _file = new File(inRealPath); // �����ļ�
			Image src = javax.imageio.ImageIO.read(_file); // ����Image����
			int width = src.getWidth(null); // �õ�Դͼ��
			int height = src.getHeight(null); // �õ�Դͼ��
			int newWidth = 1;
			int newHeight = 1;
			// ����Դ��������
			if (width < outWidth && height < outHeigh) {
				// ����ԭͼ
				copyFile(new File(inRealPath), new File(outRealPath));
			} else {
				if (width >= height) {
					newWidth = outWidth;
					newHeight = outWidth * height / width;
				} else {
					newHeight = outHeigh;
					newWidth = outHeigh * width / height;
				}
				// ������С���ͼ
				BufferedImage tag = new BufferedImage(newWidth, newHeight,
						BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(src, 0, 0, newWidth, newHeight,
						null);
				// ������ļ���
				FileOutputStream out = new FileOutputStream(outRealPath);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				// ��JPEG����
				encoder.encode(tag);
				out.close();
			}
			opFlag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return opFlag;
	}

	/**
	 * getDirectoryFilesNum(String realPath) ���һ��Ŀ¼�µ��ļ�����
	 * 
	 * @param realPath
	 *            String
	 * @return int
	 */
	public static int getDirectoryFilesNum(String realPath) {
		int filesNum = 0;
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				filesNum = entries.length;
			}
		}
		return filesNum;
	}

	/**
	 * getDirectoryFilesName(String realPath) ��ø�Ŀ¼�������ļ����֣�������չ����
	 * 
	 * @param realPath
	 *            String
	 * @return List
	 */
	public static List getDirectoryFilesName(String realPath) {
		List ls = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].isFile())
						ls.add(entries[i].getName());
				}
			}
		}
		return ls;
	}

	/**
	 * ���Ŀ¼�������ļ�����
	 * 
	 * @param realPath
	 *            String
	 * @return List
	 */
	public static List getDirectoryFilesNameByFileType(String realPath) {
		List ls = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].isFile())
						ls.add(entries[i]);
				}
			}
		}
		return ls;
	}

	/**
	 * getSubDirsName(String realPath) ��ø�Ŀ¼��������Ŀ¼
	 * 
	 * @param realPath
	 *            String
	 * @return List
	 */
	public static List getSubDirsName(String realPath) {
		List ls = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].isDirectory())
						ls.add(entries[i].getName());
				}
			}
		}
		return ls;
	}

	/**
	 * smartMakeDir(ServletContext context,String filePath) ����context�����·������Ŀ¼
	 * 
	 * @param context
	 *            ServletContext
	 * @param filePath
	 *            String
	 * @return int ����Ŀ¼������ -1�����޷�����Ŀ¼
	 */
	public static int smartMakeDir(ServletContext context, String filePath) {
		int opFlag = 0;
		// �ֽ�·��
		String[] tempPath = filePath.substring(1).split("/");
		String tempStr = "";
		String realPath;
		for (int i = 0; i < tempPath.length; i++) {
			// ����·���Ƿ���ڣ������ھͽ���
			tempStr += "/" + tempPath[i];
			realPath = getRealFilePath(context, tempStr);
			if (!checkFilesFolder(realPath)) {
				if (makeDir(realPath)) {
					++opFlag;
				} else {
					opFlag = -1;
					break;
				}
			}
		}
		return opFlag;
	}

	/**
	 * String makeDirName(int buildID) ��������ID��23,19,17ȡģ����Ŀ¼��,�Ӷ�������Ӧ��Ŀ¼�ṹ;
	 * 
	 * @param buildID
	 *            int
	 * @return String
	 */
	public static String makeDirName(int buildID) {
		int dirID = buildID;
		StringBuffer dirStructure = new StringBuffer();
		dirStructure.append("/").append(String.valueOf(dirID % 23));
		dirStructure.append("/").append(String.valueOf(dirID % 19));
		dirStructure.append("/").append(String.valueOf(dirID % 17));
		return dirStructure.toString();
	}

	/**
	 * getDirectoryAllFiles(String realPath)
	 * 
	 * @param realPath
	 *            String
	 * @return AarrayList
	 * @todo get all files in the realPath
	 */
	public static ArrayList getDirectoryAllFiles(String realPath) {
		ArrayList fileList = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].getAbsolutePath().indexOf(".") > 0) {
						fileList.add(entries[i].getAbsolutePath());
					}
				}
			}
		}
		return fileList;
	}

	/**
	 * getAllSubDirs(String realPath)
	 * 
	 * @param realPath
	 *            String
	 * @return ArrayList
	 * @todo get all directories in the realPath
	 */
	public static ArrayList getAllSubDirs(String realPath) {
		ArrayList dirList = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].getAbsolutePath().indexOf(".") < 0) {
						dirList.add(entries[i].getAbsolutePath());
					}
				}
			}
		}
		return dirList;
	}

	/**
	 * createImgWaterMark(String filePath, String watermark) ��ͼƬ�ϴ���ˮӡ
	 * 
	 * @param filePath
	 *            String
	 * @param watermark
	 *            String
	 * @return ArrayList
	 */
	public static boolean createImgWaterMark(String filePath, String watermark) {
		try {
			File _file = new File(filePath); // �����ļ�
			Image theImg = javax.imageio.ImageIO.read(_file); // ����Image����
			ImageIcon waterIcon = new ImageIcon("temp");
			Image waterImg = waterIcon.getImage();
			int width = theImg.getWidth(null);
			int height = theImg.getHeight(null);
			BufferedImage bimage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bimage.createGraphics();
			g.setColor(Color.red);
			g.setBackground(Color.white);
			g.drawImage(theImg, 0, 0, null);
			g.drawImage(waterImg, 100, 100, null);
			g.drawString(watermark, 10, 10); // �������
			g.dispose();
			FileOutputStream out = new FileOutputStream(filePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(50f, true);
			encoder.encode(bimage, param);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * ����Struts��FormFile�����ϴ�һ���ļ�
	 * 
	 * @param file
	 * @param request
	 * @return �ļ����·��
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String doUploadFile(FormFile file, HttpServletRequest request) {
		if (file.getFileSize() <= 0) {
			return null;
		}
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if ("by".equals(type)) {
			zt = "by";
		}

		String dirStructure = "/" + zt + "uploadfile/" + StringUtils.getYear()
				+ "/" + StringUtils.getMonth() + "/" + StringUtils.getDay();
		String fileName = Sequence.getInstance().getSequence(16)
				+ FileIO.getFileExt(file.getFileName());
		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		try {
			InputStream stream = file.getInputStream();
			FileIO.creatFile(stream, uploadPath + File.separator + fileName);
			filepath = dirStructure + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filepath;
	}

	/**
	 * �ļ��ϴ��·����������Ե�ַ�ϴ�
	 * 
	 * @param file
	 * @param request
	 * @param path
	 * @return
	 */
	public static String doUploadFile_new(FormFile file,
			HttpServletRequest request, String dirPath, String fileName) {
		if (file.getFileSize() <= 0) {
			return null;
		}

		InputStream stream = null;
		OutputStream bos = null;
		String filepath = "";

		try {
			stream = file.getInputStream();

			File tempFile = new File(dirPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}

			filepath = dirPath + File.separator + fileName;
			bos = new FileOutputStream(filepath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}

		return filepath;
	}

	/**
	 * ����Struts��FormFile��Ƭ�����ϴ�һ���ļ�
	 * 
	 * @param file
	 * @param request
	 * @return �ļ����·��
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String doUploadFilezp(FormFile file,
			HttpServletRequest request) {
		if (file.getFileSize() <= 0) {
			return null;
		}
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if (type.equals("by")) {
			zt = "by";
		}

		String dirStructure = "/" + zt + "uploadfile/pic";
		String fileName = Sequence.getInstance().getSequence(16)
				+ FileIO.getFileExt(file.getFileName());
		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		try {
			InputStream stream = file.getInputStream();
			FileIO.creatFile(stream, uploadPath + File.separator + fileName);
			filepath = dirStructure + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return fileName;
	}

	/**
	 * ˵����������ָ���ض�·�� ����ѧ����Ƭ�ϴ� ����: �²� �ϴ�·��: uploadfile/studentphoto/��ѧ���/ѧ��.jpg
	 * 
	 */
	public static String doXSUploadFile(FormFile file,
			HttpServletRequest request, String rxnf, String xh) {
		if (file.getFileSize() <= 0) {
			return null;
		}
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if (type.equals("by")) {
			zt = "by";
		}
		String dirStructure = "/" + zt + "uploadfile/studentphoto/" + rxnf;// StringUtils.getYear()
																			// +
																			// "/"
		// + StringUtils.getMonth() + "/" + StringUtils.getDay();
		String fileName = xh + FileIO.getFileExt(file.getFileName());

		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		try {
			InputStream stream = file.getInputStream();
			FileIO.creatFile(stream, uploadPath + File.separator + fileName);
			filepath = dirStructure + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filepath;
	}

	/**
	 * ����Struts��FormFile�����ϴ�һ���ļ���ָ���ļ���
	 * 
	 * @param file
	 * @param request
	 * @param fileName
	 * @return �ļ����·��
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String doUploadFile(FormFile file,
			HttpServletRequest request, String fileName) {
		if (file.getFileSize() <= 0) {
			return null;
		}
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if (type.equals("by")) {
			zt = "by";
		}
		String dirStructure = "/" + zt + "uploadfile/" + StringUtils.getYear()
				+ "/" + StringUtils.getMonth() + "/" + StringUtils.getDay();
		fileName += FileIO.getFileExt(file.getFileName());
		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		try {
			InputStream stream = file.getInputStream();
			FileIO.creatFile(stream, uploadPath + File.separator + fileName);
			filepath = dirStructure + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filepath;
	}

	/**
	 * ��ѹ�ļ���
	 * 
	 * @param zipFileName
	 * @param request
	 * @param extPlace
	 * @return �ļ����·��
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void extZipFileList(String zipFileName, String extPlace) {
		try {

			ZipInputStream in = new ZipInputStream(new FileInputStream(
					zipFileName));

			ZipEntry entry = null;

			while ((entry = in.getNextEntry()) != null) {

				String entryName = entry.getName();

				if (entry.isDirectory()) {
					File file = new File(entryName);
					file.mkdirs();
					System.out.println("�����ļ���: " + entryName);
				} else {
					try {
						if (entryName.indexOf("/") >= 0)// ���ļ���
						{
							String[] entryNamesz = entryName.split("/");
							entryName = entryNamesz[entryNamesz.length - 1];
						}
					} catch (Exception e) {

					}

					FileOutputStream os = new FileOutputStream(extPlace + "\\"
							+ entryName);
					System.out.println("�����ļ���: " + entryName);
					// Transfer bytes from the ZIP file to the output file
					byte[] buf = new byte[1024];

					int len;
					while ((len = in.read(buf)) > 0) {
						os.write(buf, 0, len);
					}
					os.close();
					in.closeEntry();

				}
			}

		} catch (IOException e) {
			String aaaString = "";
		}
		System.out.println("��ѹ�ļ��ɹ� ");
	}

	/**
	 * д�ļ�
	 * 
	 * @param msg
	 * @param request
	 * @return �ļ���ַ
	 */
	public static String writeFile(String msg, HttpServletRequest request) {
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if (type.equals("by")) {
			zt = "by";
		}
		String dirStructure = "/" + zt + "uploadfile/" + StringUtils.getYear()
				+ "/" + StringUtils.getMonth() + "/" + StringUtils.getDay();
		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		String fileName = Sequence.getInstance().getSequence(16) + ".html";
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		FileIO.writeFile(msg, uploadPath + File.separator + fileName);
		filepath = dirStructure + "/" + fileName;
		return filepath;
	}

	/**
	 * ͨ����Ե�ַɾ��һ���ļ�
	 * 
	 * @param filePath
	 * @param request
	 * @return boolean
	 */
	public static boolean delUploadFile(String filePath,
			HttpServletRequest request) {
		return FileIO.deleteFile(FileIO.getRealFilePath(request.getSession()
				.getServletContext(), filePath));
	}

	/**
	 * ͨ�����Ե�ַɾ��һ���ļ�
	 * 
	 * @param filePath
	 * @param request
	 * @return boolean
	 */
	public static boolean delUploadFile(String filePath) {
		return FileIO.deleteFile(filePath);
	}

	/**
	 * �ڿ���̨��������е�ӳ���ļ���Ϣ������ά��hibernate.cfg.xml
	 * 
	 * @param path
	 *            Ŀ¼·��D:/workspace1.4/QZWF/src/com/qzdatasoft/comm/framework/
	 *            bean
	 * @param packagename
	 *            ����com/qzdatasoft/comm/framework/bean/
	 * @param cnModleName
	 *            ���ƿ��
	 * @return ӳ���ļ�����
	 */
	public static int getHibernaetMappingXml(String path, String packagename,
			String cnModleName) {
		// ���framework/bean�����е�XML�ļ�
		System.out.println("<!-- " + cnModleName + "��ʼ -->");
		List fbean = FileIO.getDirectoryFilesName(path);
		int count = 0;
		for (int i = 0; i < fbean.size(); i++) {
			if (FileIO.getFileExt(fbean.get(i).toString()).equals(".xml")) {
				System.out.println("<mapping resource=\"" + packagename
						+ fbean.get(i) + "\"/>");
				count++;
			}
		}
		System.out.println("<!-- " + cnModleName + "��������" + count + "������ -->");
		return count;
	}

	public static void main(String[] args) {
		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/comm/framework/bean",
				"com/qzdatasoft/comm/framework/bean/", "���ƿ��");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/comm/framework/zdbeans",
				"com/qzdatasoft/comm/framework/zdbeans/", "�ֵ���Ϣ");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/ggxx/bean",
				"com/qzdatasoft/ggxx/bean/", "������Ϣ");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/ggxx/bean/jzg",
				"com/qzdatasoft/ggxx/bean/jzg/", "��ְ��");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/ggxx/bean/xj",
				"com/qzdatasoft/ggxx/bean/xj/", "ѧ����Ϣ");
		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/bygl",
				"com/qzdatasoft/hdjw/bean/bygl/", "��ҵ����");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/cjgl",
				"com/qzdatasoft/hdjw/bean/cjgl/", "�ɼ�����");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/jxjh",
				"com/qzdatasoft/hdjw/bean/jxjh/", "��ѧ�ƻ�");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/kjgl",
				"com/qzdatasoft/hdjw/bean/kjgl/", "��������");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/kkgl",
				"com/qzdatasoft/hdjw/bean/kkgl/", "���ι���");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/ksgl",
				"com/qzdatasoft/hdjw/bean/ksgl/", "���Թ���");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/pkgl",
				"com/qzdatasoft/hdjw/bean/pkgl/", "�ſι���");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/view",
				"com/qzdatasoft/hdjw/bean/view/", "������ͼ");
		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/xkgl",
				"com/qzdatasoft/hdjw/bean/xkgl/", "ѡ�ι���");
		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/xkjs",
				"com/qzdatasoft/hdjw/bean/xkjs/", "ѧ�ƾ���");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/zljk",
				"com/qzdatasoft/hdjw/bean/zljk/", "�������");

	}
}
