package no.hist.aitel.android.utils;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtil {

    private static final String TAG = HttpUtil.class.getSimpleName();
    private static final DefaultHttpClient httpClient = new DefaultHttpClient();

    public static String getBody(String url) {
        HttpGet method = new HttpGet(url);
        String body = null;
        try {
            HttpResponse response = httpClient.execute(method);
            body = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            Log.d(TAG, "IOException", e);
        }
        return body;
    }

}
