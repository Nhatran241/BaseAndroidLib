package com.nhatran241.ezlib.base.fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nhatran241.ezlib.base.BaseViewModel;

public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {
    private T baseViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        baseViewModel = initViewModel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(baseViewModel != null){
            baseViewModel.onDestroy();
        }
    }

    protected abstract T initViewModel();
}
