package com.example.mencobalkspart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlpaActivity extends AppCompatActivity {

    TextView tanggal,user;
    EditText keterangan;
    Button kirim;
    DBHelper MyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpa);
        tanggal = (TextView) findViewById(R.id.tanggal_alpa);
        user = (TextView) findViewById(R.id.krm_user_alpa);
        kirim = (Button) findViewById(R.id.btn_kirim_alpa);
        keterangan = (EditText) findViewById(R.id.alasan_alpa);
        MyDB = new DBHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

       kirim.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               try {
                   String finaltanggal = tanggal.getText().toString();
                   String username = user.getText().toString();
                   String finalketerangan = keterangan.getText().toString();
                   String status = "alpa";

                   if (!finalketerangan.equals("")){
                       Boolean checkuser = MyDB.checkusernamestatus(username);
                       Boolean checktgl = MyDB.checktanggalstatus(finaltanggal);
                       if (checkuser==true&&checktgl==true){
                           Toast.makeText(AlpaActivity.this, "Anda Sudah Absensi Hari Ini", Toast.LENGTH_SHORT).show();
                       }
                       else if (checkuser==true&&checktgl==false){
                           try {
                               MyDB.insertalpa(username, finalketerangan, finaltanggal);
                               Boolean Checkinsert = MyDB.insertstatus(username, status, finaltanggal);
                               if (Checkinsert==true){
                                   try {
                                       MyDB.updatealpa(username);
                                       Toast.makeText(AlpaActivity.this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                                       Intent kembaliHome = new Intent(AlpaActivity.this,HomeActivity.class);
                                       kembaliHome.putExtra("user",username);
                                       startActivity(kembaliHome);
                                   }
                                   catch (Exception ex) {
                                       Toast.makeText(AlpaActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               }
                               else {
                                   Toast.makeText(AlpaActivity.this, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
                                   Toast.makeText(AlpaActivity.this, "Silahkan Kontak Yang Bersangkutan", Toast.LENGTH_SHORT).show();
                               }
                           }
                           catch (Exception e){
                               Toast.makeText(AlpaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                       else if (checkuser==false&&checktgl==true){
                           try {
                               MyDB.insertalpa(username, finalketerangan, finaltanggal);
                               Boolean Checkinsert2 = MyDB.insertstatus(username, status, finaltanggal);
                               if (Checkinsert2==true){
                                   try {
                                       MyDB.updatealpa(username);
                                       Toast.makeText(AlpaActivity.this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                                       Intent kembaliHome = new Intent(AlpaActivity.this,HomeActivity.class);
                                       kembaliHome.putExtra("user",username);
                                       startActivity(kembaliHome);
                                   }
                                   catch (Exception ex) {
                                       Toast.makeText(AlpaActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               }
                               else {
                                   Toast.makeText(AlpaActivity.this, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
                                   Toast.makeText(AlpaActivity.this, "Silahkan Kontak Yang Bersangkutan", Toast.LENGTH_SHORT).show();
                               }
                           }
                           catch (Exception e){
                               Toast.makeText(AlpaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                       else if (checkuser==false&&checktgl==false){
                           try {
                               MyDB.insertalpa(username, finalketerangan, finaltanggal);
                               Boolean Checkinsert3 = MyDB.insertstatus(username, status, finaltanggal);
                               if (Checkinsert3==true){
                                   try {
                                       MyDB.updatealpa(username);
                                       Toast.makeText(AlpaActivity.this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                                       Intent kembaliHome = new Intent(AlpaActivity.this,HomeActivity.class);
                                       kembaliHome.putExtra("user",username);
                                       startActivity(kembaliHome);
                                   }
                                   catch (Exception ex) {
                                       Toast.makeText(AlpaActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               }
                               else {
                                   Toast.makeText(AlpaActivity.this, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
                                   Toast.makeText(AlpaActivity.this, "Silahkan Kontak Yang Bersangkutan", Toast.LENGTH_SHORT).show();
                               }
                           }
                           catch (Exception e){
                               Toast.makeText(AlpaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   }
                   else {
                       Toast.makeText(AlpaActivity.this,"Silahkan Isi Alasan Ketidakhadiran", Toast.LENGTH_SHORT).show();
                   }
               }
               catch (Exception e){
                   Toast.makeText(AlpaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           }
       });

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("EEEE,dd MMMM yyyy");
                tanggal.setText(dateFormat.format(date));
            }
        });
        Bundle bundle = getIntent().getExtras();
        String User = bundle.getString("user");
        user.setText(User);
    }
}