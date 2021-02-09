package ceneax.lib.thirdsdk.module;

import android.content.Context;

import com.tencent.tauth.Tencent;

public class QQShareModule implements IBaseModule<Tencent> {

    private final String appId;

    public QQShareModule(String appId) {
        this.appId = appId;
    }

    @Override
    public Tencent create(Context context) {
        return Tencent.createInstance(appId, context);
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.QQShare;
    }

}