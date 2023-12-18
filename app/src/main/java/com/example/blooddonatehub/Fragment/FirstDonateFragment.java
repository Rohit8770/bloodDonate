package com.example.blooddonatehub.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blooddonatehub.Adapter.AllPersonRelationAdapter;
import com.example.blooddonatehub.Adapter.PosterAdapter;
import com.example.blooddonatehub.BloodHomeActivity;
import com.example.blooddonatehub.Model.AllPersonRelationDataModel;
import com.example.blooddonatehub.Model.PosterDataModel;
import com.example.blooddonatehub.R;

import java.util.ArrayList;
import java.util.List;

public class FirstDonateFragment extends Fragment {
    AllPersonRelationAdapter allPersonRelationAdapter;
    RecyclerView rcvBloodType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_first_donate, container, false);

        rcvBloodType=v.findViewById(R.id.rcvBloodType);

        rcvBloodType.setLayoutManager(new LinearLayoutManager(getContext()));
        allPersonRelationAdapter = new AllPersonRelationAdapter(getMyData(), getContext());
        rcvBloodType.setAdapter(allPersonRelationAdapter);


        return v;
    }

    private List<AllPersonRelationDataModel> getMyData() {
        List<AllPersonRelationDataModel> myDataModels = new ArrayList<>();
        myDataModels.add(new AllPersonRelationDataModel("Rohit Malviya","4 unit","Sarkej ahemdabad near by thaltej metro station (India)","monday nov 5","Critical","A+"));
        myDataModels.add(new AllPersonRelationDataModel("Gourav Sharma","2 unit","Sarkej ahemdabad near by thaltej metro station (India)","wednesday feb 1","Critical","B+"));
        myDataModels.add(new AllPersonRelationDataModel("Vishal Raghuwanshi","6 unit","Sarkej ahemdabad near by thaltej metro station (India)","monday jan 8","No","AB+"));
        myDataModels.add(new AllPersonRelationDataModel("Anand Patel","1 unit","Sarkej ahemdabad near by thaltej metro station (India)","saturday july 22","Critical","O-"));
        myDataModels.add(new AllPersonRelationDataModel("Nikhil Roy","7 unit","Sarkej ahemdabad near by thaltej metro station (India)","sunday dec 12","No","A1+"));

        return myDataModels;
    }
}