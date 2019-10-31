package com.mohammed.recipe.DBadapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mohammed.recipe.module.Recipes;

import java.util.ArrayList;


public class DBAdapter extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DBAdapter(@Nullable Context context) {
        super(context, "universitydb", null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Recipes.TABLE_CREATE);
        //sqLiteDatabase.execSQL(Product.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Recipes.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //------------------------------------------------------------------------

    public boolean insertStudent(String name, String details, byte[] image) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Recipes.COL_NAME, name);
        contentValues.put(Recipes.COL_DETAILS, details);
        contentValues.put(Recipes.COL_IMAGE, image);

        return db.insert(Recipes.TABLE_NAME, null, contentValues) > 0;
    }

    public ArrayList<Recipes> getAllStudents() {

        ArrayList<Recipes> students = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + Recipes.TABLE_NAME, null);

        while (cursor.moveToNext()) {

            Recipes student = new Recipes();
            student.setId(cursor.getInt(cursor.getColumnIndex(Recipes.COL_ID)));
            student.setName(cursor.getString(cursor.getColumnIndex(Recipes.COL_NAME)));
            student.setdetails(cursor.getString(cursor.getColumnIndex(Recipes.COL_DETAILS)));
            student.setImage(cursor.getBlob(cursor.getColumnIndex(Recipes.COL_IMAGE)));
            students.add(student);
        }
        cursor.close();
        return students;
    }

    public boolean deleteStudent(int id) {
        //return db.delete(Student.TABLE_NAME, "id = ?", new String[]{String.valueOf(id)}) > 0;
        return db.delete(Recipes.TABLE_NAME, "id = " + id, null) > 0;
    }

}
