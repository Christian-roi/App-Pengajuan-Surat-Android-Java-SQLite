package com.example.apppengajuansurat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MenuListActivity extends AppCompatActivity {
    private CardView listMhsAktif, listTranskrip, listLegalisir, home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        setTitle("Menu Daftar Pengajuan Anda");

        listMhsAktif = findViewById(R.id.btnMhsAktif);
        listTranskrip = findViewById(R.id.btnTranskrip);
        listLegalisir = findViewById(R.id.btnLegalisir);
        home = findViewById(R.id.btnHome);

        listMhsAktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listMhsAktifIntent = new Intent(MenuListActivity.this, PengajuanListActivity.class);
                startActivity(listMhsAktifIntent);
                finish();
            }
        });

        listTranskrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listTranskrip = new Intent(MenuListActivity.this, StudentTranskripListActivity.class);
                startActivity(listTranskrip);
                finish();
            }
        });

        listLegalisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listLegalisir = new Intent(MenuListActivity.this, StudentLegalisirListActivity.class);
                startActivity(listLegalisir);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(MenuListActivity.this, StudentMainMenu.class);
                startActivity(home);
                finish();
            }
        });

    }
}