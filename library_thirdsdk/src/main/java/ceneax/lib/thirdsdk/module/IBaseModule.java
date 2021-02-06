package ceneax.lib.thirdsdk.module;

import ceneax.lib.thirdsdk.enums.ModuleEnum;

public interface IBaseModule<T> {

    T create();

    ModuleEnum getType();

}