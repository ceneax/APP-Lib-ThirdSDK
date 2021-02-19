package ceneax.lib.thirdsdk.module;

import android.content.Context;

import com.tencent.tauth.Tencent;

public class QQModule implements IBaseModule<Tencent> {

    private final String appId;

    public QQModule(String appId) {
        this.appId = appId;
    }

    @Override
    public Tencent create(Context context) {
        return Tencent.createInstance(appId, context);
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.QQ;
    }

}