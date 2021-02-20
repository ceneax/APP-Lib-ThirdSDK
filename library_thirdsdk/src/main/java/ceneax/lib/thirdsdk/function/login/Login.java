package ceneax.lib.thirdsdk.function.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.util.Random;

import ceneax.lib.thirdsdk.ThirdSDK;

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

        ThirdSDK.getTencent().login(activity, scope, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                loginCallback.onSuccess("");
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

        // 注册广播，接收 WXEntryActivity 回调
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent == null || !intent.getAction().equalsIgnoreCase(BROADCAST_RECEIVER_CALLBACK_ACTION)) {
                    return;
                }

                // 取消注册广播
                activity.unregisterReceiver(this);

                Bundle bundle = intent.getExtras();
                if (bundle == null) {
                    loginCallback.onFail("未知错误", -1);
                    return;
                }

                if (!bundle.getBoolean("state")) {
                    loginCallback.onFail(bundle.getString("errMsg"), bundle.getInt("errCode"));
                    return;
                }

                loginCallback.onSuccess(bundle.getString("openId"));
            }
        }, new IntentFilter(BROADCAST_RECEIVER_CALLBACK_ACTION));

        ThirdSDK.getIWxAPI().sendReq(req);
    }

}