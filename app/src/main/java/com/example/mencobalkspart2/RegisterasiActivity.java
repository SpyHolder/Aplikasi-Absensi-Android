package com.example.mencobalkspart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterasiActivity extends AppCompatActivity {

    EditText username,password,compassword,nis,nama,kelas;
    Button register,kembali;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerasi);

        nis = (EditText) findViewById(R.id.nis);
        nama = (EditText) findViewById(R.id.nama_siswa);
        kelas = (EditText) findViewById(R.id.kelas);
        username = (EditText) findViewById(R.id.usernamereg);
        password = (EditText) findViewById(R.id.passwordreg);
        compassword = (EditText) findViewById(R.id.passworcomdreg);
        register = (Button) findViewById(R.id.btn_regis);
        kembali = (Button) findViewById(R.id.btn_kembali);
        DB = new DBHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembalilogin = new Intent(RegisterasiActivity.this,MainActivity.class);
                startActivity(kembalilogin);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String getnis = nis.getText().toString();
                    String getnama = nama.getText().toString();
                    String getkelas = kelas.getText().toString();
                    String user = username.getText().toString().trim();
                    String pass = password.getText().toString().trim();
                    String compass = compassword.getText().toString().trim();
                    if (getnis.equals("")||getnama.equals("")||getkelas.equals("")||user.equals("")||pass.equals("")||compass.equals("")){
                        Toast.makeText(RegisterasiActivity.this,"Silahkan Melengkapi Data",Toast.LENGTH_LONG).show();
                    }
                    else{
                        if (pass.equals(compass)) {
                            Boolean checkuser = DB.checkuser(user);
                            if (checkuser == false) {
                                int finalnis = Integer.parseInt(getnis);
                                Boolean insert = DB.insertdata(user, pass);
                                Boolean insertSiswa = DB.insertsiswa(user,finalnis,getnama,getkelas);
                                if (insert == true||insertSiswa == true) {
                                    Toast.makeText(RegisterasiActivity.this, "Registerasi Berhasil", Toast.LENGTH_LONG).show();
                                    Intent kembalologin = new Intent(RegisterasiActivity.this,MainActivity.class);
                                    startActivity(kembalologin);
                                } else {
                                    Toast.makeText(RegisterasiActivity.this, "Registerasi Gagal", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterasiActivity.this, "username Sudah Terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterasiActivity.this, "Comfirmasi Password salah", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(RegisterasiActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}