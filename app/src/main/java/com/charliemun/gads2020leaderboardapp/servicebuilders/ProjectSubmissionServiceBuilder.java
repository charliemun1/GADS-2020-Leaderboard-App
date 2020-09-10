package com.charliemun.gads2020leaderboardapp.servicebuilders;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectSubmissionServiceBuilder {
    private static final String PROJECT_SUBMISSION_URL = "https://docs.google.com/forms/d/e/";

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(PROJECT_SUBMISSION_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S buildService(Class<S> serviceType) {
        return retrofit.create(serviceType);
    }
}