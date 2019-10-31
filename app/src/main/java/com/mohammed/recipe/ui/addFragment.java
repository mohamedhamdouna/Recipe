package com.mohammed.recipe.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.Color;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.mohammed.recipe.MainActivity;
import com.mohammed.recipe.R;

import com.mohammed.recipe.module.Comunt;
import com.mohammed.recipe.module.Recipe;
import com.mohammed.recipe.module.User;
import com.mohammed.recipe.module.cataegories;
import com.vansuita.pickimage.bean.PickResult;

import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Arrays;


import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.util.ArrayUtils.contains;


/**
 * A simple {@link Fragment} subclass.
 */
public class addFragment extends Fragment implements IPickResult {
    String img;
    View root;
    EditText titelRicpes, txtdetails;
    ImageView btnAdd;
    ImageView btnPick;
    ImageView imgPick,imgPike;
    TextView textTitel;
    EditText totalTime,  editDirections;
    AutoCompleteTextView editIngredients;
    Recipe recipe;
    private static final String TAG = "EmailPassword";
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ArrayList<Recipe> data;
    FirebaseFirestore db;
    User currentUser;
    User user;
    String[] language;
    ArrayList<cataegories> cat=new ArrayList<>();
    User productsModel;
    ArrayList<User> userdata = new ArrayList<>();
    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReferenceFromUrl("gs://workonline-d0d52.appspot.com/images");
    ProgressDialog pd;

    //change the url according to your firebase app
//    StorageReference storageRef = storage.getReferenceFromUrl("gs://hazemfullproject.appspot.com/images");
    private ProgressDialog progressDialog;


    // [START declare_auth]
    private FirebaseAuth mAuth;

    // [END declare_auth]


    public addFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add, container, false);
        titelRicpes = root.findViewById(R.id.titelRicpes);
        txtdetails = root.findViewById(R.id.txtDescrip);
        btnAdd = root.findViewById(R.id.btnadd);
        textTitel = root.findViewById(R.id.textTitelPike);
        imgPick = root.findViewById(R.id.imgPick);
        editDirections = root.findViewById(R.id.editDirections);
        editIngredients = root.findViewById(R.id.editIngredients);
        totalTime = root.findViewById(R.id.totalTime);
        imgPike = root.findViewById(R.id.imgPike);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading....");
        pd.setCancelable(false);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        System.out.print(currentUser);


        progressDialog = new ProgressDialog(getActivity());
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Uploading....");
        pd.setCancelable(false);

        // [END initialize_auth]
        DocumentReference docRef = db.collection("user").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
            }
        });
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

                                language =new String[cat.size()];
                                int i = 0;
                                for(com.mohammed.recipe.module.cataegories c : cat)
                                {
                                    language[i] = c.getTxtCat();
                                    i++;
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                        (getActivity(),android.R.layout.select_dialog_item,language);
                                editIngredients.setThreshold(1);//will start working from first character
                                editIngredients.setAdapter(adapter);
                                adapter.notifyDataSetChanged();//setting the adapter data into the AutoCompleteTextView
                                editIngredients.setTextColor(Color.RED);
                            }
                        } else {
                            Log.e(TAG, "Error getting documents.", task.getException());
                        }
                               /*final ProductsAdapters adapter = new ProductsAdapters(data, MainActivity.this);
                                rcHome.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                                rcHome.setAdapter(adapter);
                                Collections.reverse(data);*/



                    }
                });








        imgPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PickImageDialog.build(new PickSetup()).show(SignUpActivity.this);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // conten //if (language equals(editIngredients.getText().toString()

               if (contains(language,editIngredients.getText().toString())) {
                   pd.show();
                   conectFierbase();
               }
                else {
                    editIngredients.setError("Enter Ingredints Corect");
                }
            }
        });
        // Check if user is signed in (non-null) and update UI accordingly.

        // String idUser= currentUser.getUid();

        // getData();


        return root;
    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();


    }
    // [END on_start_check_user]


    private void conectFierbase() {

        recipe = new Recipe(mAuth.getCurrentUser().getUid(), titelRicpes.getText().toString(), txtdetails.getText().toString(), totalTime.getText().toString(), editIngredients.getText().toString(), editDirections.getText().toString(), img);

        //  User user1=new User("1","1","1","1","1");

        // Add a new document with a generated ID
        db.collection("recipe")
                .add(recipe)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                      //  Recipe recipe = documentReference.get().getResult().toObject(Recipe.class);

                        Log.e(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//  db.collection("Categories").document().set(new cataegories("Dinner",img));
                        pd.dismiss();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding document", e);

                    }
                });


        data = new ArrayList<>();


    }




 /*   private void uplodImg() {
        if (filePath != null) {
            pd.show();
            StorageReference childRef = storageRef.child(System.currentTimeMillis() + "_imagehzm.jpg");
            UploadTask uploadTask = childRef.putFile(filePath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();


                    // Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getContext(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Select an image", Toast.LENGTH_SHORT).show();
        }

    }
*/
    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            btnPick.setImageBitmap(r.getBitmap());
        } else {
            Toast.makeText(getContext(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                //Setting image to ImageView
                imgPick.setImageBitmap(bitmap);
                img = convert(bitmap);
                imgPike.setVisibility(View.GONE);
                textTitel.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


   /* public void deletDoc() {
        db.collection("recipe").document("M1eYlQYYWrEWFsjlBTlj")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }*/

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

    }/*
    public class Test {
        public boolean contains(String[] arr, String item) {
            int index = Arrays.binarySearch(arr, item);
            return index >= 0;
        }

    }*/
}





