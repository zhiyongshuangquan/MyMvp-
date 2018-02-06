package com.xfc.mymvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xfc.mymvp.base.MainView;
import com.xfc.mymvp.base.MvpActivity;
import com.xfc.mymvp.presenter.MainPresenter;
import com.xfc.mymvp.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xfc on 2018/2/3.
 */

public class LoginActivity extends MvpActivity<MainPresenter> implements MainView {
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void getDataSuccess(Object object) {

    }

    @Override
    public void getDataFail(String msg) {
        ToastUtil.showToast("123");
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onView() {
        initToolBarAsHome("MVP+Retrofit+Rxjava");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
    }
}
