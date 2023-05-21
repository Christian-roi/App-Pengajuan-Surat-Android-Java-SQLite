package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView nama, userid, jkelamin;
    Button kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new DatabaseHelper(this);
        kembali = findViewById(R.id.btKembali);
        nama = findViewById(R.id.txtNama);
        userid = findViewById(R.id.txtUserid);
        jkelamin = findViewById(R.id.txtJkelamin);

        String savedUserID = db.getLoggedInUserId();
        DatabaseHelper.UserData userData = db.getUserData(savedUserID);

        if(userData != null){
            nama.setText(userData.getValueNama());
            userid.setText(userData.getValueUserid());
            jkelamin.setText(userData.getValueJkelamin());
        }

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String role = db.checkRole(savedUserID);
                if("mahasiswa".equals(role)){
                    Intent studentMenuIntent = new Intent(ProfileActivity.this, StudentMainMenu.class);
                    startActivity(studentMenuIntent);
                    finish();
                }else {
                    Intent adminMenuIntent = new Intent(ProfileActivity.this, AdminMainMenuActivity.class);
                    startActivity(adminMenuIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        String savedUserID = db.getLoggedInUserId();
        String role = db.checkRole(savedUserID);
        if("mahasiswa".equals(role)){
            Intent studentMenuIntent = new Intent(ProfileActivity.this, StudentMainMenu.class);
            startActivity(studentMenuIntent);
            finish();
        }else {
            Intent adminMenuIntent = new Intent(ProfileActivity.this, AdminMainMenuActivity.class);
            startActivity(adminMenuIntent);
            finish();
        }
    }
}