package ceneax.lib.thirdsdk.function.login;

public interface ILoginCallback {

    void onSuccess(String openId);

    void onFail(String msg, int code);

}
