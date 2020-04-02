package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    EditText enterPasswordToChange;
    EditText enterNewPassword;
    EditText newSQ;
    EditText newSA;
    EditText moodleName;
    EditText moodlePassword;
    EditText googleName;
    EditText googlePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void changePassword(View view) {

        enterPasswordToChange = findViewById(R.id.enterPasswordToChange);
        enterNewPassword = findViewById(R.id.enterNewPassword);

        if (TextUtils.isEmpty(enterPasswordToChange.getText())) {
            enterPasswordToChange.setError("Password is required");
        }
        if (TextUtils.isEmpty((enterNewPassword.getText()))) {
            enterNewPassword.setError(("New password can't be blank"));
        } else {
            if (ClientCommunicator.changePW(enterNewPassword.getText().toString())) {

                //startActivity(new Intent(Settings.this, HomeScreen.class));
                Settings.this.finish();
            }
        }
    }
    public void changeRecovery(View view) {

        newSQ = findViewById(R.id.newRecoveryQuestion);
        newSA = findViewById(R.id.newRecoveryAnswer);

        if (ClientCommunicator.changeSQ(newSQ.getText().toString(), newSA.getText().toString())) {


                //startActivity(new Intent(Settings.this, HomeScreen.class));
                Settings.this.finish();
            }
        }

    public void deleteAccount(View view) {

        if (ClientCommunicator.deleteAccount()){

            //startActivity(new Intent(Settings.this, HomeScreen.class));
            Settings.this.finish();
        }
    }

    public void storeMoodle(View view){
        moodleName = findViewById(R.id.moodleUsername);
        moodlePassword = findViewById(R.id.moodlePassword);

        SettingsManager.storeMoodleCredentials(moodleName.getText().toString(), moodlePassword.getText().toString());
        if (MoodleAPI.checkCredentials()) {

            Settings.this.finish();
        }
        else {
            startActivity(new Intent(Settings.this, Settings.class));
            Settings.this.finish();
        }
    }
    public void storeGoogle(View view){
        googleName = findViewById(R.id.googleUsername);
        googlePassword = findViewById(R.id.googlePassword);

        SettingsManager.storeGoogleCredentials(googleName.getText().toString(), googlePassword.getText().toString());
        Settings.this.finish();
    }
}
