package com.example.pdfapp;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.Date;

import androidx.annotation.RequiresApi;

public class InvoceHeader {
    private static Context context;
    private static String bookingID;

    public InvoceHeader(Context context, String bookingId) {
        this.context = context;
        this.bookingID = bookingId;
    }
    // create table
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static PdfPTable createTable() throws DocumentException {

        PdfPTable table = null;
        try {
            table = new PdfPTable(2);

            // set the width of the table to 100% of page
            table.setWidthPercentage(100);

            // set relative columns width
            table.setWidths(new float[]{0.5f, 0.5f});

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

            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c);

            table.addCell(createLabelCell("Invoice Number : " + bookingID));
            table.addCell(createLabelCell("Invoice Date : " + formattedDate));
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return table;
    }

    private static PdfPCell createLabelCell(String text) {
        // font
        PdfPCell cell = null;
        try {
            Font font = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
            // create cell
            cell = new PdfPCell(new Phrase(text, font));
            // set style
//            Style.invoiceCell(cell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cell;
    }}