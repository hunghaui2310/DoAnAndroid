package com.doan.nhom12.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doan.nhom12.Database.NguoiDungDao;
import com.doan.nhom12.Model.NguoiDung;
import com.doan.nhom12.R;

import java.util.ArrayList;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.ViewHolder>{
    ArrayList<NguoiDung> list;
    Context context;
    NguoiDungDao nguoiDungDao;
    PersonalAdapter adapter;
    public PersonalAdapter( Context context,ArrayList<NguoiDung> list) {
        this.list = list;
        this.context = context;
        nguoiDungDao=new NguoiDungDao(context);
    }
    @NonNull
    @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View itemView=layoutInflater.inflate(R.layout.personal_lv,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final NguoiDung nguoiDung = list.get(i);
        viewHolder.TvFullname.setText(nguoiDung.Fullname);
        viewHolder.TvPhone.setText(nguoiDung.Phone);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc muốn xóa !!!");
                builder.setCancelable(false);

                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(context, "Xóa không thành công ", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Xóa thành công ", Toast.LENGTH_LONG).show();
                        delete(nguoiDung);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.personalupdate_dialog);
                dialog.setCancelable(false);
                dialog.show();
                final EditText edtUser=dialog.findViewById(R.id.edtUser);
                final EditText edtPass=dialog.findViewById(R.id.edtPass);
                final EditText edtFullname=dialog.findViewById(R.id.edtFullname);
                final EditText edtPhone=dialog.findViewById(R.id.edtsdt);
                final Button btnCan=dialog.findViewById(R.id.btnCancel);
                final Button btnCon=dialog.findViewById(R.id.btnConfirm);
                edtUser.setText(nguoiDung.User);
                edtPhone.setText(nguoiDung.Pass);
                edtFullname.setText(nguoiDung.Fullname);
                edtPhone.setText(nguoiDung.Phone);
                btnCan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                btnCon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user=edtUser.getText().toString();
                        String pass=edtPass.getText().toString();
                        String fullname=edtFullname.getText().toString();
                        String phone=edtPhone.getText().toString();
                        NguoiDung nguoiDung1=new NguoiDung(user,pass,fullname,phone);
                        if(edtUser.getText().length()!=0 && edtPass.getText().length()!=0 && edtFullname.getText().length()!=0 && edtPhone.getText().length()!=0 ){
                                nguoiDungDao.update(nguoiDung1);
                                dialog.dismiss();
                            }else{
                            Toast.makeText(context, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                        }}
                });
                    }
                });
            }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView edit,delete;
        TextView TvFullname,TvPhone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
            TvFullname=itemView.findViewById(R.id.Tvten);
            TvPhone=itemView.findViewById(R.id.Tvsdt);
        }
    }
    public void delete(NguoiDung nguoiDung){
        nguoiDungDao.delete(nguoiDung);
        list.clear();

    }

}

