package com.charliemun.gads2020leaderboardapp.services;


import com.charliemun.gads2020leaderboardapp.models.SkillIQLeadersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SkillIQLeadersService {
    @GET("skilliq")
    Call<List<SkillIQLeadersModel>> getSkillIQLeaders();
}
