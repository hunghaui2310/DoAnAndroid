package com.doan.nhom12;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.doan.nhom12.Adapter.SalesAdapter;
import com.doan.nhom12.Database.HoaDonChiTietDao;
import com.doan.nhom12.Model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.Collections;

public class SalesActivity extends AppCompatActivity {
    EditText edtsearch;
    Button btnsearch;
    ImageView back,sales;
    RecyclerView LV;
    Animation translatex,translatey;
    SalesAdapter adapter;
    HoaDonChiTiet hoaDonChiTiet;
    HoaDonChiTietDao hoaDonChiTietDao;
    DatabaseReference mData;
    ArrayList<HoaDonChiTiet> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        inits();
        sales.startAnimation(translatex);
        back.startAnimation(translatex);
        edtsearch.startAnimation(translatey);
        btnsearch.startAnimation(translatey);
        hoaDonChiTietDao = new HoaDonChiTietDao(this);
        LV.setHasFixedSize(true);
        mData= FirebaseDatabase.getInstance().getReference();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SalesActivity.this, LinearLayoutManager.VERTICAL, false);
        LV.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    list.clear();
                       final String search=edtsearch.getText().toString();
                       if(search.length()==0){
                           AlertDialog.Builder builder= new AlertDialog.Builder(SalesActivity.this);
                           builder.setMessage("Bạn chưa nhập tháng cần tìm. Vui lòng nhập lại !!!");
                           builder.setCancelable(false);

                           builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {

                               }
                           });
                           AlertDialog alertDialog=builder.create();
                           alertDialog.show();
                       }else {
                           mData.child("HoaDonChiTiet").orderByChild("soLuongMua").limitToFirst(10).addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   for (DataSnapshot data : dataSnapshot.getChildren()) {
                                       hoaDonChiTiet = data.getValue(HoaDonChiTiet.class);
                                       String ngaymua = hoaDonChiTiet.ngayMua;
                                       String thang = ngaymua.substring(5,7);
                                       if (thang.equals(search)) {
                                           list.add(hoaDonChiTiet);
                                       } else {

                                       }
                                   }
                                   Collections.reverse(list);
                                   adapter = new SalesAdapter(SalesActivity.this, list);
                                   LV.setAdapter(adapter);
                                   Load();
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });
                       }
                   }




        });
    }
    public void inits(){
        edtsearch=findViewById(R.id.edtsearch);
        btnsearch=findViewById(R.id.btnsearch);
        sales=findViewById(R.id.sales);
        LV=findViewById(R.id.containersales);
        back=findViewById(R.id.backsales);
        translatex= AnimationUtils.loadAnimation(this,R.anim.translatex);
        translatey= AnimationUtils.loadAnimation(this,R.anim.translatey);
    }
    public void Load(){
        adapter.notifyDataSetChanged();
    }
}
