package com.xfc.mymvp.retrofit;

import android.content.Context;

import com.xfc.mymvp.utils.StringUtil;
import com.xfc.mymvp.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by xfc on 2018/1/29.
 * 用于和服务器进行统一判断处理
 */

public class ServerOperation {
    private ResponseBody response;
    private Context context;

    public ServerOperation(ResponseBody response) {
        this.response = response;
    }

    public ServerOperation(Context context, ResponseBody response) {
        this.context = context;
        this.response = response;
    }

    public String setSeverflag() {
        try {
            if (response != null) {
                String back = response.string();
                if (!back.equals("") && back != null) {
                    if (back.indexOf("code") > 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(back).getJSONObject("data");
                            String code = jsonObject.getString("code");
                            if (code.equals("0")) {
                                return back;
                            } else if (code.equals("2")) {
                                ToastUtil.showToast("请重新登录账号");
                                return back;
                            } else {
                                String msg = jsonObject.getString("msg");
                                if (StringUtil.isnotEmpty(msg)) {
                                    ToastUtil.showToast(msg);
                                } else {
                                    ToastUtil.showToast("请求失败");
                                }
                                return "";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtil.showToast("数据异常");
                            return "";
                        }
                    } else {
                        if (back.indexOf("ret") > 0) {
                            try {
                                JSONObject jsonObject = new JSONObject(back);
                                String ret = jsonObject.getString("ret").trim();
                                if (ret.equals("400")) {
                                    ToastUtil.showToast("参数传递错误");
                                } else if (ret.equals("500")) {
                                    ToastUtil.showToast("服务器内部错误");
                                } else {
                                    ToastUtil.showToast("未知异常");
                                }
                                return "";
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtil.showToast("数据解析异常");
                            return "";
                        }
                    }
                }
            }
        } catch (IOException e) {
            ToastUtil.showToast("数据异常");
            e.printStackTrace();
            return "";
        }
        return "";
    }
}
