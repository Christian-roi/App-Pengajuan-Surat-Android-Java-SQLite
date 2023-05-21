package com.example.apppengajuansurat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.apppengajuansurat.adapter.LegalisirAdapter;
import com.example.apppengajuansurat.model.DataFormLegalisir;
import com.example.apppengajuansurat.model.DataFormTranskrip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentLegalisirListActivity extends AppCompatActivity {
    ListView listView;
    List<DataFormLegalisir> lists = new ArrayList<>();
    LegalisirAdapter adapter;
    DatabaseHelper db;
    private FloatingActionButton kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_legalisir_list);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_item);
        kembali = findViewById(R.id.fab);

        adapter = new LegalisirAdapter(StudentLegalisirListActivity.this, lists);
        listView.setAdapter(adapter);
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String idx = lists.get(position).getId();

                Intent intent = new Intent(StudentLegalisirListActivity.this, StudentLegalisirDetailListActivity.class);
                intent.putExtra("id", Integer.parseInt(idx));

                startActivity(intent);
            }
        });

        //kembali
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMenu = new Intent(StudentLegalisirListActivity.this, MenuListActivity.class);
                startActivity(backToMenu);
                finish();
            }
        });
    }

    private void getData(){
        String savedUserID = db.getLoggedInUserId();
        ArrayList<HashMap<String, String>> rows = db.getFormLegalisirByNim(savedUserID);
        for (int i = 0; i<rows.size(); i++){
            String id = rows.get(i).get("id");
            String nim = rows.get(i).get("nim");
            String status = rows.get(i).get("status");
            DataFormLegalisir data = new DataFormLegalisir();
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
        Intent backToMenu = new Intent(StudentLegalisirListActivity.this, MenuListActivity.class);
        startActivity(backToMenu);
        finish();
    }
}