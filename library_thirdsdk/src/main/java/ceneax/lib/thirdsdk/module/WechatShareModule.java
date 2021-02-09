package ceneax.lib.thirdsdk.module;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WechatShareModule implements IBaseModule<IWXAPI> {

    private final String appId;

    public WechatShareModule(String appId) {
        this.appId = appId;
    }

    @Override
    public IWXAPI create(Context context) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, appId, true);
        iwxapi.registerApp(appId);
        return iwxapi;
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.WechatShare;
    }

}
