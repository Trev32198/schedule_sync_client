package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {

    // This activity always gets created at the start of the app
    // Therefore, we can safely use this activity's context to power
    // SettingsManager perpetually
    public static SharedPreferences prefs;

    EditText userName;
    EditText password;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // On startup, set up the SharedPreferences object
        prefs = getSharedPreferences("storage", MODE_PRIVATE);


        userName = findViewById(R.id.enterUserName);
        password = findViewById(R.id.enterPassword);
        Button createAccountButton = findViewById(R.id.createAccount);

        View.OnClickListener accountListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, CreateAccount.class));
                LogIn.this.finish();
            }
        };
                createAccountButton.setOnClickListener(accountListener);



    }
    public void logInFunction(View view){
        if (TextUtils.isEmpty(userName.getText())){
            userName.setError("Username is required");
        }
        if (TextUtils.isEmpty((password.getText()))){
            password.setError(("Password is required"));
        }
        else {
            ClientCommunicator.setCredentials(userName.getText().toString(), password.getText().toString(), "pw");
                    startActivity(new Intent(LogIn.this, HomeScreen.class));
                    LogIn.this.finish();
        }

    }
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

        try {
            while (true) {

                Socket s = new Socket("127.0.0.1", 4963);
                DataOutputStream dos = new DataOutputStream((s.getOutputStream()));
                DataInputStream dis = new DataInputStream((s.getInputStream()));
                dos.writeUTF("THIS IS A TEST");
                dos.close();
                dis.close();
                s.close();
            }
        }
            catch(UnknownHostException e){
            System.out.println("Unknown host");
        }
        catch (IOException e){
            System.out.println("IO Problem");
        }

    }*/
}
