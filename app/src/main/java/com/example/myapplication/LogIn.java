package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {

    EditText userName;
    EditText password;
    Button logInButton;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userName = findViewById(R.id.enterUserName);
        password = findViewById(R.id.enterPassword);
        Button createAccountButton = findViewById(R.id.createAccount);

        View.OnClickListener accountButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, CreateAccount.class));
                LogIn.this.finish();
            }
        };
                createAccountButton.setOnClickListener(accountButton);
    }

       /*
        logIn.setOnKeyListener(
                void;
        );
        */
        //Im saving the below commented out code for future screens


        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);




    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
