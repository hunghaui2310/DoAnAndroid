package com.huynhps09200.duanmau.Database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huynhps09200.duanmau.AddBookActivity;
import com.huynhps09200.duanmau.Model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    private DatabaseReference mData;
    Context context;
    String SachID;

    //
    public SachDao(Context context) {
//
        this.mData = FirebaseDatabase.getInstance().getReference("Sach");
        this.context = context;

    }

    public void insert(Sach sach) {
//
        SachID=mData.push().getKey();
        mData.child(SachID).setValue(sach).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void delete(final Sach sach) {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maSach").getValue(String.class).equalsIgnoreCase(sach.maSach)) {
                        SachID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + SachID);


                        mData.child(SachID).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "xóa thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Sach> getAll() {
        final ArrayList<Sach> list = new ArrayList<>();
//
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Sach sach = data.getValue(Sach.class);
                    list.add(sach);
                }
                ((AddBookActivity) context).Load();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mData.addValueEventListener(listener);
        return list;
    }

    public void update(final Sach sach) {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maSach").getValue(String.class).equalsIgnoreCase(sach.maSach)) {

                        SachID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + SachID);


                        mData.child(SachID).setValue(sach)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "update thành công", Toast.LENGTH_SHORT).show();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "update thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
