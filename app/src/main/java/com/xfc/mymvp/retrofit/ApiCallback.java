package com.xfc.mymvp.retrofit;


import android.content.Context;
import android.os.Handler;

import com.google.gson.GsonBuilder;
import com.xfc.mymvp.base.BaseImpl;
import com.xfc.mymvp.model.MainModel;
import com.xfc.mymvp.utils.ToastUtil;


import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by xfc
 * github:https://github.com/zhiyongshuangquan/
 * 个人博客：http://blog.csdn.net/qq_35115643
 */
public abstract class ApiCallback implements Observer<ResponseBody> {
    private Class<?> clazz;
    private BaseImpl mBaseImpl;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;

    public ApiCallback(Context context) {
        this.clazz = null;
        mBaseImpl = (BaseImpl) context;
    }

    public ApiCallback(Context context, Class<?> clazz) {
        this.clazz = clazz;
        mBaseImpl = (BaseImpl) context;
    }

    public abstract void onSuccess(Object object);

    public abstract void onFailure(String msg);

    public abstract void onFinish();

    @Override
    public void onSubscribe(Disposable d) {
        if (isAddInStop) {    //  在onStop中取消订阅
            mBaseImpl.addRxStop(d);
        } else { //  在onDestroy中取消订阅
            mBaseImpl.addRxDestroy(d);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            if (e instanceof UnknownHostException || e instanceof ConnectException) {
                ToastUtil.showToast("网络不可用！请检查网络连接！");
            } else if (e instanceof SocketTimeoutException) {
                ToastUtil.showToast("请求超时！");
            } else if (e instanceof SocketException) {
                ToastUtil.showToast("连接服务器失败！请稍后再试！");
            } else if (e instanceof IOException) {
                ToastUtil.showToast("连接服务器失败！请稍后再试！！");
            } else {
                ToastUtil.showToast("连接服务器失败！请稍后再试！");
            }
        }

        onFinish();
    }

    @Override
    public void onNext(ResponseBody response) {
        //ServerOperation serverOperation = new ServerOperation(response);
        String back = null;
        try {
            back = response.string();
            if (back.equals("")) {
                onFailure("");
            } else {
                if (clazz == null) {
                    MainModel user = new GsonBuilder().create().fromJson(back, MainModel.class);
                    onSuccess(user);
                } else {
                    Object object = new GsonBuilder().create().fromJson(back, clazz);
                    onSuccess(object);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
