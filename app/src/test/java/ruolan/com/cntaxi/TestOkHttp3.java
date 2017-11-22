package ruolan.com.cntaxi;

import android.util.Log;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by wuyinlei on 2017/11/22.
 */

public class TestOkHttp3 {

    @Test
    public void testGet() {
        String url = "http://httpbin.org/get?id=1";
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建request
        Request request = new Request.Builder()
                .url(url)
                .build();
        //执行request
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.print("response : " + response.body().string());
            }
        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Test
    public void testPost() {


        RequestBody body = RequestBody.create(JSON, "{\"name\":\"若兰明月\"}");

        String url = "http://httpbin.org/post";
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建request
        Request request = new Request.Builder()
                .url(url)
                .post(body)  //请求体
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.print("response : " + response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testInterceptor() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                long startTime = System.currentTimeMillis();

                //request获取
                Request request = chain.request();
                HttpUrl url = request.url();
                System.out.println("请求地址 : " + url.toString());
                String method = request.method();
                System.out.println("请求方式 : " + method);

                Response response = chain.proceed(request);

                long endTime = System.currentTimeMillis();

                System.out.println("请求时间 : " + (endTime - startTime));

                return response;
            }
        };


        RequestBody body = RequestBody.create(JSON, "{\"name\":\"若兰明月\"}");

        String url = "http://httpbin.org/post";
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        //创建request
        Request request = new Request.Builder()
                .url(url)
                .post(body)  //请求体
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.print("response : " + response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCache() {

        //创建一个缓存对象
        Cache cache = new Cache(new File("cache.cache"), 1024 * 1024);


        String url = "http://httpbin.org/get?id=1";
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        //创建request
        Request request = new Request.Builder()
                .url(url)
//                .cacheControl(CacheControl.FORCE_NETWORK)  //可以设置强制网络还是缓存中取数据
                .build();
        //执行request
        try {
            Response response = client.newCall(request).execute();
            Response responseCache = response.cacheResponse();
            Response responseNet = response.networkResponse();

            if (response.isSuccessful()) {

                if (responseCache != null) {
                    System.out.print("缓存  response : " + response.body().string());
                } else if (responseNet != null) {
                    System.out.print("网络中 response : " + response.body().string());
                }

            }
        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
