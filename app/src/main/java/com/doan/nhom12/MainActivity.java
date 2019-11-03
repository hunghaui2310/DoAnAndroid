package com.doan.nhom12;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnPer,btnSales,btnBill,btnStatis,btnBook,btnType,btnMenu;
    Animation translate,zoomout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inits();

        btnPer.startAnimation(translate);
        btnMenu.startAnimation(translate);
        btnType.startAnimation(translate);
        btnStatis.startAnimation(translate);
        btnSales.startAnimation(translate);
        btnBook.startAnimation(translate);
        btnBill.startAnimation(translate);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(i);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, btnMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.home_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.Logout){
                            Toast.makeText(MainActivity.this, "Đăng Xuất Thành Công", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                        } if(item.getItemId()==R.id.MK){
                            Intent i=new Intent(MainActivity.this,MKActivity.class);
                            startActivity(i);
                        }if(item.getItemId()==R.id.About) {
                            Intent i = new Intent(MainActivity.this, AboutActivity.class);
                            startActivity(i);}
                        if(item.getItemId()==R.id.exit){
                            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Bạn có chắc muốn thoát khỏi ứng dụng !!!");
                            builder.setCancelable(false);

                            builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    Toast.makeText(MainActivity.this, "Thoát không thành công ", Toast.LENGTH_LONG).show();
                                }
                        });
                            builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Thoát thành công ", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Intent.ACTION_MAIN);
                                    i.addCategory(Intent.CATEGORY_HOME);
                                    startActivity(i);
                                    finish();
                                }
                            });
                            AlertDialog alertDialog=builder.create();
                            alertDialog.show();
                        }return false;
                    }
                });
                popup.show();//showing popup menu
            }
        });
        btnPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddPersonalActivity.class);
                startActivity(i);
            }
        });
        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTypeActivity.class);
                startActivity(i);
            }
        });
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BillManagerActivity.class);
                startActivity(i);
            }
        });
        btnSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SalesActivity.class);
                startActivity(i);
            }
        });
        btnStatis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ThongkeActivity.class);
                startActivity(i);
            }
        });
    }
    public void inits(){
        btnMenu=findViewById(R.id.btnMenu);
        btnBill=findViewById(R.id.btnBill);
        btnBook=findViewById(R.id.btnBook);
        btnPer=findViewById(R.id.btnPer);
        btnSales=findViewById(R.id.btnSales);
        btnStatis=findViewById(R.id.btnStatis);
        btnType=findViewById(R.id.btnType);
        translate= AnimationUtils.loadAnimation(this,R.anim.translatex);
        zoomout= AnimationUtils.loadAnimation(this,R.anim.zoomout);
    }
}
