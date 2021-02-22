package com.nhatran241.ezlib.custom.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.nhatran241.ezlib.R;

import java.util.Objects;

public class LoadingDialog extends Dialog {

    private TextView progress;
    private int maxProgress;
    public LoadingDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.library_dialog_progress, null);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress = view.findViewById(R.id.tv_percent);
        setContentView(view);
    }
    @SuppressLint("SetTextI18n")
    public void updateProgress(int progress){
        this.progress.setText( (int) (((maxProgress*1.0 - progress*1.0) / maxProgress) * 100) + "%");
    }
    public void hidePercentView(boolean b){
        if(progress != null){
            progress.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }
    public boolean isHidePercentView(){
        return progress.getVisibility() == View.VISIBLE;
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }
}
