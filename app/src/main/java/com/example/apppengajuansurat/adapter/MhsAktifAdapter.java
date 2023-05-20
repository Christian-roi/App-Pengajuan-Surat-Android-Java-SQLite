package com.example.apppengajuansurat.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apppengajuansurat.PengajuanListActivity;
import com.example.apppengajuansurat.R;
import com.example.apppengajuansurat.model.DataFormMhsAktif;

import java.util.List;

public class MhsAktifAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataFormMhsAktif> lists;


    public MhsAktifAdapter(Activity activity, List<DataFormMhsAktif> lists){
        this.activity = activity;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null && inflater != null){
            view = inflater.inflate(R.layout.list_items, null);
        }
        if(view != null){
            TextView id = view.findViewById(R.id.noPengajuan);
            TextView estimasi = view.findViewById(R.id.estimasi);
            TextView status = view.findViewById(R.id.status);
            DataFormMhsAktif dataFormMhsAktif = lists.get(i);
            id.setText("Form Pengajuan SMA/0"+dataFormMhsAktif.getId());
            estimasi.setText("NIM Mahasiswa: "+dataFormMhsAktif.getNim());
            status.setText(dataFormMhsAktif.getStatus());
            if(dataFormMhsAktif.getStatus().equals("Dibatalkan")){
                status.setTextColor(Color.RED);
            } else if (dataFormMhsAktif.getStatus().equals("Dikirim")) {
                status.setTextColor(Color.MAGENTA);
            } else if (dataFormMhsAktif.getStatus().equals("Diproses")) {
                status.setTextColor(Color.BLUE);
            }else if (dataFormMhsAktif.getStatus().equals("Selesai")){
                status.setTextColor(Color.GREEN);
            }else{
                status.setTextColor(Color.DKGRAY);
            }
        }
        return view;
    }
}
