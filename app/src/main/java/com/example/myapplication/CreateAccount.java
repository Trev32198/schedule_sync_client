package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

public class CreateAccount extends AppCompatActivity {

    EditText enterUserName;
    EditText enterPassword;
    EditText enterPasswordAgain;
    EditText enterSecurityQuestion;
    EditText enterSecurityAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        enterUserName = findViewById(R.id.enterUserName);
        enterPassword = findViewById(R.id.enterPassword);
        enterPasswordAgain = findViewById(R.id.enterPasswordAgain);
        enterSecurityQuestion = findViewById(R.id.enterSecurityQuestion);
        enterSecurityAnswer = findViewById(R.id.enterSecurityAnswer);


    }

    public void createAccountFunction(View view) {
        if (TextUtils.isEmpty(enterUserName.getText())) {
            enterUserName.setError("Username is required");
        }
        if (TextUtils.isEmpty((enterPassword.getText()))) {
            enterPassword.setError(("Password is required"));
        }
        if (TextUtils.isEmpty((enterPasswordAgain.getText()))) {
            enterPasswordAgain.setError(("Password is required"));
        }
        if (TextUtils.isEmpty((enterSecurityQuestion.getText()))) {
            enterSecurityQuestion.setError(("Password is required"));
        }
        if (TextUtils.isEmpty((enterSecurityAnswer.getText()))) {
            enterSecurityAnswer.setError(("Password is required"));
        }
        if (enterPassword.getText().equals(enterPasswordAgain.getText())) {
            enterPasswordAgain.setError("Passwords don't match");
        } else {
            System.out.println("OOOPO");

        }
    }



    //Toolbar toolbar = findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);






}
