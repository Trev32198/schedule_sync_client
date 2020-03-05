package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.*;
import java.io.*;

public class LogIn extends AppCompatActivity {


    ClientCommunicator cc = new ClientCommunicator();
    EditText userName;
    EditText password;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);




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
            cc.setCredentials(userName.toString(), password.toString(), "pw");


            try {
                while (true) {

                    Socket s = new Socket("10.0.2.2", 24602);
                    DataOutputStream dos = new DataOutputStream((s.getOutputStream()));
                    DataInputStream dis = new DataInputStream((s.getInputStream()));

                    cc.setCredentials(userName.toString(), password.toString(), "pw");
                    dos.close();
                    dis.close();
                    s.close();
                    startActivity(new Intent(LogIn.this, HomeScreen.class));
                    LogIn.this.finish();
                }
            } catch (UnknownHostException e) {
                System.out.println("Unknown host");
            } catch (IOException e) {
                System.out.println("IO Problem");

            }
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
