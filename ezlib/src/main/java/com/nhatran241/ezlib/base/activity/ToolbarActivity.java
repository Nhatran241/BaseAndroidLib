package com.nhatran241.ezlib.base.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.nhatran241.ezlib.base.BaseViewModel;
import com.nhatran241.ezlib.helper.ResourceHelper;

import java.util.Objects;

public abstract class ToolbarActivity<T extends BaseViewModel> extends BaseActivity<T> {


    @Override
    protected void initUI(){
        int toolbarId = getToolbarId();
        if(toolbarId != 0){
           initToolbar(toolbarId);
        }
        boolean isSetTransparentStatusBar = getIsTransparentStatusBar();
        if(isSetTransparentStatusBar){
            initTransparentStatusBar();
        }
    }

    protected void initToolbar(int toolbarId) {
        try {
            Toolbar toolbar = findViewById(toolbarId);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
            int toolbarBackButtonDrawableId = getToolbarBackButtonResourceId();
            if(ResourceHelper.getTypeId(this, toolbarBackButtonDrawableId).equals(ResourceHelper.TYPE_DRAWABLE)){
                getSupportActionBar().setHomeAsUpIndicator(toolbarBackButtonDrawableId);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }catch (Exception e){
            toast(e.getMessage());
        }
    }

    protected void initTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
    }

    protected abstract boolean getIsTransparentStatusBar();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract int getToolbarBackButtonResourceId();

    protected abstract int getToolbarId();

}
