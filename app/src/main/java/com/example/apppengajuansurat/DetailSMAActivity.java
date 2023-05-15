package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailSMAActivity extends AppCompatActivity {

    Button ajukan, kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sma);

        ajukan = findViewById(R.id.btAjukan);
        kembali = findViewById(R.id.btKembali);

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
}