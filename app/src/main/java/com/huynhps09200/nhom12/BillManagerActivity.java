package com.huynhps09200.nhom12;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huynhps09200.nhom12.Adapter.HoaDonAdapter;
import com.huynhps09200.nhom12.Database.HoaDonChiTietDao;
import com.huynhps09200.nhom12.Model.HoaDonChiTiet;
import com.huynhps09200.nhom12.Model.Sach;
import com.huynhps09200.duanmau.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillManagerActivity extends AppCompatActivity {
    Button btnAdd,btnTT;
    Dialog dialog;
    RecyclerView LV;
    ImageView back, bill;
    Animation translatex, translatey;
    Context context;
    HoaDonAdapter adapter;
    HoaDonChiTiet hoaDonChiTiet;
    DatabaseReference mData;
    String spn,tenSach;
    int gia;
    TextView Tongtien;
    Button btnNgay,btnHientai;
    HoaDonChiTietDao hoaDonChiTietDao;
    ArrayList<HoaDonChiTiet> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_manager);
        inits();
        mData= FirebaseDatabase.getInstance().getReference();
        bill.startAnimation(translatex);
        back.startAnimation(translatex);
        btnAdd.startAnimation(translatey);
        btnTT.startAnimation(translatey);
        Tongtien.startAnimation(translatey);
        hoaDonChiTietDao =new HoaDonChiTietDao(this);
        LV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(BillManagerActivity.this,LinearLayoutManager.VERTICAL,false);
        LV.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
        list= hoaDonChiTietDao.getAll();
        list=new ArrayList<>();
        list= hoaDonChiTietDao.getAll();
        adapter=new HoaDonAdapter(BillManagerActivity.this,list);
        LV.setAdapter(adapter);
        Load();
        mData.child("HoaDonChiTiet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double sum=0;
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    int TT= Integer.parseInt(((data.child("tongTien").getValue(String.class))));
                    sum =sum + TT;
                    Tongtien.setText("Tổng tiền:  "+sum +" VNĐ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BillManagerActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
                final EditText edtMaHD=dialog.findViewById(R.id.edtmahd);

                Button btnConfirm=dialog.findViewById(R.id.btnConfirm);
                Button btncancel=dialog.findViewById(R.id.btnCancel);
                btnNgay=dialog.findViewById(R.id.btnngaymua);
                btnHientai=dialog.findViewById(R.id.btnhientai);
                getCurrentDate();
                btnNgay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChooseDate();
                    }
                });
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        showdialogHDCT();
                        final EditText edtSL = dialog.findViewById(R.id.edtSL);
                        final Spinner spnMaSach = dialog.findViewById(R.id.SpnmaSach);
                        final TextView TvGia = dialog.findViewById(R.id.TvGia);
                        final TextView TvTT = dialog.findViewById(R.id.TvTT);
                        final TextView TvtenSach = dialog.findViewById(R.id.TvTenSach);
                        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                        Button btncan = dialog.findViewById(R.id.btnCancel);
                        mData.child("Sach").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final List<String> maSachlist = new ArrayList<>();
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    String tenTL = data.child("maSach").getValue(String.class);
                                    if (tenTL != null) {
                                        maSachlist.add(tenTL);
                                    }
                                }
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter(BillManagerActivity.this, R.layout.lv_tentl, R.id.TVTENTL, maSachlist);
                                spnMaSach.setAdapter(arrayAdapter);
                                spnMaSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                        spn = adapterView.getItemAtPosition(position).toString();
                                        String masach = spn;
                                        mData.child("Sach").orderByChild("maSach").equalTo(masach).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Sach sach = dataSnapshot.getChildren().iterator().next().getValue(Sach.class);
                                                 gia = sach.giaBan;
                                                 tenSach = sach.tenSach;
                                                TvtenSach.setText(tenSach);
                                                TvGia.setText(gia + "");
                                                if (edtSL.getText().toString().length() == 0) {
                                                    TvTT.setText(gia + "");
                                                } else {
                                                        int sl = Integer.parseInt(edtSL.getText().toString());
                                                        int TT = sl * gia;
                                                        TvTT.setText(TT + "");
                                                }


                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });


                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String maHoadon = edtMaHD.getText().toString();
                                String ngay = btnNgay.getText().toString();
                                String tenSach = TvtenSach.getText().toString();
                                String gia = TvGia.getText().toString();
                                int Sl = Integer.parseInt(edtSL.getText().toString());
                                String TT = TvTT.getText().toString();
                                hoaDonChiTiet = new HoaDonChiTiet(maHoadon, ngay, tenSach, gia, TT, Sl);
                                if (edtMaHD.getText().length() != 0 && edtSL.getText().length() != 0) {
                                    hoaDonChiTietDao.insert(hoaDonChiTiet);
                                    dialog.dismiss();
                                    Toast.makeText(BillManagerActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(BillManagerActivity.this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        btncan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public void showdialog() {
        dialog = new Dialog(BillManagerActivity.this);
        dialog.setContentView(R.layout.bill_manager);
        dialog.setCancelable(false);

        dialog.show();

    }
    public void showdialogHDCT() {
        dialog = new Dialog(BillManagerActivity.this);
        dialog.setContentView(R.layout.hdct_dialog);
        dialog.setCancelable(false);

        dialog.show();

    }
    public void inits() {
        btnAdd = findViewById(R.id.btnBillmg);
        bill = findViewById(R.id.bill);
        back = findViewById(R.id.backbill);
        Tongtien=findViewById(R.id.TvSOTT);
        btnTT=findViewById(R.id.btnTT);
        LV=findViewById(R.id.containerbill);
        translatex = AnimationUtils.loadAnimation(this, R.anim.translatex);
        translatey = AnimationUtils.loadAnimation(this, R.anim.translatey);
    }
    public void Load(){
        adapter.notifyDataSetChanged();
    }
    public void ChooseDate(){
        final Calendar calendar=Calendar.getInstance();
        //Date
        int Day=calendar.get(Calendar.DAY_OF_MONTH);
        int Month=calendar.get(Calendar.MONTH);
        int Year=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(BillManagerActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                btnNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },Year,Month,Day);
        datePickerDialog.show();
    }
    public void getCurrentDate(){
        btnHientai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = sdf.format(calendar1.getTime());

                btnNgay.setText(currentDate);

            }
        });

    }
}


