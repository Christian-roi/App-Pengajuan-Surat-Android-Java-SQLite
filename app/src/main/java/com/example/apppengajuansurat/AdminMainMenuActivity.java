package com.example.apppengajuansurat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminMainMenuActivity extends AppCompatActivity {

    DatabaseHelper db;
    private CardView btnMhsAktif, btnTrasnskrip, btnLegalisir, btnAlumni, btnProfile;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        DatabaseHelper db = new DatabaseHelper(this);
        btnMhsAktif = findViewById(R.id.btnMhsAktif);
        btnTrasnskrip = findViewById(R.id.btnTranskrip);
        btnLegalisir = findViewById(R.id.btnLegalisir);
        btnProfile = findViewById(R.id.btnProfile);
        logout = (Button) findViewById(R.id.btLogout);

        //Profile
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuProfile = new Intent(AdminMainMenuActivity.this, ProfileActivity.class);
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
                    Intent loginIntent = new Intent(AdminMainMenuActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        });
    }
}