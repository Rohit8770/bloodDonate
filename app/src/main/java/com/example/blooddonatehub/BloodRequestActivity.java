package com.example.blooddonatehub;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonatehub.Fragment.ConditionAgreeMentFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BloodRequestActivity extends AppCompatActivity {


    RelativeLayout txCalender;
    TextView txUnits,txSubmitRequest;
    TextView txDate,txAgreeMentCondition;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);

        txCalender=findViewById(R.id.txCalender);
        txDate=findViewById(R.id.txDate);
        imgBack=findViewById(R.id.imgBack);
        txSubmitRequest=findViewById(R.id.txSubmitRequest);
        txAgreeMentCondition=findViewById(R.id.txAgreeMentCondition);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BloodRequestActivity.this, "Request Send Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        txCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
      /*  txUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnitsDialog();
            }
        });*/

        txAgreeMentCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showAgreementDialog();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ConditionAgreeMentFragment fragmentFilter = new ConditionAgreeMentFragment();
                fragmentFilter.show(fragmentTransaction, "#tag");
                fragmentFilter.setCancelable(false);
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

   /* private void showUnitsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.units_item_file, null);
        LinearLayout unitsLayout = dialogView.findViewById(R.id.unitsLayout);
        final AlertDialog dialog = builder.create();

        unitsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedData = ((TextView) ((LinearLayout) v).getChildAt(0)).getText().toString();

                Log.d("Debug", "Selected Data: " + selectedData);
                txUnits.setText(selectedData);
                dialog.dismiss();
            }
        });

        builder.setView(dialogView);
        builder.setCancelable(false);
        dialog.show();
    }*/




}