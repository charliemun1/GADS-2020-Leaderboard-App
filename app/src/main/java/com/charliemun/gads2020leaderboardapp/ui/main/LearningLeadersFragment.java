package com.charliemun.gads2020leaderboardapp.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.charliemun.gads2020leaderboardapp.R;
import com.charliemun.gads2020leaderboardapp.adapters.LearningLeadersAdapter;
import com.charliemun.gads2020leaderboardapp.models.LearningLeadersModel;
import com.charliemun.gads2020leaderboardapp.services.LearningLeadersService;
import com.charliemun.gads2020leaderboardapp.servicebuilders.LeadersServiceBuilder;
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

public class LearningLeadersFragment extends Fragment {
    private ArrayList<LearningLeadersModel> learningLeadersModelList;
    private LearningLeadersAdapter learningLeaderAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater
                .inflate(R.layout.fragment_learning_leaders, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.learning_leader_recycler);
        learningLeadersModelList = new ArrayList<>();

        learningLeaderAdapter = new
                LearningLeadersAdapter(getContext(), learningLeadersModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(learningLeaderAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLearningLeaders();
    }


    private void loadLearningLeaders() {
        LearningLeadersService learningLeadersService = LeadersServiceBuilder.buildService(LearningLeadersService.class);
        Call<List<LearningLeadersModel>> request = learningLeadersService.getLearningLeaders();

        request.enqueue(new Callback<List<LearningLeadersModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<LearningLeadersModel>> request, @NonNull Response<List<LearningLeadersModel>> response) {
                if(response.isSuccessful() && response.body() != null){
                    learningLeadersModelList.addAll(response.body());
                    Collections.sort(learningLeadersModelList, new Comparator<LearningLeadersModel>() {
                        @Override
                        public int compare(LearningLeadersModel model, LearningLeadersModel t1) {
                            return Integer.compare(t1.getHours(), model.getHours());
                        }
                    });

                    learningLeaderAdapter.notifyDataSetChanged();
                } else {
                    displaySnackbar("Failed to retrieve items");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LearningLeadersModel>> request, @NonNull Throwable t) {
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