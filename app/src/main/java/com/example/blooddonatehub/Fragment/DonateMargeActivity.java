package com.example.blooddonatehub.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.blooddonatehub.BloodHomeActivity;
import com.example.blooddonatehub.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DonateMargeActivity extends AppCompatActivity {

    TabLayout tab;
    ViewPager2 view;
    FirstDonateFragment firstFragment;
    SecondDonateFragment secondFragment;

    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_marge);

        tab=findViewById(R.id.tab);
        view=findViewById(R.id.view);
        imgBack=findViewById(R.id.imgBack);
        firstFragment=new FirstDonateFragment();
        secondFragment=new SecondDonateFragment();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(DonateMargeActivity.this, BloodHomeActivity.class));
            }
        });
        view.setAdapter(new ViewPagerAdapter(DonateMargeActivity.this));
        new TabLayoutMediator(tab, view, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position==0){
                    tab.setText("Your Blood Group");
                }else {
                    tab.setText("Other Blood Group");
                }
            }
        }).attach();
    }
    public class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity ) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position==0){
                return firstFragment;
            }else {
                return secondFragment;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}