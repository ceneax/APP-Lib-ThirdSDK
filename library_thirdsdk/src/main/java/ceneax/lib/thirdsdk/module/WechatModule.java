package ceneax.lib.thirdsdk.module;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import ceneax.lib.thirdsdk.ThirdSDK;

public class WechatModule implements IBaseModule<IWXAPI> {

    private final String appId;
    private final String secret;

    public WechatModule(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    @Override
    public IWXAPI create(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("appId", appId);
        map.put("secret", secret);
        ThirdSDK.argumentMap.put(ModuleEnum.Wechat.name(), map);

        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, appId, true);
        iwxapi.registerApp(appId);
        return iwxapi;
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.Wechat;
    }

}
