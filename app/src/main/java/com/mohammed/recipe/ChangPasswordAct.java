package com.mohammed.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ChangPasswordAct extends AppCompatActivity {
     EditText editPass;
    EditText OldeditPass;
    EditText editConpass;
    FirebaseUser user;
    FirebaseAuth mAuth;
    Button btnchanPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_password);
        editPass= findViewById(R.id.Password);
        editConpass = findViewById(R.id.Confirm_Password);
        OldeditPass=findViewById(R.id.OldPassword);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
         if (email==null){
             email= "m120112066";
         }
        btnchanPass = findViewById(R.id.btnChang);
        final String finalEmail = email;
        btnchanPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editPass.getText().toString().equals(null)){
                    editPass.setError("Enter Od Passwprd");
                }
                else  if (editConpass.getText().toString().equals(null)){
                    editConpass.setError("Enter Confirm Password");

                }
                else  if (editPass.getText().toString().equals(editConpass.getText().toString())){

                // Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(finalEmail,OldeditPass.getText().toString() );

// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(editPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("TAG", "Password updated");
                                                Toast.makeText(ChangPasswordAct.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(ChangPasswordAct.this, "Error password not updated", Toast.LENGTH_SHORT).show();

                                                Log.d("TAG", "Error password not updated");
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(ChangPasswordAct.this, "Error auth failed", Toast.LENGTH_SHORT).show();

                                    Log.d("TAG", "Error auth failed");
                                }
                            }
                        });
            }
                else
                    editConpass.setError("Password is not match");

            }

        });


    }
}
