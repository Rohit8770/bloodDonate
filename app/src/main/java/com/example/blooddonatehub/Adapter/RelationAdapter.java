package com.example.blooddonatehub.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.BloodDonateListResponse;

import java.util.ArrayList;
import java.util.List;

public class RelationAdapter extends RecyclerView.Adapter<RelationAdapter.PosterDataHolder> {

    Context context;
    List<BloodDonateListResponse.GetBloodGroup> bloodDonateListResponseList;
    List<BloodDonateListResponse.GetBloodGroup> searchList;

    EditClick editClick;
    public  interface EditClick{
        void EditPage1(BloodDonateListResponse.GetBloodGroup bloodGroup);
        void FilterDialog(BloodDonateListResponse.GetBloodGroup bloodGroup);
    }

    public  void SetUpInterFace(RelationAdapter.EditClick editClick1){
        this.editClick=editClick1;
    }

    public RelationAdapter(Context context, List<BloodDonateListResponse.GetBloodGroup> bloodDonateListResponseList) {
        this.context = context;
        this.bloodDonateListResponseList = bloodDonateListResponseList;
        this.searchList = new ArrayList<>(bloodDonateListResponseList);

    }

    public boolean isEmpty() {
        return getItemCount() == 0;
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
        BloodDonateListResponse.GetBloodGroup bloodGroup=bloodDonateListResponseList.get(position);

        holder.txName.setText(bloodGroup.getPatientFullName());
        holder.txUnit.setText(bloodGroup.getSelectUnits());
        holder.txtime.setText(bloodGroup.getDate());
        holder.txLocation.setText( bloodGroup.getLocation());
        holder.txCritical.setText(bloodGroup.getCriticalSituation());
        holder.txBloodGroup.setText(bloodGroup.getBloodGroup());

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editClick.EditPage1(searchList.get(position));
                editClick.FilterDialog(searchList.get(position));


                holder.ivFollowed.setVisibility(View.VISIBLE);
                holder.btnAccept.setVisibility(View.GONE);
            }
        });
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                String message = "Users Details for blood donation :\n\n"
                        + "Name: " + holder.txName.getText().toString() + "\n"
                        + "Location: " + holder.txLocation.getText().toString() + "\n"
                        + "Blood Group: " + holder.txBloodGroup.getText().toString() + "\n"
                        + "Units: " + holder.txUnit.getText().toString() + "\n"
                        + "Date: " + holder.txtime.getText().toString();

                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, "Share details using");
                context.startActivity(shareIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bloodDonateListResponseList.size();
    }

    public  class PosterDataHolder extends RecyclerView.ViewHolder {
        TextView txName,txCritical,txUnit,txLocation,txtime,txBloodGroup;
        ImageView imgShare,ivFollowed;
        TextView btnAccept;
        RelativeLayout acceptBtn;
       public PosterDataHolder(@NonNull View itemView) {
           super(itemView);
           txName=itemView.findViewById(R.id.txName);
           txCritical=itemView.findViewById(R.id.txCritical);
           txUnit=itemView.findViewById(R.id.txUnit);
           txLocation=itemView.findViewById(R.id.txLocation);
           txtime=itemView.findViewById(R.id.txTime);
           acceptBtn=itemView.findViewById(R.id.acceptBtn);
           txBloodGroup=itemView.findViewById(R.id.txBloodGroup);
           imgShare=itemView.findViewById(R.id.imgShare);
           btnAccept=itemView.findViewById(R.id.btnAccept);
           ivFollowed=itemView.findViewById(R.id.ivFollowed);
           ivFollowed.setVisibility(View.GONE);       }
   }

    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try {
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                searchList = new ArrayList<>(bloodDonateListResponseList);
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            } else {
                List<BloodDonateListResponse.GetBloodGroup> filterList = new ArrayList<>();
                for (BloodDonateListResponse.GetBloodGroup row : bloodDonateListResponseList) {
                    if (row.getPatientFullName().toLowerCase().contains(charString) ||
                            row.getLocation().toLowerCase().contains(charString) ||
                            row.getSelectUnits().toLowerCase().contains(charString) ||
                            row.getBloodGroup().toLowerCase().contains(charString) ||
                            row.getDate().toLowerCase().contains(charString)) {
                        filterList.add(row);
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


    private void openAcceptDialog() {
        // Create a new AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Thank you for Accepting");
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.verification);
        builder.setView(imageView);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
