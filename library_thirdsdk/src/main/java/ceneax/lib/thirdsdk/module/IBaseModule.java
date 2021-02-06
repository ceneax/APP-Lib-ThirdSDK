package ceneax.lib.thirdsdk.module;

import android.content.Context;

import ceneax.lib.thirdsdk.enums.ModuleEnum;

public interface IBaseModule<T> {

    T create(Context context);

    ModuleEnum getType();

}