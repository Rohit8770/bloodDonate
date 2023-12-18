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
import android.widget.ImageView;

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
    ImageView imgPoster;
    PersonRelationAdapter personRelationAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int currentPosition = 0;


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
       // imgPoster = findViewById(R.id.imgPoster);


     /*   imgSteps = findViewById(R.id.imgSteps);
        updateImage();
        autoProgressRunnable = new Runnable() {
            @Override
            public void run() {
                pos++;
                if (pos > 6) {
                    pos = 0;
                }
                updateImage();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(autoProgressRunnable, 5000);

*/

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




   /* protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoProgressRunnable);
    }
    void updateImage(){
        if (pos == 0) {
            imgSteps.setImageResource(R.drawable.poster_image_icon_first);
        }if (pos == 1) {
            imgSteps.setImageResource(R.drawable.poster_image_icon_second);
        } else if (pos == 2) {
            imgSteps.setImageResource(R.drawable.poster_image_icon_third);
        } else if (pos == 3) {
            imgSteps.setImageResource(R.drawable.poster_image_icon_five);
        } else if (pos == 4) {
            imgSteps.setImageResource(R.drawable.poster_image_icon_four);
        } else if (pos == 5) {
            imgSteps.setImageResource(R.drawable.hand_icon);
        } *//*else if (pos == 6) {
            imgSteps.setImageResource(R.drawable.blood_donate_icon);
        }*//*
    }*/
}