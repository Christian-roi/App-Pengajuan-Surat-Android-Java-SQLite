package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        EditText ttl = findViewById(R.id.valTtl);
        EditText jurusan = findViewById(R.id.valJurusan);
        EditText kelas = findViewById(R.id.valKelas);

        String savedUserID = db.getLoggedInUserId();
        DatabaseHelper.UserData userData = db.getUserData(savedUserID);

        if(userData != null){
            nama.setText(userData.getValueNama());
            nim.setText(userData.getValueUserid());
        }

        //Daftar
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valNama = nama.getText().toString();
                String valNim = nim.getText().toString();
                String valTtl = ttl.getText().toString();
                String valJurusan = jurusan.getText().toString();
                String valKelas = kelas.getText().toString();

                if(valNama.trim().isEmpty() || valNim.trim().isEmpty() || valTtl.trim().isEmpty()
                   || valJurusan.trim().isEmpty() || valKelas.trim().isEmpty()) {
                    Toast.makeText(FormLegalisirActivity.this, "Harap Isi Data dengan Baik dan Benar!", Toast.LENGTH_SHORT).show();
                }else{
                    if(valNim.equals(userData.getValueUserid())){
                        Boolean dataMasuk = db.insertFormLegalisir(valNama, valNim, valTtl, valJurusan, valKelas);
                        if(dataMasuk == true){
                            Toast.makeText(FormLegalisirActivity.this, "Form Legalisir Ijazah telah dikirim", Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent(FormLegalisirActivity.this, StudentLegalisirListActivity.class);
                            startActivity(resultIntent);
                            finish();
                        }else{
                            Toast.makeText(FormLegalisirActivity.this, "Form Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(FormLegalisirActivity.this, "NIM Anda tidak sama dengan Profil\nHarap Masukkan NIM anda sendiri", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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

    @Override
    public void onBackPressed() {
        Intent kembali = new Intent(FormLegalisirActivity.this, DetailLegalisirActivity.class);
        startActivity(kembali);
        finish();
    }
}