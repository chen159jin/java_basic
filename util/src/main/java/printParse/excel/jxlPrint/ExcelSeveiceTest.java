package printParse.excel.jxlPrint;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExcelSeveiceTest {
	/**
	 * jxl ��ӡ���
	 * @param request
	 * @param response
	 * @param fieldTitle   ������
	 * String[] fieldTitle = {"ѧ��ѧ��:4:1:100:t1.xnxq01id,Ժϵ����:1:1:140:t1.dwmc,����ԭ��:8:1:100:dmmc,�����˴�:2:1:100:t1.jsrs,����ѧʱ:3:1:80:t1.zxs,��ѧʱ:6:1:80:t1.syzxs,�ڴ�:3:1:80:t1.zxs,������:5:1:80:t1.tkl,"};
	 * @param excelTitle	��ӡ����
	 * @param strFoot		��ӡ���
	 * @param rowSet		sql��ѯlist���鼯��
	 */
	private void printManyTitleExl(HttpServletRequest request, HttpServletResponse response, String[] fieldTitle,
			String excelTitle, String strFoot, List rowSet) {
		String path = request.getRealPath("/ExcelPath");
		try {
			//���ֶ���Ϣ
			String[] fieldInfo = fieldTitle[fieldTitle.length-1].split(",");
			
			String[] titles1 = new String[1];
			titles1[0] = excelTitle;
			WriteExcelUtil weu = new WriteExcelUtil(response, 2, 0, "", path);
			weu.setBottomFooter(null, null, "&10" + strFoot + "&10��&Pҳ / ��&Nҳ");
			// ��ʽ
			String titleFontName = "����";
			int titleFontSize = 18;
			String filedFontName = "����";
			int filedFontSize =10;
			String cellFontName = "����";
			int cellFontSize = 10;
			weu.mergeCells(0, 0, fieldInfo.length - 1, 1);//��0�кϲ���fieldInfo.length - 1��,��0�кϲ���1��
			weu.startWriteComm(0, 0, titles1, true, 1, titleFontName, titleFontSize);
			weu.startWriteComm(0, 2, fieldInfo, false, 1, filedFontName, filedFontSize);
			int index = 2 + fieldTitle.length;
			int count = 0;
			for (Iterator it = rowSet.iterator(); it.hasNext();) {
				weu.writeCell(0, index, it.next(), 0, cellFontName, cellFontSize, fieldInfo);
				index++;
			}
			for (int i = 0; i < rowSet.size(); i++) {
				Object[] obj=(Object[]) rowSet.get(i);
				String xh = obj[9].toString();
				if("1".equals(xh)){
					count++;
				}else{
					weu.mergeCells(0, 3+i-count,0, 3+i);//��Ԫ����к�,��Ԫ����к�,���ºϲ�������,���ºϲ�������
					weu.mergeCells(1, 3+i-count,1, 3+i);//��Ԫ����к�,��Ԫ����к�,���ºϲ�������,���ºϲ�������
					count=0;
				}
			}
			
			weu.saveFile(request, excelTitle);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
