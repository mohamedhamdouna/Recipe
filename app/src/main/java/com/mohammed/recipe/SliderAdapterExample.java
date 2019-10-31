package com.mohammed.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {


        switch (position) {
            case 0:
                viewHolder.imgitemView.setImageResource(R.drawable.welcomeone);
                break;
            case 1:
                viewHolder.imgitemView.setImageResource(R.drawable.welcomethree);
                break;
            case 2:
                viewHolder.imgitemView.setImageResource(R.drawable.welcometow);
                break;
            default:
                viewHolder.imgitemView.setImageResource(R.drawable.welcomeone);

                break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 3;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imgitemView;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            imgitemView = itemView.findViewById(R.id.imgitemView);
            this.itemView = itemView;
        }
    }
}