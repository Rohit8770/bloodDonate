package com.example.blooddonatehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imgSteps;
    int pos = 0;
    private Handler handler = new Handler();
    private Runnable autoProgressRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgSteps = findViewById(R.id.imgSteps);
        updateImage();
        autoProgressRunnable = new Runnable() {
            @Override
            public void run() {
                pos++;
                if (pos > 6) {
                    pos = 0;
                }
                updateImage();
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(autoProgressRunnable, 2000);

    }
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoProgressRunnable);
    }
    void updateImage(){
        if (pos == 0) {
            imgSteps.setImageResource(R.drawable.hand_icon);
        }if (pos == 1) {
            imgSteps.setImageResource(R.drawable.saving_money_icon);
        } else if (pos == 2) {
            imgSteps.setImageResource(R.drawable.blood_donation_icon);
        } else if (pos == 3) {
            imgSteps.setImageResource(R.drawable.mobile_icon);
        } else if (pos == 4) {
            imgSteps.setImageResource(R.drawable.location_share_icon);
        } else if (pos == 5) {
            imgSteps.setImageResource(R.drawable.wallet_icon);
        } else if (pos == 6) {
            imgSteps.setImageResource(R.drawable.blood_donate_icon);
        }
    }
}