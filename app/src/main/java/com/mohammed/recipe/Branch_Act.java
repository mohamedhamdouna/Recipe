package com.mohammed.recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohammed.recipe.adapter.CollecAdapter;
import com.mohammed.recipe.adapter.MyAdapter;
import com.mohammed.recipe.adapter.RecentAdapter;
import com.mohammed.recipe.module.Recipe;

import java.util.ArrayList;
import java.util.Collections;

public class Branch_Act extends AppCompatActivity {
    private static String TAG = "Enter";
    FirebaseFirestore db;
    RecyclerView recycel_act;
    ArrayList<Recipe> myRec = new ArrayList<>();
    TextView txtTitell;
    ProgressDialog pd;
    Button btntext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_);
        recycel_act = findViewById(R.id.recycel_act);
        btntext = findViewById(R.id.toast);
        btntext.setVisibility(View.GONE);
        pd = new ProgressDialog(this);
        pd.setMessage("loading....");
        pd.setCancelable(false);
        pd.show();

        db = FirebaseFirestore.getInstance();
        txtTitell = findViewById(R.id.txtTitell);
        String data = getIntent().getStringExtra("ingredients");
        txtTitell.setText(data);

        try {
/*

            db.collection("recpie")
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
                                    // Toast.makeText(getContext(), productsModel.toString(), Toast.LENGTH_SHORT).show();
                                    data.add(productsModel);





                                }
                            } else {
                                Log.e(TAG, "Error getting documents.", task.getException());
                            }
                     /*   final ProductsAdapters adapter = new ProductsAdapters(data, MainActivity.this);
                        rcHome.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                        rcHome.setAdapter(adapter);
                        Collections.reverse(data);*/

            Log.e("data", data);

            db.collection("recipe").whereEqualTo("ingredients", data).
                    get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.e(TAG, document.getId() + " => " + document.getData());
                            Recipe productsModel = document.toObject(Recipe.class);
                            productsModel.setId(document.getId());
                            Log.i("iddoc", document.getId());
                            // Toast.makeText(getContext(), productsModel.toString(), Toast.LENGTH_SHORT).show();
                            myRec.add(productsModel);
                        }

                    } else {
                        Toast.makeText(Branch_Act.this, "No Internet", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error getting documents.", task.getException());
                    }

                    final MyAdapter myAdapter = new MyAdapter(Branch_Act.this, myRec);
                    LinearLayoutManager layoutManager = new GridLayoutManager(Branch_Act.this, 2);
                    recycel_act.setLayoutManager(layoutManager);
                    recycel_act.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                    pd.dismiss();
                    if (myRec.size() == 0) {
                        btntext.setVisibility(View.VISIBLE);
                        btntext.setText("No item show");
                    }

                            /*
                            final RecentAdapter recentAdapter = new RecentAdapter(getActivity(),data);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView4.setLayoutManager(layoutManager2);
                            recyclerView4.setAdapter(recentAdapter);
                            //  final MyAdapter myAdapter2 = new MyAdapter(getActivity(),data);
                            //  recyclerView4.setLayoutManager(layoutManager);
                            //   recyclerView4.setAdapter(myAdapter2);
*/


                }
            });
            //code that may throw an exception
        } catch (Exception ref) {
        }

    }
}
