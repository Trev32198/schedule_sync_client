package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


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

        enterUserName = findViewById(R.id.enterUserName);
        enterPassword = findViewById(R.id.enterPassword);
        enterPasswordAgain = findViewById(R.id.enterPasswordAgain);
        enterSecurityQuestion = findViewById(R.id.enterSecurityQuestion);
        enterSecurityAnswer = findViewById(R.id.enterSecurityAnswer);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }




    public void createAccountFunction(View view) {
        enterUserName = findViewById(R.id.enterUserName);
        enterPassword = findViewById(R.id.enterPassword);
        enterPasswordAgain = findViewById(R.id.enterPasswordAgain);
        enterSecurityQuestion = findViewById(R.id.enterSecurityQuestion);
        enterSecurityAnswer = findViewById(R.id.enterSecurityAnswer);

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
            try {
            while (true) {

                Socket s = new Socket("10.0.2.2", 24602);
                DataOutputStream dos = new DataOutputStream((s.getOutputStream()));
                DataInputStream dis = new DataInputStream((s.getInputStream()));

                if (cc.createAccount(enterUserName.toString(), enterPassword.toString(),
                        enterSecurityQuestion.toString(), enterSecurityAnswer.toString())){
                    startActivity(new Intent(CreateAccount.this, HomeScreen.class));
                    CreateAccount.this.finish();
                }
                dos.close();
                dis.close();
                s.close();
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown host");
        } catch (IOException e) {
            System.out.println("IO Problem");
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
