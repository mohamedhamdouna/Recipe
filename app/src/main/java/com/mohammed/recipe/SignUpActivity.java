package com.mohammed.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.mohammed.recipe.module.User;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class SignUpActivity extends AppCompatActivity implements IPickResult {
    String img;
    Bitmap bitmapGoogle;

    User user;
    FirebaseUser userWithGoogle;
    ImageView signwithGoogle;
    ImageView backToLog;
    private static final String TAG = "EmailPassword";
    private EditText mEmailField;
    private EditText mPasswordField, mconPsddwordField;
    private EditText mNameField;
    TextView btnGologin;
    Button btnSignUp;
    ImageView addImg;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;
    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReferenceFromUrl("gs://workonline-d0d52.appspot.com/images");


    //change the url according to your firebase app
//    StorageReference storageRef = storage.getReferenceFromUrl("gs://hazemfullproject.appspot.com/images");
    private ProgressDialog progressDialog;

    private static final int RC_SIGN_IN = 2;

    // [START declare_auth]
    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;
    // [END declare_auth]


    // [START declare_auth]

    // [END declare_auth]
    FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = FirebaseFirestore.getInstance();
        mconPsddwordField = findViewById(R.id.Confim_Password);
        btnSignUp = findViewById(R.id.btnSignUp);
        mEmailField = findViewById(R.id.mEmailField);
        mNameField = findViewById(R.id.feild_Name);
        mPasswordField = findViewById(R.id.mPasswordField);
        imageView = findViewById(R.id.addImg);
        addImg = findViewById(R.id.imageView8);
        mAuth = FirebaseAuth.getInstance();
        signwithGoogle = findViewById(R.id.signwithGoogle);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signwithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        backToLog = findViewById(R.id.backToLog);
        backToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(this);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
        pd.setCancelable(false);
        // [START config_signin]
        // [START signin]

        // [END signin]

        // Configure Google Sign In

        // [END config_signin]


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [END initialize_auth]
        btnGologin = findViewById(R.id.txtGoSignIn);
        btnGologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                startActivity(intent);

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());


            }
        });
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PickImageDialog.build(new PickSetup()).show(SignUpActivity.this);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        Log.e(TAG, "createAccount:" + email);
        if (!validateForm()) {
            uplodImg();
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "Sign Up is Successful", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "createUserWithEmail:success");
                            uplodImg();
                            sendEmailVerification();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Please enter a valid email and password",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }


    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.btnSignUp).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.btnSignUp).setEnabled(false);


                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(SignUpActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        String name = mNameField.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(name)) {
            mEmailField.setError("Required.");
            mNameField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        String conpassword = mconPsddwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else if (TextUtils.isEmpty(conpassword)) {
            mconPsddwordField.setError("Required.");


        } else if (!password.equals(conpassword)) {
            mPasswordField.setError("Not Match.");
            mconPsddwordField.setError("Not Match.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void conectFierbase() {
       /* Map<String, Object> user = new HashMap<>();
        user.put("first", "Hazem");
        user.put("last", "Al Rekhawi");
        user.put("born", 1815);*/

        user = new User(mAuth.getCurrentUser().getUid(), mNameField.getText().toString(), mEmailField.getText().toString(), img);

        //cataegories cataegories= new cataegories(mNameField.getText(),img);
        //  User user1=new User("1","1","1","1","1");
        db.collection("user").document(mAuth.getCurrentUser().getUid()).set(user);
    }

    private void uplodImg() {
        if (filePath != null) {
            pd.show();

            //for random name image
            StorageReference childRef = storageRef.child(System.currentTimeMillis() + "_imagehzm.jpg");
            //StorageReference childRef = storageRef.child("imagehzm_" + UUID.randomUUID().toString() + ".jpg");

            //uploading the image
            UploadTask uploadTask = childRef.putFile(filePath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    conectFierbase();
                    // Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Select an image", Toast.LENGTH_SHORT).show();
        }

    }


    private void hideProgressDialog() {
       /* if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }*/

    }

    private void showProgressDialog() {

       /* progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();*/
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            imageView.setImageBitmap(r.getBitmap());
        } else {
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imageView.setImageBitmap(bitmap);
                img = convert(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        }
    }


    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


    // [END onactivityresult]

    // [START auth_with_google]

    // [START auth_with_google]
    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            userWithGoogle = mAuth.getCurrentUser();
                            String uri = userWithGoogle.getPhotoUrl().toString();
                            Log.e(TAG, userWithGoogle.getProviderData().toString());
                            user = new User(userWithGoogle.getUid(), userWithGoogle.getDisplayName(), userWithGoogle.getEmail(), convertUrlToBase64(uri));
                            //cataegories cataegories= new cataegories(mNameField.getText(),img);
                            //  User user1=new User("1","1","1","1","1");
                            db.collection("user").document(userWithGoogle.getUid()).set(user);
                            Toast.makeText(SignUpActivity.this, "signInWithGoogle:success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            SignUpActivity.this.startActivity(intent);
                            SignUpActivity.this.finish();
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });


    }

    // [END auth_with_google]

    public String convertUrlToBase64(String url) {
        URL newurl;
        Bitmap bitmap;
        String base64 = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            newurl = new URL(url);
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }


    // [END onactivityresult]

}





