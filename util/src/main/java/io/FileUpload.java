package io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.upload.FormFile;

public class FileUpload {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String fileDir;
	private String fileName;
	private int fileMaxSize;
	private String[] fileType;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(int fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}

	public String[] getFileType() {
		return fileType;
	}

	public void setFileType(String[] fileType) {
		this.fileType = fileType;
	}

	public FileUpload(){
		//Ĭ�����֧��50M
		this.fileMaxSize = 50 * 1024 * 1024;
		
		//Ĭ��֧�ֵ��ļ���ʽ
		this.fileType = new String[]{".jpg",".png",".jpeg",".gif",".doc",".docx",".txt",".zip"};
	}

	public void init(HttpServletRequest request,HttpServletResponse response,String fileDir){
		this.request = request;
		this.response = response;
		this.fileDir = fileDir;
	} 
	
	public void init2(HttpServletRequest request,String fileDir){
		this.request = request;
		this.fileDir = fileDir;
	} 
	
	/**
	 * �ļ��ϴ�����
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void upLoad() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		List fileTypeList = Arrays.asList(fileType);
		try{ 
        	//�ж��ļ�·��
        	if(fileDir==null || "".equals(fileDir.trim())){
        		throw new Exception("δָ���ļ��洢Ŀ¼��");
        	}
        	
            if(ServletFileUpload.isMultipartContent(request)) {  
                DiskFileItemFactory factory = new DiskFileItemFactory();  
                //�����ڴ滺�����Ĵ�С
                factory.setSizeThreshold(1 * 1024 * 1024);
                //ָ����ʱ�ļ�Ŀ¼
                factory.setRepository(new File(fileDir+"\\temp"));   
                ServletFileUpload sfu = new ServletFileUpload(factory);  
                sfu.setHeaderEncoding("UTF-8");
                
                //����ÿ���ļ����Ϊ
                sfu.setFileSizeMax(fileMaxSize);
                
                //һ��������ϴ��Ĵ�С
                //sfu.setSizeMax(10 * 1024 * 1024);   
                 
                List<FileItem> fileItems = sfu.parseRequest(request);  
                if (!new File(fileDir).isDirectory()){  
                    new File(fileDir).mkdirs();   
                }  
                
                //����Form�еı��б�
                int leng = fileItems.size();  
                for(int n=0;n<leng;n++) {  
                    FileItem item = fileItems.get(n); // �Ӽ����л��һ���ļ���  
                    
                    if(item==null || item.getName()==null || item.getName().length()==0){
                    	continue;
                    }
                    
                    //��ͨ���ֶ��б�
                    if(item.isFormField()){
                    	continue;
                    }
                    
                    //��ȡ�ϴ��ļ�������
                    String ifileName = item.getName();
                    ifileName = ifileName.substring(ifileName.lastIndexOf("\\") + 1);
                    
                    //��ȡ�ļ���׺��,�Ƚ��ļ���ʽ
                    String eName = ifileName.substring(ifileName.lastIndexOf("."));
                    eName = eName!=null?eName.toLowerCase():"";
                    
                    if(!fileTypeList.contains(eName)){
                    	throw new Exception("�ϴ��ļ���ʽ��֧�֣�");
                   	}
                    
                    //�ϴ���洢�ļ����ƣ����δ�����ļ�������Ĭ��
                    String fname = this.fileName + eName;
                    if(fname==null || "".equals(fname)){
                    	 fname=sdf.format(new Date())+"-"+n+ifileName;
                    }
                    
                    // д���ļ� 
                    item.write(new File(fileDir,fname));   
                }  
            }  
        }catch(Exception e) {
        	//throw e;
        	e.printStackTrace();
        }  
	}
	
	public void upload3() throws Exception {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		List fileTypeList = Arrays.asList(fileType);
		try {
			List<FileItem> list = upload.parseRequest(request);
			int n=0;
			for(FileItem item : list){
				if(item.isFormField()){
					//��ȡ�ϴ��ļ�������
                    String ifileName = item.getName();
                    ifileName = ifileName.substring(ifileName.lastIndexOf("\\") + 1);
                    
                    //��ȡ�ļ���׺��,�Ƚ��ļ���ʽ
                    String eName = ifileName.substring(ifileName.lastIndexOf("."));
                    eName = eName!=null?eName.toLowerCase():"";
                    
                    if(!fileTypeList.contains(eName)){
                    	throw new Exception("�ϴ��ļ���ʽ��֧�֣�");
                   	}
                    
                    //�ϴ���洢�ļ����ƣ����δ�����ļ�������Ĭ��
                    String fname = this.fileName + eName;
                    if(fname==null || "".equals(fname)){
                    	 fname=sdf.format(new Date())+"-"+n+ifileName;
                    }
                    
                    // д���ļ� 
                    item.write(new File(fileDir,fname));
                    n=n+1;
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * �ļ�����
	 * 
	 * @param downfilename
	 * @param filename
	 * @throws IOException
	 */ 
	public void downloadFile(String downfilename, String filename) throws Exception {
		FileInputStream fin = null;
		OutputStream outs = null;
		
		try{
			File file = new File(fileDir, downfilename);
			long len = file.length();
			byte a[] = new byte[600];

			fin = new FileInputStream(file);
			outs = response.getOutputStream();

			response.setHeader("Content-Disposition", "attachment; filename="+ filename);
			//response.setContentType(filetype);
			response.setContentType("application/x-msdownload");
			response.setHeader("Content_Lenght", String.valueOf(len));

			int read = 0;
			while ((read = fin.read(a)) != -1) {
				outs.write(a, 0, read);
			}
			outs.flush();
			outs.close();
			fin.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(fin!=null){
				fin.close();
			}
			if(outs!=null){
				outs.close();
			}
		}
	}
	
