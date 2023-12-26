package com.example.blooddonatehub.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.BloodDonateListResponse;

import java.util.List;

public class RelationAdapter extends RecyclerView.Adapter<RelationAdapter.PosterDataHolder> {

    Context context;
    List<BloodDonateListResponse.GetBloodGroup> bloodDonateListResponseList;

    public RelationAdapter(Context context, List<BloodDonateListResponse.GetBloodGroup> bloodDonateListResponseList) {
        this.context = context;
        this.bloodDonateListResponseList = bloodDonateListResponseList;
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
                openAcceptDialog();
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
       public PosterDataHolder(@NonNull View itemView) {
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
           ivFollowed.setVisibility(View.GONE);       }
   }

    private void openAcceptDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thank you for Accepting");
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
