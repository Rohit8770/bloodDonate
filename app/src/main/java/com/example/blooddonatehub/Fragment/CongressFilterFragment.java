package com.example.blooddonatehub.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Utils.Tools;

public class CongressFilterFragment extends DialogFragment {

    ImageView ivBackGroundGIF;
    LinearLayout layIdDone;
    Tools tools;
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_congress_filter, container, false);

        tools=new Tools(getContext());
        ivBackGroundGIF = v.findViewById(R.id.ivBackGroundGIF);
        layIdDone = v.findViewById(R.id.layIdDone);

        layIdDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                dismiss();
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        /*Glide.with(this)
                .asGif()
                .load(R.drawable.thank_you_three)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivBackGroundGIF);*/
        return v;
    }
}