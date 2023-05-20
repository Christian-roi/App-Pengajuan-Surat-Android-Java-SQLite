package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormMhsAktifActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button daftar, kembali;
    EditText nama, nim, ttl, jurusan, kelas, alamat, nama_ortu, pekerjaan, instansi, alamat_ortu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mhs_aktif);
        db = new DatabaseHelper(this);
        daftar = findViewById(R.id.btDaftar);
        kembali = findViewById(R.id.btKembali);

        //Form
        nama = findViewById(R.id.valNama);
        nim = findViewById(R.id.valNim);
        ttl = findViewById(R.id.valTtl);
        jurusan = findViewById(R.id.valJurusan);
        kelas = findViewById(R.id.valKelas);
        alamat = findViewById(R.id.valAlamat);
        nama_ortu = findViewById(R.id.valNamaOrtu);
        pekerjaan = findViewById(R.id.valPekerjaan);
        instansi = findViewById(R.id.valInstansi);
        alamat_ortu = findViewById(R.id.valAlamatOrtu);

        String savedUserID = db.getLoggedInUserId();
        DatabaseHelper.UserData userData = db.getUserData(savedUserID);

        if(userData != null){
            nama.setText(userData.getValueNama());
            nim.setText(userData.getValueUserid());
        }

        //BtDaftar
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valNama = nama.getText().toString();
                String valNim = nim.getText().toString();
                String valTtl = ttl.getText().toString();
                String valJurusan = jurusan.getText().toString();
                String valKelas = kelas.getText().toString();
                String valAlamat = alamat.getText().toString();
                String valNamaOrtu = nama_ortu.getText().toString();
                String valPekerjaan = pekerjaan.getText().toString();
                String valInstansi = instansi.getText().toString();
                String valAlamatOrtu = alamat_ortu.getText().toString();

                if(valNama.trim().isEmpty() || valNim.trim().isEmpty() || valTtl.trim().isEmpty()
                   || valJurusan.trim().isEmpty() || valKelas.trim().isEmpty() || valAlamat.trim().isEmpty()
                   || valNamaOrtu.trim().isEmpty() || valPekerjaan.trim().isEmpty() || valInstansi.trim().isEmpty()
                   || valAlamatOrtu.trim().isEmpty()){
                    Toast.makeText(FormMhsAktifActivity.this, "Harap Isi Data dengan Baik dan Benar!", Toast.LENGTH_SHORT).show();
                }else{
                    if(valNim.equals(userData.getValueUserid())){
                        Boolean dataMasuk = db.insertFormMahasiswaAktif(valNama, valNim, valTtl, valJurusan, valKelas, valAlamat, valNamaOrtu, valPekerjaan, valInstansi,valAlamatOrtu);
                        if(dataMasuk == true){
                            Toast.makeText(FormMhsAktifActivity.this, "Form Surat Mahasiswa Aktif telah dikirim", Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent(FormMhsAktifActivity.this, PengajuanListActivity.class);
                            startActivity(resultIntent);
                            finish();
                        }else{
                            Toast.makeText(FormMhsAktifActivity.this, "Form Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(FormMhsAktifActivity.this, "NIM Anda tidak sama dengan Profil\nHarap Masukkan NIM anda sendiri", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //BtKembali
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali = new Intent(FormMhsAktifActivity.this, DetailSMAActivity.class);
                startActivity(kembali);
                finish();
            }
        });
    }
}