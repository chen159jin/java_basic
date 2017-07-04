package printParse.excel.templatePrint;

import java.io.OutputStream;

import com.aspose.cells.Border;
import com.aspose.cells.BorderType;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Picture;
import com.aspose.cells.Style;
import com.aspose.cells.StyleFlag;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.XlsSaveOptions;

public class ExcelServicePrinter {
    private int nIndexBaseOffset = 0;
    private static final String BLANK_BORDER_STYLE = "_blankBorder";
    
    Workbook wb = null;
    
    ExcelServicePrinter(String szExcelFilePath, boolean bIndexBaseOnZero) {
        try {
            wb = new Workbook(szExcelFilePath);
            initBlankBorderStyle();
            
            if (!bIndexBaseOnZero) {
                nIndexBaseOffset = -1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private void initBlankBorderStyle() {
        int nStyleIndex = wb.getStyles().add();
        Style blankBorderStyle = wb.getStyles().get(nStyleIndex);
        Border bottomBorder = blankBorderStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER);
        bottomBorder.setLineStyle(CellBorderType.NONE);
        Border leftBorder = blankBorderStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER);
        leftBorder.setLineStyle(CellBorderType.NONE);
        Border rightBorder = blankBorderStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER);
        rightBorder.setLineStyle(CellBorderType.NONE);
        blankBorderStyle.setName(BLANK_BORDER_STYLE);
    }
    
    /*����ģ��,ģ��ĵ�һ���Ǵ�1��ʼ*/
    public static ExcelServicePrinter openTemplate(String szExcelFilePath) {
        return new ExcelServicePrinter(szExcelFilePath, false);
    }
    
    public static ExcelServicePrinter openTemplate(String szExcelFilePath, boolean bIndexBaseOnZero) {
        return new ExcelServicePrinter(szExcelFilePath, bIndexBaseOnZero);
    }
    
    public Workbook getWorkbook() {
        return wb;
    }
    /*����ģ����Excel(��nSrcStartRow��nSrcEndRow) ��ws ��nDstStartRow ��*/
    public void copyRange(Worksheet ws, int nSrcStartRow, int nSrcEndRow, int nDstStartRow) {
        int l_nSrcStartRow = nSrcStartRow + this.nIndexBaseOffset;
        int l_nSrcEndRow = nSrcEndRow + this.nIndexBaseOffset;
        int l_nDstStartRow = nDstStartRow + this.nIndexBaseOffset;
        
        int l_nSrcStartRow1 = l_nSrcStartRow;
        int l_nSrcEndRow1 = l_nSrcEndRow;
        int l_nDstStartRow1 = l_nDstStartRow;
        
        int l_nSrcStartRow2 = -1;
        int l_nSrcEndRow2 = -1;
        int l_nDstStartRow2 = -1;
        
        if (l_nDstStartRow <= l_nSrcStartRow) {
            int nSrcMovedRows = l_nSrcEndRow - l_nSrcStartRow + 1;
            l_nSrcStartRow1 += nSrcMovedRows;
            l_nSrcEndRow1 += nSrcMovedRows;
        }
        
        if (l_nDstStartRow > l_nSrcStartRow && l_nDstStartRow <= l_nSrcEndRow) {
            l_nSrcEndRow1 = l_nDstStartRow - 1;

            int nSrcMovedRows = l_nSrcEndRow - l_nSrcStartRow + 1;
            l_nSrcStartRow2 = l_nDstStartRow + nSrcMovedRows;
            l_nSrcEndRow2 = l_nSrcEndRow + nSrcMovedRows;
            
            int nSrcMovedRows2 = l_nSrcEndRow1 - l_nSrcStartRow1 + 1;
            l_nDstStartRow2 = l_nDstStartRow + nSrcMovedRows2;
        }
        
        int nInsertRows1 = l_nSrcEndRow1 - l_nSrcStartRow1 + 1;
        if (nInsertRows1 >= 1) {
            ws.getCells().insertRows(l_nDstStartRow1, 1);
            Style blankStyle = wb.getStyles().get(BLANK_BORDER_STYLE);
            StyleFlag sf = new StyleFlag();
            sf.setBorders(true);
            ws.getCells().getRows().get(l_nDstStartRow1).applyStyle(blankStyle, sf);
          
            if (nInsertRows1 > 1) {
                ws.getCells().insertRows(l_nDstStartRow1 + 1, nInsertRows1 - 1);
            }
        }
        ws.getCells().copyRows(ws.getCells(), l_nSrcStartRow1, l_nDstStartRow1, l_nSrcEndRow1 - l_nSrcStartRow1 + 1);

        if (l_nDstStartRow2 >= 0) {
            int nInsertRows2 = l_nSrcEndRow2 - l_nSrcStartRow2 + 1;
            if (nInsertRows2 >= 1) {
                ws.getCells().insertRows(l_nDstStartRow2, 1);
                Style blankStyle = wb.getStyles().get(BLANK_BORDER_STYLE);
                StyleFlag sf = new StyleFlag();
                sf.setBorders(true);
                ws.getCells().getRows().get(l_nDstStartRow2).applyStyle(blankStyle, sf);
              
                if (nInsertRows2 > 1) {
                    ws.getCells().insertRows(l_nDstStartRow2 + 1, nInsertRows2 - 1);
                }
            }
            ws.getCells().copyRows(ws.getCells(), l_nSrcStartRow2, l_nDstStartRow2, l_nSrcEndRow2 - l_nSrcStartRow2 + 1);
        }
    }
    /*����ģ����Excel(��nSrcStartRow��nSrcEndRow) ��szSheetName��sheet�еĵ�nDstStartRow ��*/
    public void copyRange(String szSheetName, int nSrcStartRow, int nSrcEndRow, int nDstStartRow) {
        Worksheet ws = wb.getWorksheets().get(szSheetName);
        copyRange(ws, nSrcStartRow, nSrcEndRow, nDstStartRow);
    }
    
