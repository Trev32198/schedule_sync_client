package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
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
    ClientCommunicator cc = new ClientCommunicator();



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
        enterPasswordToChange = findViewById(R.id.enterPasswordToChange);
        enterPasswordToChange = findViewById(R.id.enterNewPassword);

        if (TextUtils.isEmpty(enterPasswordToChange.getText())) {
            enterPasswordToChange.setError("Password is required");
        }
        if (TextUtils.isEmpty((enterNewPassword.getText()))) {
            enterNewPassword.setError(("New password can't be blank"));
        } else {
            if (cc.changePW(enterNewPassword.toString())) {

                startActivity(new Intent(Settings.this, HomeScreen.class));
                Settings.this.finish();
            }
        }
    }
    public void changeRecovery(View view) {
            newSQ = findViewById(R.id.newRecoveryQuestion);
            newSA = findViewById(R.id.newRecoveryAnswer);


        if (cc.changeSQ(newSQ.toString(), newSA.toString())) {


                startActivity(new Intent(Settings.this, HomeScreen.class));
                Settings.this.finish();
            }
        }

    public void deleteAccount(View view) {
                if (cc.deleteAccount()){

                    startActivity(new Intent(Settings.this, HomeScreen.class));
                    Settings.this.finish();
                }
                }


}
