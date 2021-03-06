package com.whiteandc.capture.clients;

import android.content.SharedPreferences;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whiteandc.capture.data.Monument;
import com.whiteandc.capture.data.MonumentLoader;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by BLANCA on 23/02/2016.
 */
public class CityManagerClient {
    private OkHttpClient client = new OkHttpClient();

    private static final String MONUMENTS_URL= "http://192.168.0.103:8080/monument";

    public void getMonuments(final SharedPreferences sharedpreferences) throws IOException {
        Request request = new Request.Builder()
                .url(MONUMENTS_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = response.body().string();

                //JSON from String to Object
                List<Monument> monuments = mapper.readValue(jsonInString, new TypeReference<List<Monument>>(){});
                for (Monument monument:monuments) {
                    MonumentLoader.addMonument(sharedpreferences, monument);
                }
            }
        });
    }
}
