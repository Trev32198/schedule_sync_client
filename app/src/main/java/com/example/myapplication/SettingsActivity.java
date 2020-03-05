package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    EditText enterPasswordToChange;
    EditText enterNewPassword;
    EditText newSQ;
    EditText newSA;
    ClientCommunicator cc = new ClientCommunicator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public void changePassword(View view) {
        if (TextUtils.isEmpty(enterPasswordToChange.getText())) {
            enterPasswordToChange.setError("Password is required");
        }
        if (TextUtils.isEmpty((enterNewPassword.getText()))) {
            enterNewPassword.setError(("New password can't be blank"));
        }
        else{
            cc.changePW(enterNewPassword.toString());
        }
        }
        public void changeRecovery(View view) {
        cc.changeSQ(newSQ.toString(), newSA.toString());
        }
        public void deleteAccount(View view) {
        cc.deleteAccount();
        }

}
