package com.mohammed.recipe.module;

public class Recipes {

    private int id;
    private String name;
    private String details;
    private byte[] image;


    public static final String TABLE_NAME = "student";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DETAILS = "details";
    public static final String COL_IMAGE = "image";

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public static final String TABLE_CREATE = "create table " + TABLE_NAME + "(" +
            COL_ID + " integer primary key autoincrement," + COL_NAME +
            " varchar(50)," + COL_DETAILS +
            " varchar(50)," + COL_IMAGE + " BLOB,"
            + "timestamp DATETIME default CURRENT_TIMESTAMP" + ")";


    public Recipes(int id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public Recipes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdetails() {
        return details;
    }

    public void setdetails(String avg) {
        this.details = avg;
    }
}
