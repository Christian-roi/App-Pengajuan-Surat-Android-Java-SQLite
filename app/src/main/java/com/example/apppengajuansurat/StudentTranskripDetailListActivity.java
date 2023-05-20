package com.example.apppengajuansurat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StudentTranskripDetailListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_transkrip_detail_list);

        DatabaseHelper db = new DatabaseHelper(this);
        Button batal = findViewById(R.id.btBatal);
        Button kembali =findViewById(R.id.btBack);
        Button ambil  = findViewById(R.id.btAmbil);
        TextView id = findViewById(R.id.idPengajuan);
        TextView nama = findViewById(R.id.nama);
        TextView nim = findViewById(R.id.nim);
        TextView status = findViewById(R.id.status);
        TextView jurusan = findViewById(R.id.jurusan);
        TextView kelas = findViewById(R.id.kelas);

        int idx = getIntent().getIntExtra("id", 0);
        String query = "SELECT * FROM form_transkrip WHERE id = " +idx;
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //Value
                String valNama = cursor.getString(cursor.getColumnIndexOrThrow("nama_mhs"));
                String valStatus = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String valNim = cursor.getString(cursor.getColumnIndexOrThrow("nim"));
                String valJurusan = cursor.getString(cursor.getColumnIndexOrThrow("jurusan"));
                String valKelas = cursor.getString(cursor.getColumnIndexOrThrow("kelas"));
                String valTglPengajuan = cursor.getString(cursor.getColumnIndexOrThrow("tgl_kirim"));
                //Set to TextView
                nama.setText("Nama: "+valNama);
                nim.setText("NIM: "+valNim);
                status.setText("Status: "+valStatus);
                jurusan.setText("Jurusan/Prodi: "+valJurusan);
                kelas.setText("Kelas: "+valKelas);

                convertSQLiteDateTime(valTglPengajuan);

                if(valStatus.equals("Dikirim")){
                    batal.setVisibility(View.VISIBLE);
                    ambil.setVisibility(View.GONE);
                } else if(valStatus.equals("Selesai")){
                    ambil.setVisibility(View.VISIBLE);
                    batal.setVisibility(View.GONE);
                }else{
                    ambil.setVisibility(View.GONE);
                    batal.setVisibility(View.GONE);
                }
            }while(cursor.moveToNext());

        }
        id.setText("Form Pengajuan TNA/0"+String.valueOf(idx));

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmBatalDialog();
            }
        });

        ambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfrimAmbilDialog();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent(StudentTranskripDetailListActivity.this, StudentTranskripListActivity.class);
                startActivity(result);
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
            TextView tglPengajuan = findViewById(R.id.tglPengajuan);
            tglPengajuan.setText("Waktu Pengajuan: "+indonesianDateTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void showConfirmBatalDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi Proses Dokumen");
        builder.setMessage("Seluruh Data Pengajuan anda akan Dihapus.\nApakah anda yakin ingin membatalkan Pengajuan?");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateBatal();
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

    private void showConfrimAmbilDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi Proses Dokumen");
        builder.setMessage("Apakah anda yakin sudah mengambil dan menerima Dokumen?");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateAmbil();
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

    private void updateBatal(){
        DatabaseHelper db = new DatabaseHelper(this);
        int idx = getIntent().getIntExtra("id", 0);
        String idValue = String.valueOf(idx);
        db.getWritableDatabase().delete("form_transkrip", "id = ?", new String[]{idValue});
        db.close();
        Toast.makeText(getApplicationContext(), "Pengajuan Dibatalkan", Toast.LENGTH_SHORT).show();
        Intent result = new Intent(this, StudentTranskripListActivity.class);
        startActivity(result);
        finish();
    }

    private void updateAmbil(){
        DatabaseHelper db = new DatabaseHelper(this);
        int idx = getIntent().getIntExtra("id", 0);
        String idValue = String.valueOf(idx);
        String statusValue = "Sudah Diambil";
        ContentValues values = new ContentValues();
        values.put("status", statusValue);
        db.getWritableDatabase().update("form_transkrip", values, "id = ?", new String[]{idValue});
        db.close();
        Toast.makeText(getApplicationContext(), "Proses Dokumen Sudah Selesai", Toast.LENGTH_SHORT).show();
        Intent result = new Intent(this, StudentTranskripListActivity.class);
        startActivity(result);
        finish();
    }
}