    /*����ģ����Excel(��nSrcStartRow��nSrcEndRow) ��nSheetIndex��sheet�еĵ�nDstStartRow ��*/
    public void copyRange(int nSheetIndex, int nSrcStartRow, int nSrcEndRow, int nDstStartRow) {
        Worksheet ws = wb.getWorksheets().get(nSheetIndex);
        copyRange(ws, nSrcStartRow, nSrcEndRow, nDstStartRow);
    }
    public Worksheet getSheet(int index){
    	 Worksheet ws = wb.getWorksheets().get(index);
    	 return ws;
    }
    
    /*����ģ����Excel(��nSrcStartRow��nSrcEndRow) ��nDstStartRow ��*/
    public void copyRange(int nSrcStartRow, int nSrcEndRow, int nDstStartRow) {
        Worksheet ws = wb.getWorksheets().get(0);
        copyRange(ws, nSrcStartRow, nSrcEndRow, nDstStartRow);
    }
    
    /*ɾ����*/
    public void delRows(Worksheet ws, int nStartRow, int nEndRow) {
        int l_nStartRow = nStartRow + this.nIndexBaseOffset;
        int l_nEndRow = nEndRow + this.nIndexBaseOffset;
        ws.getCells().deleteRows(l_nStartRow, l_nEndRow - l_nStartRow + 1);
    }

    public void delRows(String szSheetName, int nStartRow, int nEndRow) {
        Worksheet ws = wb.getWorksheets().get(szSheetName);
        ws.getCells().deleteRows(nStartRow, nEndRow - nStartRow + 1);
    }

    public void delRows(int nSheetIndex, int nStartRow, int nEndRow) {
        Worksheet ws = wb.getWorksheets().get(nSheetIndex);
        ws.getCells().deleteRows(nStartRow, nEndRow - nStartRow + 1);
    }
    /*ɾ����nStartRow ��nEndRow*/
    public void delRows(int nStartRow, int nEndRow) {
        Worksheet ws = wb.getWorksheets().get(0);
        delRows(ws, nStartRow, nEndRow);
    }
    
    /*�ϲ���Ԫ��*/
    public void mergeCells(Worksheet ws, int nStartCol, int nStartRow, int nEndCol, int nEndRow) {
        int l_nStartCol = nStartCol + this.nIndexBaseOffset;
        int l_nStartRow = nStartRow + this.nIndexBaseOffset;
        int l_nEndCol = nEndCol + this.nIndexBaseOffset;
        int l_nEndRow = nEndRow + this.nIndexBaseOffset;
        ws.getCells().merge(l_nStartRow, l_nStartCol, (l_nEndRow - l_nStartRow + 1), (l_nEndCol - l_nStartCol + 1));
    }

    public void mergeCells(String szSheetName, int nStartCol, int nStartRow, int nEndCol, int nEndRow) {
        Worksheet ws = wb.getWorksheets().get(szSheetName);
        mergeCells(ws, nStartCol, nStartRow, nEndCol, nEndRow);
    }

    public void mergeCells(int nSheetIndex, int nStartCol, int nStartRow, int nEndCol, int nEndRow) {
        Worksheet ws = wb.getWorksheets().get(nSheetIndex);
        mergeCells(ws, nStartCol, nStartRow, nEndCol, nEndRow);
    }
    
