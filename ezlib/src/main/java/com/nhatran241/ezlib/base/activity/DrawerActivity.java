package com.nhatran241.ezlib.base.activity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.nhatran241.ezlib.base.BaseViewModel;

public abstract class DrawerActivity<T extends BaseViewModel> extends ToolbarActivity<T> {
    private DrawerLayout vDrawerLayout;
    private NavigationView vNavigationView;

    @Override
    protected void initUI() {
        super.initUI();
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        try {
            int drawerLayoutId = getDrawerLayoutId();
            if (drawerLayoutId != 0) {
                vDrawerLayout = findViewById(drawerLayoutId);
            }
            int navigationViewId = getNavigationViewId();
            if (navigationViewId != 0) {
                vNavigationView = findViewById(navigationViewId);
                vNavigationView.setNavigationItemSelectedListener(menuItem -> {
                    onOptionsItemSelected(menuItem);
                    return false;
                });
            }
        } catch (Exception e) {
            toast(e.getMessage());
        }
    }

    protected abstract int getNavigationViewId();

    protected abstract int getDrawerLayoutId();

    public void openDrawer() {
        if (vDrawerLayout != null) {
            vDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void closeDrawer() {
        if (vDrawerLayout != null) {
            vDrawerLayout.closeDrawer(GravityCompat.END);
        }
    }

}
