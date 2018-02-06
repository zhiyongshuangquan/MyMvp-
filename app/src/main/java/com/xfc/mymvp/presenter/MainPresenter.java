package com.xfc.mymvp.presenter;


import android.content.Context;

import com.xfc.mymvp.base.BasePresenter;
import com.xfc.mymvp.base.MainView;
import com.xfc.mymvp.config.Config;
import com.xfc.mymvp.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xfc
 * github:https://github.com/zhiyongshuangquan/
 * 个人博客：http://blog.csdn.net/qq_35115643
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void loadDataByRetrofitRxjava(Context context) {
        mvpView.showLoading();
        Map<String, Object> map = new HashMap<>();
        addSubscription(apiStores.getMapRxjava(Config.Login, map), new ApiCallback(context) {
                    @Override
                    public void onSuccess(Object object) {
                        mvpView.getDataSuccess(object);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                }
        );
    }

}
