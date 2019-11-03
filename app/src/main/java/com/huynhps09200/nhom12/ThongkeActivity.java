package com.huynhps09200.nhom12;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huynhps09200.nhom12.Model.HoaDonChiTiet;
import com.huynhps09200.duanmau.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThongkeActivity extends AppCompatActivity {
    ImageView back,thongke;
    TextView Tvthang,Tvtienthang,Tvngay,Tvtienngay,Tvnam,TvtienNam;
    Animation FadeIn;
    String hientai;
    HoaDonChiTiet hoaDonChiTiet;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        inits();
        mData= FirebaseDatabase.getInstance().getReference();
        back.startAnimation(FadeIn);
        thongke.startAnimation(FadeIn);
        Tvngay.startAnimation(FadeIn);
        Tvtienthang.startAnimation(FadeIn);
        Tvtienngay.startAnimation(FadeIn);
        TvtienNam.startAnimation(FadeIn);
        Tvthang.startAnimation(FadeIn);
        Tvnam.startAnimation(FadeIn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mData.child("HoaDonChiTiet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double sum = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    hoaDonChiTiet = data.getValue(HoaDonChiTiet.class);
                    String ngaymua = hoaDonChiTiet.ngayMua;
                    getCurrentDate();
                    String ngaynay = ngaymua.substring(8);
                    String hientaingay = hientai.substring(8);
                    if (ngaynay.equals(hientaingay)) {
                        int TT = Integer.parseInt(((data.child("tongTien").getValue(String.class))));
                        sum = sum + TT;
                        Tvtienngay.setText(sum + " VNĐ");
                    } else {
                        Tvtienngay.setText(sum + " VNĐ");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mData.child("HoaDonChiTiet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double sum = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    hoaDonChiTiet = data.getValue(HoaDonChiTiet.class);
                    String ngaymua = hoaDonChiTiet.ngayMua;
                    getCurrentDate();
                    String thangnay = ngaymua.substring(5,7);
                    String hientaithang = hientai.substring(5,7);
                    if (thangnay.equals(hientaithang)) {
                        int TT = Integer.parseInt(((data.child("tongTien").getValue(String.class))));
                        sum = sum + TT;
                        Tvtienthang.setText(sum + " VNĐ");
                    } else {
                        Tvtienthang.setText(sum + "VNĐ");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mData.child("HoaDonChiTiet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double sum = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    hoaDonChiTiet = data.getValue(HoaDonChiTiet.class);
                    String ngaymua = hoaDonChiTiet.ngayMua;
                    getCurrentDate();
                    String namnay = ngaymua.substring(0,4);
                    String hientainam = hientai.substring(0,4);
                    if (namnay.equals(hientainam)) {
                        int TT = Integer.parseInt(((data.child("tongTien").getValue(String.class))));
                        sum = sum + TT;
                        TvtienNam.setText(sum + " VNĐ");
                    } else {
                        TvtienNam.setText(sum + " VNĐ");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void inits(){
        back=findViewById(R.id.back);
        thongke=findViewById(R.id.imgthongke);
        Tvnam=findViewById(R.id.TvNam);
        Tvngay=findViewById(R.id.TvNgay);
        Tvthang=findViewById(R.id.TvThang);
        TvtienNam=findViewById(R.id.TvtienNam);
        Tvtienngay=findViewById(R.id.Tvngaytien);
        Tvtienthang=findViewById(R.id.TvtienThang);
        FadeIn= AnimationUtils.loadAnimation(this,R.anim.fadein);
    }
    public void getCurrentDate(){

                Calendar calendar1 = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                 String currentDate = sdf.format(calendar1.getTime());
                 hientai=currentDate;
        };
}
