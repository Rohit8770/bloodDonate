package com.example.blooddonatehub.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonatehub.Adapter.LocationAdapter;
import com.example.blooddonatehub.BloodRequestActivity;
import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.LocationListResponse;
import com.example.blooddonatehub.Utils.Tools;
import com.example.blooddonatehub.Utils.VariableBag;
import com.example.blooddonatehub.network.RestClient;
import com.example.blooddonatehub.network.Restcall;

import java.util.ArrayList;

import rx.Subscriber;
import rx.schedulers.Schedulers;


public class LocationFragment extends DialogFragment {

    ImageView imgClose;
    RecyclerView rcvLocationFragment;
    EditText etSearchLocation;
    Restcall restcall;
    LocationAdapter locationAdapter;
    Tools tools;
    ImageView tvNoData;
    TextView tvNoDataFound;
    LinearLayout voiceSearch;
    private static final int VOICE_SEARCH_REQUEST_CODE = 123;
    DataClick dataClick;
    public interface DataClick{
        void dataClick(String data);

    }
    public void setupInterface(DataClick dataClick){
        this.dataClick = dataClick;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_location, container, false);

        tools=new Tools(getContext());
        imgClose=view.findViewById(R.id.imgClose);
        rcvLocationFragment=view.findViewById(R.id.rcvLocationFragment);
        etSearchLocation=view.findViewById(R.id.etSearchLocation);
        tvNoData = view.findViewById(R.id.tvNoData);
        tvNoDataFound = view.findViewById(R.id.tvNoDataFound);
        voiceSearch = view.findViewById(R.id.voiceSearch);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        LocationCall();
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        voiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVoiceSearch();
            }
        });

        etSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (locationAdapter != null) {
                    locationAdapter.Search(s, rcvLocationFragment);
                }

                boolean isSearchResultsEmpty = locationAdapter.isEmpty();
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


        return view;
    }
    private void LocationCall() {
        tools.showLoading();
        String location="Ahe";

        restcall.LocationCall("search_pincode",location)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LocationListResponse>() {
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
                             //   Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(LocationListResponse locationListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (locationListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && locationListResponse.getAreaResponseList() != null && locationListResponse.getAreaResponseList().size() > 0) {

                                    //  rcvLocationFragment.setVisibility(View.VISIBLE);
                                    rcvLocationFragment.setVisibility(View.VISIBLE);
                                    tvNoData.setVisibility(View.GONE);
                                    tvNoDataFound.setVisibility(View.GONE);

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcvLocationFragment.setLayoutManager(layoutManager);
                                    locationAdapter = new LocationAdapter(getContext(), locationListResponse.getAreaResponseList());
                                    rcvLocationFragment.setAdapter(locationAdapter);

                                    locationAdapter.SetUpInterFace(new LocationAdapter.LocationClick() {
                                        @Override
                                        public void SetLocation(LocationListResponse.AreaResponse listLocation) {
                                            etSearchLocation.setText(listLocation.getAreaName()+ " " +listLocation.getCity()+ " "  +listLocation.getState() + " " +listLocation.getPincode());

                                            if (dataClick != null) {
                                                dataClick.dataClick(etSearchLocation.getText().toString());
                                                dismiss();
                                            }
                                        }
                                    });
                                }
                                else {
                                    rcvLocationFragment.setVisibility(View.GONE);
                                    tvNoData.setVisibility(View.VISIBLE);
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                }//Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
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
    public  void searchListCategory(String BloodGroup){

        locationAdapter.Search(BloodGroup,rcvLocationFragment);
        if (locationAdapter.isEmpty()){
            tvNoData.setVisibility(View.GONE);
            tvNoDataFound.setVisibility(View.GONE);
        }
    }
}