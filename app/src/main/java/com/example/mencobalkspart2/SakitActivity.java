package com.example.mencobalkspart2;

import static com.example.mencobalkspart2.R.drawable.ic_baseline_add_photo_alternate_24;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;

public class SakitActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 100;
    TextView Tanggal,user;
    Button kirim;
    ImageView surat;
    DBHelper MyDB;
    private  Uri imageFilePath;
    private Bitmap imageToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sakit);
        Tanggal = (TextView) findViewById(R.id.tanggal_sakit);
        user = (TextView) findViewById(R.id.krm_user_sakit);
        surat = (ImageView) findViewById(R.id.img_surat);
        kirim = (Button) findViewById(R.id.btn_kirim);
        MyDB = new DBHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                @SuppressLint("SimpleDateFormat")
                DateFormat dateFormat = new SimpleDateFormat("EEEE,dd MMMM yyyy");
                Tanggal.setText(dateFormat.format(date));
            }
        });
        Bundle bundle = getIntent().getExtras();
        String User = bundle.getString("user");
        user.setText(User);
    }

    public void chooseImage(View objectView){
        try {
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent,SELECT_PHOTO);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==SELECT_PHOTO && resultCode== RESULT_OK && data != null && data.getData() != null){
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);

                surat.setImageBitmap(imageToStore);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void kirim(View view){
        String username = user.getText().toString();
        String finaltanggal = Tanggal.getText().toString();
        String status = "sakit";

        if (!user.getText().toString().isEmpty() && surat.getDrawable() != null && imageToStore != null) {
            Boolean checkuser = MyDB.checkusernamestatus(username);
            Boolean checktanggal = MyDB.checktanggalstatus(finaltanggal);

            if (checkuser == true && checktanggal == true) {
                Toast.makeText(this, "Anda Sudah Absensi Hari Ini", Toast.LENGTH_SHORT).show();
            }
            else if (checkuser == true && checktanggal == false) {
                try {
                    MyDB.storeimage(new ModelClass(imageToStore,user.getText().toString(),finaltanggal));
                    Boolean Checkinsert = MyDB.insertstatus(username,status,finaltanggal);
                    if (Checkinsert==true){
                        try {
                            MyDB.updatesakit(username);
                            Toast.makeText(this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                            Intent masukhome = new Intent(SakitActivity.this,HomeActivity.class);
                            masukhome.putExtra("user",username);
                            startActivity(masukhome);
                        }
                        catch (Exception ex) {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Silahkan Hubungin Yang Bersangkutan!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else if (checkuser==false&&checktanggal==true){
                try {
                    MyDB.storeimage(new ModelClass(imageToStore,user.getText().toString(),finaltanggal));
                    Boolean Checkinsert2 = MyDB.insertstatus(username,status,finaltanggal);
                    if (Checkinsert2==true){
                        try {
                            MyDB.updatesakit(username);
                            Toast.makeText(this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                            Intent kembaliHome = new Intent(SakitActivity.this,HomeActivity.class);
                            kembaliHome.putExtra("user",username);
                            startActivity(kembaliHome);
                        }
                        catch (Exception ex) {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Silahkan Hubungin Yang Bersangkutan!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else if (checkuser==false&&checktanggal==false){
                try {
                    MyDB.storeimage(new ModelClass(imageToStore,user.getText().toString(),finaltanggal));
                    Boolean Checkinsert3 = MyDB.insertstatus(username,status,finaltanggal);
                    if (Checkinsert3==true){
                        try {
                            MyDB.updatesakit(username);
                            Toast.makeText(this, "Berhasil Terkirim", Toast.LENGTH_SHORT).show();
                            Intent kembaliHome = new Intent(SakitActivity.this,HomeActivity.class);
                            kembaliHome.putExtra("user",username);
                            startActivity(kembaliHome);
                        }
                        catch (Exception ex) {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Silahkan Hubungin Yang Bersangkutan!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(SakitActivity.this, "Silahkan Masukkan Gambar", Toast.LENGTH_SHORT).show();
        }
    }
}