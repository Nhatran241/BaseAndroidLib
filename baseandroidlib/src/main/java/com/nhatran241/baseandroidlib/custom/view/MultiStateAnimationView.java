package com.nhatran241.baseandroidlib.custom.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieListener;
import com.nhatran241.baseandroidlib.R;
import com.nhatran241.baseandroidlib.helper.ResourceHelper;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MultiStateAnimationView extends CardView {
    public static final int STATUS_READY = 0;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_LOADED = 2;
    private static int STATUS_CURRENT = -1;
    private AppCompatImageView imageView;
    private int readyId,loadingId,loadedId;
    private int readyMinFrame,readyMaxFrame,loadingMinFrame,loadingMaxFrame,loadedMinFrame,loadedMaxFrame;
    private boolean readyMinMaxAfterFirstLoop,loadingMinMaxAfterFirstLoop,loadedMinMaxAfterFirstLoop;
    private boolean shadowOnReady,shadowOnLoading,shadowOnLoaded,readyLoop,loadingLoop, loadedLoop;
    private boolean readyAutoPlay,loadingAutoPlay,loadedAutoPlay;
    private float elevation,maxElevation,radius;

    private CustomDrawable readyDrawable = null;
    private CustomDrawable loadingDrawable = null;
    private CustomDrawable loadedDrawable = null;

    public MultiStateAnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs,context);
    }

    public MultiStateAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,context);
    }

    public MultiStateAnimationView(Context context) {
        super(context);
        init(null,context);
    }


    private void init(AttributeSet attrs, Context context) {
        imageView = new AppCompatImageView(context);
        setBackgroundColor(Color.TRANSPARENT);
        addView(imageView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateAnimationView);

        readyId = ta.getResourceId(R.styleable.MultiStateAnimationView_mav_readyDrawable, -1);
        loadingId = ta.getResourceId(R.styleable.MultiStateAnimationView_mav_loadingDrawable, -1);
        loadedId = ta.getResourceId(R.styleable.MultiStateAnimationView_mav_loadedDrawable, -1);

        readyMinFrame = ta.getInt(R.styleable.MultiStateAnimationView_mav_readyMinFrame, -1);
        loadingMinFrame = ta.getInt(R.styleable.MultiStateAnimationView_mav_loadingMinFrame, -1);
        loadedMinFrame = ta.getInt(R.styleable.MultiStateAnimationView_mav_loadedMinFrame, -1);

        readyMaxFrame = ta.getInt(R.styleable.MultiStateAnimationView_mav_readyMaxFrame, Integer.MAX_VALUE);
        loadingMaxFrame = ta.getInt(R.styleable.MultiStateAnimationView_mav_loadingMaxFrame, Integer.MAX_VALUE);
        loadedMaxFrame = ta.getInt(R.styleable.MultiStateAnimationView_mav_loadedMaxFrame, Integer.MAX_VALUE);

        readyMinMaxAfterFirstLoop = ta.getBoolean(R.styleable.MultiStateAnimationView_mav_readyMinMaxFrameAfterFirstLoop,false);
        loadingMinMaxAfterFirstLoop = ta.getBoolean(R.styleable.MultiStateAnimationView_mav_loadingMinMaxFrameAfterFirstLoop,false);
        loadedMinMaxAfterFirstLoop = ta.getBoolean(R.styleable.MultiStateAnimationView_mav_loadedMinMaxFrameAfterFirstLoop,false);

        shadowOnReady=ta.getBoolean(R.styleable.MultiStateAnimationView_mav_shadowOnReady,false);
        shadowOnLoading=ta.getBoolean(R.styleable.MultiStateAnimationView_mav_shadowOnLoading,false);
        shadowOnLoaded=ta.getBoolean(R.styleable.MultiStateAnimationView_mav_shadowOnLoaded,false);

        readyLoop=ta.getBoolean(R.styleable.MultiStateAnimationView_mav_readyLoop,false);
        loadingLoop=ta.getBoolean(R.styleable.MultiStateAnimationView_mav_loadingLoop,false);
        loadedLoop =ta.getBoolean(R.styleable.MultiStateAnimationView_mav_loadedLoop,false);

        readyAutoPlay=ta.getBoolean(R.styleable.MultiStateAnimationView_mav_readyAutoPlay,false);
        loadingAutoPlay=ta.getBoolean(R.styleable.MultiStateAnimationView_mav_loadingAutoPlay,false);
        loadedAutoPlay =ta.getBoolean(R.styleable.MultiStateAnimationView_mav_loadedAutoPlay,false);

        elevation=ta.getDimension(R.styleable.MultiStateAnimationView_mav_elevation,0f);
        maxElevation=ta.getDimension(R.styleable.MultiStateAnimationView_mav_maxElevation,elevation);
        radius=ta.getDimension(R.styleable.MultiStateAnimationView_mav_cornerRadius,0f);

        ta.recycle();
        loadResource(context);
    }

    public void setReadyMinMaxFrame(int min, int max){
        readyMinFrame = min;
        readyMaxFrame = max;
        if(readyDrawable != null){
            readyDrawable.setMinMaxFrame(min, max);
        }
    }

    public void moveReadyToFrame(int frame){
        if(readyDrawable != null){
            readyDrawable.moveToFrame(frame);
        }
    }
    private void loadResource(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(readyId != -1){
                    readyDrawable = new CustomDrawable(context,readyId);
                }
                if(loadingId != -1){
                    loadingDrawable = new CustomDrawable(context,loadingId);
                }
                if(loadedId != -1){
                    loadedDrawable = new CustomDrawable(context,loadedId);
                }
                post(new Runnable() {
                    @Override
                    public void run() {
                        moveReadyState();
                    }
                });
            }
        }).start();


    }
    public int getStatusCurrent(){
        return STATUS_CURRENT;
    }
    public void startAnimation(){
        switch (STATUS_CURRENT){
            case STATUS_READY : {
                if(readyDrawable != null) {
                    readyDrawable.startAnimation();
                }
                break;
            }
            case STATUS_LOADING : {
                if(loadingDrawable != null) {
                    loadingDrawable.startAnimation();
                }
                break;
            }
            case STATUS_LOADED : {
                if(loadedDrawable != null) {
                    loadedDrawable.startAnimation();
                }
                break;
            }
        }
    }
    public void resumeAnimation(){
        switch (STATUS_CURRENT){
            case STATUS_READY : {
                if(readyDrawable != null) {
                    readyDrawable.resumeAnimation();
                }
                break;
            }
            case STATUS_LOADING : {
                if(loadingDrawable != null) {
                    loadingDrawable.resumeAnimation();
                }
                break;
            }
            case STATUS_LOADED : {
                if(loadedDrawable != null) {
                    loadedDrawable.resumeAnimation();
                }
                break;
            }
        }
    }
    public void pauseAnimation(){
        switch (STATUS_CURRENT){
            case STATUS_READY : {
                if(readyDrawable != null) {
                    readyDrawable.pauseAnimation();
                }
                break;
            }
            case STATUS_LOADING : {
                if(loadingDrawable != null) {
                    loadingDrawable.pauseAnimation();
                }
                break;
            }
            case STATUS_LOADED : {
                if(loadedDrawable != null) {
                    loadedDrawable.pauseAnimation();
                }
                break;
            }
        }
    }
    public void cancelAnimtion(){
        switch (STATUS_CURRENT){
            case STATUS_READY : {
                if(readyDrawable != null) {
                    readyDrawable.cancelAnimation();
                }
                break;
            }
            case STATUS_LOADING : {
                if(loadingDrawable != null) {
                    loadingDrawable.cancelAnimation();
                }
                break;
            }
            case STATUS_LOADED : {
                if(loadedDrawable != null) {
                    loadedDrawable.cancelAnimation();
                }
                break;
            }
        }
    }
    private void showShadow(Boolean b){
        if(b){
            setRadius(radius);
            setCardElevation(elevation);
            setMaxCardElevation(maxElevation);
        }else {
            setRadius(0f);
            setCardElevation(0f);
            setMaxCardElevation(0f);
        }
    }
    public void addLoadingAnimatorListener(Animator.AnimatorListener animatorListener){
        if(loadingDrawable != null){
            loadingDrawable.addAnimatorListener(animatorListener);
        }

    }
    public void movePreviousState(){
        if(STATUS_CURRENT == STATUS_READY){
            STATUS_CURRENT = STATUS_LOADED;
        }else {
            STATUS_CURRENT--;
        }
        setImage();
    }
    public void moveReadyState(){
        STATUS_CURRENT = STATUS_READY;
        setImage();
    }
    public void moveLoadingState(){
        STATUS_CURRENT = STATUS_LOADING;
        setImage();
    }
    public void moveLoadedState(){
        STATUS_CURRENT = STATUS_LOADED;
        setImage();
    }

    private void setImage() {
        switch (STATUS_CURRENT){
            case STATUS_READY : {
                if(readyDrawable == null) {
                    moveNextState();
                    break;
                }
                if(loadingDrawable != null) {
                    loadingDrawable.cancelAnimation();
                }
                if(loadedDrawable != null){
                    loadedDrawable.cancelAnimation();
                }
                if(readyAutoPlay) {
                    readyDrawable.startAnimation();
                }
                readyDrawable.setMinMaxFrameAfterLoopCount(readyMinMaxAfterFirstLoop,readyMinFrame,readyMaxFrame);
                readyDrawable.setAnimationLoop(readyLoop);
                showShadow(shadowOnReady);
                imageView.setImageDrawable(readyDrawable.getDrawable());
                break;
            }
            case STATUS_LOADING : {
                if(loadingDrawable == null) {
                    moveNextState();
                    break;
                }
                if(readyDrawable != null) {
                    readyDrawable.cancelAnimation();
                }
                if(loadedDrawable != null){
                    loadedDrawable.cancelAnimation();
                }
                if(loadingAutoPlay) {
                    loadingDrawable.startAnimation();
                }
                loadingDrawable.setMinMaxFrameAfterLoopCount(loadingMinMaxAfterFirstLoop,loadingMinFrame,loadingMaxFrame);
                loadingDrawable.setAnimationLoop(loadingLoop);
                showShadow(shadowOnLoading);
                imageView.setImageDrawable(loadingDrawable.getDrawable());
                break;
            }
            case STATUS_LOADED : {
                if(loadedDrawable == null) {
                    moveNextState();
                    break;
                }
                if(loadingDrawable != null) {
                    loadingDrawable.cancelAnimation();
                }
                if(readyDrawable != null){
                    readyDrawable.cancelAnimation();
                }
                if(loadedAutoPlay) {
                    loadedDrawable.startAnimation();
                }
                loadedDrawable.setMinMaxFrameAfterLoopCount(loadedMinMaxAfterFirstLoop,loadedMinFrame,loadedMaxFrame);
                loadedDrawable.setAnimationLoop(loadedLoop);
                showShadow(shadowOnLoaded);
                imageView.setImageDrawable(loadedDrawable.getDrawable());
                break;
            }
        }
    }
    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }
    public void moveNextState(){
        if(STATUS_CURRENT == STATUS_LOADED){
            STATUS_CURRENT = STATUS_READY;
        }else {
            STATUS_CURRENT++;
        }
        setImage();
    }
    private void setBackgroundResource(final CustomDrawable drawable, boolean shadow) {
        if (drawable == null)
            return;
        post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageDrawable(drawable.getDrawable());
                showShadow(true);
            }
        });
    }
    private LottieDrawable createLottieDrawable(int id) {
        final LottieDrawable lottieDrawable = new LottieDrawable();
        LottieCompositionFactory.fromRawRes(getContext(), id).addListener(new LottieListener<LottieComposition>() {
            @Override
            public void onResult(LottieComposition result) {
                lottieDrawable.setComposition(result);
            }
        });

        return lottieDrawable;
    }

    private class CustomDrawable {
        private LottieDrawable lottieDrawable = null;
        private Drawable normalDrawable = null;
        public CustomDrawable(Context context, int id) {
            if (ResourceHelper.checkTypeId(context, id, ResourceHelper.TYPE_RAW)) {
                lottieDrawable = createLottieDrawable(id);
            } else
                normalDrawable = ResourceHelper.getDrawable(context, id);
        }
        public Drawable getDrawable() {
            return isLottie() ? lottieDrawable : normalDrawable;
        }
        public boolean isLottie() {
            return lottieDrawable != null && normalDrawable == null;
        }
        public void cancelAnimation(){
            if(lottieDrawable != null){
                lottieDrawable.cancelAnimation();
            }
        }
        public void setAnimationLoop(boolean loop){
            if(lottieDrawable != null){
                lottieDrawable.setRepeatCount(loop ? LottieDrawable.INFINITE : 0);
            }
        }
        public void setMinMaxFrame(int min , int max){
            if(lottieDrawable != null){
                lottieDrawable.setMinAndMaxFrame(min,max);
            }
        }
        public void moveToFrame(int frame){
            if(lottieDrawable != null){
                lottieDrawable.setFrame(frame);
            }
        }
        public void addAnimatorListener(Animator.AnimatorListener animationListener){
            if(lottieDrawable != null){
                lottieDrawable.addAnimatorListener(animationListener);
            }
        }
        public void setMinMaxFrameAfterLoopCount(boolean setAfterFirstLoop, final int min , final int max){
            if(lottieDrawable != null){
                if(setAfterFirstLoop) {
                    lottieDrawable.addAnimatorListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            super.onAnimationRepeat(animation);
                            lottieDrawable.setMinAndMaxFrame(min, max);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            super.onAnimationCancel(animation);
                            lottieDrawable.setMinFrame(0);
                            lottieDrawable.setMaxFrame(Integer.MAX_VALUE);
                        }
                    });
                }else {
                    lottieDrawable.setMinAndMaxFrame(min,max);
                }
                lottieDrawable.addAnimatorListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);
                        Log.d("nhatnhat", "onAnimationRepeat: ");
                    }
                });
            }
        }
        public void pauseAnimation(){
            if(lottieDrawable != null){
                lottieDrawable.pauseAnimation();
            }
        }
        public void resumeAnimation(){
            if(lottieDrawable != null){
                lottieDrawable.resumeAnimation();
            }
        }
        public void startAnimation(){
            if(lottieDrawable != null){
                lottieDrawable.playAnimation();
            }
        }

    }

}