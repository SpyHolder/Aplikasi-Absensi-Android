package com.example.mencobalkspart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login,register,forgetpass;
    DBHelper DB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnlogin);
        register = (Button) findViewById(R.id.btnregister);
        forgetpass = (Button) findViewById(R.id.btn_forgetpass);
        DB = new DBHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                Intent masukreg = new Intent(MainActivity.this,RegisterasiActivity.class);
                startActivity(masukreg);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")){
                    Toast.makeText( MainActivity.this,"Silahkan Masukkan Username dan Password",Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean checkuserpass = DB.checkpass(user,pass);
                    if (checkuserpass == true){
                        Toast.makeText(MainActivity.this,"Login Berhasil",Toast.LENGTH_LONG).show();
                        Intent masuklogin = new Intent(MainActivity.this,HomeActivity.class);
                        masuklogin.putExtra("user",user);
                        Toast.makeText(MainActivity.this, "Aplikasi Absensi Siswa", Toast.LENGTH_SHORT).show();
                        Boolean checkrekap = DB.checkrekap(user);
                        if (checkrekap==false) {
                            try {
                                Boolean insertrekap = DB.insertrekap(user);
                                if (insertrekap==false){
                                    Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                }
                                startActivity(masuklogin);
                            }
                            catch (Exception e){
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            startActivity(masuklogin);
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Login Gagal",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masukforget = new Intent(MainActivity.this,ForgetPassActivity.class);
                startActivity(masukforget);
            }
        });

    }
}