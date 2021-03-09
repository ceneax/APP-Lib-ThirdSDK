package ceneax.lib.thirdsdk.module;

import android.content.Context;

import com.tencent.tauth.Tencent;

import java.util.HashMap;
import java.util.Map;

import ceneax.lib.thirdsdk.ThirdSDK;

public class QQModule implements IBaseModule<Tencent> {

    private final String appId;

    public QQModule(String appId) {
        this.appId = appId;
    }

    @Override
    public Tencent create(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("appId", appId);
        ThirdSDK.argumentMap.put(ModuleEnum.QQ.name(), map);

        return Tencent.createInstance(appId, context);
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.QQ;
    }

}