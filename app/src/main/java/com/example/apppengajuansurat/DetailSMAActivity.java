package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailSMAActivity extends AppCompatActivity {

    Button ajukan, kembali;
    TextView jlh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sma);

        DatabaseHelper db = new DatabaseHelper(this);

        ajukan = findViewById(R.id.btAjukan);
        kembali = findViewById(R.id.btKembali);

        int count = db.getRowCount("form_mhs_aktif");


        ajukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formAKtifIntent = new Intent(DetailSMAActivity.this, FormMhsAktifActivity.class);
                startActivity(formAKtifIntent);
                finish();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuUtama = new Intent(DetailSMAActivity.this, StudentMainMenu.class);
                startActivity(menuUtama);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent menuUtama = new Intent(DetailSMAActivity.this, StudentMainMenu.class);
        startActivity(menuUtama);
        finish();
    }
}