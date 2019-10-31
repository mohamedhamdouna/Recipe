package com.mohammed.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RestActivity extends AppCompatActivity {
    Button restPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        restPassword = findViewById(R.id.restPassword);
        restPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestActivity.this, ChangPasswordAct.class);
                startActivity(intent);
            }
        });
    }
}
