package com.huynhps09200.duanmau;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.huynhps09200.duanmau.Adapter.PersonalAdapter;
import com.huynhps09200.duanmau.Adapter.TypeAdapter;
import com.huynhps09200.duanmau.Database.NguoiDungDao;
import com.huynhps09200.duanmau.Database.TheLoaiDao;
import com.huynhps09200.duanmau.Model.NguoiDung;
import com.huynhps09200.duanmau.Model.TheLoai;

import java.util.ArrayList;

public class AddTypeActivity extends AppCompatActivity {
    Button btnAdd;
    Dialog dialog;
    ImageView back,type;
    TheLoaiDao theLoaiDao;
    Animation translatex,translatey;
    RecyclerView LV;
    Context context;
    TypeAdapter adapter;
    TheLoai theLoai;
    ArrayList<TheLoai> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        inits();
        type.startAnimation(translatex);
        back.startAnimation(translatex);
        btnAdd.startAnimation(translatey);
        theLoaiDao =new TheLoaiDao(this);
        LV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AddTypeActivity.this,LinearLayoutManager.VERTICAL,false);
        LV.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
        list= theLoaiDao.getAll();
        adapter=new TypeAdapter(AddTypeActivity.this,list);
        LV.setAdapter(adapter);
        Load();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddTypeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
                final EditText edtMaTL=dialog.findViewById(R.id.edtMTL);
                final EditText edtTenTL=dialog.findViewById(R.id.edtTenTL);
                final EditText edtVT=dialog.findViewById(R.id.edtVT);
                final EditText edtMota=dialog.findViewById(R.id.edtMota);
                Button btnConfirm=dialog.findViewById(R.id.btnConfirm);
                Button btncancel=dialog.findViewById(R.id.btnCancel);

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String maTL=edtMaTL.getText().toString();
                        String tenTL=edtTenTL.getText().toString();
                        String Mota=edtMota.getText().toString();
                        int VT= Integer.parseInt(edtVT.getText().toString());
                        theLoai=new TheLoai(maTL,tenTL,Mota,VT);
                        if(edtMaTL.getText().length()!=0 && edtTenTL.getText().length()!=0 && edtMota.getText().length()!=0 && edtVT.getText().length()!=0){
                                theLoaiDao.insert(theLoai);
                                dialog.dismiss();
                                Toast.makeText(AddTypeActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();
                            }else {
                            Toast.makeText(AddTypeActivity.this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                        }}
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
    public void inits(){
        btnAdd=findViewById(R.id.btnAddtype);
        type=findViewById(R.id.type);
        back=findViewById(R.id.backtype);
        LV=findViewById(R.id.containertype);
        translatex= AnimationUtils.loadAnimation(this,R.anim.translatex);
        translatey= AnimationUtils.loadAnimation(this,R.anim.translatey);
    }
    public void showdialog(){
        dialog=new Dialog(AddTypeActivity.this);
        dialog.setContentView(R.layout.type_dialog);
        dialog.setCancelable(false);
        dialog.show();

    }
    public void Load(){
        adapter.notifyDataSetChanged();
    }
}
