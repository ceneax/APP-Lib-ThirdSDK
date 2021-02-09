package ceneax.lib.thirdsdk.module;

import android.content.Context;

public interface IBaseModule<T> {

    T create(Context context);

    ModuleEnum getType();

}