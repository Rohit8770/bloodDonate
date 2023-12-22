package com.example.blooddonatehub.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blooddonatehub.Adapter.LocationAdapter;
import com.example.blooddonatehub.BloodRequestActivity;
import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.LocationListResponse;
import com.example.blooddonatehub.Utils.VariableBag;
import com.example.blooddonatehub.network.RestClient;
import com.example.blooddonatehub.network.Restcall;

import rx.Subscriber;
import rx.schedulers.Schedulers;


public class LocationFragment extends DialogFragment {

    ImageView imgClose;
    RecyclerView rcvLocationFragment;
    EditText etSearchLocation;

    Restcall restcall;
    LocationAdapter locationAdapter;
    Button btnDoneLocation;



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

        imgClose=view.findViewById(R.id.imgClose);
        rcvLocationFragment=view.findViewById(R.id.rcvLocationFragment);
        etSearchLocation=view.findViewById(R.id.etSearchLocation);
     //   btnDoneLocation=view.findViewById(R.id.btnDoneLocation);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        LocationCall();
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        /*btnDoneLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });*/


        etSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               /* if(s.toString().length()>=3){

                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    private void LocationCall() {
        String location="AHN";

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
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(LocationListResponse locationListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (locationListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && locationListResponse.getAreaResponseList() != null && locationListResponse.getAreaResponseList().size() > 0) {

                                  //  rcvLocationFragment.setVisibility(View.VISIBLE);

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcvLocationFragment.setLayoutManager(layoutManager);
                                    locationAdapter = new LocationAdapter(getContext(), locationListResponse.getAreaResponseList());
                                    rcvLocationFragment.setAdapter(locationAdapter);

                                    locationAdapter.SetUpInterFace(new LocationAdapter.LocationClick() {
                                        @Override
                                        public void SetLocation(LocationListResponse.AreaResponse listLocation) {
                                            etSearchLocation.setText(listLocation.getAreaName()+ " " +listLocation.getCity()+ " "  +listLocation.getState() + " " +listLocation.getPincode());
                                        //    rcvLocationFragment.setVisibility(View.GONE);

                                            if (dataClick != null) {
                                                dataClick.dataClick(etSearchLocation.getText().toString());
                                                dismiss();

                                            }
                                        }
                                    });
                                   /* ArrayAdapter<LocationListResponse.Pincode> adapter = new ArrayAdapter<>(BloodRequestActivity.this, android.R.layout.simple_spinner_item, locationListResponse.getPincodes());
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerLocationSuggest.setAdapter(adapter);
                                    spinnerData.equals("Suggested locations: " + locationListResponse.getPincodes().toString());*/

                                }//Toast.makeText(BloodRequestActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}