package com.vizit.draghicistefan.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dd.processbutton.FlatButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private FlatButton loginButton, createAccountButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        loginButton= (FlatButton) findViewById(R.id.loginButton);
        createAccountButton= (FlatButton) findViewById(R.id.createAccountButton);
        loginButton.setOnClickListener(this);
        createAccountButton.setOnClickListener(this);

//        ParseUser user = new ParseUser();
//        user.setUsername("my name");
//        user.setPassword("my pass");
//        user.setEmail("email@example.com");
//        user.put("phone", "650-253-0000");
//        user.signUpInBackground(new SignUpCallback()
//        {
//            @Override
//            public void done(ParseException e)
//            {
//                if(e==null)
//                {
//
//                }
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.loginButton:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.createAccountButton:
                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
                break;
        }
    }
}
