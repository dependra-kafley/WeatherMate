package com.example.weathermate;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomItemAdapter extends RecyclerView.Adapter<CustomItemAdapter.CustomAdapterViewHolder> {
    public Context mContext;
    ArrayList<WeatherModel> weatherList;
    int last_position=1;
    public  CustomItemAdapter(){
        
    }

    public CustomItemAdapter(Context mContext,ArrayList<WeatherModel> weatherList) {
        this.mContext=mContext;
        this.weatherList=weatherList;
    }

    @NonNull
    @Override
    public CustomAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        return new CustomAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterViewHolder holder, int position) {
//        holder.txtTemparature.setText(weatherList.get(position).getTemp());
//        holder.txtDay.setText(weatherList.get(position).getDay());
//        holder.ivcloud.setImageURI(Uri.parse(weatherList.get(position).url));
       if(holder.getAdapterPosition()>last_position) {
           Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.row_in);
           holder.itemView.startAnimation(animation);
       }
        last_position=holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        if(weatherList.size()==0){
            return 0;
        }
        Log.d("TAG", "getItemCount: "+weatherList.size());
        return 15;
    }

    public class CustomAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtTemparature,txtDay;
        ImageView ivcloud;
        public CustomAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ivcloud=itemView.findViewById(R.id.custom_clouds);
            txtDay=itemView.findViewById(R.id.tvDay);
            txtTemparature=itemView.findViewById(R.id.tvTemp);
        }
    }
}
