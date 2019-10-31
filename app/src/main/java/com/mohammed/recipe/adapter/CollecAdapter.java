package com.mohammed.recipe.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mohammed.recipe.Branch_Act;
import com.mohammed.recipe.DetailesAct;
import com.mohammed.recipe.R;
import com.mohammed.recipe.module.Recipe;
import com.mohammed.recipe.module.cataegories;


import java.util.ArrayList;

public class CollecAdapter extends RecyclerView.Adapter<CollecAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<Recipe> data;


    public CollecAdapter(Activity activity, ArrayList<Recipe> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.item_myrec, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        String foodImage = data.get(position).getImg();

        Bitmap bitmap = convert(foodImage);

        //Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imgRec.setImageBitmap(bitmap);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe recipe = data.get(position);
                Intent intent = new Intent(activity, DetailesAct.class);
                intent.putExtra("recipe", recipe);
                activity.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgRec;
        public CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRec = itemView.findViewById(R.id.imgMyRec);
            cardView = itemView.findViewById(R.id.cardView);


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
