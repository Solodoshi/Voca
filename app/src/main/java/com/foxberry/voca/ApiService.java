package com.foxberry.voca;

import com.foxberry.voca.dto.TranslateUnit;
import com.foxberry.voca.dto.TranslateWrapper;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiService {

    interface Api {
        @GET("dicservice.json/lookup")
                Call<TranslateWrapper> getTranslateWrapper(
                @Query("key") String key,
                @Query("lang") String lang,
                @Query("text") String text
        );
        @GET("dicservice.json/getLangs")
        Call<List<String>> getLanguages(
                @Query("key") String key
        );
    }

     public static Api getApiService(){
         HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
         loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
         OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
          httpClient.addInterceptor(loggingInterceptor);

         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("https://dictionary.yandex.net/api/v1/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(httpClient.build())
                 .build();
         Api api = retrofit.create(Api.class);
         return api;
     }
}
