package com.vizit.draghicistefan.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import data.ChatAdapter;
import model.Message;
import util.ProgressGenerator;

public class ChatActivity extends AppCompatActivity
{
    private EditText messageEditText;
    private Button sendButton;
    private ProgressGenerator progressGenerator;
    private String currentUserId;
    private ListView listView;
    private ArrayList<Message> messages;
    private ChatAdapter mAdapter;
    private Handler handler=new Handler();

    public static final String USER_ID_KEY="userId";
    public static final int MAX_CHAT_MESSAGE_TO_SHOW=70;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getCurrentUser();
        handler.postDelayed(runnable, 100);
    }

    private void getCurrentUser()
    {
        currentUserId= ParseUser.getCurrentUser().getObjectId();
        messagePosting();
    }

    private void messagePosting()
    {
        messageEditText= (EditText) findViewById(R.id.messageEditText);
        sendButton= (Button) findViewById(R.id.sendButton);
        listView= (ListView) findViewById(R.id.chatListView);
        messages=new ArrayList<>();
        mAdapter=new ChatAdapter(ChatActivity.this, currentUserId, messages);
        listView.setAdapter(mAdapter);

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!messageEditText.getText().toString().equals(""))
                {
                    Message msg = new Message();
                    msg.setUserId(currentUserId);
                    msg.setBody(messageEditText.getText().toString());
                    msg.saveInBackground(new SaveCallback()
                    {
                        @Override
                        public void done(ParseException e)
                        {
                            if (e == null)
                            {
                                receiveMessages();
                            }
                        }
                    });
                    messageEditText.setText("");
                } else
                {
                    Toast.makeText(getApplicationContext(), "Empty message", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void receiveMessages()
    {
        ParseQuery<Message> query=ParseQuery.getQuery(Message.class);
        query.setLimit(MAX_CHAT_MESSAGE_TO_SHOW);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Message>()
        {
            @Override
            public void done(List<Message> messagesList, ParseException e)
            {
                if (e == null)
                {
                    messages.clear();
                    messages.addAll(messagesList);
                    mAdapter.notifyDataSetChanged();
                    listView.invalidate();
                } else
                {
                    Log.v("Error", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void refreshMessages()
    {
        receiveMessages();
    }

    Runnable runnable=new Runnable()
    {
        @Override
        public void run()
        {
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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
        if (id == R.id.logout)
        {
            ParseUser.logOutInBackground(new LogOutCallback()
            {
                @Override
                public void done(ParseException e)
                {
                    if(e==null)
                    {
                        startActivity(new Intent(ChatActivity.this, MainActivity.class));
                    }
                    else
                    {
                        Log.v("Error", "Error: " + e.getMessage());
                    }
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
