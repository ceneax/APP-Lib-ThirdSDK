package ceneax.lib.thirdsdk;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.List;

import ceneax.lib.thirdsdk.module.IBaseModule;

/**
 * @Description: 主类
 * @Date: 2021/2/6 11:59
 * @Author: ceneax
 */
public class ThirdSDK {

    // 建造者对象
    private final Builder builder;

    /**
     * 构造方法，传入建造者对象
     */
    private ThirdSDK(Builder builder) {
        this.builder = builder;
    }

    public void init() {
        List<IBaseModule<?>> tmpList = builder.moduleList;

        for (int i = 0; i < tmpList.size(); i ++) {
            IBaseModule<?> baseModule = tmpList.get(i);

            switch (baseModule.getType()) {
                case QQShare:
                    mTencent = (Tencent) baseModule.create(builder.context);
                    break;
                case WechatShare:
                    break;
            }
        }
    }

    public static class Builder {
        private Context context;
        private final List<IBaseModule<?>> moduleList = new ArrayList<>();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder addModule(IBaseModule<?> module) {
            moduleList.add(module);
            return this;
        }

        public ThirdSDK build() {
            return new ThirdSDK(this);
        }
    }

    // ---------- 第三方SDK对象 ----------
    // QQ
    private static Tencent mTencent;
    // 微信
    private static IWXAPI mIWxAPI;

    public static Tencent getTencent() {
        return mTencent;
    }

    public static IWXAPI getIWxAPI() {
        return mIWxAPI;
    }
    // ---------- 第三方SDK对象 ----------

}
