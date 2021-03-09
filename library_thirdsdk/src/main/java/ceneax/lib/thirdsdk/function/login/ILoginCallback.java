package ceneax.lib.thirdsdk.function.login;

import ceneax.lib.thirdsdk.bean.LoginBean;

public interface ILoginCallback {

    void onSuccess(LoginBean loginBean);

    void onFail(String msg, int code);

}
