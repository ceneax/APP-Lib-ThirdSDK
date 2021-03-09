package ceneax.lib.thirdsdk.actvity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

import ceneax.lib.thirdsdk.ThirdSDK;
import ceneax.lib.thirdsdk.module.ModuleEnum;
import ceneax.lib.thirdsdk.net.Http;

public class ThirdWXEntryActivity extends Activity implements IWXAPIEventHandler {

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
        if (!(baseResp instanceof SendAuth.Resp) || ThirdSDK.onWXLoginCallback == null) {
            finish();
            return;
        }

        Bundle bundle = new Bundle();

        switch (baseResp.errCode) {
            // 成功
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        getOpenId((SendAuth.Resp) baseResp, bundle);
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        bundle.putBoolean("state", true);
                        finishOwn(bundle);
                        break;
                }
                break;
            // 失败
            default:
                bundle.putBoolean("state", false);
                bundle.putString("errMsg", baseResp.errStr);
                bundle.putInt("errCode", baseResp.errCode);
                finishOwn(bundle);
                break;
        }
    }

    private void finishOwn(Bundle bundle) {
        ThirdSDK.onWXLoginCallback.onFinish(bundle);
        finish();
    }

    private void getOpenId(SendAuth.Resp resp, Bundle bundle) {
        Map<String, String> argument = Objects.requireNonNull(ThirdSDK.argumentMap.get(ModuleEnum.Wechat.name()));

        Http.getInstance().get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + argument.get("appId") +
                        "&secret=" + argument.get("secret") + "&code=" + resp.code + "&grant_type=authorization_code",
                new Http.HttpCallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            bundle.putString("openId", jsonObject.getString("openid"));
                            bundle.putString("access_token", jsonObject.getString("access_token"));
                            bundle.putString("expires_in", jsonObject.getString("expires_in"));
                            bundle.putString("refresh_token", jsonObject.getString("refresh_token"));
                            bundle.putString("scope", jsonObject.getString("scope"));
                            bundle.putString("unionid", jsonObject.getString("unionid"));
                            bundle.putBoolean("state", true);
                        } catch (JSONException e) {
                            bundle.putBoolean("state", false);
                            bundle.putString("errMsg", e.getMessage());
                            bundle.putInt("errCode", -1);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finishOwn(bundle);
                            }
                        });
                    }

                    @Override
                    public void onFail(String msg) {
                        bundle.putBoolean("state", false);
                        bundle.putString("errMsg", "openid request failed");
                        bundle.putInt("errCode", -1);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finishOwn(bundle);
                            }
                        });
                    }
                });
    }

}
