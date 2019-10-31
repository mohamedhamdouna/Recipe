package com.mohammed.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohammed.recipe.module.Recipe;

import java.util.ArrayList;

public class DetailesAct extends AppCompatActivity {
    TextView tvName, tvtime, tvDescrip, tvIngred, tvInstruction;
    ImageView imgRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);
        imgRec = findViewById(R.id.imgRec);
        tvDescrip = findViewById(R.id.tvDescrip);
        tvtime = findViewById(R.id.tvtime);
        tvName = findViewById(R.id.tvtitel);
        tvName = findViewById(R.id.tvtitel);
        tvIngred = findViewById(R.id.tvIngred);
        tvInstruction = findViewById(R.id.tvInstruction);
        Recipe data = getIntent().getParcelableExtra("recipe");
        tvDescrip.setText(data.getDescription());
        tvName.setText(data.getRecipe_Title());
        tvtime.setText(data.getTotal_Time() + " min");
        tvIngred.setText(data.getIngredients());
        tvInstruction.setText(data.getIngredients());
        imgRec.setImageBitmap(convert(data.getImg()));

        Log.e("data", String.valueOf(data));
    }

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