	/**
     * FORMACTION�µ��ļ��ϴ�
     * 
     * @param file
     * @param request
     * @param path
     * @return
     */
    public void doUploadFile_formfile(FormFile file) throws Exception{
    	List fileTypeList = Arrays.asList(fileType);
		if(file.getFileSize() <= 0){
		    return;
		} 
		if(fileDir==null || "".equals(fileDir.trim())){
    		throw new Exception("δָ���ļ��洢Ŀ¼��");
    	}
		InputStream stream = null;
		OutputStream bos = null;
		String filepath="";
		
		try{
			stream = file.getInputStream();
			
			File tempFile=new File(fileDir);
		    if(!tempFile.exists()){
		    	tempFile.mkdirs();
		    }
		    String extension=(file.getFileName()).substring((file.getFileName()).lastIndexOf("."), (file.getFileName()).length());
		    if(!fileTypeList.contains(extension.toLowerCase())){
            	throw new Exception("�ϴ��ļ���ʽ��֧�֣�");
           	}
		    
		    filepath = fileDir + File.separator + fileName;
		    bos = new FileOutputStream(filepath);
		    int bytesRead = 0;
		    byte[] buffer = new byte[8192];
		    while ((bytesRead = stream.read(buffer, 0, 8192))!=-1){
		    	bos.write(buffer, 0, bytesRead);
		    }
		    bos.close();
		    stream.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
			if(stream!=null){
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
    }
    
    /**
     * @param args
     * folderFullPath Ŀ���ļ���
     */
    //ɾ���ļ����µ������ļ�
    public void deleteAllFile(String folderFullPath)
    {
	boolean ret = false;
	File file = new File(folderFullPath);
	if (file.exists())
	{
	    if (file.isDirectory())
	    {
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++)
		{
		    String filePath = fileList[i].getPath();
		    deleteAllFile(filePath);
		}
	    }
	    if (file.isFile())
	    {
		file.delete();
	    }
	}else{
		file.mkdirs();
	}
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
     * zipFileName Ŀ���ļ�
     * extPlace ��ѹ��ַ
     */
    public void extZipFileList(String zipFileName, String extPlace)
    {
	try
	{
		ZipInputStream in = new ZipInputStream(new FileInputStream(
		    zipFileName));

	    ZipEntry entry = null;

	    while ((entry = in.getNextEntry()) != null)
	    {

		String entryName = entry.getName();
		
		if (entry.isDirectory())
		{
		    File file = new File(entryName);
		    file.mkdirs();
		    System.out.println("�����ļ���: " + entryName);
		} else
		{
            try
            {
			if(entryName.indexOf("/")>=0)//���ļ���
			{
				String[] entryNamesz=entryName.split("/");
				entryName=entryNamesz[entryNamesz.length-1];
			}
            }
            catch (Exception e) {
				
			}
			
		    FileOutputStream os = new FileOutputStream(extPlace
			    + "\\" + entryName);
		    System.out.println("�����ļ���: " + entryName);
		    //	   Transfer   bytes   from   the   ZIP   file   to   the   output   file 
		    byte[] buf = new byte[1024];

		    int len;
		    while ((len = in.read(buf)) > 0)
		    {
			os.write(buf, 0, len);
		    }
		    os.close();
		    in.closeEntry();

		}
	    }

	} catch (IOException e)
	{
		String aaaString="";
		e.printStackTrace();
	}
	System.out.println("��ѹ�ļ��ɹ� ");
    } 
    
    public void extZipFileListDG(String zipFileName, String extPlace)
    {
	try
	{
		ZipInputStream in = new ZipInputStream(new FileInputStream(
		    zipFileName));

	    ZipEntry entry = null;

	    while ((entry = in.getNextEntry()) != null)
	    {

		String entryName = entry.getName();
		
		if (entry.isDirectory())
		{
		    File file = new File(entryName);
		    file.mkdirs();
		    System.out.println("�����ļ���: " + entryName);
		} else
		{
            try
            {
			if(entryName.indexOf("/")>=0)//���ļ���
			{
				String[] entryNamesz=entryName.split("/");
				entryName=entryNamesz[entryNamesz.length-1];
			}
            }
            catch (Exception e) {
				
			}
			
		    FileOutputStream os = new FileOutputStream(extPlace
			    + File.separator + File.separator + entryName);
		    System.out.println("�����ļ���: " + entryName);
		    //	   Transfer   bytes   from   the   ZIP   file   to   the   output   file 
		    byte[] buf = new byte[1024];

		    int len;
		    while ((len = in.read(buf)) > 0)
		    {
			os.write(buf, 0, len);
		    }
		    os.close();
		    in.closeEntry();

		}
	    }

	} catch (IOException e)
	{
		String aaaString="";
		e.printStackTrace();
	}
	System.out.println("��ѹ�ļ��ɹ� ");
    }
    
}
