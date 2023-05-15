package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormLegalisirActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button daftar, kembali;
    EditText nama, nim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_legalisir);
        db = new DatabaseHelper(this);
        daftar = findViewById(R.id.btDaftar);
        kembali = findViewById(R.id.btKembali);

        //Form
        nama = findViewById(R.id.valNama);
        nim = findViewById(R.id.valNim);

        String savedUserID = db.getLoggedInUserId();
        DatabaseHelper.UserData userData = db.getUserData(savedUserID);

        if(userData != null){
            nama.setText(userData.getValueNama());
            nim.setText(userData.getValueUserid());
        }


        //BtKembali
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali = new Intent(FormLegalisirActivity.this, DetailLegalisirActivity.class);
                startActivity(kembali);
                finish();
            }
        });
    }
}