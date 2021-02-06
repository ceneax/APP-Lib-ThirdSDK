package ceneax.lib.thirdsdk.module;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import ceneax.lib.thirdsdk.enums.ModuleEnum;

public class WechatShareModule implements IBaseModule<IWXAPI> {

    private final String[] args;

    public WechatShareModule(String... args) {
        this.args = args;
    }

    @Override
    public IWXAPI create(Context context) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, args[0], true);
        iwxapi.registerApp(args[0]);
        return iwxapi;
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.WechatShare;
    }

}
