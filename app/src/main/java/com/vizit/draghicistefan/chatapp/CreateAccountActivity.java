package com.vizit.draghicistefan.chatapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import util.ProgressGenerator;

public class CreateAccountActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener
{
    private EditText emailET, usernameET, passwordET;
    private ActionProcessButton createAccountButton;
    private ProgressGenerator progressGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emailET= (EditText) findViewById(R.id.userEmailEditText);
        usernameET= (EditText) findViewById(R.id.usernameAccountEditText);
        passwordET= (EditText) findViewById(R.id.usernamePasswordEditText);
        progressGenerator=new ProgressGenerator(this);
        createAccountButton = (ActionProcessButton) findViewById(R.id.signupButton);
        createAccountButton.setMode(ActionProcessButton.Mode.PROGRESS);
        createAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createAccount();
            }
        });

    }

    @Override
    public void onComplete()
    {
        startActivity(new Intent(CreateAccountActivity.this, ChatActivity.class));
    }

    private void createAccount()
    {
        final String uEmail=emailET.getText().toString();
        final String uUsername=usernameET.getText().toString();
        final String uPassword=passwordET.getText().toString();

        if(uEmail.equals("") || uUsername.equals("") || uPassword.equals(""))
        {
            AlertDialog.Builder dialog=new AlertDialog.Builder(CreateAccountActivity.this);
            dialog.setTitle("Empty fields!").setMessage("Please complete the form!");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else
        {
            ParseUser user=new ParseUser();
            user.setUsername(uUsername);
            user.setPassword(uPassword);
            user.setEmail(uEmail);
            user.signUpInBackground(new SignUpCallback()
            {
                @Override
                public void done(ParseException e)
                {
                    if(e==null)
                    {
                        progressGenerator.start(createAccountButton);
                        createAccountButton.setEnabled(false);

                        emailET.setEnabled(false);
                        usernameET.setEnabled(false);
                        passwordET.setEnabled(false);

                        logUserIn(uUsername, uPassword);
                    }
                }
            });
        }
    }

    private void logUserIn(String uUsername, String uPassword)
    {
        if(!uUsername.equals("") || !uPassword.equals(""))
        {
            ParseUser.logInInBackground(uUsername, uPassword, new LogInCallback()
            {
                @Override
                public void done(ParseUser user, ParseException e)
                {
                    if(e==null)
                    {
                        Log.v("AppInfo", user.getUsername());
                    }
                }
            });
        }
    }
}
