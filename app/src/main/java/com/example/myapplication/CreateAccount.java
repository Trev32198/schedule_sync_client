package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class CreateAccount extends AppCompatActivity {

    EditText enterUserName;
    EditText enterPassword;
    EditText enterPasswordAgain;
    EditText enterSecurityQuestion;
    EditText enterSecurityAnswer;



    ClientCommunicator cc = new ClientCommunicator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


    }




    public void createAccountFunction(View view) {
        enterUserName = findViewById(R.id.enterUserName);
        enterPassword = findViewById(R.id.enterPassword);
        enterPasswordAgain = findViewById(R.id.enterPasswordAgain);
        enterSecurityQuestion = findViewById(R.id.enterSecurityQuestion);
        enterSecurityAnswer = findViewById(R.id.enterSecurityAnswer);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

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

            if (cc.createAccount(enterUserName.getText().toString(), enterPassword.toString(),
                    enterSecurityQuestion.toString(), enterSecurityAnswer.toString())) {
                startActivity(new Intent(CreateAccount.this, HomeScreen.class));
                CreateAccount.this.finish();
            } else {
                enterUserName.setError("Account Creation Failed");
            }

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Toolbar toolbar = findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);






}
