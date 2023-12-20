package com.example.blooddonatehub;

import static com.example.blooddonatehub.Utils.Tools.isValidEmail;
import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonatehub.Fragment.ConditionAgreeMentFragment;
import com.example.blooddonatehub.Response.BloodRequestListResponse;
import com.example.blooddonatehub.Response.LocationListResponse;
import com.example.blooddonatehub.Utils.VariableBag;
import com.example.blooddonatehub.network.RestClient;
import com.example.blooddonatehub.network.Restcall;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class BloodRequestActivity extends AppCompatActivity {


    RelativeLayout txCalender;
    Button txSubmitRequest;
    TextView txDate,txAgreeMentCondition;
    ImageView imgBack;
    String bloodGroupSp="";
    String bloodTypeSp="";
    String bloodUnitsSp="";
    CheckBox checkBoxAgree;
    AppCompatSpinner bloodTypeSpinner,bloodGroupSpinner,bloodUnitSpinner;
    SwitchCompat switchCritical;
    TextInputEditText etName,etMobileNumber,etLocation,etDescription;
    Restcall restcall;


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
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);


        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationCall();
            }
        });

        bloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroupSp = parent.getSelectedItem().toString();}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});

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


                if (name.isEmpty()) {
                    etName.setError("Name is required");
                }else if (mobileNumber.isEmpty()) {
                    etMobileNumber.setError("Mobile number is required");
                }else if (date.isEmpty()) {
                    txDate.setError("Date is required");
                } else if (location.isEmpty()) {
                    etLocation.setError("Location is required");
                }
                else if (bloodType.isEmpty()) {

                } else if (bloodGroup.equals("Select Blood Group")) {

                }else if (bloodUnit.equals("Select Blood Unit")) {

                } else if (!checkBoxChecked) {
                    Toast.makeText(BloodRequestActivity.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                }else {
                    RequestForBloodCall();
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

        restcall.CallRequestForBlood("blood_donation",bloodTypeSp,bloodGroupSp,
                 etName.getText().toString(),
                        etMobileNumber.getText().toString(),
                        txDate.getText().toString(),
                        bloodUnitsSp,switchCritical.getText().toString(),
                        etDescription.getText().toString(),
                        etLocation.getText().toString())
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
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(BloodRequestActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(BloodRequestListResponse bloodRequestListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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

    private void LocationCall() {

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
                                  && locationListResponse.getPincodes() != null && locationListResponse.getPincodes().size() > 0){

                                    etLocation.setText("");

                                }
                                Toast.makeText(BloodRequestActivity.this, locationListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

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