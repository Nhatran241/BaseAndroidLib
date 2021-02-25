package com.nhatran241.ezlib.base.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nhatran241.ezlib.base.BaseViewModel;
import com.nhatran241.ezlib.base.fragment.BaseFragment;
import com.nhatran241.ezlib.custom.dialog.LoadingDialog;

import java.util.HashMap;
import java.util.Map;


public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {
    private LoadingDialog loadingDialog;
    private AlertDialog alertDialog;
    private T baseViewModel;
    private Map<String, BroadcastReceiver> broadcastReceiverList;

    public void registerBroadcast(String broadcastTag, IntentFilter intentFilter) {
        if(broadcastReceiverList == null){
            broadcastReceiverList = new HashMap<>();
        }
        if(broadcastReceiverList.get(broadcastTag) != null){
            unregisterReceiver(broadcastReceiverList.get(broadcastTag));
        }
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onBroadcastReceiver(intent, broadcastTag);
            }
        };
        broadcastReceiverList.put(broadcastTag, broadcastReceiver);
        registerReceiver(broadcastReceiver, intentFilter);
    }
    public void unregisterBroadcast(String broadcastTag){
        if(broadcastReceiverList == null){
            return;
        }
        if(broadcastReceiverList.get(broadcastTag) != null){
            unregisterReceiver(broadcastReceiverList.get(broadcastTag));
        }
    }

    private void onBroadcastReceiver(Intent intent, String broadcastTag) {
    }

    public void showLoading(boolean hidePercentView) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.hidePercentView(hidePercentView);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void showMessage(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Message").setMessage(message).show();
    }

    public void updateLoadingMaxProgress(int maxProgress) {
        loadingDialog.setMaxProgress(maxProgress);
    }

    public void updateLoadingProgress(int progress) {
        if (loadingDialog != null) {
            loadingDialog.updateProgress(progress);
        }
    }

    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showMessageDialog(String title, String message, String positive, String negative, String neutral) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!TextUtils.isEmpty(positive)){
            builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onMessageDialogPositiveClick(title);
                }
            });
        }
        if(!TextUtils.isEmpty(negative)){
            builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onMessageDialogNegativeClick(title);
                }
            });
        }
        if(!TextUtils.isEmpty(neutral)){
            builder.setNeutralButton(neutral, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onMessageDialogNeutralClick(title);
                }
            });
        }
        builder.setTitle(title).setMessage(message);
        alertDialog = builder.create();
        try {
            alertDialog.show();
        } catch (Exception ignored) {

        }
    }

    protected void onMessageDialogNegativeClick(String title) {

    }

    protected void onMessageDialogPositiveClick(String title) {

    }

    protected void onMessageDialogNeutralClick(String title) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseViewModel = initViewModel();
    }

    protected abstract T initViewModel();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baseViewModel != null) {
            baseViewModel.onDestroy();
        }
        if (loadingDialog != null) {
            loadingDialog.cancel();
            loadingDialog = null;
        }
        if (alertDialog != null) {
            alertDialog.cancel();
            alertDialog = null;
        }
        if(broadcastReceiverList != null){
            for(Map.Entry<String, BroadcastReceiver> entry : broadcastReceiverList.entrySet()) {
                unregisterReceiver(entry.getValue());
            }
        }
    }

    public void replaceFragment(BaseFragment baseFragment, int containerId, boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager().beginTransaction().replace(containerId, baseFragment).addToBackStack(String.valueOf(getSupportFragmentManager().getBackStackEntryCount())).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(containerId, baseFragment).commit();
        }
    }

    public void removeFragment(BaseFragment baseFragment) {
        if (baseFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(baseFragment).commit();
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("nhatnhat", "onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    protected abstract void initUI();

    protected abstract void initData();

}
