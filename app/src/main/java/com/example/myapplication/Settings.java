package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Settings extends AppCompatActivity {

    EditText enterPasswordToChange;
    EditText enterNewPassword;
    EditText newSQ;
    EditText newSA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void changePassword(View view) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        enterPasswordToChange = findViewById(R.id.enterPasswordToChange);
        enterNewPassword = findViewById(R.id.enterNewPassword);

        if (TextUtils.isEmpty(enterPasswordToChange.getText())) {
            enterPasswordToChange.setError("Password is required");
        }
        if (TextUtils.isEmpty((enterNewPassword.getText()))) {
            enterNewPassword.setError(("New password can't be blank"));
        } else {
            if (ClientCommunicator.changePW(enterNewPassword.getText().toString())) {

                startActivity(new Intent(Settings.this, HomeScreen.class));
                Settings.this.finish();
            }
        }
    }
    public void changeRecovery(View view) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        newSQ = findViewById(R.id.newRecoveryQuestion);
            newSA = findViewById(R.id.newRecoveryAnswer);


        if (ClientCommunicator.changeSQ(newSQ.getText().toString(), newSA.getText().toString())) {


                startActivity(new Intent(Settings.this, HomeScreen.class));
                Settings.this.finish();
            }
        }

    public void deleteAccount(View view) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        if (ClientCommunicator.deleteAccount()){

                    startActivity(new Intent(Settings.this, HomeScreen.class));
                    Settings.this.finish();
                }
                }


}
