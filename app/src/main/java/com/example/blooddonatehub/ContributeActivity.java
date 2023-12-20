package com.example.blooddonatehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContributeActivity extends AppCompatActivity {
    ImageView imgBack,imgUpDown1;
    TextView txTheory;

    LinearLayout lytQ1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);

        imgBack = findViewById(R.id.imgBack);
        txTheory = findViewById(R.id.txTheory);
        lytQ1 = findViewById(R.id.lytQ1);
        imgUpDown1 = findViewById(R.id.imgUpDown1);

        txTheory.setVisibility(View.GONE);

        lytQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickData(v);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    boolean hideShow;
    public void onClickData(View view) {
        if (!hideShow) {
            txTheory.setText("You can create a Reservation by Selecting Create a New Reservation. " +
                    "Select Desired Date and time Slot and Check the Availability for the rooms and " +
                    "Select any from the Room List.");
            txTheory.setVisibility(View.VISIBLE);
            hideShow = true;
            imgUpDown1.setImageResource(R.drawable.expend_more_icon);
        } else {
            txTheory.setVisibility(View.GONE);
            hideShow = false;
            imgUpDown1.setImageResource(R.drawable.less_expend);
        }
    }
}

