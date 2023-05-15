package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StudentMainMenu extends AppCompatActivity {
    private CardView btnMhsAktif, btnTrasnskrip, btnLegalisir, btnAlumni, btnPengajuanList, btnProfile;
    DatabaseHelper db;
    Button logout;
    TextView nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);
        //setTitle("Menu Utama");
        db = new DatabaseHelper(this);
        btnMhsAktif = findViewById(R.id.btnMhsAktif);
        btnTrasnskrip = findViewById(R.id.btnTranskrip);
        btnLegalisir = findViewById(R.id.btnLegalisir);
        btnPengajuanList = findViewById(R.id.btnPengajuanList);
        btnProfile = findViewById(R.id.btnProfile);
        logout = (Button) findViewById(R.id.btLogout);

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
        btnTrasnskrip.setOnClickListener(new View.OnClickListener() {
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
                Intent menuPengajuan = new Intent(StudentMainMenu.this, PengajuanListActivity.class);
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
}