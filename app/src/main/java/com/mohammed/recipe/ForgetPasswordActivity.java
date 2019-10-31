package com.mohammed.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ForgetPasswordActivity extends AppCompatActivity {
    Button btnGoConfinPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        btnGoConfinPassword = findViewById(R.id.btnGoConfinPassword);
        btnGoConfinPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ForgetPasswordActivity.this, RestActivity.class);
                startActivity(i);
            }
        });
    }
}
