package printParse.excel.templatePrint;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrintClientTest {/*
	public ActionForward printCjfxbdtb(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			// �����������
			PrintWriter out = response.getWriter();
			// out.print("<script
			// language='javaScript'>window.parent.Form1.action='';</script>");
			// ģ������
			String BBMC = "�ɼ�������";
			// ��ȡģ��URL
			String MBURL = "http://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath()
					+ "/template/cjfxddtb.xls";
			// ��������Excel�ļ�
			ImportExcel.importExcelStart(MBURL, out);
			// ���ñ�ͷ
			ImportExcel.printCellText(1, 1, "xxxxѧУ"
					+ "�γ̳ɼ�������", out);

			int startrow = 7;
			int rol1 = 1;
			int row = 0;
			for (int i = 0; i < 5; i = i + 2) {
				rol1 = 1;
				ImportExcel.printCellText(startrow, rol1++, "", out);
				ImportExcel.printCellText(startrow, rol1++, "", out);

				startrow++;
				row++;
			}
			startrow = 7;
			row = startrow + row - 1;
			int m = 0;
			ImportExcel.printCellText(row + 2, 1, "1", out);// �ɼ��ٷֱ�

			ImportExcel.printCellText(row + 5 + m++, 2,"2", out);// Ӧ������

			ImportExcel.printCellText(row + 5 + m, 2, 3+ "", out);// ȱ������
			ImportExcel.printCellText(row + 5 + m++, 3, 4 + "%", out);// ȱ���ٷֱ�

			ImportExcel.printCellText(row + 5 + m, 2, 5+ "", out);// 100-90��
			ImportExcel.printCellText(row + 5 + m++, 3, 6 + "%", out);// 100-90�ְٷֱ�

			ImportExcel.printCellText(row + 5 + m, 2, 7+ "", out);// 89-80��
			ImportExcel.printCellText(row + 5 + m++, 3, 8 + "%", out);// 89-80�ְٷֱ�

			ImportExcel.printCellText(row + 5 + m, 2, 9+ "", out);// 79-70��
			ImportExcel.printCellText(row + 5 + m++, 3, 10 + "%", out);// 79-70�ְٷֱ�

			ImportExcel.printCellText(row + 5 + m, 2, 11+ "", out);// 69-60��
			ImportExcel.printCellText(row + 5 + m++, 3, 12 + "%", out);// 69-60�ְٷֱ�

			ImportExcel.printCellText(row + 5 + m, 2, 13+ "", out);// 59�ּ�������
			ImportExcel.printCellText(row + 5 + m++, 3, 14 + "%", out);// 59�ּ������°ٷֱ�
			ImportExcel.printCellText(row + 5 + m, 2, 15+ "", out);// ƽ����
			ImportExcel.delRows(6, 6, out);
			ImportExcel.importExcelEnd(BBMC, out);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
*/}
