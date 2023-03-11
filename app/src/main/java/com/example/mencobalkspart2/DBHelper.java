package com.example.mencobalkspart2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    public static final String DBNAME = "login.db";
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInByte;

    public DBHelper(Context context)
    {
        super(context, DBNAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY,password TEXT)");
        MyDB.execSQL("CREATE TABLE rekap (id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,hadir INTEGER,sakit INTEGER,alpa INTEGER)");
        MyDB.execSQL("CREATE TABLE siswa (id INTEGER PRIMARY KEY AUTOINCREMENT,nis INTEGER,nama_siswa TEXT,kelas TEXT)");
        MyDB.execSQL("CREATE TABLE status (id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,status TEXT,tanggal TEXT)");
        MyDB.execSQL("CREATE TABLE alpa (id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,keterangan_alpa TEXT,tanggal TEXT)");
        MyDB.execSQL("CREATE TABLE sakit (id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,foto BLOB,tanggal TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("DROP TABLE IF EXISTS siswa");
        MyDB.execSQL("DROP TABLE IF EXISTS rekap");
        MyDB.execSQL("DROP TABLE IF EXISTS status");
        MyDB.execSQL("DROP TABLE IF EXISTS alpa");
        MyDB.execSQL("DROP TABLE IF EXISTS sakit");
    }

//    STATUS HELPER
    public Boolean insertstatus(String username,String status,String tanggal){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("status",status);
        values.put("tanggal",tanggal);
        long result = MyDB.insert("status",null,values);
        if (result==-1)
            return false;
        else
            return true;
    }
    public Boolean checkusernamestatus (String namauser){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM status WHERE username = ?",new String[] {namauser});
        if (cursor.getCount() >0) return true;
        else return false;
    }
    public  Boolean checktanggalstatus(String tanggal){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM status WHERE tanggal = ?",new String[] {tanggal});
        if (cursor.getCount() >0) return true;
        else return false;
    }

//    END STATUS HELPER

//    HELPER LOGIN
    public Boolean insertdata (String username,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        long result = MyDB.insert("users",null,values);
        if (result==-1)
            return false;
        else
            return true;
    }
    public  Boolean insertsiswa (String username,int nis,String nama,String kelas){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("nis",nis);
        values.put("nama_siswa",nama);
        values.put("kelas",kelas);
        long check = MyDB.insert("siswa",null,values);
        if (check==-1)
            return false;
        else
            return true;
    }
    public Boolean updatepass(String username,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password",password);
        MyDB.update("users",values,"username = ?",new String[] {username});
        return true;
    }
    public Boolean checkuser (String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean checkpass (String username,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",new String[] {username,password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
//    END HELPER LOGIN

//    HELPER HADIR
    public Boolean updatehadir(String username) {
        try {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("SELECT * FROM rekap WHERE username = ?", new String[]{username});
            cursor.moveToFirst();
            int cbhadir = cursor.getInt(2) + 1;
            ContentValues values = new ContentValues();
            values.put("hadir", cbhadir);
            long check = MyDB.update("rekap", values, "username = ?", new String[]{username});
            return true;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
//    END HELPER HADIR

//    HELPER REKAP
    public Boolean checkrekap(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM rekap WHERE username = ?",new String[] {username});
        if (cursor.getCount()>0) return true;
        else return false;
    }
    public Boolean insertrekap(String username){
        try {
            int hadir = 0;
            int sakit = 0;
            int alpa = 0;
            SQLiteDatabase MyDB = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username",username);
            values.put("hadir",hadir);
            values.put("sakit",sakit);
            values.put("alpa",alpa);
            long result = MyDB.insert("rekap",null,values);
            if (result==-1)
                return false;
            else
                return true;
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public Boolean updatesakit(String username){
        try {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("SELECT * FROM rekap WHERE username = ?",new String[] {username});
            cursor.moveToFirst();
            int no_auto = (cursor.getInt(3)+1);
            ContentValues values = new ContentValues();
            values.put("sakit",no_auto);
            MyDB.update("rekap",values,"username = ?",new String[] {username});
            return true;
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public Boolean updatealpa(String username){
        try {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("SELECT * FROM rekap WHERE username = ?",new String[] {username});
            cursor.moveToFirst();
            int no_auto = (cursor.getInt(4)+1);
            ContentValues values = new ContentValues();
            values.put("alpa",no_auto);
            long check = MyDB.update("rekap",values,"username = ?",new String[] {username});
            return true;
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public Cursor selectrekap(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor kirim = MyDB.rawQuery("SELECT * FROM rekap WHERE username = ?",new String[] {username});
        kirim.moveToFirst();
        return kirim;
    }
    public  Cursor selectsiswa (String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor send = MyDB.rawQuery("SELECT * FROM siswa WHERE username = ?",new String[] {username});
        send.moveToFirst();
        return send;
    }
//    END HELPER REKAP

//    HELPER ALPA
    public Boolean insertalpa(String username,String keterangan,String tanggal){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("keterangan_alpa",keterangan);
        values.put("tanggal",tanggal);
        long result = MyDB.insert("alpa",null,values);
        if (result==-1)
            return false;
        else
            return true;
    }
//     END HELPER ALPA

//     HELPER SAKIT
    public void storeimage(ModelClass objectModelClass){
        try {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = objectModelClass.getImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);

            imageInByte = objectByteArrayOutputStream.toByteArray();
            ContentValues values = new ContentValues();
            values.put("username",objectModelClass.getUsername());
            values.put("foto",imageInByte);
            values.put("tanggal", objectModelClass.getTanggal());
            long check = MyDB.insert("sakit",null,values);
            if (check!=-1){
                Toast.makeText(context, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
//    END HELPER SAKIT

}
