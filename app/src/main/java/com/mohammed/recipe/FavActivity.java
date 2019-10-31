


package com.mohammed.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohammed.recipe.adapter.CollecAdapter;
import com.mohammed.recipe.module.Recipe;
import com.mohammed.recipe.ui.chifFragment;

import java.util.ArrayList;
import java.util.Collections;

public class FavActivity extends AppCompatActivity {
    ArrayList<Recipe> myRec = new ArrayList<>();
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    ProgressDialog pd;
    RecyclerView rvCollection;
    ImageView backToChif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        pd = new ProgressDialog(this);
        pd.setMessage("loading....");
        pd.setCancelable(false);
        pd.show();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        rvCollection = findViewById(R.id.rvCollection);
        backToChif = findViewById(R.id.backToChif);
        backToChif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, new chifFragment()).commit();
            }
        });
        getMyRec();
    }

    public void getMyRec() {

        try {


            db.collection(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.e(TAG, document.getId() + " => " + document.getData());
                                    Recipe productsModel = document.toObject(Recipe.class);
                                    productsModel.setId(document.getId());
                                    Log.i("iddoc", document.getId());
//                                    Toast.makeText(getContext(), productsModel.toString(), Toast.LENGTH_SHORT).show();
                                    myRec.add(productsModel);
                                    pd.dismiss();

                                }
                            } else {
                                Log.e(TAG, "Error getting documents.", task.getException());
                            }
                            final CollecAdapter adapter = new CollecAdapter(FavActivity.this, myRec);
                            rvCollection.setLayoutManager(new GridLayoutManager(FavActivity.this, 3));
                            rvCollection.setAdapter(adapter);
                            Collections.reverse(myRec);
                            adapter.notifyDataSetChanged();


                        }
                    });
            //code that may throw an exception
        } catch (Exception ref) {
        }
    }
}


