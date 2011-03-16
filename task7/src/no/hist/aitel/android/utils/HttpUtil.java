package no.hist.aitel.android.utils;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class HttpUtil {

    private static final String TAG = HttpUtil.class.getSimpleName();

    public static String postFile(String url, String path) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(url);

        String body = null;
        try {
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            entity.addPart("", new FileBody(new File(path)));
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            body = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
        return body;
    }

}
