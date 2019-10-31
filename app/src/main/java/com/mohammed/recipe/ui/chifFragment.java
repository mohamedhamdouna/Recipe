package com.mohammed.recipe.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohammed.recipe.ChangPasswordAct;
import com.mohammed.recipe.CollectionActivity;
import com.mohammed.recipe.FavActivity;
import com.mohammed.recipe.R;
import com.mohammed.recipe.adapter.CustomAdapter;
import com.mohammed.recipe.adapter.MyRecAdapter;
import com.mohammed.recipe.editProfile;
import com.mohammed.recipe.loginActivity;
import com.mohammed.recipe.module.Recipe;
import com.mohammed.recipe.module.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class chifFragment extends Fragment {
    int recSize;

    ImageView btnEdit;
    private static final String TAG = "EmailPassword";
    TextView txtSignOut;
    MyRecAdapter myRecAdapter;
    TextView moreMyRec;
    TextView moreMyFav;
    TextView txtitem, txtlike, txtcoll, myrectxt, myFavtxt, txtsit, txtap, txtedit;
    ImageView recicon, iconsit, iconedit, iconexit, myfavicon;
    TextView editTextPass;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    public chifFragment() {
        // Required empty public constructor
    }

    TextView txtrecSize;
    TextView txtBio;
    RecyclerView simpleGrid, SimpleGrid2;
    User currentUser;
    TextView txtName;
    de.hdodenhof.circleimageview.CircleImageView imgProfile;

    ArrayList<Recipe> myRec = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;
    // [END declare_auth]
    FirebaseFirestore db;
    ProgressDialog pd;
    int logos[] = {R.drawable.banana, R.drawable.img40, R.drawable.img40, R.drawable.pizaa};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_chif, container, false);
        progressDialog = new ProgressDialog(getActivity());
        pd = new ProgressDialog(getActivity());
        pd.setMessage("loading....");
        pd.setCancelable(false);
        pd.show();
        txtitem = root.findViewById(R.id.txtrecitem);
        txtcoll = root.findViewById(R.id.txtcoll);
        txtlike = root.findViewById(R.id.txtlike);
        recicon = root.findViewById(R.id.myrecicon);
        myrectxt = root.findViewById(R.id.myrectext);
        myFavtxt = root.findViewById(R.id.myFavtxt);
        iconedit = root.findViewById(R.id.iconedit);
        iconexit = root.findViewById(R.id.iconexit);
        iconsit = root.findViewById(R.id.iconsit);
        myfavicon = root.findViewById(R.id.myfavicon);
        txtsit = root.findViewById(R.id.txtsit);
        txtap = root.findViewById(R.id.txtApp);
        txtedit = root.findViewById(R.id.txteditpass);
        txtSignOut = root.findViewById(R.id.txtSignOut);
        editTextPass= root.findViewById(R.id.txteditpass);
        editTextPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity(), ChangPasswordAct.class);
                startActivity(intent);
            }
        });

        myRecAdapter = new MyRecAdapter(getActivity(), myRec);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        btnEdit = root.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), editProfile.class);
                startActivity(intent);
            }
        });
        moreMyRec = root.findViewById(R.id.moreMyRec);
        moreMyFav = root.findViewById(R.id.moreMyFav);
        txtBio = root.findViewById(R.id.txtbio);
        txtName = root.findViewById(R.id.txtName);
        txtrecSize = root.findViewById(R.id.recSize);
        imgProfile = root.findViewById(R.id.profile_image);
        simpleGrid = root.findViewById(R.id.gridImgRe);
        SimpleGrid2 = root.findViewById(R.id.griImgF);
        txtlike.setVisibility(View.GONE);
        txtcoll.setVisibility(View.GONE);
        txtitem.setVisibility(View.GONE);
        simpleGrid.setVisibility(View.GONE);
        SimpleGrid2.setVisibility(View.GONE);
        moreMyFav.setVisibility(View.GONE);
        moreMyRec.setVisibility(View.GONE);
        txtlike.setVisibility(View.GONE);
        recicon.setVisibility(View.GONE);
        myrectxt.setVisibility(View.GONE);
        myFavtxt.setVisibility(View.GONE);
        iconsit.setVisibility(View.GONE);
        iconedit.setVisibility(View.GONE);
        iconexit.setVisibility(View.GONE);
        iconsit.setVisibility(View.GONE);
        txtsit.setVisibility(View.GONE);
        txtap.setVisibility(View.GONE);
        txtedit.setVisibility(View.GONE);
        txtSignOut.setVisibility(View.GONE);
        myfavicon.setVisibility(View.GONE);

        CustomAdapter customAdapter = new CustomAdapter(getContext(), logos);


        moreMyRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
            }
        });

        moreMyFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FavActivity.class);
                startActivity(intent);
            }
        });
        //SimpleGrid2.setVisibility(View.GONE);
        // simpleGrid.setVisibility(View.GONE);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        db.collection("recipe").whereEqualTo("id", mAuth.getCurrentUser().getUid())
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
                                recSize = myRec.size();
                                txtrecSize.setText(recSize + "");
                                txtlike.setVisibility(View.VISIBLE);
                                txtcoll.setVisibility(View.VISIBLE);
                                txtitem.setVisibility(View.VISIBLE);
                                simpleGrid.setVisibility(View.VISIBLE);
                                SimpleGrid2.setVisibility(View.VISIBLE);
                                moreMyFav.setVisibility(View.VISIBLE);
                                moreMyRec.setVisibility(View.VISIBLE);
                                myrectxt.setVisibility(View.VISIBLE);
                                myFavtxt.setVisibility(View.VISIBLE);
                                txtlike.setVisibility(View.VISIBLE);
                                recicon.setVisibility(View.VISIBLE);
                                iconsit.setVisibility(View.VISIBLE);
                                iconedit.setVisibility(View.VISIBLE);
                                iconexit.setVisibility(View.VISIBLE);
                                iconsit.setVisibility(View.VISIBLE);
                                txtsit.setVisibility(View.VISIBLE);
                                txtap.setVisibility(View.VISIBLE);
                                txtedit.setVisibility(View.VISIBLE);
                                txtSignOut.setVisibility(View.VISIBLE);
                                myfavicon.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.e(TAG, "Error getting documents.", task.getException());
                        }
                               /*final ProductsAdapters adapter = new ProductsAdapters(data, MainActivity.this);
                                rcHome.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                                rcHome.setAdapter(adapter);

                                Collections.reverse(data);*/
                        MyRecAdapter myRecAdapter = new MyRecAdapter(getActivity(), myRec);
                        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                        simpleGrid.setLayoutManager(layoutManager);
                        simpleGrid.setAdapter(myRecAdapter);
                        myRecAdapter.notifyDataSetChanged();
                        MyRecAdapter myRecAdapter2 = new MyRecAdapter(getActivity(), myRec);
                        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 2);
                        SimpleGrid2.setLayoutManager(layoutManager2);
                        SimpleGrid2.setAdapter(myRecAdapter2);
                        myRecAdapter2.notifyDataSetChanged();
                        //GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 2);
                        //  simpleGrid.setLayoutManager(layoutManager2);
                        //  SimpleGrid2.setAdapter(myRecAdapter);

                    }
                });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////


        DocumentReference docRef = db.collection("user").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
                setDAtaProf();

                pd.dismiss();
            }
        });

        txtSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revokeAccess();
                Intent intent = new Intent(getActivity(), loginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                getActivity().finish();
                startActivity(intent);
            }
        });


        return root;
    }

    private void signOut() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mAuth.signOut();
            mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

            Toast.makeText(getActivity(), user.getEmail() + " Sign out!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "You aren't login Yet!", Toast.LENGTH_SHORT).show();
        }

    }

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void setDAtaProf() {
        txtName.setText(currentUser.getName());
        final Bitmap imgProBitmap = convert(currentUser.getImg());
        imgProfile.setImageBitmap(imgProBitmap);
        txtBio.setText(currentUser.getBio());
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }


    //code that may throw an exception


}
