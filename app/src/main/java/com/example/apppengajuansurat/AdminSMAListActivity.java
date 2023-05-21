package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.apppengajuansurat.adapter.MhsAktifAdapter;
import com.example.apppengajuansurat.model.DataFormMhsAktif;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminSMAListActivity extends AppCompatActivity {

    ListView listView;
    List<DataFormMhsAktif> lists = new ArrayList<>();
    MhsAktifAdapter adapter;
    DatabaseHelper db;
    private FloatingActionButton kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_smalist);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_item);
        kembali = findViewById(R.id.fab);

        adapter = new MhsAktifAdapter(AdminSMAListActivity.this, lists);
        listView.setAdapter(adapter);
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String idx = lists.get(position).getId();

                Intent intent = new Intent(AdminSMAListActivity.this, DetailAdminActvity.class);
                intent.putExtra("id",Integer.parseInt(idx));

                startActivity(intent);
            }
        });


        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMenu = new Intent(AdminSMAListActivity.this, AdminMainMenuActivity.class);
                startActivity(backToMenu);
                finish();
            }
        });
    }

    private void getData(){
        ArrayList<HashMap<String, String>> rows = db.getAllFormMhsAktif();
        for (int i = 0; i<rows.size(); i++){
            String id = rows.get(i).get("id");
            String status = rows.get(i).get("status");
            String nim = rows.get(i).get("nim");
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
        Intent backToMenu = new Intent(AdminSMAListActivity.this, AdminMainMenuActivity.class);
        startActivity(backToMenu);
        finish();
    }
}