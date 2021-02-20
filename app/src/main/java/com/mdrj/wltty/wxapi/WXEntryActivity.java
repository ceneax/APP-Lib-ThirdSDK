package com.mdrj.wltty.wxapi;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import ceneax.lib.thirdsdk.ThirdSDK;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ThirdSDK.getIWxAPI() != null) {
            // 注册回调
            ThirdSDK.getIWxAPI().handleIntent(getIntent(), this);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        // 这里可以获取登录请求的参数
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Bundle bundle = new Bundle();

        if (!(baseResp instanceof SendAuth.Resp)) {
            finish();
            return;
        }

        switch (baseResp.errCode) {
            // 成功
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        bundle.putBoolean("state", true);
                        bundle.putString("openId", ((SendAuth.Resp) baseResp).code);
                        // 这里要请求get
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        bundle.putBoolean("state", true);
                        break;
                }
                break;
            // 失败
            default:
                bundle.putBoolean("state", false);
                bundle.putString("errMsg", baseResp.errStr);
                bundle.putInt("errCode", baseResp.errCode);
                break;
        }

        // 发送广播
        sendBroadcast(getIntent().putExtras(bundle));

        finish();
    }

}
