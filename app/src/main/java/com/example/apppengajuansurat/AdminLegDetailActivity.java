package com.example.apppengajuansurat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminLegDetailActivity extends AppCompatActivity {

    Button btnSelesai, btnKembali, btnVerif;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_leg_detail);

        DatabaseHelper db = new DatabaseHelper(this);
        btnSelesai=findViewById(R.id.btSelesai);
        btnKembali=findViewById(R.id.btBack);
        btnVerif=findViewById(R.id.btVerif);

        TextView id = findViewById(R.id.valId);
        TextView nama = findViewById(R.id.valNama);
        TextView nim = findViewById(R.id.valNim);
        TextView ttl = findViewById(R.id.valTtl);
        TextView jurusan = findViewById(R.id.valJurusan);
        TextView kelas = findViewById(R.id.valKelas);
        TextView status = findViewById(R.id.status);

        int idx = getIntent().getIntExtra("id", 0);
        String query = "SELECT * FROM form_legalisir WHERE id = " +idx;
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                //Value
                String valNama = cursor.getString(cursor.getColumnIndexOrThrow("nama_mhs"));
                String valStatus = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String valNim = cursor.getString(cursor.getColumnIndexOrThrow("nim"));
                String valTtl = cursor.getString(cursor.getColumnIndexOrThrow("ttl"));
                String valJurusan = cursor.getString(cursor.getColumnIndexOrThrow("jurusan"));
                String valKelas = cursor.getString(cursor.getColumnIndexOrThrow("kelas"));
                String valTglPengajuan = cursor.getString(cursor.getColumnIndexOrThrow("tgl_kirim"));
                //Set to TextView
                nama.setText(valNama);
                nim.setText(valNim);
                ttl.setText(valTtl);
                jurusan.setText(valJurusan);
                kelas.setText(valKelas);
                status.setText(valStatus);
                convertSQLiteDateTime(valTglPengajuan);

                if(valStatus.equals("Dikirim")){
                    btnSelesai.setVisibility(View.GONE);
                    btnVerif.setVisibility(View.VISIBLE);
                }
                if(valStatus.equals("Diproses")){
                    btnSelesai.setVisibility(View.VISIBLE);
                    btnVerif.setVisibility(View.GONE);
                }
                if(valStatus.equals("Selesai")){
                    btnSelesai.setVisibility(View.GONE);
                    btnVerif.setVisibility(View.GONE);
                }
                if(valStatus.equals("Sudah Diambil")){
                    btnSelesai.setVisibility(View.GONE);
                    btnVerif.setVisibility(View.GONE);
                }


            }while(cursor.moveToNext());

        }
        id.setText("Form Pengajuan LGI/0"+String.valueOf(idx));

        //Button-button
        btnVerif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfrimProsesDialog();
            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfrimSelesaiDialog();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), AdminLegListActivity.class);
                startActivity(back);
                finish();
            }
        });
    }

    private String convertSQLiteDateTime(String sqliteDateTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy, HH:mm", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(sqliteDateTime);
            String indonesianDateTime = outputFormat.format(date);
            TextView tglPengajuan = findViewById(R.id.tglKirim);
            tglPengajuan.setText(indonesianDateTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void showConfrimProsesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminLegDetailActivity.this);
        builder.setTitle("Konfirmasi Proses Dokumen");
        builder.setMessage("Apakah anda yakin ingin memproses dokumen?");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateProses();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showConfrimSelesaiDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminLegDetailActivity.this);
        builder.setTitle("Konfirmasi Proses Dokumen");
        builder.setMessage("Apakah dokumen sudah siap untuk diambil?");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateSelesai();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Fungsi Update Status
    private void updateProses(){
        DatabaseHelper db = new DatabaseHelper(this);
        int idx = getIntent().getIntExtra("id", 0);
        String idValue = String.valueOf(idx);
        String statusValue = "Diproses";
        ContentValues values = new ContentValues();
        values.put("status", statusValue);
        db.getWritableDatabase().update("form_legalisir", values, "id = ?", new String[]{idValue});
        db.close();
        Toast.makeText(getApplicationContext(), "Pengajuan Telah Diproses", Toast.LENGTH_SHORT).show();
        Intent result = new Intent(getApplicationContext(), AdminLegListActivity.class);
        startActivity(result);
        finish();
    }

    private void updateSelesai(){
        DatabaseHelper db = new DatabaseHelper(this);
        int idx = getIntent().getIntExtra("id", 0);
        String idValue = String.valueOf(idx);
        String statusValue = "Selesai";
        ContentValues values = new ContentValues();
        values.put("status", statusValue);
        db.getWritableDatabase().update("form_legalisir", values, "id = ?", new String[]{idValue});
        db.close();
        Toast.makeText(getApplicationContext(), "Dokumen Sudah Selesai.", Toast.LENGTH_SHORT).show();
        Intent result = new Intent(getApplicationContext(), AdminLegListActivity.class);
        startActivity(result);
        finish();
    }
}