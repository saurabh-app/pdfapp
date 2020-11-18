package com.example.pdfapp;

import android.content.Context;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import static android.content.Context.MODE_PRIVATE;

public class AmountTableBuilder { private static Context context;
    private static double totalAmount;
    private static String price;
    private static String packageName;


    public AmountTableBuilder(Context context, String price, String packageName) {
        this.context = context;
        this.price = price;
        this.packageName = packageName;
    }

    // create table
    public static PdfPTable createTable() throws DocumentException {

        PdfPTable table = null;
        try {
            table = new PdfPTable(2);

            // set the width of the table to 100% of page
            table.setWidthPercentage(100);

            // set relative columns width
            table.setWidths(new float[]{0.8f, 0.2f});

            // ----------------Table Header "Title"----------------

            Font font = new Font(Font.FontFamily.HELVETICA, 17, Font.BOLD, BaseColor.WHITE);
            // create header cell
            PdfPCell cell = new PdfPCell();
            // set Column span "1 cell = 6 cells width"
            cell.setColspan(2);
            // set style
//            Style.headerCellStyle(cell);
            // add to table
            table.addCell(cell);

            //-----------------Table Cells Label/Value------------------

            table.addCell(createLabelCell("Package Name"));
            table.addCell(createLabelCell("Price "));
            table.addCell(createValueCell("" + packageName));
            table.addCell(createValueCell("" + price));
            table.addCell(createLabelCellGST("GST : "));
            table.addCell(createValueCell("0"));
            table.addCell(createLabelCellGST("Total : "));
            table.addCell(createValueCell("" + price));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return table;
    }

    // create cells
    private static PdfPCell createLabelCellGST(String text) {
        // font
        PdfPCell cell = null;
        try {
            Font font = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.DARK_GRAY);

            // create cell
            cell = new PdfPCell(new Phrase(text, font));

            // set style
//            Style.valueGSTTOTAL(cell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cell;
    }

    // create cells
    private static PdfPCell createLabelCell(String text) {
        // font
        PdfPCell cell = null;
        try {
            Font font = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.DARK_GRAY);

            // create cell
            cell = new PdfPCell(new Phrase(text, font));

            // set style
//            Style.valueCellStyle(cell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cell;
    }

    // create cells
    private static PdfPCell createValueCell(String text) {

        // font
        Font font = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);

        // create cell
        PdfPCell cell = null;

        try {
            cell = new PdfPCell(new Phrase(text, font));

            // set style
//            Style.valueCellStyle(cell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cell;
    }
}
