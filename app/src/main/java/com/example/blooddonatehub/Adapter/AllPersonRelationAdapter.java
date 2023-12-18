package com.example.blooddonatehub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blooddonatehub.Model.AllPersonRelationDataModel;
import com.example.blooddonatehub.Model.PersonRelationDataModel;
import com.example.blooddonatehub.Model.PosterDataModel;
import com.example.blooddonatehub.R;

import java.util.ArrayList;
import java.util.List;

public class AllPersonRelationAdapter extends RecyclerView.Adapter<AllPersonRelationAdapter.AllPersonViewHolder>{
    List<AllPersonRelationDataModel> allPersonRelationDataModelList;
    List<AllPersonRelationDataModel> searchList;
    Context context;

    public AllPersonRelationAdapter(List<AllPersonRelationDataModel> allPersonRelationDataModelList, Context context) {
        this.allPersonRelationDataModelList = allPersonRelationDataModelList;
        this.searchList =new ArrayList<>(allPersonRelationDataModelList);
        this.context = context;
    }
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    @NonNull
    @Override
    public AllPersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.personrelation_item_file, parent, false);
        return  new AllPersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPersonViewHolder holder, int position) {
        AllPersonRelationDataModel allPersonRelationDataModel=searchList.get(position);

        holder.txName.setText(allPersonRelationDataModel.getName());
        holder.txUnit.setText(allPersonRelationDataModel.getUnit());
        holder.txtime.setText(allPersonRelationDataModel.getTime());
        holder.txLocation.setText( allPersonRelationDataModel.getLocation());
        holder.txCritical.setText(allPersonRelationDataModel.getCritical());
        holder.txBloodGroup.setText(allPersonRelationDataModel.getBloodgroup());


    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public  class AllPersonViewHolder extends RecyclerView.ViewHolder {

        TextView txName,txCritical,txUnit,txLocation,txtime,txBloodGroup;

        public AllPersonViewHolder(@NonNull View itemView) {
            super(itemView);


                txName=itemView.findViewById(R.id.txName);
            txCritical=itemView.findViewById(R.id.txCritical);
            txUnit=itemView.findViewById(R.id.txUnit);
            txLocation=itemView.findViewById(R.id.txLocation);
            txtime=itemView.findViewById(R.id.txTime);
            txBloodGroup=itemView.findViewById(R.id.txBloodGroup);
        }
    }

    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try {
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                searchList = new ArrayList<>(allPersonRelationDataModelList);
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            } else {
                List<AllPersonRelationDataModel> filterList = new ArrayList<>();
                for (AllPersonRelationDataModel Row : allPersonRelationDataModelList) {
                    if (Row.getBloodgroup().toLowerCase().contains(charString.toLowerCase())) {
                        filterList.add(Row);
                    }
                }
                searchList = new ArrayList<>(filterList);

                if (searchList.isEmpty()) {
                    categoryListRecyclerView.setVisibility(View.GONE);
                } else {
                    categoryListRecyclerView.setVisibility(View.VISIBLE);
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}