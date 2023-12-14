package com.example.blooddonatehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class BloodHomeActivity extends AppCompatActivity {

    CardView cardRequestBlood,cardDonateBlood,cardContributeBlood;
    RecyclerView rcvBloodGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_home);

        cardRequestBlood=findViewById(R.id.cardRequestBlood);
        cardDonateBlood=findViewById(R.id.cardDonateBlood);
        cardContributeBlood=findViewById(R.id.cardContributeBlood);
        rcvBloodGroup=findViewById(R.id.rcvBloodGroup);
    }
}