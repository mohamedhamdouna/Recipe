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
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohammed.recipe.Branch_Act;
import com.mohammed.recipe.R;
import com.mohammed.recipe.module.Recipe;
import com.mohammed.recipe.module.cataegories;


import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {

    Activity activity;
    public String nameCat;
    ArrayList<cataegories> data;

    public CatAdapter(Activity activity, ArrayList<cataegories> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.itemcateg, null, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        holder.txtCat.setText(data.get(position).getTxtCat());
        nameCat = data.get(position).getTxtCat();

        String foodImage = data.get(position).getImgCat();
        Bitmap bitmap = convert(foodImage);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imgCat.setImageBitmap(bitmap);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, Branch_Act.class);
                intent.putExtra("ingredients", holder.txtCat.getText());
                activity.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgCat;
        public TextView txtCat;
        public LinearLayout linearLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCat = itemView.findViewById(R.id.imgCat);
            txtCat = itemView.findViewById(R.id.txtCat);
            linearLayout = itemView.findViewById(R.id.lvCat);


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
