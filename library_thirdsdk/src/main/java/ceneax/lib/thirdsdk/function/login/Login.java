package ceneax.lib.thirdsdk.function.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;


import java.util.Random;

import ceneax.lib.thirdsdk.ThirdSDK;
import ceneax.lib.thirdsdk.bean.LoginBean;

public class Login {

    private static final String BROADCAST_RECEIVER_CALLBACK_ACTION = "third_sdk_login_wx_broadcast_receiver_callback_action";

    /**
     * QQ登录
     */
    public static void QQ(Activity activity, ILoginCallback loginCallback) {
        QQ(activity, loginCallback, "get_simple_userinfo");
    }
    /**
     * QQ登录
     */
    public static void QQ(Activity activity, ILoginCallback loginCallback, String scope) {
        QQ(activity, loginCallback, scope, false);
    }
    /**
     * QQ登录
     */
    public static void QQ(Activity activity, ILoginCallback loginCallback, String scope, boolean qrCode) {
        if (ThirdSDK.getTencent() == null) {
            return;
        }

        // 未安装QQ
        if (!ThirdSDK.getTencent().isQQInstalled(activity)) {
            loginCallback.onFail("未安装QQ", -1);
            return;
        }

        ThirdSDK.getTencent().login(activity, scope, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                loginCallback.onSuccess(new LoginBean());
            }

            @Override
            public void onError(UiError uiError) {
                loginCallback.onFail(uiError.errorMessage + " " + uiError.errorDetail, uiError.errorCode);
            }

            @Override
            public void onCancel() {}

            @Override
            public void onWarning(int i) {}
        }, qrCode);
    }

    /**
     * 微信登录
     */
    public static void wechat(Activity activity, ILoginCallback loginCallback) {
        wechat(activity,  loginCallback, "snsapi_userinfo");
    }
    /**
     * 微信登录
     */
    public static void wechat(Activity activity, ILoginCallback loginCallback, String scope) {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = scope;
        req.state = "wechat_sdk_login_" + new Random().nextInt(10);

        if (ThirdSDK.getIWxAPI() == null) {
            return;
        }

        // 未安装微信
        if (!ThirdSDK.getIWxAPI().isWXAppInstalled()) {
            loginCallback.onFail("未安装微信", -1);
            return;
        }

        // 接收 WXEntryActivity 回调
        ThirdSDK.onWXLoginCallback = new ThirdSDK.OnWXLoginCallback() {
            @Override
            public void onFinish(Bundle bundle) {
                if (bundle == null) {
                    loginCallback.onFail("unknown error", -1);
                    return;
                }

                if (!bundle.getBoolean("state")) {
                    loginCallback.onFail(bundle.getString("errMsg"), bundle.getInt("errCode"));
                    return;
                }

                loginCallback.onSuccess(new LoginBean(bundle.getString("access_token"),
                        Integer.parseInt(bundle.getString("expires_in")),
                        bundle.getString("refresh_token"),
                        bundle.getString("openId"),
                        bundle.getString("scope"),
                        bundle.getString("unionid")));
            }
        };

        ThirdSDK.getIWxAPI().sendReq(req);
    }

}