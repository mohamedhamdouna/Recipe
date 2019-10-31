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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohammed.recipe.R;
import com.mohammed.recipe.adapter.CatAdapter;
import com.mohammed.recipe.adapter.MyAdapter;
import com.mohammed.recipe.adapter.RecentAdapter;
import com.mohammed.recipe.module.Recipe;
import com.mohammed.recipe.module.Recipes;
import com.mohammed.recipe.module.cataegories;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class dashFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView recyclerView3;
    RecyclerView recyclerView4;
    ArrayList<Recipe> data = new ArrayList<>();
    ArrayList<cataegories> cat = new ArrayList<>();
    TextView txV1, txV2, txV3;
    ProgressDialog pd;


    FirebaseFirestore db;
    private static final String TAG = "EmailPassword";

    public dashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dash, container, false);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("loading....");
        pd.setCancelable(false);
        pd.show();

        db = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.viewPager);
        recyclerView3 = root.findViewById(R.id.viewPager3);
        recyclerView4 = root.findViewById(R.id.viewPager4);
        txV1 = root.findViewById(R.id.txtV1);
        txV2 = root.findViewById(R.id.txtV2);
        txV3 = root.findViewById(R.id.txtV3);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,true));

        try {


            db.collection("recipe")
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
                                    pd.dismiss();

                                }
                            } else {
                                Log.e(TAG, "Error getting documents.", task.getException());
                            }
                     /*   final ProductsAdapters adapter = new ProductsAdapters(data, MainActivity.this);
                        rcHome.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                        rcHome.setAdapter(adapter);
                        Collections.reverse(data);*/
                            final MyAdapter myAdapter = new MyAdapter(getActivity(), data);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();
                            final RecentAdapter recentAdapter = new RecentAdapter(getActivity(), data);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView4.setLayoutManager(layoutManager2);
                            recyclerView4.setAdapter(recentAdapter);
                            recentAdapter.notifyDataSetChanged();
                            //  final MyAdapter myAdapter2 = new MyAdapter(getActivity(),data);
                            //  recyclerView4.setLayoutManager(layoutManager);
                            //   recyclerView4.setAdapter(myAdapter2);


                        }
                    });
            //code that may throw an exception
        } catch (Exception ref) {
        }


        try {


            db.collection("Categories")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.e(TAG, document.getId() + " => " + document.getData());
                                    cataegories productsModel = document.toObject(cataegories.class);
                                    productsModel.setId(document.getId());
                                    Log.i("iddoc", document.getId());
//                                    Toast.makeText(getContext(), productsModel.toString(), Toast.LENGTH_SHORT).show();
                                    cat.add(productsModel);
                                }
                            } else {
                                Log.e(TAG, "Error getting documents.", task.getException());
                            }
                               /*final ProductsAdapters adapter = new ProductsAdapters(data, MainActivity.this);
                                rcHome.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                                rcHome.setAdapter(adapter);
                                Collections.reverse(data);*/

                            final CatAdapter myAdapter1 = new CatAdapter(getActivity(), cat);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView3.setLayoutManager(layoutManager);
                            recyclerView3.setAdapter(myAdapter1);
                            txV1.setVisibility(View.VISIBLE);
                            txV2.setVisibility(View.VISIBLE);
                            txV3.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView3.setVisibility(View.VISIBLE);
                            recyclerView4.setVisibility(View.VISIBLE);


                        }
                    });
            //code that may throw an exception
        } catch (Exception ref) {
        }


        return root;
    }

}
