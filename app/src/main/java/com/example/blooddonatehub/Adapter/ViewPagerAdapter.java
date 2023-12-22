package com.example.blooddonatehub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.blooddonatehub.R;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    int image[] = {
            R.drawable.first_poster_icon,
            R.drawable.second_poster_icon,
            R.drawable.third_poster_icon,
            R.drawable.four_poster_icon,
            R.drawable.five_poster_icon,
            R.drawable.six_poster_icon,
    };

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);
        ImageView slideTitleImage = view.findViewById(R.id.titleImage);
        slideTitleImage.setImageResource(image[position]);
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}

