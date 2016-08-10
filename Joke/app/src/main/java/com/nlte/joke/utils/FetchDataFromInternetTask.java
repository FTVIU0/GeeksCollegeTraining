package com.nlte.joke.utils;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nlte.joke.bean.Article;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**异步执行操作工具类
 *
 *特点：
 *  实现接口的回调，利于代码的复用
 *
 * NLTE on 2016/8/4 0004 13:22
 */
public class FetchDataFromInternetTask extends AsyncTask<String, Integer, List<Article>> {

    private OnAsyncTaskCompleteListener mAsyncTaskCallbackInterface;

    public void setOnAsyncTaskCallback(OnAsyncTaskCompleteListener asyncTaskCallbackInterface) {
        mAsyncTaskCallbackInterface = asyncTaskCallbackInterface;
    }

    //执行异步操作的回调接口
    public interface OnAsyncTaskCompleteListener {
        void asyncFinishCallback(List<Article> articles);
    }

    //开始执行异步操作
    @Override
    protected List<Article> doInBackground(String... strings) {

        //访问网络，获取返回的json数据
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(strings[0])
                .build();
        Response response;
        String strJson = "";
        try {
            response = okHttpClient.newCall(request).execute();
            strJson = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //对返回的数据进行解析
        Gson gson = new Gson();
        //TypeToken，它是gson提供的数据类型转换器，可以支持各种数据集合类型转换
        List<Article> articles = gson.fromJson(strJson,
                new TypeToken<List<Article>>() {
                }.getType());

        return articles;
    }

    //异步操作执行完毕
    @Override
    protected void onPostExecute(List<Article> articles) {
        super.onPostExecute(articles);
        if (mAsyncTaskCallbackInterface != null) {
            //接口回调
            mAsyncTaskCallbackInterface.asyncFinishCallback(articles);
        }
    }
}
