package com.nguyenven299.vpn_easy_one_click.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nguyenven299.vpn_easy_one_click.R;

public class DialogHelper {
    private static DialogHelper instance;

    public static DialogHelper getInstance() {
        if (instance == null)
            instance = new DialogHelper();
        return instance;
    }

    private TextView titleTextView, contentTextView;
    private Button actionDialogButton;
    private ImageView dialogImageView, closeDialogButton;
    private Dialog mDialog;
    private AlertDialog.Builder builder;
    private Activity mActivity;

    public interface iActionDialog {
        void actionOnClick (DialogHelper dialogHelper);
    }

    public void showDialog(Activity activity, String title, String content, int dialogImage, String actionText, iActionDialog iActionDialog) {

        mActivity = activity;
        builder = new AlertDialog.Builder(activity);

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_helper_layout, null);
        builder.setView(view);
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.gravity = Gravity.CENTER;
        mDialog.getWindow().setAttributes(params);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().getAttributes().windowAnimations = R.style.NotificationScreenDialog;

        titleTextView = view.findViewById(R.id.titleDialogTextView);
        contentTextView = view.findViewById(R.id.contentDialogTextView);
        closeDialogButton = view.findViewById(R.id.closeDialogButton);
        actionDialogButton = view.findViewById(R.id.actionDialogButton);
        dialogImageView = view.findViewById(R.id.iconDialogImageView);
        if (dialogImage == 0) {
            dialogImageView.setVisibility(View.GONE);
        }
        Glide.with(dialogImageView).load(dialogImage).into(dialogImageView);
        titleTextView.setText(title);
        contentTextView.setText(content);
        actionDialogButton.setText(actionText);

        actionDialogButton.setOnClickListener((view1)->{
            hideFilter();
            iActionDialog.actionOnClick(this);
        });

        closeDialogButton.setOnClickListener((view1) -> {
            hideFilter();
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.show();
            }
        },100);
    }


    public void hideFilter() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDialog != null && mActivity != null) {
                    if (mDialog.isShowing())
                        mDialog.dismiss();
                } else
                    return;
            }
        }, 100);
    }


}
