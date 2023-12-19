package com.example.blooddonatehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ContributeActivity extends AppCompatActivity {
    ImageView imgBack,imgLower,imgUpper;
    TextView txTheory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);

        imgBack=findViewById(R.id.imgBack);
        imgLower=findViewById(R.id.imgLower);
        txTheory=findViewById(R.id.txTheory);
        imgUpper=findViewById(R.id.imgUpper);

        imgLower.setVisibility(View.GONE);
        txTheory.setVisibility(View.GONE);
        imgUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgLower.setVisibility(View.VISIBLE);
                if (txTheory.getVisibility() == View.VISIBLE) {
                    txTheory.setVisibility(View.GONE);
                    imgLower.setVisibility(View.GONE);
                    imgUpper.setVisibility(View.VISIBLE);
                } else {
                    txTheory.setVisibility(View.VISIBLE);
                }
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}