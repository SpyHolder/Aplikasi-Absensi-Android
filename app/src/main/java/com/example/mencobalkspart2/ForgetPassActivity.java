package com.example.mencobalkspart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassActivity extends AppCompatActivity {

    EditText username,newpass,newcompass;
    Button forgetpass,kembali;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        forgetpass = (Button) findViewById(R.id.btn_gantipass);
        kembali = (Button) findViewById(R.id.btn_kembali_forget);
        username = (EditText) findViewById(R.id.username_forget);
        newpass = (EditText) findViewById(R.id.password_forget);
        newcompass = (EditText) findViewById(R.id.passwordcom_forget);
        DB = new DBHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = newpass.getText().toString().trim();
                String compass = newcompass.getText().toString().trim();
                Boolean chekcusername = DB.checkuser(user);
                if (chekcusername==true){
                    if (pass.equals(compass)){
                        Boolean update = DB.updatepass(user,pass);
                        if (update==true){
                            Toast.makeText(ForgetPassActivity.this, "Passsword Berhasil Diganti", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ForgetPassActivity.this, "Password Gagal Diganti", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(ForgetPassActivity.this, "Comfirmasi Password Salah", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ForgetPassActivity.this, "Username Tidak Ada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masuklogin = new Intent(ForgetPassActivity.this,MainActivity.class);
                startActivity(masuklogin);
            }
        });
    }
}