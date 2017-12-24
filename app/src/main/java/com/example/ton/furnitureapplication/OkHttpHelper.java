package com.example.ton.furnitureapplication;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpHelper {
    private WebServiceURL webServiceURL = new WebServiceURL();
    private String mServerURL;

    public OkHttpHelper(){
        this.mServerURL = webServiceURL.URL_server;
    }

    public String serverRequest(String URL, RequestBody params) {

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        if(params == null){
            params = new FormBody.Builder().build();
        }

        Request request = new Request.Builder()
                .url(mServerURL+URL)
                .post(params)
                .build();

        okhttp3.Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e("QUERY STATUS",e.toString());
            return "ERROR";
        }
    }
}
