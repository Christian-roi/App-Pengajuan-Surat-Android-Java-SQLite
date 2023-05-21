package com.example.apppengajuansurat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apppengajuansurat.adapter.MhsAktifAdapter;
import com.example.apppengajuansurat.model.DataFormMhsAktif;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PengajuanListActivity extends AppCompatActivity {

    ListView listView;
    List<DataFormMhsAktif> lists = new ArrayList<>();
    MhsAktifAdapter adapter;
    DatabaseHelper db;
    private FloatingActionButton kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_list);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_item);
        kembali = findViewById(R.id.fab);

        adapter = new MhsAktifAdapter(PengajuanListActivity.this, lists);
        listView.setAdapter(adapter);
        getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String idx = lists.get(position).getId();

                Intent intent = new Intent(PengajuanListActivity.this, DetailPengajuanActivity.class);
                intent.putExtra("id",Integer.parseInt(idx));

                startActivity(intent);
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMenu = new Intent(PengajuanListActivity.this, MenuListActivity.class);
                startActivity(backToMenu);
                finish();
            }
        });
    }

    private void getData(){
        String savedUserID = db.getLoggedInUserId();
        ArrayList<HashMap<String, String>> rows = db.getFormMhsAktifByNim(savedUserID);
        for (int i = 0; i<rows.size(); i++){
            String id = rows.get(i).get("id");
            String nim = rows.get(i).get("nim");
            String status = rows.get(i).get("status");
            DataFormMhsAktif data = new DataFormMhsAktif();
            data.setId(id);
            data.setNim(nim);
            data.setStatus(status);
            lists.add(data);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        lists.clear();
        getData();
    }

    @Override
    public void onBackPressed() {
        Intent backToMenu = new Intent(PengajuanListActivity.this, MenuListActivity.class);
        startActivity(backToMenu);
        finish();
    }
}