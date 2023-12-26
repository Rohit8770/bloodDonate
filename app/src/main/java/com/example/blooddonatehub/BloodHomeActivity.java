package com.example.blooddonatehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonatehub.Adapter.AllPersonRelationAdapter;
import com.example.blooddonatehub.Adapter.RelationAdapter;
import com.example.blooddonatehub.Adapter.ViewPagerAdapter;
import com.example.blooddonatehub.Fragment.DonateMargeActivity;
import com.example.blooddonatehub.Response.BloodDonateListResponse;
import com.example.blooddonatehub.Utils.Tools;
import com.example.blooddonatehub.Utils.VariableBag;
import com.example.blooddonatehub.network.RestClient;
import com.example.blooddonatehub.network.Restcall;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class BloodHomeActivity extends AppCompatActivity {

    CardView cardRequestBlood, cardDonateBlood, cardContributeBlood;
    RecyclerView rcvBloodGroup;
    RelationAdapter posterAdapter;
    boolean flag = true;
    ImageView imgPoster;
    //AllPersonRelationAdapter allPersonRelationAdapter;

    RelationAdapter relationAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int currentPosition = 1;
    private DotsIndicator dotsIndicator;
    ImageView tvNoData;
    TextView tvNoDataFound;
    LinearLayout indicatorLayout;
    Restcall restcall;
    Tools tools;

    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;


    private boolean isForward = true;
    private final Runnable autoSlideRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = mSLideViewPager.getCurrentItem();
            if (isForward) {
                currentItem += 1;
            } else {
                currentItem -= 1;
            }
            if (currentItem >= viewPagerAdapter.getCount()) {
                currentItem = viewPagerAdapter.getCount() - 2;
                isForward = false;
            } else if (currentItem < 0) {
                currentItem = 1;
                isForward = true;
            }

            mSLideViewPager.setCurrentItem(currentItem, true);
            handler.postDelayed(this, 2000); // Auto slide every 2 seconds
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_home);

        cardRequestBlood = findViewById(R.id.cardRequestBlood);
        cardDonateBlood = findViewById(R.id.cardDonateBlood);
        cardContributeBlood = findViewById(R.id.cardContributeBlood);
        rcvBloodGroup = findViewById(R.id.rcvBloodGroup);
        tools=new Tools(this);
        tools.ScreenshotBlock(getWindow());

        //  rcvBloodType = findViewById(R.id.rcvBloodType);
        tvNoData = findViewById(R.id.tvNoData);
        tvNoDataFound = findViewById(R.id.tvNoDataFound);
        indicatorLayout = findViewById(R.id.indicator_layout);
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

     /*   tvNoDataFound.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);
*/

        GetallBloodgroupCall();
    //    startAutoScroll();


        mSLideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.indicator_layout);
        viewPagerAdapter = new ViewPagerAdapter(this);
        mSLideViewPager.setAdapter(viewPagerAdapter);
        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);
        handler.postDelayed(autoSlideRunnable, 2000);



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

       // rcvBloodType.setLayoutManager(new LinearLayoutManager(BloodHomeActivity.this,RecyclerView.HORIZONTAL,false));
      //  posterAdapter = new PosterAdapter(getMyData(), BloodHomeActivity.this);
    //    rcvBloodType.setAdapter(posterAdapter);

       // startAutoScroll();

    }

 /*   private List<PosterDataModel> getMyData() {
        List<PosterDataModel> myDataModels = new ArrayList<>();
        myDataModels.add(new PosterDataModel( "https://i.postimg.cc/2Shdfsdk/My.jpg"));
        myDataModels.add(new PosterDataModel( "https://i.postimg.cc/Y9Bq0cfb/landing-page-template-world-book-day-celebration-23-2150184554.jpg"));
        myDataModels.add(new PosterDataModel("https://i.postimg.cc/YCq4Bmt8/1000-F-305868293-Yprj4a-HIGhlxkaw-Hovb-UE7y-K4-My-J1-LXP.jpg"));
        myDataModels.add(new PosterDataModel( "https://i.postimg.cc/05nJQqcV/Connexia-firma-world-blood-donor-day-1170-jpg.webp"));
        myDataModels.add(new PosterDataModel("https://i.postimg.cc/KjPFsFjC/grocery-shopping-1.webp"));
        myDataModels.add(new PosterDataModel("https://i.postimg.cc/Bnj9HhmD/Untitled-design-1-2-1.png"));

        return myDataModels;
    }*/

    private void GetallBloodgroupCall() {
        tools.showLoading();
        restcall.GetallBloodgroups("getallBloodgroups")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<BloodDonateListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(BloodHomeActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(BloodDonateListResponse bloodDonateListResponse) {
                       runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (bloodDonateListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {

                                    List<BloodDonateListResponse.GetBloodGroup> filteredList = filterData(bloodDonateListResponse.getGetBloodGroupList(), "A+");

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(BloodHomeActivity.this, RecyclerView.HORIZONTAL, false);
                                    rcvBloodGroup.setLayoutManager(layoutManager);
                                    relationAdapter = new RelationAdapter(BloodHomeActivity.this, filteredList);
                                    rcvBloodGroup.setAdapter(relationAdapter);

                                    if (filteredList.isEmpty()) {
                                        tvNoDataFound.setVisibility(View.VISIBLE);
                                        tvNoData.setVisibility(View.VISIBLE);
                                    } else {
                                        tvNoDataFound.setVisibility(View.GONE);
                                        tvNoData.setVisibility(View.GONE);
                                    }
                                }
                                Toast.makeText(BloodHomeActivity.this, bloodDonateListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    private List<BloodDonateListResponse.GetBloodGroup> filterData(List<BloodDonateListResponse.GetBloodGroup> dataList, String bloodGroup) {
                        List<BloodDonateListResponse.GetBloodGroup> filteredList = new ArrayList<>();
                        for (BloodDonateListResponse.GetBloodGroup item : dataList) {
                            if (item.getBloodGroup().equalsIgnoreCase(bloodGroup)) {
                                filteredList.add(item);
                            }
                        }
                        return filteredList;
                    }

                });
    }


   /* private void startAutoScroll() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int itemCount = relationAdapter.getItemCount();

                if (currentPosition < itemCount - 1) {
                    currentPosition++;
                    rcvBloodGroup.smoothScrollToPosition(currentPosition);
                    handler.postDelayed(this, 3000);
                } else {
                    // If currentPosition is at the last item, pause for a while and then restart from the beginning
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rcvBloodGroup.smoothScrollToPosition(currentPosition);
                            handler.postDelayed(this, 3000);
                        }
                    }, 3000); // Pause for 3 seconds
                }
            }
        }, 3000);
    }*/


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



    public void setUpindicator(int position) {
        dots = new TextView[6];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.active, getApplicationContext().getTheme()));
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setUpindicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoSlideRunnable);
    }


}