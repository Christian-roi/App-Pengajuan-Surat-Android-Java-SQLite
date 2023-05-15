package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailAdminActvity extends AppCompatActivity {

    Button btnSelesai, btnKembali, btnVerif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_admin_actvity);

        btnSelesai=findViewById(R.id.btSelesai);
        btnKembali=findViewById(R.id.btBack);
        btnVerif=findViewById(R.id.btVerif);

        btnSelesai.setVisibility(View.GONE);
        btnVerif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVerif.setVisibility(View.GONE);
                btnSelesai.setVisibility(View.VISIBLE);
                btnSelesai.setBackgroundResource(R.drawable.round_button);
            }
        });
    }
}