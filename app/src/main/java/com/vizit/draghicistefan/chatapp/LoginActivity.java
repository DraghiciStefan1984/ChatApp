package com.vizit.draghicistefan.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import util.ProgressGenerator;

public class LoginActivity extends AppCompatActivity
{
    private Button loginAfterSignupButton;
    private EditText usernameId, passwordId;
    private ProgressGenerator progressGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginAfterSignupButton= (Button) findViewById(R.id.loginAfterSignupButton);
        usernameId= (EditText) findViewById(R.id.usernameId);
        passwordId= (EditText) findViewById(R.id.passwordId);

        loginAfterSignupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String uName=usernameId.getText().toString();
                String pWord=passwordId.getText().toString();
                if(!uName.equals("") || !pWord.equals(""))
                {
                    ParseUser.logInInBackground(uName, pWord, new LogInCallback()
                    {
                        @Override
                        public void done(ParseUser user, ParseException e)
                        {
                            if(e==null)
                            {
                                startActivity(new Intent(LoginActivity.this, ChatActivity.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Not logged in", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
