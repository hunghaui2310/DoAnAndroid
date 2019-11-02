package com.huynhps09200.duanmau;

import android.app.Dialog;
import android.app.NotificationChannelGroup;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huynhps09200.duanmau.Adapter.PersonalAdapter;
import com.huynhps09200.duanmau.Database.NguoiDungDao;
import com.huynhps09200.duanmau.Model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class AddPersonalActivity extends AppCompatActivity {
    Button btnAdd;
    Dialog dialog;
    ImageView back,personal;
    Animation translatex,translatey;
    RecyclerView LV;
    Context context;
    PersonalAdapter adapter;
    NguoiDung nguoiDung;
    NguoiDungDao nguoiDungDao;
    ArrayList<NguoiDung> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal);
        inits();
        final DatabaseReference mdata=FirebaseDatabase.getInstance().getReference("NguoiDung");
        personal.startAnimation(translatex);
        back.startAnimation(translatex);
        btnAdd.startAnimation(translatey);
        nguoiDungDao=new NguoiDungDao(this);
        LV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AddPersonalActivity.this,LinearLayoutManager.VERTICAL,false);
        LV.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
        list= nguoiDungDao.getAll();
        adapter=new PersonalAdapter(AddPersonalActivity.this,list);
        LV.setAdapter(adapter);
        Load();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddPersonalActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showdialog();
                final EditText edtUser=dialog.findViewById(R.id.edtUser);
                final EditText edtPass=dialog.findViewById(R.id.edtPass);
                final EditText edtRePass=dialog.findViewById(R.id.edtRePass);
                final EditText edtFullname=dialog.findViewById(R.id.edtFullname);
                final EditText edtPhone=dialog.findViewById(R.id.edtsdt);
                Button btnConfirm=dialog.findViewById(R.id.btnConfirm);
                Button btncancel=dialog.findViewById(R.id.btnCancel);

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user=edtUser.getText().toString();
                        String pass=edtPass.getText().toString();
                        String repass=edtRePass.getText().toString();
                        String fullname=edtFullname.getText().toString();
                        String phone=edtPhone.getText().toString();
                        nguoiDung=new NguoiDung(user,pass,fullname,phone);
                        if(edtUser.getText().length()!=0 && edtPass.getText().length()!=0 && edtFullname.getText().length()!=0 && edtPhone.getText().length()!=0 && edtRePass.getText().length()!=0){
                                if(edtPass.getText().toString().equals(edtRePass.getText().toString())) {
                                    nguoiDungDao.insert(nguoiDung);
                                    mdata.child(user).setValue(nguoiDung);
                                    dialog.dismiss();
                                    Toast.makeText(AddPersonalActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(AddPersonalActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                                }}else {
                            Toast.makeText(AddPersonalActivity.this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
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
    public void showdialog(){
        dialog=new Dialog(AddPersonalActivity.this);
        dialog.setContentView(R.layout.personal_dialog);
        dialog.setCancelable(false);
        dialog.show();

    }
    public void inits(){
        btnAdd=findViewById(R.id.btnAddper);
        personal=findViewById(R.id.personal);
        back=findViewById(R.id.back);
        LV=findViewById(R.id.containerper);
        translatex= AnimationUtils.loadAnimation(this,R.anim.translatex);
        translatey= AnimationUtils.loadAnimation(this,R.anim.translatey);
    }
    public void Load(){
        adapter.notifyDataSetChanged();
    }

}
