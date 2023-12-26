package com.example.blooddonatehub.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonatehub.Adapter.AllPersonRelationAdapter;
import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.BloodDonateListResponse;
import com.example.blooddonatehub.Utils.Tools;
import com.example.blooddonatehub.Utils.VariableBag;
import com.example.blooddonatehub.network.RestClient;
import com.example.blooddonatehub.network.Restcall;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SecondDonateFragment extends Fragment {

    AllPersonRelationAdapter  allPersonRelationAdapter;
    RecyclerView rcvBloodType;
    EditText etSearchBloodGroup;
    ImageView tvNoData;
    TextView tvNoDataFound;
    LinearLayout voiceSearch;
    private static final int VOICE_SEARCH_REQUEST_CODE = 123;

    Restcall restcall;
    Tools tools;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_second_donate, container, false);

        rcvBloodType=v.findViewById(R.id.rcvBloodType);
        etSearchBloodGroup=v.findViewById(R.id.etSearchBloodGroup);
        tvNoData = v.findViewById(R.id.tvNoData);
        tvNoDataFound = v.findViewById(R.id.tvNoDataFound);
        voiceSearch = v.findViewById(R.id.voiceSearch);
        tools=new Tools(getContext());
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);


      /*  tvNoDataFound.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);*/

        GetallBloodgroupCall();


        voiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVoiceSearch();
            }
        });
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

    private void GetallBloodgroupCall() {
        tools.showLoading();
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
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(BloodDonateListResponse bloodDonateListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (bloodDonateListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {

                                           rcvBloodType.setVisibility(View.VISIBLE);
                                           tvNoData.setVisibility(View.GONE);
                                           tvNoDataFound.setVisibility(View.GONE);

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcvBloodType.setLayoutManager(layoutManager);
                                    allPersonRelationAdapter = new AllPersonRelationAdapter(getContext(), bloodDonateListResponse.getGetBloodGroupList());
                                    rcvBloodType.setAdapter(allPersonRelationAdapter);

                                    allPersonRelationAdapter.SetUpInterFace(new AllPersonRelationAdapter.EditClick() {
                                        @Override
                                        public void EditPage(BloodDonateListResponse.GetBloodGroup bloodGroup) {

                                        }
                                    });
                                }
                                else {
                                    rcvBloodType.setVisibility(View.GONE);
                                tvNoData.setVisibility(View.VISIBLE);
                                tvNoDataFound.setVisibility(View.VISIBLE);
                            }
                                Toast.makeText(getContext(), bloodDonateListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                  /*  @Override
                    public void onNext(BloodDonateListResponse bloodDonateListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (bloodDonateListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    // Filter the data for blood group B+
                                    List<BloodDonateListResponse.GetBloodGroup> filteredList = filterData(bloodDonateListResponse.getGetBloodGroupList(), "");

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcvBloodType.setLayoutManager(layoutManager);
                                    allPersonRelationAdapter = new AllPersonRelationAdapter(getContext(), filteredList);
                                    rcvBloodType.setAdapter(allPersonRelationAdapter);

                                    // Check if the filtered list is empty and show/hide the appropriate views
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

                    // Method to filter data based on blood group
                    private List<BloodDonateListResponse.GetBloodGroup> filterData(List<BloodDonateListResponse.GetBloodGroup> dataList, String bloodGroup) {
                        List<BloodDonateListResponse.GetBloodGroup> filteredList = new ArrayList<>();
                        for (BloodDonateListResponse.GetBloodGroup item : dataList) {
                            if (item.getBloodGroup().equalsIgnoreCase(bloodGroup)) {
                                filteredList.add(item);
                            }
                        }
                        return filteredList;
                    }
*/

                });
    }




    public void openVoiceSearch() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(i, VOICE_SEARCH_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_SEARCH_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!arrayList.isEmpty()){
                String voice =arrayList.get(0);
                searchListCategory(voice);
            }else {
                Toast.makeText(getContext(), "No voice detected", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    public  void searchListCategory(String CategoryName){

        allPersonRelationAdapter.Search(CategoryName,rcvBloodType);
        if (allPersonRelationAdapter.isEmpty()){
            tvNoData.setVisibility(View.VISIBLE);
        }
    }
}