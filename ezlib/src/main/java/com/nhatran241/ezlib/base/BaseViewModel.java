package com.nhatran241.ezlib.base;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel<T extends BaseViewModel.BaseListener> {

    private WeakReference<T> weakReference;

    protected BaseViewModel(T listener){
        weakReference = new WeakReference<>(listener);
    }

    protected T getListener(){
        return weakReference.get();
    }

    public void onDestroy() {
        if(weakReference != null){
            weakReference.clear();
            weakReference = null;
        }
    }

    public interface BaseListener{
        void onShowLoading();
        void onHideLoading();
        void onShowMessage(String title, String message);
    }
}
