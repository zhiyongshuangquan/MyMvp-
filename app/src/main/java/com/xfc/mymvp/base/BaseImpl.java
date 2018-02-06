package com.xfc.mymvp.base;

import io.reactivex.disposables.Disposable;

/**
 * Created by xfc
 * github:https://github.com/zhiyongshuangquan/
 * 个人博客：http://blog.csdn.net/qq_35115643
 */
public interface BaseImpl {

    boolean addRxStop(Disposable disposable);

    boolean addRxDestroy(Disposable disposable);

    void remove(Disposable disposable);
}
