package com.mohammed.recipe.ui;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohammed.recipe.R;
import com.mohammed.recipe.adapter.ComuntAdapter;
import com.mohammed.recipe.adapter.MyRecAdapter;
import com.mohammed.recipe.module.Comunt;
import com.mohammed.recipe.module.Recipe;
import com.mohammed.recipe.module.User;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class globelFragment extends Fragment {
    private static final String TAG = "EmailPassword";
    FirebaseFirestore db;
    ComuntAdapter comuntAdapter;
    FirebaseAuth mAuth;
    Recipe productsRecipe;
    ProgressDialog pd;

    User user;
    ArrayList<Recipe> recipe = new ArrayList<>();
    ArrayList<User> userArrayList = new ArrayList<>();
    RecyclerView recyclerView;

    public globelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_globel, container, false);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(getActivity());
        pd.setMessage("loading....");
        pd.setCancelable(false);
        pd.show();

        recyclerView = root.findViewById(R.id.recyclerView);
        try {
            db.collection("recipe")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.e(TAG, document.getId() + " => " + document.getData());
                                    productsRecipe = document.toObject(Recipe.class);
                                    Log.i("idCom", String.valueOf(productsRecipe));
                                    Log.i("productsRecipe.getId()", productsRecipe.getId());
                                    recipe.add(productsRecipe);
                                    getUser();


                                }
                            } else {
                                Log.e(TAG, "Error getting documents.", task.getException());
                            }
                            comuntAdapter = new ComuntAdapter(getActivity(), recipe, userArrayList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(comuntAdapter);
                            comuntAdapter.notifyDataSetChanged();
                            pd.dismiss();


                        }

                    });
            //code that may throw an exception
        } catch (Exception ref) {

        }
        return root;
    }

    public void getUser() {
        try {
            db.collection("user")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.e(TAG, document.getId() + " => " + document.getData());
                                    user = document.toObject(User.class);
                                    Log.i("idCom", String.valueOf(user));
                                    user.setId(document.getId());
                                    userArrayList.add(user);

                                }
                            } else {
                                Log.e(TAG, "Error getting documents.", task.getException());
                            }


                        }
                    });
            //code that may throw an exception
        } catch (Exception ref) {


        }

    }

}
