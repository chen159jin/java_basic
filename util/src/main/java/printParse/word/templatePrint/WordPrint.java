package printParse.word.templatePrint;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspose.words.Body;
import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.NodeType;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.RelativeVerticalPosition;
import com.aspose.words.Row;
import com.aspose.words.Run;
import com.aspose.words.SaveFormat;
import com.aspose.words.Section;
import com.aspose.words.Table;
import com.aspose.words.Underline;
import com.aspose.words.WrapType;

public class WordPrint {
	/**
	 * ��ӡ��������
	 * 
	 * @param request
	 * @param response
	 * @param list
	 */
	private void zdzmPrint(HttpServletRequest request,HttpServletResponse response, List list) {
		String MBURL = request.getSession().getServletContext()
				.getRealPath("template//xszdzm.doc");
		try {
			Document doc = new Document(MBURL);
			Section section = doc.getSections().get(0);
			Body body = section.getBody();
			Run run = new Run(doc, "����:");
			run.getFont().setSize(16);
			run.getFont().setName("����");
			run.getFont().setUnderline(Underline.SINGLE);// �»���
			body.getParagraphs().get(4).getRuns().add(run);// ����ڵ�4����

			run = new Run(doc, ",�Ա�");
			run.getFont().setSize(16);
			run.getFont().setName("����");
			body.getParagraphs().get(5).getRuns().add(run);// ����ڵ�5����

			String filename = "��������ѧԺѧ���ڶ�֤�������";
			byte[] by = filename.getBytes("gbk");
			filename = new String(by, "ISO-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename + ".doc");
			response.addHeader("Content-Length", "");
			response.setContentType("application/octet-stream");
			OutputStream out = response.getOutputStream();
			doc.save(out, SaveFormat.DOC);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ӡ���
	 * @param request
	 * @param response
	 * @param list
	 */
	private void qgzxPrint(HttpServletRequest request,HttpServletResponse response, List list) {
		String MBURL = request.getSession().getServletContext()
				.getRealPath("template//qgzxsq.doc");
		try {
			Document doc = new Document(MBURL);

			String filename = "�ڹ���ѧ�����";
			
			Section section = doc.getSections().get(0);
			Body body = section.getBody();
			Run run = new Run(doc, "");
			
			Table t = (Table) doc.getChild(NodeType.TABLE, 0, true);
			Row rows = t.getRows().get(0);//��0�� 
			Cell cell = rows.getCells().get(1);
			run = new Run(doc, "xxx");
			run.getFont().setSize(12);
			run.getFont().setName("����");
			cell.getParagraphs().get(0).getRuns().add(run);	
			
			cell = rows.getCells().get(3);
			run = new Run(doc, "ABC��");
			run.getFont().setSize(12);
			run.getFont().setName("����");
			cell.getParagraphs().get(0).getRuns().add(run);	
			 
			String imgPath = "xxx.jpg";
			DocumentBuilder builder = new DocumentBuilder(doc);
			
			try{
				builder.insertImage(imgPath, RelativeHorizontalPosition.MARGIN,315, RelativeVerticalPosition.MARGIN, 47, 100, 140, WrapType.SQUARE);
			}catch (FileNotFoundException e) {
				//	 this.writeJsMessage(response, "alert('�Ҳ�����Ƭ');");
			}
			rows = t.getRows().get(1); //��1�� 
			cell = rows.getCells().get(1);
			run = new Run(doc, "�༶1");
			run.getFont().setSize(12);
			run.getFont().setName("����");
			cell.getParagraphs().get(0).getRuns().add(run);
			
			cell = rows.getCells().get(3);
			run = new Run(doc, "1");
			run.getFont().setSize(12);
			run.getFont().setName("����");
			cell.getParagraphs().get(0).getRuns().add(run);
			
			rows = t.getRows().get(2); //��2�� 
			cell = rows.getCells().get(1);
			run = new Run(doc, "��");
			run.getFont().setSize(12);
			run.getFont().setName("����");
			cell.getParagraphs().get(0).getRuns().add(run);
			
			cell = rows.getCells().get(3);
			run = new Run(doc, "2016");
			run.getFont().setSize(12);
			run.getFont().setName("����");
			cell.getParagraphs().get(0).getRuns().add(run);
			
			byte[] by = filename.getBytes("gbk");
			filename = new String(by, "ISO-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename + ".doc");
			response.addHeader("Content-Length", "");
			response.setContentType("application/octet-stream");
			OutputStream out = response.getOutputStream();
			doc.save(out, SaveFormat.DOC);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
