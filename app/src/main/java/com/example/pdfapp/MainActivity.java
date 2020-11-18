package com.example.pdfapp;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


public class MainActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    private String path = Environment.getExternalStorageDirectory().getPath()+"/app_" ;
    private com.github.barteksc.pdfviewer.PDFView pdfView;
    private Button share_pdf;
    private String fileName="saurabh"+path+".pdf";
//    private String fileName="saurabh"+".pdf";
    private String price="500";
    private String vehicleCompany="mnjkhbkjf";
    private String vehicleModel="kjjkhkhv";
    private String vehicleNumber="kjnjkhk";
    private String time="10:00";
    private String date="20/10/2020";
    private String pickupdrop="true";
    private String pickupAddress="hjjghjg";
    private String pickupTime;
    private String statusText;
    private String packageName="fhfhgfghfhf";
    private String bokking_id="12345";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdfView = findViewById(R.id.pdfView);
//        pdfView.fromAsset("sample_file.pdf");
        share_pdf = findViewById(R.id.share_pdf);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
            try {

                Document document = new Document();

                PdfWriter.getInstance(document, new FileOutputStream(fileName));

                //create table class instance.
                TableBuilder tableBuilder = new TableBuilder(MainActivity.this);
                TableWithImageInfo tableWithImageInfo = new TableWithImageInfo(MainActivity.this);
                InvoceHeader invoceHeader = new InvoceHeader(MainActivity.this, bokking_id);
                AmountTableBuilder amountTableBuilder = new AmountTableBuilder(MainActivity.this, price, packageName);

                document.open();

                document.add(tableWithImageInfo.createTable());

                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    document.add(invoceHeader.createTable());
                }

                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));

                document.add(tableBuilder.createTable());

                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));

                document.add(amountTableBuilder.createTable());
                document.close();

                Toast.makeText(getApplicationContext(), "PDF Saved to: " + fileName, Toast.LENGTH_LONG).show();

            } catch (DocumentException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error : " + e, Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error : " + e, Toast.LENGTH_SHORT).show();
            }

        try {
                displayPDF(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error : " + e, Toast.LENGTH_SHORT).show();
            }

        share_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create new Intent
                File file = new File(fileName);

                Uri path = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                // The second parameter is the name of authorities.
//                Uri path = null;
//                try {
//                    path = FileProvider.getUriForFile(MainActivity.this,
//                            "com.example.pdfapp.provider", file);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                Intent pdfOpenintent = new Intent(Intent.ACTION_SEND);
                pdfOpenintent.putExtra(Intent.EXTRA_STREAM, path);
                pdfOpenintent.setDataAndType(path, "application/pdf");
                try {
                    startActivity(pdfOpenintent);
                } catch (ActivityNotFoundException e) {
                }
            }

        });
    }
    private void displayPDF(String file) {

        try {
            pdfView.fromFile(new File(file))
                    .enableSwipe(true)
                    .onLoad(this)
                    .load();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error : " + e, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}
