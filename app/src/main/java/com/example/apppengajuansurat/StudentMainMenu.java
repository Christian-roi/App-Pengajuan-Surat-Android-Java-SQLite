package com.example.apppengajuansurat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StudentMainMenu extends AppCompatActivity {
    private CardView btnMhsAktif, btnTranskrip, btnLegalisir, btnAlumni, btnPengajuanList, btnProfile;
    DatabaseHelper db;
    Button logout;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "0";
    private static final String CHANNEL_NAME = "Notifikasi Mahasiswa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);
        //setTitle("Menu Utama");
        db = new DatabaseHelper(this);
        btnMhsAktif = findViewById(R.id.btnMhsAktif);
        btnTranskrip = findViewById(R.id.btnTranskrip);
        btnLegalisir = findViewById(R.id.btnLegalisir);
        btnPengajuanList = findViewById(R.id.btnPengajuanList);
        btnProfile = findViewById(R.id.btnProfile);
        logout = (Button) findViewById(R.id.btLogout);
        String savedUserID = db.getLoggedInUserId();

        showAlertSelesai();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        showNotificationProses();

        // Cek session
        Boolean cekSession = db.checkSession("ada");
        if (cekSession == false){
            Intent loginIntent = new Intent(StudentMainMenu.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        //Menu Surat Mahasiswa Aktif
        btnMhsAktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuMahasiswaAktif = new Intent(StudentMainMenu.this, DetailSMAActivity.class);
                startActivity(menuMahasiswaAktif);
                finish();
            }
        });

        //Menu Legalisir
        btnLegalisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuLegalisir = new Intent(StudentMainMenu.this, DetailLegalisirActivity.class);
                startActivity(menuLegalisir);
                finish();
            }
        });

        //Menu Transkrip
        btnTranskrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuTranskrip = new Intent(StudentMainMenu.this, DetailTranskripActivity.class);
                startActivity(menuTranskrip);
                finish();
            }
        });

        //Menu Pengajuan Saya
        btnPengajuanList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuPengajuan = new Intent(StudentMainMenu.this, MenuListActivity.class);
                startActivity(menuPengajuan);
                finish();
            }
        });

        //Menu Profile
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuProfile = new Intent(StudentMainMenu.this, ProfileActivity.class);
                startActivity(menuProfile);
                finish();
            }
        });

        //Logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean updateSession = db.upgradeSession("kosong", 1);
                if(updateSession == true){
                    Toast.makeText(getApplicationContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(StudentMainMenu.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        });
    }

    private void showAlertSelesai(){
        DatabaseHelper db = new DatabaseHelper(this);
        String savedUserID = db.getLoggedInUserId();
        int jlhSuratSelesai = db.getCountFormSelesai(savedUserID, "Selesai");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pemberitahuan Pengajuan Selesai");

        if(jlhSuratSelesai == 0){
        }else{
            builder.setMessage(jlhSuratSelesai+" Pengajuan Anda telah selesai." +
                    "\nSilahkan Ambil ke Bagian Akademik di Gedung Z lt 1.");
            builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void showNotificationProses() {
        DatabaseHelper db = new DatabaseHelper(this);
        String savedUserID = db.getLoggedInUserId();
        int jlhSuratProses = db.getCountFormSelesai(savedUserID, "Diproses");
        if(jlhSuratProses == 0){
        }else{
            Intent intent = new Intent(this, MenuListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.doc)
                    .setContentTitle("Progress Pengajuan Anda")
                    .setContentText(jlhSuratProses+ " Pengajuan Anda telah diverifikasi dan diproses.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManager notificationManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationManager = getSystemService(NotificationManager.class);
            }
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

}