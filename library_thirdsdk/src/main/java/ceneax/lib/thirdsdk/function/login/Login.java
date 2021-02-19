package ceneax.lib.thirdsdk.function.login;

import android.app.Activity;

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
            public void onComplete(Object o) { }

            @Override
            public void onError(UiError uiError) { }

            @Override
            public void onCancel() { }

            @Override
            public void onWarning(int i) { }
        }, qrCode);
    }

    /**
     * 微信登录
     */
    public static void Wechat(Activity activity, String scope, ILoginCallback loginCallback) {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = scope;
        req.state = "wechat_sdk_login_" + new Random().nextInt(10);

        if (ThirdSDK.getIWxAPI() == null) {
            return;
        }

        ThirdSDK.getIWxAPI().handleIntent(activity.getIntent(), new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) { }

            @Override
            public void onResp(BaseResp baseResp) { }
        });
        ThirdSDK.getIWxAPI().sendReq(req);
    }

}