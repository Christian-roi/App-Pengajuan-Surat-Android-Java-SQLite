package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button login, register;
    EditText userid, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        userid = (EditText)findViewById(R.id.etUserid);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button)findViewById(R.id.btLogin);
        register = (Button)findViewById(R.id.btRegister);

        // Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUserid = userid.getText().toString();
                String strPass = password.getText().toString();
                Boolean loggedIn = db.checkLogin(strUserid, strPass);
                if(loggedIn == true){
                    Boolean updateSession = db.upgradeSession("ada", 1);
                    if(updateSession == true){
                        String role = db.checkRole(strUserid);
                        String user_id = db.saveLoggedInUserId(strUserid);
                        if("mahasiswa".equals(role)){
                            Toast.makeText(getApplicationContext(), "Berhasil Login", Toast.LENGTH_SHORT).show();
                            Intent studentMenuIntent = new Intent(LoginActivity.this, StudentMainMenu.class);
                            startActivity(studentMenuIntent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Anda Berhasil Masuk sebagai Admin", Toast.LENGTH_SHORT).show();
                            Intent adminMenuIntent = new Intent(LoginActivity.this, AdminMainMenuActivity.class);
                            startActivity(adminMenuIntent);
                            finish();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Gagal Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

    }
}