package com.example.mencobalkspart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RekapActivity extends AppCompatActivity {

    TextView tanggal,user,hadir,sakit,alpa,nis,nama,kelas;
    Button kembali;
    DBHelper MyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap);
        nis = (TextView) findViewById(R.id.nis_rekap);
        nama = (TextView) findViewById(R.id.name);
        kelas = (TextView) findViewById(R.id.kelas_rekap);
        tanggal = (TextView) findViewById(R.id.tanggal_rekap);
        user = (TextView) findViewById(R.id.krm_user_rekap);
        hadir = (TextView) findViewById(R.id.no_hadir);
        sakit = (TextView) findViewById(R.id.no_sakit);
        alpa = (TextView) findViewById(R.id.no_alpa);
        kembali = (Button) findViewById(R.id.btn_kembali_rekap);
        MyDB = new DBHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                tanggal.setText(dateFormat.format(date));
            }
        });

        Bundle bundle = getIntent().getExtras();
        String User = bundle.getString("user");
        user.setText(User);

        try {
            String username = user.getText().toString();
            Cursor selectRekap = MyDB.selectrekap(username);
            Cursor selectSiswa = MyDB.selectsiswa(username);

            String finalnis = Integer.toString(selectSiswa.getInt(2));
            String finalnama = selectSiswa.getString(3);
            String finalkelas = selectSiswa.getString(4);
            String finalhadir = Integer.toString(selectRekap.getInt(2));
            String finalsakit = Integer.toString(selectRekap.getInt(3));
            String finalalpa = Integer.toString(selectRekap.getInt(4));

            nis.setText(finalnis);
            nama.setText(finalnama);
            kelas.setText(finalkelas);
            hadir.setText(finalhadir);
            sakit.setText(finalsakit);
            alpa.setText(finalalpa);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getText().toString();
                Intent kembaliHome = new Intent(RekapActivity.this,HomeActivity.class);
                kembaliHome.putExtra("user",username);
                startActivity(kembaliHome);
            }
        });
    }
}