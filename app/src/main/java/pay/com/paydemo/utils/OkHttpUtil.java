package pay.com.paydemo.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/3/8.
 */
public class OkHttpUtil {
    //下载用的httpclient
    private static final OkHttpClient mDownloadOkHttpClient = new OkHttpClient.Builder().
            connectTimeout(600, TimeUnit.SECONDS).readTimeout(600, TimeUnit.SECONDS).build();

    private static final OkHttpClient mOkHttpClient = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).build();

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 该不会开启异步线程。
     *
     * @param request
     * @return
     */
    public static Response execute(Request request) throws Exception {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 该不会开启异步线程。
     *
     * @param request
     * @return
     */
    public static Response downloadExecute(Request request) throws Exception {
        return mDownloadOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void downloadEnqueue(Request request, Callback responseCallback) {
        mDownloadOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     *
     * @param request
     */
    public static void enqueue(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

            }
        });
    }

    public static String getStringFromServer(String url) throws Exception {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new Exception("Unexpected code " + response);
        }
    }

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     *
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }


}
