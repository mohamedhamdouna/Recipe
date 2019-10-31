package com.mohammed.recipe.module;

import android.text.Editable;

public class cataegories {
    private String txtCat;
    private String imgCat;
    private String id;

    public cataegories() {
    }

    public cataegories(String txtCat, String imgCat, String id) {
        this.txtCat = txtCat;
        this.imgCat = imgCat;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public cataegories(String txtCat, String imgCat) {
        this.txtCat = txtCat;
        this.imgCat = imgCat;

    }

    @Override
    public String toString() {
        return "cataegories{" +
                "txtCat='" + txtCat + '\'' +
                ", imgCat='" + imgCat + '\'' +
                '}';
    }

    public String getTxtCat() {
        return txtCat;
    }

    public void setTxtCat(String txtCat) {
        this.txtCat = txtCat;
    }

    public String getImgCat() {
        return imgCat;
    }

    public void setImgCat(String imgCat) {
        this.imgCat = imgCat;
    }
}
