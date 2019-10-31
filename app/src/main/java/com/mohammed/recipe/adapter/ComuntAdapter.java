package com.mohammed.recipe.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohammed.recipe.Branch_Act;
import com.mohammed.recipe.R;
import com.mohammed.recipe.module.Comunt;
import com.mohammed.recipe.module.Recipe;
import com.mohammed.recipe.module.User;
import com.mohammed.recipe.module.cataegories;


import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ComuntAdapter extends RecyclerView.Adapter<ComuntAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<Recipe> data;
    ArrayList<User> user;


    public ComuntAdapter(Activity activity, ArrayList<Recipe> data, ArrayList<User> user) {
        this.activity = activity;
        this.data = data;
        this.user = user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.item_comunity, parent, false);
        return new MyViewHolder(root);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.txtDetails.setText(data.get(position).getDescription());
        String foodImage = data.get(position).getImg();
        Bitmap bitmap = convert(foodImage);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imgRec.setImageBitmap(bitmap);
        for (User u : user) {
            Log.e("test2", String.valueOf(u.getId()));
            Log.e("test2", data.get(position).getId());

            if (u.getId().equals(data.get(position).getId())) {
                holder.txtName.setText(u.getName());
                String UserImage = u.getImg();
                Bitmap bitmapUser = convert(UserImage);
//      Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
                holder.imgUser.setImageBitmap(bitmapUser);

            } else
                Log.e("test", String.valueOf(user.get(position)));
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgRec;
        public TextView txtDetails;
        public ImageView imgUser;
        public TextView txtName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRec = itemView.findViewById(R.id.imgRec);
            txtDetails = itemView.findViewById(R.id.txtDtails);
            imgUser = itemView.findViewById(R.id.profile_image);
            txtName = itemView.findViewById(R.id.userName);

        }
    }

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
