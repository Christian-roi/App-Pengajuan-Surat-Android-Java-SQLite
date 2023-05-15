package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button login, register;
    EditText nama, userid, password, passConf;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        setTitle("Registrasi");

        db = new DatabaseHelper(this);

        //Variabel Input
        nama = findViewById(R.id.etNama);
        userid = findViewById(R.id.etUserid);
        radioGroup = findViewById(R.id.radioGrup);
        password = findViewById(R.id.etPassword);
        passConf = findViewById(R.id.etKonfirmPass);
        login = findViewById(R.id.btLogin);
        register = findViewById(R.id.btRegister);

        //Balik ke Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        // Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                String strNama = nama.getText().toString();
                String strUserid = userid.getText().toString();
                String strJ_kelamin = radioButton.getText().toString();
                String strPass = password.getText().toString();
                String strPassConf = passConf.getText().toString();
                String role = "mahasiswa";
                if(strPass.equals(strPassConf)){
                    Boolean registered = db.insertUser(strNama,strUserid, strJ_kelamin, strPass, role);
                    if(registered == true){
                        Toast.makeText(getApplicationContext(), "Registrasi Berhasil !!", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(RegistActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Registrasi Gagal!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Password Tidak Cocok", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}