package com.example.blooddonatehub.Utils;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.blooddonatehub.R;

public class Tools {
    Context context;
    private Dialog dialog;

    public Tools(Context context) {
        this.context = context;
        this.dialog = new Dialog(context);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(null);
        dialog.setCancelable(false);
    }

    public static boolean isValidEmail(String str) {
        if (!TextUtils.isEmpty(str)) {
            return Patterns.EMAIL_ADDRESS.matcher(str.toLowerCase()).matches();
        }
        return false;
    }

 /*   public void showLoading() {
        try {
            if (dialog != null) {
                dialog.setContentView(R.layout.loadingdialog);
                dialog.setCancelable(false);
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void stopLoading() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  public static void displayImage(Context context, ImageView img, String urlimg) {

        Glide.with(context).load(urlimg).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.person_image)).into(img);

    }*/

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }






    public void ScreenshotBlock(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }













}