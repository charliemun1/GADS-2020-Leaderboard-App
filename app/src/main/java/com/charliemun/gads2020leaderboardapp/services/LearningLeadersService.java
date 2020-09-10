package com.charliemun.gads2020leaderboardapp.services;


import com.charliemun.gads2020leaderboardapp.models.LearningLeadersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LearningLeadersService {
    @GET("hours")
    Call<List<LearningLeadersModel>> getLearningLeaders();
}
