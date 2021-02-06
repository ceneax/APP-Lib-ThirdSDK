package ceneax.lib.thirdsdk.module;

import android.content.Context;

import com.tencent.tauth.Tencent;

import ceneax.lib.thirdsdk.enums.ModuleEnum;

public class WechatShareModule implements IBaseModule<Tencent> {

    private final Context context;
    private final String[] args;

    public WechatShareModule(Context context, String... args) {
        this.context = context;
        this.args = args;
    }

    @Override
    public Tencent create() {
        return Tencent.createInstance(args[0], context);
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.WechatShare;
    }

}
