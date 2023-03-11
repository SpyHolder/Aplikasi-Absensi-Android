package com.example.mencobalkspart2;

import android.graphics.Bitmap;

public class ModelClass {
    private Bitmap image;
    private String username;
    private String tanggal;

    public ModelClass(Bitmap image, String username, String tanggal) {
        this.image = image;
        this.username = username;
        this.tanggal = tanggal;
    }
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
