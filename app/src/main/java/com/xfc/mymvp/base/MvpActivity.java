package com.xfc.mymvp.base;

import android.os.Bundle;


/**
 * Created by xfc
 * github:https://github.com/zhiyongshuangquan/
 * 个人博客：http://blog.csdn.net/qq_35115643
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onView();
    }

    protected abstract P createPresenter();

    /**
     * 加载布局
     */
    protected abstract int getLayoutId();

    /**
     * 初始化操作
     */
    protected abstract void onView();

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }
}
