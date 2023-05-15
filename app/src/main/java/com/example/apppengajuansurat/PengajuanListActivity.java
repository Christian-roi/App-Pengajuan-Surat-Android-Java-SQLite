package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PengajuanListActivity extends AppCompatActivity {

    Button kembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_list);

        kembali = findViewById(R.id.btKembali);

        //Back Action
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kemabli = new Intent(PengajuanListActivity.this, StudentMainMenu.class);
                startActivity(kemabli);
                finish();
            }
        });
    }
}