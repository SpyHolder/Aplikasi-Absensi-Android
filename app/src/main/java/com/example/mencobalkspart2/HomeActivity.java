package com.example.mencobalkspart2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.LogRecord;

public class HomeActivity extends AppCompatActivity {

    TextView namauser,tanggal;
    Button hadir,sakit,alpa,rekap,logout;
    DBHelper MyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        namauser = (TextView) findViewById(R.id.krm_user);
        tanggal = (TextView) findViewById(R.id.tanggal);
        hadir = (Button) findViewById(R.id.btn_hadir);
        sakit = (Button) findViewById(R.id.btn_sakit);
        alpa = (Button) findViewById(R.id.btn_alpa);
        rekap = (Button) findViewById(R.id.btn_rekap);
        logout = (Button) findViewById(R.id.btn_logout);
        MyDB = new DBHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user");
        namauser.setText(user);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                @SuppressLint("SimpleDateFormat")
                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                tanggal.setText(dateFormat.format(date));
                handler.postDelayed(this,1000);
            }
        });

        hadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = "hadir";
                String finalnama = namauser.getText().toString();
                String finaltanggal = tanggal.getText().toString();
               try {
                   String nmuser = namauser.getText().toString();
                   Boolean checkusername = MyDB.checkusernamestatus(finalnama);
                   Boolean checktgl = MyDB.checktanggalstatus(finaltanggal);
                   if (checkusername==true&&checktgl==true){
                       Toast.makeText(HomeActivity.this, "Anda Sudah Absensi Hari Ini", Toast.LENGTH_SHORT).show();
                   }
                   else if (checkusername==true&&checktgl==false) {
                        try {
                            Boolean checkstatus = MyDB.insertstatus(finalnama,status,finaltanggal);
                            if (checkstatus==true){
                                MyDB.updatehadir(finalnama);
                                Toast.makeText(HomeActivity.this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                                onResume();
                            }
                        }
                        catch (Exception ex){
                            Toast.makeText(HomeActivity.this, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
                            Toast.makeText(HomeActivity.this, "Silahkan Hubungi Yang Bersangkutan", Toast.LENGTH_SHORT).show();
                        }
                   }
                   else if(checkusername==false&&checktgl==true){
                       try {
                           Boolean checkstatus = MyDB.insertstatus(finalnama,status,finaltanggal);
                           if (checkstatus==true){
                               MyDB.updatehadir(finalnama);
                               Toast.makeText(HomeActivity.this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                               onResume();
                           }
                       }
                       catch (Exception ex){
                           Toast.makeText(HomeActivity.this, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
                           Toast.makeText(HomeActivity.this, "Silahkan Hubungi Yang Bersangkutan", Toast.LENGTH_SHORT).show();
                       }
                   }
                   else if(checkusername==false&&checktgl==false){
                       try {
                           Boolean checkstatus = MyDB.insertstatus(finalnama,status,finaltanggal);
                           if (checkstatus==true){
                               MyDB.updatehadir(finalnama);
                               Toast.makeText(HomeActivity.this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                               onResume();
                           }
                       }
                       catch (Exception ex){
                           Toast.makeText(HomeActivity.this, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
                           Toast.makeText(HomeActivity.this, "Silahkan Hubungi Yang Bersangkutan", Toast.LENGTH_SHORT).show();
                       }
                   }
               }
               catch (Exception e) {
                   Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
        });

        sakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = namauser.getText().toString();
                Intent masuksakit = new Intent(HomeActivity.this,SakitActivity.class);
                masuksakit.putExtra("user",user);
                startActivity(masuksakit);
            }
        });
        alpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = namauser.getText().toString();
                Intent masukalpa = new Intent(HomeActivity.this,AlpaActivity.class);
                masukalpa.putExtra("user",user);
                startActivity(masukalpa);
            }
        });
        rekap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = namauser.getText().toString();
                Intent masukrekap = new Intent(HomeActivity.this,RekapActivity.class);
                masukrekap.putExtra("user",user);
                startActivity(masukrekap);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keluar = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(keluar);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