    public void mergeCells(int nStartCol, int nStartRow, int nEndCol, int nEndRow) {
        Worksheet ws = wb.getWorksheets().get(0);
        mergeCells(ws, nStartCol, nStartRow, nEndCol, nEndRow);
    }
    /*����*/
    public void write(OutputStream os) {
        try {
            XlsSaveOptions saveOptions = new XlsSaveOptions();
            wb.save(os, saveOptions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /*ǿ�Ʒ�ҳ*/
    public void printPageBreak(int nRow) {
        Worksheet ws = wb.getWorksheets().get(0);
        printPageBreak(ws, nRow);
    }
    public void printPageBreak(Worksheet ws, int nRow) {
        int l_nRow = nRow + this.nIndexBaseOffset;
        ws.getHorizontalPageBreaks().add(l_nRow);
    }

    public void printPageBreak(String szSheetName, int nRow) {
        Worksheet ws = wb.getWorksheets().get(szSheetName);
        printPageBreak(ws, nRow);
    }

    public void printPageBreak(int nSheetIndex, int nRow) {
        Worksheet ws = wb.getWorksheets().get(nSheetIndex);
        printPageBreak(ws, nRow);
    }
    
    public void printCellText(Worksheet ws, int nRow, int nCol, String inputString) {
        int l_nRow = nRow + this.nIndexBaseOffset;
        int l_nCol = nCol + this.nIndexBaseOffset;
        ws.getCells().get(l_nRow, l_nCol).setValue(inputString);
    }
    
    /*��inputString��ӡ��szSheetName ��sheet�ĵ�nRow�� nCol��*/

    public void printCellText(String szSheetName, int nRow, int nCol, String inputString) {
        Worksheet ws = wb.getWorksheets().get(szSheetName);
        printCellText(ws, nRow, nCol, inputString);
    }
    
    public void printCellText(int nSheetIndex, int nRow, int nCol, String inputString) {
        Worksheet ws = wb.getWorksheets().get(nSheetIndex);
        printCellText(ws, nRow, nCol, inputString);
    }
    /*��ӡinputString��nRow�е�nCol��*/
    public void printCellText(int nRow, int nCol, String inputString) {
        Worksheet ws = wb.getWorksheets().get(0);
        printCellText(ws, nRow, nCol, inputString);
    }
    public void printCellText(int nRow, int nCol, double inputString) {
        Worksheet ws = wb.getWorksheets().get(0);
        int l_nRow = nRow + this.nIndexBaseOffset;
        int l_nCol = nCol + this.nIndexBaseOffset;
        ws.getCells().get(l_nRow, l_nCol).setValue(inputString);
    }
    
    /*ͨ��Excel��ӡpdf��ʽ��*/
    /*��Excel��ӡ���ĵ�ת����pdf*/
    public void exportPDF(OutputStream os) {
        try {
            PdfSaveOptions saveOptions = new PdfSaveOptions();
            wb.save(os, saveOptions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void _insertPic(Worksheet sheet, String PicAddress, int PicXh, int PicHeight, int PicWidth, int PicAlignRow, int PicAlignCol, int offsetLeft, int offsetTop)
    {
        Worksheet ws = sheet;
        int l_nPicAlignCol = PicAlignCol + this.nIndexBaseOffset;
        int l_nPicAlignRow = PicAlignRow + this.nIndexBaseOffset;
        
        try {
            int nIndex = ws.getPictures().add(l_nPicAlignRow, l_nPicAlignCol, PicAddress);
            Picture pic = ws.getPictures().get(nIndex);
            pic.setWidth(PicWidth);
            pic.setHeight(PicHeight);
            pic.setLeft(offsetLeft);
            pic.setTop(offsetTop);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void _insertPic(String szSheetName, String PicAddress, int PicXh, int PicHeight, int PicWidth, int PicAlignRow, int PicAlignCol, int offsetLeft, int offsetTop)
    {
        Worksheet ws = wb.getWorksheets().get(szSheetName);
        _insertPic(ws, PicAddress, PicXh, PicHeight, PicWidth, PicAlignRow, PicAlignCol, offsetLeft, offsetTop);
    }
    
    public void _insertPic(int nSheetIndex, String PicAddress, int PicXh, int PicHeight, int PicWidth, int PicAlignRow, int PicAlignCol, int offsetLeft, int offsetTop)
    {
        Worksheet ws = wb.getWorksheets().get(nSheetIndex);
        _insertPic(ws, PicAddress, PicXh, PicHeight, PicWidth, PicAlignRow, PicAlignCol, offsetLeft, offsetTop);
    }
    /*��ӡ��Ƭ*/
    
    public void insertPic(String PicAddress, int PicXh, int PicHeight, int PicWidth, int PicAlignRow, int PicAlignCol, int offsetLeft, int offsetTop)
    {
        Worksheet ws = wb.getWorksheets().get(0);
        _insertPic(ws, PicAddress, PicXh, PicHeight, PicWidth, PicAlignRow, PicAlignCol, offsetLeft, offsetTop);
    }
    
    public void insertPic(String PicAddress, int PicXh, int PicHeight, int PicWidth, int PicAlignRow, int PicAlignCol)
    {
        insertPic(PicAddress, PicXh, PicHeight, PicWidth, PicAlignRow, PicAlignCol, 2, 0);
    }
}
