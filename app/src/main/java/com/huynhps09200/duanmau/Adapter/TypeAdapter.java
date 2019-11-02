package com.huynhps09200.duanmau.Adapter;

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

import com.huynhps09200.duanmau.Database.TheLoaiDao;
import com.huynhps09200.duanmau.Model.TheLoai;
import com.huynhps09200.duanmau.R;

import java.util.ArrayList;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder>{
    ArrayList<TheLoai> list;
    Context context;
    TheLoaiDao theLoaiDao;
    TypeAdapter adapter;
    public TypeAdapter( Context context,ArrayList<TheLoai> list) {
        this.list = list;
        this.context = context;
        theLoaiDao =new TheLoaiDao(context);
    }
    @NonNull
    @Override
    public TypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View itemView=layoutInflater.inflate(R.layout.type_lv,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TypeAdapter.ViewHolder viewHolder, int i) {
        final TheLoai theLoai = list.get(i);
        viewHolder.tenTL.setText(theLoai.tenTheLoai);
        viewHolder.VT.setText("Vị trí: "+theLoai.viTri);
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
                        delete(theLoai);
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
                dialog.setContentView(R.layout.type_dialog);
                dialog.setCancelable(false);
                dialog.show();
                final EditText edtMaTL=dialog.findViewById(R.id.edtMTL);
                final EditText edtTenTL=dialog.findViewById(R.id.edtTenTL);
                final EditText edtVT=dialog.findViewById(R.id.edtVT);
                final EditText edtMota=dialog.findViewById(R.id.edtMota);
                final Button btnCan=dialog.findViewById(R.id.btnCancel);
                final Button btnCon=dialog.findViewById(R.id.btnConfirm);
                edtMaTL.setText(theLoai.maTheLoai);
                edtTenTL.setText(theLoai.tenTheLoai);
                edtVT.setText(""+theLoai.viTri);
                edtMota.setText(theLoai.moTa);
                btnCan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                btnCon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String maTL=edtMaTL.getText().toString();
                        String tenTL=edtTenTL.getText().toString();
                        String Mota=edtMota.getText().toString();
                        int VT= Integer.parseInt(edtVT.getText().toString());
                        TheLoai theLoai1=new TheLoai(maTL,tenTL,Mota,VT);
                        if(edtMaTL.getText().length()!=0 && edtTenTL.getText().length()!=0 && edtVT.getText().length()!=0 && edtMota.getText().length()!=0 ){
                            theLoaiDao.update(theLoai1);
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
        TextView VT,tenTL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenTL=itemView.findViewById(R.id.TvTenTL);
            edit=itemView.findViewById(R.id.editype);
            delete=itemView.findViewById(R.id.deletetype);
            VT=itemView.findViewById(R.id.TvVT);
        }
    }
    public void delete(TheLoai theLoai){
        theLoaiDao.delete(theLoai);
        list.clear();

    }

}


