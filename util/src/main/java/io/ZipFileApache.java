package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import org.apache.tools.zip.ZipEntry;  
import org.apache.tools.zip.ZipOutputStream; 

import javax.servlet.http.HttpServletResponse;
/**
 * ���� ����ѹ�����������
 * �� ZipFile.java ��ͬ �����������ƺ�Apache��������ͻ
 * @author jin
 *
 */ 
public class ZipFileApache
{
    private HttpServletResponse response;
    private File file;
    /**  
     * ����ļ�ѹ������  
     *   
     * @param files  
     *            ��Ҫ������ļ��б�  
     * @param outputStream  
     */  
    public void zipFile(List files, ZipOutputStream outputStream) {   
        int size = files.size();   
        for (int i = 0; i < size; i++) {   
            File file = (File) files.get(i);   
            zipFilepath(file, outputStream);   
        }   
    }   
  
    /**  
     * ����������ļ��������ļ����д��  
     */  
    public void zipFilepath(File inputFile, ZipOutputStream ouputStream) {   
        try {   
        	ouputStream.setEncoding("gbk");
            if (inputFile.exists()) {   
                if (inputFile.isFile()) {   
                    FileInputStream in = new FileInputStream(inputFile);   
                    BufferedInputStream bins = new BufferedInputStream(in, 512);   
                    ZipEntry entry = new ZipEntry(inputFile.getName());   
                    ouputStream.putNextEntry(entry);   
                    // ��ѹ���ļ����������   
                    int nNumber;   
                    byte[] buffer = new byte[512];   
                    while ((nNumber = bins.read(buffer)) != -1) {   
                        ouputStream.write(buffer, 0, nNumber);   
                        // �رմ�����������   
                    }   
                    bins.close();   
                    in.close();   
                } else {   
                    try {   
                        File[] files = inputFile.listFiles();   
                        for (int i = 0; i < files.length; i++) {   
                            zipFilepath(files[i], ouputStream);   
                        }   
                    } catch (Exception e) {   
                        e.printStackTrace();   
                    }   
                }   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }  
    
    /**
     * �����ļ�
     * @param filePath --�ļ�����·��
     * @param response --HttpServletResponse����
     */
    public  void downloadFile(String filePath,
	    javax.servlet.http.HttpServletResponse response)
    {

	String fileName = ""; //�ļ�����������û������ضԻ���
	//    ���ļ�����·������ȡ�ļ����������б���ת������ֹ������ȷ��ʾ������
	try
	{
	    if (filePath.lastIndexOf("/") > 0)
	    {
		fileName = new String(filePath.substring(
			filePath.lastIndexOf("/") + 1, filePath.length())
			.getBytes("GB2312"), "ISO8859_1");
	    } else if (filePath.lastIndexOf("\\") > 0)
	    {
		fileName = new String(filePath.substring(
			filePath.lastIndexOf("\\") + 1, filePath.length())
			.getBytes("GB2312"), "ISO8859_1");
	    }

	} catch (Exception e)
	{
	}
	//    ��ָ���ļ�������Ϣ
	FileInputStream fs = null;
	try
	{
	    fs = new FileInputStream(new File(filePath));
	} catch (FileNotFoundException e)
	{
	    e.printStackTrace();
	    return;
	}
	//    ������Ӧͷ�ͱ����ļ��� 
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=\""
		+ fileName + "\"");
	//    д������Ϣ
	int b = 0;
	try
	{
	    PrintWriter out = response.getWriter();
	    while ((b = fs.read()) != -1)
	    {
		out.write(b);
	    }
	    fs.close();
	    out.close();
	    System.out.println("�ļ��������.");
	} catch (Exception e)
	{
	    System.out.println("�����ļ�ʧ��!");
	}
    }

}
