package com.charliemun.gads2020leaderboardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import com.charliemun.gads2020leaderboardapp.services.ProjectSubmissionService;
import com.charliemun.gads2020leaderboardapp.servicebuilders.ProjectSubmissionServiceBuilder;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitProjectActivity extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String githubLink;

    private TextInputEditText inputFirstName;
    private TextInputEditText inputLastName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputGithubLink;

    private AlertDialog confirmationAlertDialog, successAlertDialog, failureAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_submission);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inputFirstName = findViewById(R.id.input_first_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputEmail = findViewById(R.id.input_email);
        inputGithubLink = findViewById(R.id.input_github_link);
        MaterialButton buttonSubmit = findViewById(R.id.button_submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submissionInputValidations();
            }
        });

        confirmationAlertInit();
        successAlertInit();
        failureAlertInit();
    }

    private void submissionInputValidations() {
        firstName = Objects.requireNonNull(inputFirstName.getText()).toString();
        lastName = Objects.requireNonNull(inputLastName.getText()).toString();
        emailAddress = Objects.requireNonNull(inputEmail.getText()).toString();
        githubLink = Objects.requireNonNull(inputGithubLink.getText()).toString();

        String cannotBeBlank = "Input cannot be blank";
        String mustBeValidEmail = "Enter a valid email";

        if (firstName.isEmpty()) {
            inputFirstName.setError(cannotBeBlank);
            return;
        }
        if (lastName.isEmpty()) {
            inputLastName.setError(cannotBeBlank);
            return;
        }
        if (emailAddress.isEmpty()) {
            inputEmail.setError(cannotBeBlank);
            return;
        }
        if (githubLink.isEmpty()) {
            inputGithubLink.setError(cannotBeBlank);
            return;
        }
        if (!(Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())) {
            inputEmail.setError(mustBeValidEmail);
            return;
        }

        confirmationAlertDialog.show();
    }

    private void submitProject() {
        ProjectSubmissionService projectSubmissionService = ProjectSubmissionServiceBuilder.buildService(ProjectSubmissionService.class);
        Call<Void> request = projectSubmissionService
                .submitProject(
                        emailAddress,
                        firstName,
                        lastName,
                        githubLink
                );

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    successAlertDialog.show();
                } else {
                    failureAlertDialog.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                failureAlertDialog.show();
            }
        });
    }

    private void confirmationAlertInit(){
        //Alert Dialog
        Rect displayRectangle = new Rect();
        Window window = SubmitProjectActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                SubmitProjectActivity.this, R.style.CustomDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater
                .from(SubmitProjectActivity.this)
                .inflate(R.layout.dialog_confirm_submission, viewGroup, false);

        view.setMinimumWidth((int) (displayRectangle.width() * 1f));

        MaterialButton approveButton = view.findViewById(R.id.confirm_project_submission_button);
        ImageButton cancelDialog = view.findViewById(R.id.cancel_submission_button);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitProject();
                confirmationAlertDialog.dismiss();
            }
        });
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationAlertDialog.dismiss();
            }
        });
        builder.setView(view);

        confirmationAlertDialog = builder.create();

    }

    private void successAlertInit(){
        Rect displayRectangle = new Rect();
        Window window = SubmitProjectActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                SubmitProjectActivity.this, R.style.CustomDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(SubmitProjectActivity.this)
                .inflate(R.layout.dialog_successful_submission, viewGroup,
                        false);
        view.setMinimumWidth((int) (displayRectangle.width() * 1f));
        builder.setView(view);

        successAlertDialog = builder.create();
    }

    private void failureAlertInit(){
        Rect displayRectangle = new Rect();
        Window window = SubmitProjectActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                SubmitProjectActivity.this, R.style.CustomDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater
                .from(SubmitProjectActivity.this)
                .inflate(R.layout.dialog_unsuccessful_submission, viewGroup, false);
        view.setMinimumWidth((int) (displayRectangle.width() * 1f));
        builder.setView(view);
        failureAlertDialog = builder.create();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}