package ru.geekbrains.javabackendatretrofit.dto.utils;

import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@UtilityClass
public class RetrofitUtil {

    public Retrofit getRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new PrettyLogger());

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
                .baseUrl(ConfigUtils.getBaseUrl())
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
