package com.example.pdfapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

class TableWithImageInfo { private static Context context;
    private static double totalAmount;
    private static Image image;

    public TableWithImageInfo(Context context) {
        this.context = context;
    }

    // create table
    public static PdfPTable createTable() throws DocumentException {


        PdfPTable table = null;
        try {
            table = new PdfPTable(2);

            // set the width of the table to 100% of page
            table.setWidthPercentage(100);

            // set relative columns width
            table.setWidths(new float[]{0.2f,0.8f});

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


            try {

                table.addCell(createImageCell());
                table.setWidthPercentage(100);
                table.setWidths(new int[]{1, 2});
                table.addCell(createTextCell("Groom Tech Enterprise" +
                        "\n5 th Floor, Surya Plaza, Dattawadi Chowk, " +
                        "\nDandekar Bridge, Pune, Maharashtra 411030" +
                        "\nEmail-ID: info@groomauto.in " +
                        "\nWebsite: https://groomauto.in" +
                        "\nContat Us: +91 98000 11111"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return table;

    }

    public static PdfPCell createImageCell() throws DocumentException, IOException {
        // get input stream
        PdfPCell cell = null;
        try {
            InputStream ims = context.getAssets().open("logo.png");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
//            document.add(image);
            cell = new PdfPCell(image, true);

            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingTop(20);
            cell.setPaddingRight(100);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(Rectangle.NO_BORDER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        }

        return cell;
    }


    public static PdfPCell createTextCell(String text) throws DocumentException, IOException {
        PdfPCell cell = null;
        try {
            cell = new PdfPCell();
            Paragraph p = new Paragraph(text);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cell;
    }

}