package ceneax.lib.thirdsdk.function.login;

import android.app.Activity;
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
                Log.e("ThirdSDK", "QQ登录 成功");
                Object oo = o;
            }

            @Override
            public void onError(UiError uiError) {
                Log.e("ThirdSDK", "QQ登录 失败: " + uiError.toString());
            }

            @Override
            public void onCancel() {
                Log.e("ThirdSDK", "QQ登录 取消");
            }

            @Override
            public void onWarning(int i) {
                Log.e("ThirdSDK", "QQ登录 警告: " + i);
            }
        }, qrCode);
    }

    /**
     * 微信登录
     */
    public static void wechat(Activity activity) {
        wechat(activity, "snsapi_userinfo");
    }
    /**
     * 微信登录
     */
    public static void wechat(Activity activity, String scope) {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = scope;
        req.state = "wechat_sdk_login_" + new Random().nextInt(10);

        if (ThirdSDK.getIWxAPI() == null) {
            return;
        }

        ThirdSDK.getIWxAPI().sendReq(req);
    }

}