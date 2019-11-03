package com.doan.nhom12.Database;

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
import com.doan.nhom12.AddTypeActivity;
import com.doan.nhom12.Model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiDao {
    private DatabaseReference mData;
    Context context;
    String TheLoaiID;

    //
    public TheLoaiDao(Context context) {
//
        this.mData = FirebaseDatabase.getInstance().getReference("TheLoai");
        this.context = context;

    }

    public void insert(TheLoai theLoai) {
//
        TheLoaiID = mData.push().getKey();
        mData.child(TheLoaiID).setValue(theLoai).addOnSuccessListener(new OnSuccessListener<Void>() {
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


    public void delete(final TheLoai theLoai) {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maTheLoai").getValue(String.class).equalsIgnoreCase(theLoai.maTheLoai)) {
                        TheLoaiID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + TheLoaiID);


                        mData.child(TheLoaiID).setValue(null)
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

    public ArrayList<TheLoai> getAll() {
        final ArrayList<TheLoai> list = new ArrayList<>();
//
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    TheLoai theLoai = data.getValue(TheLoai.class);
                    list.add(theLoai);
                }
                ((AddTypeActivity) context).Load();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mData.addValueEventListener(listener);
        return list;
    }

    public void update(final TheLoai theLoai) {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maTheLoai").getValue(String.class).equalsIgnoreCase(theLoai.maTheLoai)) {

                            TheLoaiID = data.getKey();
                            Log.d("getKey", "onCreate: key :" + TheLoaiID);


                            mData.child(TheLoaiID).setValue(theLoai)
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
    public List<String> getTL(){

        return null;
    }
}
