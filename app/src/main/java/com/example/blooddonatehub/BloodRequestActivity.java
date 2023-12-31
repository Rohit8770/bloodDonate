package com.example.blooddonatehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonatehub.Adapter.LocationAdapter;
import com.example.blooddonatehub.Fragment.ConditionAgreeMentFragment;
import com.example.blooddonatehub.Fragment.LocationFragment;
import com.example.blooddonatehub.Response.BloodRequestListResponse;
import com.example.blooddonatehub.Utils.Tools;
import com.example.blooddonatehub.Utils.VariableBag;
import com.example.blooddonatehub.network.RestClient;
import com.example.blooddonatehub.network.Restcall;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class BloodRequestActivity extends AppCompatActivity {


    RelativeLayout txCalender;
    Button txSubmitRequest;
    TextView txDate,txAgreeMentCondition,etLocation;
    ImageView imgBack;
    String bloodGroupSp="";
    String bloodTypeSp="";
    String bloodUnitsSp="";
    String spinnerData="";
    CheckBox checkBoxAgree;
    AppCompatSpinner bloodTypeSpinner,bloodGroupSpinner,bloodUnitSpinner,spinnerLocationSuggest;
    SwitchCompat switchCritical;
    TextInputEditText etName,etMobileNumber,etDescription;
    Restcall restcall;
    LocationAdapter locationAdapter;
  //  RecyclerView rcvLocation;
    AppCompatSpinner spinnerLocation;
    Tools tools;
    String status;

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
        setContentView(R.layout.activity_blood_request);

        etName=findViewById(R.id.etName);
        etMobileNumber=findViewById(R.id.etMobileNumber);
        txCalender=findViewById(R.id.txCalender);
        txDate=findViewById(R.id.txDate);
        imgBack=findViewById(R.id.imgBack);
        etLocation=findViewById(R.id.etLocation);
        checkBoxAgree=findViewById(R.id.checkBoxAgree);
        bloodTypeSpinner=findViewById(R.id.bloodTypeSpinner);
        bloodGroupSpinner=findViewById(R.id.bloodGroupSpinner);
        etDescription=findViewById(R.id.etDescription);
        bloodUnitSpinner=findViewById(R.id.bloodUnitSpinner);
        switchCritical=findViewById(R.id.switchCritical);
        txSubmitRequest=findViewById(R.id.txSubmitRequest);
        txAgreeMentCondition=findViewById(R.id.txAgreeMentCondition);
        tools=new Tools(this);
        tools.ScreenshotBlock(getWindow());
        // spinnerLocation=findViewById(R.id.spinnerLocation);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);



      /*  etLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length()>=3){
                    LocationCall();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LocationFragment locationFragment = new LocationFragment();
                locationFragment.show(fragmentTransaction, "#tag");
                locationFragment.setCancelable(false);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                locationFragment.setupInterface(new LocationFragment.DataClick() {
                    @Override
                    public void dataClick(String data) {
                        etLocation.setText(data);
                    }
                });
            }
        });

/*        spinnerLocationSuggest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerData = parent.getSelectedItem().toString();}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});*/

        bloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroupSp = parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bloodTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodTypeSp = parent.getSelectedItem().toString();}
        @Override
            public void onNothingSelected(AdapterView<?> parent) {}});

        bloodUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodUnitsSp = parent.getSelectedItem().toString();}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        txSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String mobileNumber = etMobileNumber.getText().toString().trim();
                String date = txDate.getText().toString().trim();
                String location = etLocation.getText().toString().trim();
                String bloodType = bloodTypeSpinner.getSelectedItem().toString().trim();
                String bloodGroup = bloodGroupSpinner.getSelectedItem().toString().trim();
                String bloodUnit = bloodUnitSpinner.getSelectedItem().toString().trim();
                boolean checkBoxChecked = checkBoxAgree.isChecked();


                if (bloodType.equals("Select_Blood_Type")) {
                    Toast.makeText(BloodRequestActivity.this, "Please select blood Type", Toast.LENGTH_SHORT).show();

                }  else if (bloodGroup.equals("Select_Blood_Group")) {
                    Toast.makeText(BloodRequestActivity.this, "Please select blood group", Toast.LENGTH_SHORT).show();

                } else if (name.isEmpty()) {
                    etName.setError("Name is required");

                } else if (mobileNumber.isEmpty()) {
                    etMobileNumber.setError("Mobile number is required");

                } else if (date.equals("Select Date")) {
                    Toast.makeText(BloodRequestActivity.this, "Please select date", Toast.LENGTH_SHORT).show();

                } else if (bloodUnit.equals("Select_Units")) {
                    Toast.makeText(BloodRequestActivity.this, "Please select blood units", Toast.LENGTH_SHORT).show();

                } else if (location.isEmpty()) {
                    etLocation.setError("Location is required");

                } else if (!checkBoxChecked) {
                    Toast.makeText(BloodRequestActivity.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                }else {
                    RequestForBloodCall();
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });




        txCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        txAgreeMentCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ConditionAgreeMentFragment fragmentFilter = new ConditionAgreeMentFragment();
                fragmentFilter.show(fragmentTransaction, "#tag");
                fragmentFilter.setCancelable(false);
            }
        });
    }

    private void RequestForBloodCall() {
        tools.showLoading();


        restcall.CallRequestForBlood("blood_donation",bloodTypeSp,bloodGroupSp,
                 etName.getText().toString(),
                        etMobileNumber.getText().toString(),
                        txDate.getText().toString(),
                        bloodUnitsSp,  switchCritical.isChecked() ? "Critical" : "Not Critical",
                        etDescription.getText().toString(),
                        etLocation.getText().toString(),status)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<BloodRequestListResponse>() {
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
                                Toast.makeText(BloodRequestActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(BloodRequestListResponse bloodRequestListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (bloodRequestListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){
                                    etName.setText("");
                                    etDescription.setText("");
                                    etLocation.setText("");
                                    etMobileNumber.setText("");
                                    checkBoxAgree.setText("");
                                    txDate.setText("");
                                    switchCritical.setText("");
                                }
                               Toast.makeText(BloodRequestActivity.this, bloodRequestListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

  /*  private void LocationCall() {
        restcall.LocationCall("search_pincode", etLocation.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LocationListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(BloodRequestActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(LocationListResponse locationListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (locationListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                        && locationListResponse.getPincodes() != null
                                        && locationListResponse.getPincodes().size() > 0) {

                                    // Initialize the locationAdapter
                                    locationAdapter = new LocationAdapter(BloodRequestActivity.this, locationListResponse.getPincodes());

                                    // Assuming LocationAdapter has a method getLocationList() to get the list of locations
                                    List<String> locationStrings = locationAdapter.getLocationList();

                                    // Create an ArrayAdapter using the location list
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BloodRequestActivity.this, android.R.layout.simple_spinner_item, locationStrings);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerLocation.setAdapter(adapter);

                                    Toast.makeText(BloodRequestActivity.this, "success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }*/
   /* private void LocationCall() {

        restcall.LocationCall("search_pincode",etLocation.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LocationListResponse>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(BloodRequestActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(LocationListResponse locationListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (locationListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)
                                  && locationListResponse.getAreaResponseList() != null && locationListResponse.getAreaResponseList().size() > 0) {

                                    rcvLocation.setVisibility(View.VISIBLE);

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(BloodRequestActivity.this, RecyclerView.VERTICAL, false);
                                    rcvLocation.setLayoutManager(layoutManager);
                                    locationAdapter = new LocationAdapter(BloodRequestActivity.this, locationListResponse.getAreaResponseList());
                                    rcvLocation.setAdapter(locationAdapter);

                                    locationAdapter.SetUpInterFace(new LocationAdapter.LocationClick() {
                                        @Override
                                        public void SetLocation(LocationListResponse.AreaResponse listLocation) {
                                            etLocation.setText(listLocation.getAreaName()+ " " +listLocation.getCity()+ " "  +listLocation.getState() + " " +listLocation.getPincode());
                                            rcvLocation.setVisibility(View.GONE);

                                        }
                                    });
                                   *//* ArrayAdapter<LocationListResponse.Pincode> adapter = new ArrayAdapter<>(BloodRequestActivity.this, android.R.layout.simple_spinner_item, locationListResponse.getPincodes());
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerLocationSuggest.setAdapter(adapter);
                                    spinnerData.equals("Suggested locations: " + locationListResponse.getPincodes().toString());*//*

                                }//Toast.makeText(BloodRequestActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }*/

    private void showDatePickerDialog() {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                BloodRequestActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDay);
                        if (selectedDate.getTimeInMillis() >= currentDate.getTimeInMillis()) {
                            String formattedDate = new SimpleDateFormat("EEE MMM-dd-yyyy", Locale.getDefault()).format(selectedDate.getTime());
                            txDate.setText(formattedDate);
                        }
                    }
                }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
        datePickerDialog.show();
    }
}