package com.example.blooddonatehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.example.blooddonatehub.Adapter.PersonRelationAdapter;
import com.example.blooddonatehub.Adapter.PosterAdapter;
import com.example.blooddonatehub.Fragment.DonateMargeActivity;
import com.example.blooddonatehub.Model.AllPersonRelationDataModel;
import com.example.blooddonatehub.Model.PersonRelationDataModel;
import com.example.blooddonatehub.Model.PosterDataModel;

import java.util.ArrayList;
import java.util.List;

public class BloodHomeActivity extends AppCompatActivity {

    CardView cardRequestBlood, cardDonateBlood, cardContributeBlood;
    RecyclerView rcvBloodGroup,rcvBloodType;
    PosterAdapter posterAdapter;
    PersonRelationAdapter personRelationAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_home);

        cardRequestBlood = findViewById(R.id.cardRequestBlood);
        cardDonateBlood = findViewById(R.id.cardDonateBlood);
        cardContributeBlood = findViewById(R.id.cardContributeBlood);
        rcvBloodGroup = findViewById(R.id.rcvBloodGroup);
        rcvBloodType = findViewById(R.id.rcvBloodType);

        cardContributeBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BloodHomeActivity.this, ContributeActivity.class));
            }
        });
        cardDonateBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BloodHomeActivity.this, DonateMargeActivity.class));
            }
        });

        cardRequestBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BloodHomeActivity.this, BloodRequestActivity.class));
            }
        });

        rcvBloodType.setLayoutManager(new LinearLayoutManager(BloodHomeActivity.this,RecyclerView.HORIZONTAL,false));
        posterAdapter = new PosterAdapter(getMyData(), BloodHomeActivity.this);
        rcvBloodType.setAdapter(posterAdapter);

        rcvBloodGroup.setLayoutManager(new LinearLayoutManager(BloodHomeActivity.this,RecyclerView.HORIZONTAL,false));
        personRelationAdapter = new PersonRelationAdapter(getMyData1(),BloodHomeActivity.this);
        rcvBloodGroup.setAdapter(personRelationAdapter);

        startAutoScroll();

    }

    private List<PosterDataModel> getMyData() {
        List<PosterDataModel> myDataModels = new ArrayList<>();
        myDataModels.add(new PosterDataModel("KR$ALL", "Your Favorite", "Local Shop Online", "Groceries More delivered", "Download Now" , "https://st2.depositphotos.com/16122460/47721/i/600/depositphotos_477215468-stock-photo-courier-holding-box-assortment-exotic.jpg"));
        myDataModels.add(new PosterDataModel("Blood_@G_15", "Local Shop Online", "Groceries More delivered", "Your Favorite", "Download Now" , "https://st2.depositphotos.com/1005233/8401/i/600/depositphotos_84010300-stock-photo-attractive-pharmacist-clean-the-store.jpg"));
        myDataModels.add(new PosterDataModel("Donation_G#_15", "Your Favorite", "Local Shop Online", "Groceries More delivered", "Download Now", "https://static8.depositphotos.com/1031551/817/i/380/depositphotos_8175565-stock-photo-blood-from-the-blood-donation.jpg"));
        myDataModels.add(new PosterDataModel("Blood_%G_15", "Local Shop Online", "Your Favorite", "Groceries More delivered", "Download Now", "https://st2.depositphotos.com/3591429/10776/i/380/depositphotos_107766826-stock-photo-diverse-people-holding-hands.jpg"));
        myDataModels.add(new PosterDataModel("KR$ALL G7", "Your Favorite", "Local Shop Online", "Groceries More delivered", "Download Now" , "https://img.freepik.com/free-vector/flat-vertical-poster-template-world-blood-donor-day_23-2150334098.jpg?size=626&ext=jpg&ga=GA1.1.1458931086.1702550423&semt=ais"));
        myDataModels.add(new PosterDataModel("Donation*_15", "Local Shop Online", "Your Favorite", "Groceries More delivered", "Download Now", "https://st2.depositphotos.com/1017986/8169/i/380/depositphotos_81693086-stock-photo-female-hands-holding-red-heart.jpg"));

        return myDataModels;
    }
    private List<PersonRelationDataModel> getMyData1() {
        List<PersonRelationDataModel> personRelationDataModelList = new ArrayList<>();
        personRelationDataModelList.add(new PersonRelationDataModel("Rohit Malviya","4 unit","Sarkej ahemdabad near by thaltej metro station (India)","monday nov 5","Critical","A+"));
        personRelationDataModelList.add(new PersonRelationDataModel("Gourav Sharma","2 unit","Sarkej ahemdabad near by thaltej metro station (India)","wednesday feb 1","Critical","B+"));
        personRelationDataModelList.add(new PersonRelationDataModel("Vishal Raghuwanshi","6 unit","Sarkej ahemdabad near by thaltej metro station (India)","monday jan 8","No","AB+"));
        personRelationDataModelList.add(new PersonRelationDataModel("Anand Patel","1 unit","Sarkej ahemdabad near by thaltej metro station (India)","saturday july 22","Critical","O-"));
        personRelationDataModelList.add(new PersonRelationDataModel("Nikhil Roy","7 unit","Sarkej ahemdabad near by thaltej metro station (India)","sunday dec 12","No","A1+"));
        return personRelationDataModelList;
    }

    private void startAutoScroll() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPosition < posterAdapter.getItemCount() - 1) {
                    currentPosition++;
                } else {
                    currentPosition = 0;
                }
                rcvBloodType.smoothScrollToPosition(currentPosition);
                handler.postDelayed(this, 3000); // 3000 milliseconds = 3 seconds
            }
        }, 3000);
    }
}