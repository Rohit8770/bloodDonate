package com.example.blooddonatehub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonatehub.Adapter.AcceptedBloodAdapter;
import com.example.blooddonatehub.Adapter.AllPersonRelationAdapter;
import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.AcceptedBloodListResponse;
import com.example.blooddonatehub.Response.BloodDonateListResponse;
import com.example.blooddonatehub.Utils.Tools;
import com.example.blooddonatehub.Utils.VariableBag;
import com.example.blooddonatehub.network.RestClient;
import com.example.blooddonatehub.network.Restcall;

import java.util.ArrayList;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class CompleteActivity extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView rcvAcceptedData;
    AcceptedBloodAdapter acceptedBloodAdapter;
    Restcall restcall;
    Tools tools;
    LinearLayout voiceSearch;
    ImageView tvNoData;
    private static final int VOICE_SEARCH_REQUEST_CODE = 123;
    TextView tvNoDataFound;
    EditText etSearch;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, BloodHomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        tools=new Tools(this);
        tools.ScreenshotBlock(getWindow());
        imgBack=findViewById(R.id.imgBack);
        tvNoData=findViewById(R.id.tvNoData);
        tvNoDataFound=findViewById(R.id.tvNoDataFound);
        rcvAcceptedData=findViewById(R.id.rcvAcceptedData);
        etSearch=findViewById(R.id.etSearch);
        voiceSearch=findViewById(R.id.voiceSearch);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        AcceptedBloodCall();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        voiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVoiceSearch();
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (acceptedBloodAdapter != null) {
                    acceptedBloodAdapter.Search(charSequence, rcvAcceptedData);

                    boolean isSearchResultsEmpty = acceptedBloodAdapter.isEmpty();
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



    }

    private void AcceptedBloodCall() {
        tools.showLoading();
        restcall.AcceptedBloodCall("requestApproveBloodgroups")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<AcceptedBloodListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(CompleteActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(AcceptedBloodListResponse acceptedBloodListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                    if (acceptedBloodListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    rcvAcceptedData.setVisibility(View.VISIBLE);
                                    tvNoData.setVisibility(View.GONE);
                                    tvNoDataFound.setVisibility(View.GONE);

                                        LinearLayoutManager layoutManager = new LinearLayoutManager(CompleteActivity.this, RecyclerView.VERTICAL, false);
                                        rcvAcceptedData.setLayoutManager(layoutManager);
                                        acceptedBloodAdapter = new AcceptedBloodAdapter(CompleteActivity.this, acceptedBloodListResponse.getGetBloodGroupList());
                                        rcvAcceptedData.setAdapter(acceptedBloodAdapter);
                                    }
                                    else {
                                        rcvAcceptedData.setVisibility(View.GONE);
                                        tvNoData.setVisibility(View.VISIBLE);
                                        tvNoDataFound.setVisibility(View.VISIBLE);
                                }
                                    Toast.makeText(CompleteActivity.this, acceptedBloodListResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CompleteActivity.this, "No voice detected", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(CompleteActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    public  void searchListCategory(String BloodGroup){

        acceptedBloodAdapter.Search(BloodGroup,rcvAcceptedData);
        if (acceptedBloodAdapter.isEmpty()){
            tvNoData.setVisibility(View.GONE);
            tvNoDataFound.setVisibility(View.GONE);
        }
    }
}