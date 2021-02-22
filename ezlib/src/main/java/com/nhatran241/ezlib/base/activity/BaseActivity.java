package com.nhatran241.ezlib.base.activity;

import android.content.DialogInterface;
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


public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {
    private LoadingDialog loadingDialog;
    private AlertDialog alertDialog;
    private T baseViewModel;

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

    protected abstract void onMessageDialogNegativeClick(String title);

    protected abstract void onMessageDialogPositiveClick(String title);

    protected abstract void onMessageDialogNeutralClick(String title);

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
