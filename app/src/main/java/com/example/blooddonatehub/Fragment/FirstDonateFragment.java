package com.example.blooddonatehub.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonatehub.Adapter.AllPersonRelationAdapter;
import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.BloodDonateListResponse;
import com.example.blooddonatehub.Utils.VariableBag;
import com.example.blooddonatehub.network.RestClient;
import com.example.blooddonatehub.network.Restcall;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class FirstDonateFragment extends Fragment {
    AllPersonRelationAdapter allPersonRelationAdapter;
    RecyclerView rcvBloodType;
    EditText etSearch;
    ImageView tvNoData;
    TextView tvNoDataFound;
    Restcall restcall;
    String bg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_donate, container, false);

        rcvBloodType = v.findViewById(R.id.rcvBloodType);
        etSearch = v.findViewById(R.id.etSearch);
        tvNoData = v.findViewById(R.id.tvNoData);
        tvNoDataFound = v.findViewById(R.id.tvNoDataFound);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

      /*  tvNoDataFound.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);*/

        GetallBloodgroupCall();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (allPersonRelationAdapter != null) {
                    allPersonRelationAdapter.Search(charSequence, rcvBloodType);
                }
                boolean isSearchResultsEmpty = allPersonRelationAdapter.isEmpty();
                if (isSearchResultsEmpty) {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.VISIBLE);
                } else {
                    tvNoDataFound.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return v;
    }

    private void GetallBloodgroupCall() {

        restcall.GetallBloodgroups("getallBloodgroups")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<BloodDonateListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                  /*  @Override
                    public void onNext(BloodDonateListResponse bloodDonateListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (bloodDonateListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE))
                                       {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcvBloodType.setLayoutManager(layoutManager);
                                    allPersonRelationAdapter = new AllPersonRelationAdapter(getContext(), bloodDonateListResponse.getGetBloodGroupList());
                                    rcvBloodType.setAdapter(allPersonRelationAdapter);

                                }
                                Toast.makeText(getContext(), bloodDonateListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }*/


                    @Override
                    public void onNext(BloodDonateListResponse bloodDonateListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (bloodDonateListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {

                                     List<BloodDonateListResponse.GetBloodGroup> filteredList = filterData(bloodDonateListResponse.getGetBloodGroupList(), "A+");

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcvBloodType.setLayoutManager(layoutManager);
                                    allPersonRelationAdapter = new AllPersonRelationAdapter(getContext(), filteredList);
                                    rcvBloodType.setAdapter(allPersonRelationAdapter);

                                    if (filteredList.isEmpty()) {
                                        tvNoDataFound.setVisibility(View.VISIBLE);
                                        tvNoData.setVisibility(View.VISIBLE);
                                    } else {
                                        tvNoDataFound.setVisibility(View.GONE);
                                        tvNoData.setVisibility(View.GONE);
                                    }
                                }
                                Toast.makeText(getContext(), bloodDonateListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                   private List<BloodDonateListResponse.GetBloodGroup> filterData(List<BloodDonateListResponse.GetBloodGroup> dataList, String bloodGroup) {
                        List<BloodDonateListResponse.GetBloodGroup> filteredList = new ArrayList<>();
                        for (BloodDonateListResponse.GetBloodGroup item : dataList) {
                            if (item.getBloodGroup().equalsIgnoreCase(bloodGroup)) {
                                filteredList.add(item);
                            }
                        }
                        return filteredList;
                    }


                });
    }

}