package com.example.blooddonatehub.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blooddonatehub.Adapter.AllPersonRelationAdapter;
import com.example.blooddonatehub.Adapter.PosterAdapter;
import com.example.blooddonatehub.Model.AllPersonRelationDataModel;
import com.example.blooddonatehub.Model.PosterDataModel;
import com.example.blooddonatehub.R;

import java.util.ArrayList;
import java.util.List;

public class SecondDonateFragment extends Fragment {

    AllPersonRelationAdapter  allPersonRelationAdapter;
    RecyclerView rcvBloodType;
    EditText etSearchBloodGroup;
    ImageView tvNoData;
    TextView tvNoDataFound;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_second_donate, container, false);

        rcvBloodType=v.findViewById(R.id.rcvBloodType);
        etSearchBloodGroup=v.findViewById(R.id.etSearchBloodGroup);
        tvNoData = v.findViewById(R.id.tvNoData);
        tvNoDataFound = v.findViewById(R.id.tvNoDataFound);

        tvNoDataFound.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);

        rcvBloodType.setLayoutManager(new LinearLayoutManager(getContext()));
        allPersonRelationAdapter = new AllPersonRelationAdapter(getMyData(), getContext());
        rcvBloodType.setAdapter(allPersonRelationAdapter);



        etSearchBloodGroup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (allPersonRelationAdapter != null) {
                    allPersonRelationAdapter.Search(charSequence, rcvBloodType);

                    boolean isSearchResultsEmpty = allPersonRelationAdapter.isEmpty();
                    if (isSearchResultsEmpty) {
                        tvNoDataFound.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.VISIBLE);
                    } else {
                        tvNoDataFound.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


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