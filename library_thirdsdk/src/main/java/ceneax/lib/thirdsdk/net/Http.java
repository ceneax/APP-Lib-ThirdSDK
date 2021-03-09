package ceneax.lib.thirdsdk.net;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http {

    private static Http instance;

    private final OkHttpClient mOkHttpClient;

    public synchronized static Http getInstance() {
        if (instance == null) {
            instance = new Http();
        }
        return instance;
    }

    private Http() {
        mOkHttpClient = new OkHttpClient();
    }

    public void get(String url, HttpCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (callback != null) {
                    callback.onFail(e.getMessage());
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (callback == null) {
                    return;
                }

                String result = response.body().string();
                try {
                    callback.onSuccess(new JSONObject(result));
                } catch (JSONException e) {
                    callback.onFail(e.getMessage());
                }
            }
        });
    }

    public interface HttpCallback {
        void onSuccess(JSONObject jsonObject);
        void onFail(String msg);
    }

}
