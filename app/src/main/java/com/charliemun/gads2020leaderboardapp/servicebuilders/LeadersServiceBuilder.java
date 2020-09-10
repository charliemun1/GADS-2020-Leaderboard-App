package com.charliemun.gads2020leaderboardapp.servicebuilders;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeadersServiceBuilder {
    private static final String URL = "https://gadsapi.herokuapp.com/api/";

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S buildService(Class<S> serviceType) {
        return retrofit.create(serviceType);
    }
}