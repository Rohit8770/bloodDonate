package com.example.blooddonatehub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.BloodDonateListResponse;
import com.example.blooddonatehub.Response.LocationListResponse;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    Context context;

   List<LocationListResponse.AreaResponse> areaResponseList;

    LocationClick locationClick;

    public interface LocationClick{
        void SetLocation(LocationListResponse.AreaResponse areaResponse1);
    }

    public  void SetUpInterFace(LocationClick locationClick1){
        this.locationClick=locationClick1;
    }

    public LocationAdapter(Context context, List<LocationListResponse.AreaResponse> areaResponseList) {
        this.context = context;
        this.areaResponseList = areaResponseList;
    }


/*    public List<String> getLocationList() {
        List<String> locations = new ArrayList<>();
        for (LocationListResponse.AreaResponse pincode : areaResponseList) {
            locations.add(pincode.getPincode());
        }
        return locations;
    }*/
    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.location_item_file, parent, false);
        return  new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationListResponse.AreaResponse areaResponse=areaResponseList.get(position);

        holder.txArea.setText(areaResponse.getAreaName() + " " + areaResponse.getCity() + " " + areaResponse.getState() + " " + areaResponse.getPincode());

        /*holder.txState.setText(pincode.getState());
        holder.txCity.setText(pincode.getCity());
        holder.txPincode.setText(pincode.getPincode());*/

        holder.txArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {

                locationClick.SetLocation(areaResponseList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaResponseList.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView txArea,txState,txCity,txPincode;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            txArea=itemView.findViewById(R.id.txArea);
        /*    txState=itemView.findViewById(R.id.txState);
            txCity=itemView.findViewById(R.id.txCity);
            txPincode=itemView.findViewById(R.id.txPincode);*/
        }
    }
}
