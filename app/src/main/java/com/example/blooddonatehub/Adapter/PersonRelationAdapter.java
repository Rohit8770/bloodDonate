package com.example.blooddonatehub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blooddonatehub.Model.PersonRelationDataModel;
import com.example.blooddonatehub.Model.PosterDataModel;
import com.example.blooddonatehub.R;

import java.util.List;

public class PersonRelationAdapter extends RecyclerView.Adapter<PersonRelationAdapter.personRelationViewHolder>{

    Context context;
    List<PersonRelationDataModel>personRelationDataModelList;

    public PersonRelationAdapter(List<PersonRelationDataModel> personRelationDataModelList,Context context) {
        this.personRelationDataModelList = personRelationDataModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public personRelationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.personrelation_item_file, parent, false);
        return  new personRelationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull personRelationViewHolder holder, int position) {
        PersonRelationDataModel personRelationDataModel=personRelationDataModelList.get(position);

        holder.txName.setText(personRelationDataModel.getName());
        holder.txtime.setText(personRelationDataModel.getTime());
        holder.txCritical.setText(personRelationDataModel.getCritical());
        holder.txLocation.setText( personRelationDataModel.getLocation());
        holder.txBloodGroup.setText(personRelationDataModel.getBloodgroup());
        holder.txUnit.setText(personRelationDataModel.getUnit());



    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class personRelationViewHolder extends RecyclerView.ViewHolder {
        TextView txName,txCritical,txUnit,txLocation,txtime,txBloodGroup;

        public personRelationViewHolder(@NonNull View itemView) {
            super(itemView);



                txName=itemView.findViewById(R.id.txName);
                txCritical=itemView.findViewById(R.id.txCritical);
                txUnit=itemView.findViewById(R.id.txUnit);
                txLocation=itemView.findViewById(R.id.txLocation);
                txtime=itemView.findViewById(R.id.txTime);
                txBloodGroup=itemView.findViewById(R.id.txBloodGroup);
        }
    }
}
