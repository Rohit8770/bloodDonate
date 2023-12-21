package com.example.blooddonatehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blooddonatehub.Adapter.PersonRelationAdapter;
import com.example.blooddonatehub.Adapter.PosterAdapter;
import com.example.blooddonatehub.Fragment.DonateMargeActivity;
import com.example.blooddonatehub.Model.AllPersonRelationDataModel;
import com.example.blooddonatehub.Model.PersonRelationDataModel;
import com.example.blooddonatehub.Model.PosterDataModel;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class BloodHomeActivity extends AppCompatActivity {

    CardView cardRequestBlood, cardDonateBlood, cardContributeBlood;
    RecyclerView rcvBloodGroup,rcvBloodType;
    PosterAdapter posterAdapter;
    boolean flag = true;
    ImageView imgPoster;
    PersonRelationAdapter personRelationAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int currentPosition = 1;
    private DotsIndicator dotsIndicator;
    ImageView tvNoData;
    TextView tvNoDataFound;
    LinearLayout indicatorLayout;

   /* ImageView imgSteps;
    int pos = 0;
    private Runnable autoProgressRunnable;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_home);

        cardRequestBlood = findViewById(R.id.cardRequestBlood);
        cardDonateBlood = findViewById(R.id.cardDonateBlood);
        cardContributeBlood = findViewById(R.id.cardContributeBlood);
        rcvBloodGroup = findViewById(R.id.rcvBloodGroup);
        rcvBloodType = findViewById(R.id.rcvBloodType);
        tvNoData = findViewById(R.id.tvNoData);
        tvNoDataFound = findViewById(R.id.tvNoDataFound);
        indicatorLayout = findViewById(R.id.indicator_layout);

        tvNoDataFound.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);




        cardContributeBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BloodHomeActivity.this, ContributeActivity.class));
            }
        });



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
        if (getMyData1().isEmpty()) {
            tvNoDataFound.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoDataFound.setVisibility(View.GONE);
            tvNoData.setVisibility(View.GONE);
        }

        startAutoScroll();

    }

    private List<PosterDataModel> getMyData() {
        List<PosterDataModel> myDataModels = new ArrayList<>();
        myDataModels.add(new PosterDataModel( "https://i.postimg.cc/2Shdfsdk/My.jpg"));
        myDataModels.add(new PosterDataModel( "https://i.postimg.cc/Y9Bq0cfb/landing-page-template-world-book-day-celebration-23-2150184554.jpg"));
        myDataModels.add(new PosterDataModel("https://i.postimg.cc/YCq4Bmt8/1000-F-305868293-Yprj4a-HIGhlxkaw-Hovb-UE7y-K4-My-J1-LXP.jpg"));
        myDataModels.add(new PosterDataModel( "https://i.postimg.cc/05nJQqcV/Connexia-firma-world-blood-donor-day-1170-jpg.webp"));
        myDataModels.add(new PosterDataModel("https://i.postimg.cc/KjPFsFjC/grocery-shopping-1.webp"));
     //   myDataModels.add(new PosterDataModel("KR$ALL G7", "Your Favorite", "Local Shop Online", "Groceries More delivered", "Download Now" , "https://img.freepik.com/free-vector/flat-vertical-poster-template-world-blood-donor-day_23-2150334098.jpg?size=626&ext=jpg&ga=GA1.1.1458931086.1702550423&semt=ais"));
        myDataModels.add(new PosterDataModel("https://i.postimg.cc/Bnj9HhmD/Untitled-design-1-2-1.png"));

        return myDataModels;
    }
    private List<PersonRelationDataModel> getMyData1() {
        List<PersonRelationDataModel> personRelationDataModelList = new ArrayList<>();
        personRelationDataModelList.add(new PersonRelationDataModel("Rohit Malviya","4","Sarkej ahemdabad near by thaltej metro station (India)","monday nov 5","Critical","A+"));
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
                int itemCount = posterAdapter.getItemCount();

                if (currentPosition < itemCount - 1) {
                    currentPosition++;
                } else {
                    currentPosition = 0;
                }

                rcvBloodType.smoothScrollToPosition(currentPosition);
                handler.postDelayed(this, 3000);
            }
        }, 3000);
    }


   /* private void startAutoScroll() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int itemCount = posterAdapter.getItemCount();

                if (flag){
                    if (currentPosition < itemCount) {
                        currentPosition++;
                    } else {
                        currentPosition--;
                        flag = false;
                    }

                }
                else {
                    if (itemCount > 1) {
                        currentPosition--;
                    } else {
                        currentPosition++;
                        flag = true;
                    }
                }
                rcvBloodType.smoothScrollToPosition(currentPosition);
                handler.postDelayed(this, 3000);
            }
        }, 3000);
    }*/






}