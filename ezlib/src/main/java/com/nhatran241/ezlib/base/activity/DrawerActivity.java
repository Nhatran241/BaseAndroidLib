package com.nhatran241.ezlib.base.activity;

import android.view.MenuItem;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.nhatran241.ezlib.base.BaseViewModel;

public abstract class DrawerActivity<T extends BaseViewModel> extends ToolbarActivity<T> {
    private DrawerLayout vDrawerLayout;
    private boolean isSetAutoCloseDrawerLayoutOnClick;
    @Override
    protected void initUI() {
        super.initUI();
        initDrawerLayout();
    }

    public void setSetAutoCloseDrawerLayoutOnClick(boolean isSetAutoCloseDrawerLayoutOnClick){
        this.isSetAutoCloseDrawerLayoutOnClick = isSetAutoCloseDrawerLayoutOnClick;
    }

    private void initDrawerLayout() {
        int drawerLayoutId = getDrawerLayout();
        if(drawerLayoutId != 0){
            try {
                vDrawerLayout = findViewById(drawerLayoutId);
            }catch (Exception e){
                toast(e.getMessage());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(isSetAutoCloseDrawerLayoutOnClick){
            hideNavigationDrawer();
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract int getDrawerLayout();


    public void showNavigationDrawer() {
        if (vDrawerLayout != null) {
            vDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void hideNavigationDrawer() {
        if (vDrawerLayout != null) {
            vDrawerLayout.closeDrawer(GravityCompat.END);
        }
    }

}
