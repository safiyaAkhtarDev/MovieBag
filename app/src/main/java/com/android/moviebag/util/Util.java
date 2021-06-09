package com.android.moviebag.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.android.moviebag.R;
import com.android.volley.toolbox.StringRequest;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    private static Dialog dialog;

    public static void showProgressDialog(Context context_dilog) {
        // check privious dialog
        dialog = new Dialog(context_dilog);
        if (dialog != null || dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable((new ColorDrawable(android.graphics.Color.TRANSPARENT)));
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void hideProgressDialog() {
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String dateFormat(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(date);

            format = new SimpleDateFormat("MMM dd,yyyy");
            return format.format(newDate);
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    public static void errorDialog(Context context, String title, String text) {
        MaterialDialog mDialog = new MaterialDialog.Builder((AppCompatActivity) context)
                .setTitle(title)
                .setMessage(text)
                .setCancelable(false)
                .setAnimation(R.raw.error)
                .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();

                    }
                })

                .build();

        // Show Dialog
        mDialog.show();
    }

    public static void noInternetDialog(Context context) {
        MaterialDialog mDialog = new MaterialDialog.Builder((AppCompatActivity) context)
                .setTitle("Oh, No Internet!")
                .setMessage("Please Check your Internet Connection. And Try Again Later.")
                .setCancelable(false)
                .setAnimation(R.raw.nointernet)
                .setNegativeButton("Ok", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }
}
