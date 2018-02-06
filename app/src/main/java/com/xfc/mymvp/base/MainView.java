package com.xfc.mymvp.base;

/**
 * Created by xfc
 * github:https://github.com/zhiyongshuangquan/
 * 个人博客：http://blog.csdn.net/qq_35115643
 */
public interface MainView extends BaseView {

    void getDataSuccess(Object object);

    void getDataFail(String msg);

}
