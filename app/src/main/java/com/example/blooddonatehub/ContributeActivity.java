package com.example.blooddonatehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blooddonatehub.Utils.Tools;

public class ContributeActivity extends AppCompatActivity {
    ImageView imgBack,imgUpDown1;
    TextView txTheory;
    Tools tools;
    LinearLayout clickLy;

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
        setContentView(R.layout.activity_contribute);

        imgBack = findViewById(R.id.imgBack);
        txTheory = findViewById(R.id.txTheory);
        clickLy = findViewById(R.id.clickLy);
        tools=new Tools(this);
        tools.ScreenshotBlock(getWindow());
        imgUpDown1 = findViewById(R.id.imgUpDown1);

        txTheory.setVisibility(View.GONE);

        clickLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickData(v);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
    boolean hideShow;
    public void onClickData(View view) {
        if (!hideShow) {
            txTheory.setText("\n" +
                    "Blood donation and financial contributions are vital for healthcare." +
                    " Donating blood saves lives, while monetary support fuels research and facilities." +
                    " Your generosity contributes to a healthier community and supports medical advancements.");
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

