package pay.com.paydemo.utils;

import android.text.TextUtils;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    public final static String APP_CODE = "kjb_android";
    public final static String APP_CODE_OFFLINE = "kjb_android_offline";


    public static String httpPost(String url, String entity) {
        if (url == null || url.length() == 0) {
            return null;
        }
        try {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, entity);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = OkHttpUtil.execute(request);
            if (response.isSuccessful()) {
                String data = response.body().string();
                if(!TextUtils.isEmpty(data))
                    return data;
                else
                    return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
