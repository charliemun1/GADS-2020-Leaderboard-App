package com.charliemun.gads2020leaderboardapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charliemun.gads2020leaderboardapp.R;
import com.charliemun.gads2020leaderboardapp.adapters.SkillIQLeadersAdapter;
import com.charliemun.gads2020leaderboardapp.models.SkillIQLeadersModel;
import com.charliemun.gads2020leaderboardapp.servicebuilders.LeadersServiceBuilder;
import com.charliemun.gads2020leaderboardapp.services.SkillIQLeadersService;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillIQLeadersFragment extends Fragment {
    private List<SkillIQLeadersModel> skillIQModelList;
    private SkillIQLeadersAdapter skillIQAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_skill_iq_leaders, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.skill_iq_recycler);
        skillIQModelList = new ArrayList<>();

        skillIQAdapter = new
                SkillIQLeadersAdapter(getContext(), skillIQModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(skillIQAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSkillIQLeaders();
    }

    private void loadSkillIQLeaders() {
        SkillIQLeadersService skillIQLeadersService = LeadersServiceBuilder.buildService(SkillIQLeadersService.class);
        Call<List<SkillIQLeadersModel>> request = skillIQLeadersService.getSkillIQLeaders();

        request.enqueue(new Callback<List<SkillIQLeadersModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<SkillIQLeadersModel>> request, @NonNull Response<List<SkillIQLeadersModel>> response) {
                if(response.isSuccessful() && response.body() != null){
                    skillIQModelList.addAll(response.body());
                    Collections.sort(skillIQModelList, new Comparator<SkillIQLeadersModel>() {
                        @Override
                        public int compare(SkillIQLeadersModel lhs, SkillIQLeadersModel rhs) {
                            return Integer.compare( rhs.getScore(),lhs.getScore());
                        }
                    });

                    skillIQAdapter.notifyDataSetChanged();
                } else {
                    displaySnackbar("Failed to retrieve items");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SkillIQLeadersModel>> request, @NonNull Throwable t) {
                if (t instanceof IOException){
                    displaySnackbar("A connection error occurred");
                } else {
                    displaySnackbar("Failed to retrieve items \n" + t.getMessage());
                }
            }
        });
    }

    private void displaySnackbar(String message){
        Snackbar.make(Objects.requireNonNull(getView()), message,
                Snackbar.LENGTH_LONG).show();
    }

}