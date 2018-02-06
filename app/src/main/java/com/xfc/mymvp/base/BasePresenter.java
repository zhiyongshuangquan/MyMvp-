package com.xfc.mymvp.base;


import com.xfc.mymvp.retrofit.ApiClient;
import com.xfc.mymvp.retrofit.ApiStores;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by xfc
 * github:https://github.com/zhiyongshuangquan/
 * 个人博客：http://blog.csdn.net/qq_35115643
 */
public class BasePresenter<V> {
    public V mvpView;
    protected ApiStores apiStores;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = ApiClient.getInstance().setForm();
    }

    public void addSubscription(Observable observable, Observer<ResponseBody> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
