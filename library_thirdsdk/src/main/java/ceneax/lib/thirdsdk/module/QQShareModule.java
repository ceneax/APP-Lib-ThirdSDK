package ceneax.lib.thirdsdk.module;

import android.content.Context;

import com.tencent.tauth.Tencent;

import ceneax.lib.thirdsdk.enums.ModuleEnum;

public class QQShareModule implements IBaseModule<Tencent> {

    private final String[] args;

    public QQShareModule(String... args) {
        this.args = args;
    }

    @Override
    public Tencent create(Context context) {
        return Tencent.createInstance(args[0], context);
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.QQShare;
    }

}