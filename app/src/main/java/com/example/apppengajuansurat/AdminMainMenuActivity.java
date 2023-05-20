package com.example.apppengajuansurat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

public class AdminMainMenuActivity extends AppCompatActivity {

    DatabaseHelper db;
    private CardView btnMhsAktif, btnTrasnskrip, btnLegalisir, btnAlumni, btnProfile;
    Button logout;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "0";
    private static final String CHANNEL_NAME = "Notifikasi Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        DatabaseHelper db = new DatabaseHelper(this);
        btnMhsAktif = findViewById(R.id.btnMhsAktif);
        btnTrasnskrip = findViewById(R.id.btnTranskrip);
        btnLegalisir = findViewById(R.id.btnLegalisir);
        btnProfile = findViewById(R.id.btnProfile);
        logout = (Button) findViewById(R.id.btLogout);

        //showAlertProgress();

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

        showNotificationMasuk();

        //Profile
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuProfile = new Intent(AdminMainMenuActivity.this, ProfileActivity.class);
                startActivity(menuProfile);
                finish();
            }
        });

        //MhsAKtif
        btnMhsAktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listSMA = new Intent(AdminMainMenuActivity.this, AdminSMAListActivity.class);
                startActivity(listSMA);
                finish();
            }
        });

        //Transkrip
        btnTrasnskrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listTrans = new Intent(AdminMainMenuActivity.this, AdminTransListActivity.class);
                startActivity(listTrans);
                finish();
            }
        });

        //Legalisir
        btnLegalisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listLegal = new Intent(AdminMainMenuActivity.this, AdminLegListActivity.class);
                startActivity(listLegal);
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
                    Intent loginIntent = new Intent(AdminMainMenuActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        });
    }

    private void showAlertProgress(){
        DatabaseHelper db = new DatabaseHelper(this);
        int jlhFormMhsAktif = db.getFormMhsAktifCount();
        int jlhFormTranskrip = db.getFormTranskripCount();
        int jlhFormLegalisri = db.getFormLegalisirCount();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pemberitahuan Pengajuan Terkirim");
        if(jlhFormTranskrip == 0 && jlhFormLegalisri == 0 && jlhFormTranskrip == 0){
            builder.setMessage("Tidak Ada Pengajuan Masuk");
        }else{
            builder.setMessage("Pengajuan Mahasiswa Aktif masuk: "+jlhFormMhsAktif+
                    "\nPengajuan Transkrip Nilai masuk: "+jlhFormTranskrip+
                    "\nPengajuan Legalisir Ijazah masuk: "+jlhFormLegalisri);

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

    private void showNotificationMasuk() {
        DatabaseHelper db = new DatabaseHelper(this);
        int jlhFormMhsAktif = db.getFormMhsAktifCount();
        int jlhFormTranskrip = db.getFormTranskripCount();
        int jlhFormLegalisri = db.getFormLegalisirCount();
        int total = jlhFormMhsAktif + jlhFormTranskrip + jlhFormLegalisri;
        if(total == 0){
        }else{
            Intent intent = new Intent(this, AdminMainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.doc)
                    .setContentTitle("Notifikasi Pengajuan Masuk")
                    .setContentText(total+ " Pengajuan masuk dan perlu diverifikasi dan diproses.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationManager = getSystemService(NotificationManager.class);
            }
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}