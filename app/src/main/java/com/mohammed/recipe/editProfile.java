package com.mohammed.recipe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohammed.recipe.module.Comunt;
import com.mohammed.recipe.module.User;
import com.mohammed.recipe.ui.chifFragment;

public class editProfile extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    EditText txtBio;
    ImageView btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        txtBio = findViewById(R.id.txtBio);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("user").document(mAuth.getCurrentUser().getUid());
                docRef.update("bio", txtBio.getText().toString());
                finish();
            }
        });


    }
}
