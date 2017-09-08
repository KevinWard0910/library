package com.chinaredstar.core.view.pulltorefresh.header;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.chinaredstar.core.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public class PullHeader extends FrameLayout implements PtrUIHandler {

    public PullHeader(@NonNull Context context) {
        super(context);
        init();
    }

    public PullHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullHeader(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.libbase_pull_header, this);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        System.out.println("onUIReset: ");
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        System.out.println("onUIRefreshPrepare: ");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        System.out.println("onUIRefreshBegin: ");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        System.out.println("onUIRefreshComplete: ");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        System.out.println("getCurrentPosY: " + ptrIndicator.getCurrentPosY());
    }
}
