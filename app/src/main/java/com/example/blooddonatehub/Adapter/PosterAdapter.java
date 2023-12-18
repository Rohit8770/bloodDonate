package com.example.blooddonatehub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blooddonatehub.BloodHomeActivity;
import com.example.blooddonatehub.Model.PosterDataModel;
import com.example.blooddonatehub.R;

import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterDataHolder> {

    Context context;
    List<PosterDataModel> posterDataModelList;

    public PosterAdapter(List<PosterDataModel> posterDataModelList,Context context) {
        this.posterDataModelList = posterDataModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PosterDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.poster_item_file, parent, false);
        return  new PosterDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterDataHolder holder, int position) {
        PosterDataModel posterDataModel=posterDataModelList.get(position);


        try {
            Glide.with(context)
                    .load(posterDataModelList.get(position).getImg())
                    .placeholder(R.drawable.person_24)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.imgPoster);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return posterDataModelList.size();
    }

    public  class PosterDataHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
       public PosterDataHolder(@NonNull View itemView) {
           super(itemView);
           imgPoster=itemView.findViewById(R.id.imgPoster);
       }
   }
}
