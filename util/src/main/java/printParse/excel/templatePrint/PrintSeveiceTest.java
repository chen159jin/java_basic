package printParse.excel.templatePrint;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PrintSeveiceTest {/*
	public static void main(String[] args) {
		//String webRoot = getServletContext().getRealPath("/")+ "temple"+ File.separatorChar + "xscjfxb.xls";
		String filePath = ""+ File.separatorChar + "src"+ File.separatorChar + "main"+ File.separatorChar + 
				"resources"+ File.separatorChar + "temple"
				+ File.separatorChar + "xscjfxb.xls";
		System.out.println(filePath);
		ExcelServicePrinter print = ExcelServicePrinter.openTemplate(filePath);
		//response.reset();// response.reset() ������ײ��Ŀհ��С�
		//response.setContentType("application/x-msdownload");
		//String filename = new String(title.getBytes("GBK"),"iso8859-1");
		//response.addHeader("Content-Disposition", "attachment;filename="+filename+".xls");// ��ʾ�����ļ��� headerͷ	
		//output = response.getOutputStream();	
		String title="�ɼ���������";
		print.printCellText(2, 1, " ��ӡ��2��" );// ��ӡ��2��
		print.printCellText(3, 1, "��ӡ��3��" );// ��ӡ��3��
		int startrow = 7;
		int rol1 = 1;
		int row = 0;
		for (int i = 0; i <5; i = i + 2) {
			rol1 = 1;
			print.copyRange(6, 6, startrow );
			print.printCellText(startrow, rol1++,"ѧ��ѧ��"+i );
			print.printCellText(startrow, rol1++,
					"ѧ������"+i);
			
			startrow++;
			row++;
		}
		startrow = 7;
		row = startrow + row - 1;
		int m = 0;
		print.printCellText(row + 2, 1, 1 );// �ɼ��ٷֱ�
		 
		print.printCellText(row + 5 + m++, 2, 2 );// Ӧ������

		print.printCellText(row + 5 + m++, 2, 3 );// ȱ������

		print.printCellText(row + 5 + m++, 2, 4 );// 100-90��

		print.printCellText(row + 5 + m++, 2, 5 );// 89-80��

		print.printCellText(row + 5 + m++, 2, 6 );// 79-70��

		print.printCellText(row + 5 + m++, 2, 7 );// 69-60��

		print.printCellText(row + 5 + m++, 2, 8 );// 59�ּ�������
		print.printCellText(row + 5 + m++, 2, 9 );// ƽ����
		print.delRows(6, 6 );
		//	print.importExcelEnd(BBMC, out);
			//------------------------------------------- 
		//print.write();	
	}
	public ActionForward printCjfxbdtb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream output = null;   
		String filePath = this.servlet.getServletContext().getRealPath("template")
				+ File.separatorChar + "xscjfxb.xls";
		ExcelServicePrinter print = ExcelServicePrinter.openTemplate(filePath);
		String title="�ɼ���������";
		response.reset();// response.reset() ������ײ��Ŀհ��С�
		response.setContentType("application/x-msdownload");
		String filename = new String(title.getBytes("GBK"),"iso8859-1");
		response.addHeader("Content-Disposition", "attachment;filename="+filename+".xls");// ��ʾ�����ļ��� headerͷ	
		output = response.getOutputStream();
		//------------------------
		print.printCellText(2, 1, " ��ӡ��2��" );// ��ӡ��2��
		print.printCellText(3, 1, "��ӡ��3��" );// ��ӡ��3��
		int startrow = 7;
		int rol1 = 1;
		int row = 0;
		int zps=1; 
		for (int i = 0; i <5; i = i + 2) {
			rol1 = 1;
			print.copyRange(6, 6, startrow );
			print.printCellText(startrow, rol1++,"ѧ��ѧ��"+i );
			print.printCellText(startrow, rol1++,
					"ѧ������"+i);
			
			startrow++;
			row++;
		}
		print.printPageBreak(row);//ǿ�Ʒ�ҳ
		String path="http://" +request.getServerName() + ":" + request.getServerPort()+request.getContextPath() + "";
		print.insertPic(path, zps, 80, 75, startrow+1, 5);
		zps++;
		startrow = 7;
		row = startrow + row - 1;
		int m = 0;
		print.printCellText(row + 2, 1, 1 );// �ɼ��ٷֱ�
		 
		print.printCellText(row + 5 + m++, 2, 2 );// Ӧ������

		print.printCellText(row + 5 + m++, 2, 3 );// ȱ������

		print.printCellText(row + 5 + m++, 2, 4 );// 100-90��

		print.printCellText(row + 5 + m++, 2, 5 );// 89-80��

		print.printCellText(row + 5 + m++, 2, 6 );// 79-70��

		print.printCellText(row + 5 + m++, 2, 7 );// 69-60��

		print.printCellText(row + 5 + m++, 2, 8 );// 59�ּ�������
		print.printCellText(row + 5 + m++, 2, 9 );// ƽ����
		print.delRows(6, 6 );
		print.write(output);
		return null;
	}
	
*/}
