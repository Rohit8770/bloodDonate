package com.example.blooddonatehub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.AcceptedBloodListResponse;
import com.example.blooddonatehub.Response.BloodDonateListResponse;

import java.util.ArrayList;
import java.util.List;

public class AcceptedBloodAdapter extends RecyclerView.Adapter<AcceptedBloodAdapter.AcceptViewHolder>{

    Context context;
    List<AcceptedBloodListResponse.GetBloodGroup> acceptlist;
    List<AcceptedBloodListResponse.GetBloodGroup> searchlist;

    public AcceptedBloodAdapter(Context context, List<AcceptedBloodListResponse.GetBloodGroup> acceptlist) {
        this.context = context;
        this.acceptlist = acceptlist;
        this.searchlist = new ArrayList<>(acceptlist);
    }
    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    @NonNull
    @Override
    public AcceptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.accepted_data_item_file, parent, false);
        return  new AcceptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptViewHolder holder, int position) {
        AcceptedBloodListResponse.GetBloodGroup bloodGroup=searchlist.get(position);

        holder.txName.setText(bloodGroup.getPatientFullName());
        holder.txUnit.setText(bloodGroup.getSelectUnits());
        holder.txtime.setText(bloodGroup.getDate());
        holder.txLocation.setText( bloodGroup.getLocation());
        holder.txCritical.setText(bloodGroup.getCriticalSituation());
        holder.txBloodGroup.setText(bloodGroup.getBloodGroup());
    }

    @Override
    public int getItemCount() {
        return searchlist.size();
    }

    public  class AcceptViewHolder extends RecyclerView.ViewHolder {
        TextView txName,txCritical,txUnit,txLocation,txtime,txBloodGroup;
        ImageView imgShare,ivFollowed;

        RelativeLayout btnAccept;
        public AcceptViewHolder(@NonNull View itemView) {
            super(itemView);

            txName=itemView.findViewById(R.id.txName);
            txCritical=itemView.findViewById(R.id.txCritical);
            txUnit=itemView.findViewById(R.id.txUnit);
            txLocation=itemView.findViewById(R.id.txLocation);
            txtime=itemView.findViewById(R.id.txTime);
            txBloodGroup=itemView.findViewById(R.id.txBloodGroup);
            imgShare=itemView.findViewById(R.id.imgShare);
            btnAccept=itemView.findViewById(R.id.btnAccept);
            ivFollowed=itemView.findViewById(R.id.ivFollowed);
            ivFollowed.setVisibility(View.GONE);

        }
    }

    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try {
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                searchlist = new ArrayList<>(acceptlist);
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            } else {
                List<AcceptedBloodListResponse.GetBloodGroup> filterList = new ArrayList<>();
                for (AcceptedBloodListResponse.GetBloodGroup row : acceptlist) {
                    if (row.getPatientFullName().toLowerCase().contains(charString) ||
                            row.getLocation().toLowerCase().contains(charString) ||
                            row.getSelectUnits().toLowerCase().contains(charString) ||
                            row.getBloodGroup().toLowerCase().contains(charString) ||
                            row.getDate().toLowerCase().contains(charString)) {
                        filterList.add(row);
                    }
                }
                searchlist = new ArrayList<>(filterList);

                if (searchlist.isEmpty()) {
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
