package data;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vizit.draghicistefan.chatapp.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import model.Message;

/**
 * Created by Draghici Stefan on 05.02.2016.
 */
public class ChatAdapter extends ArrayAdapter<Message>
{
    private String mUserId;
    public ChatAdapter(Context context, String userId, List<Message> messages)
    {
        super(context, 0, messages);
        mUserId=userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.chat_row, parent, false);
            final ViewHolder holder=new ViewHolder();
            holder.imageLeft= (ImageView) convertView.findViewById(R.id.profileLeftImageView);
            holder.imageRight= (ImageView) convertView.findViewById(R.id.profileRightImageView);
            holder.body= (TextView) convertView.findViewById(R.id.messageBodyTextView);
            convertView.setTag(holder);
        }
        final Message message=(Message)getItem(position);
        final ViewHolder viewHolder= (ViewHolder) convertView.getTag();
        final boolean isMe=message.getUserId().equals(mUserId);

        if(isMe)
        {
            viewHolder.imageRight.setVisibility(View.VISIBLE);
            viewHolder.imageLeft.setVisibility(View.GONE);
            viewHolder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        else
        {
            viewHolder.imageRight.setVisibility(View.GONE);
            viewHolder.imageLeft.setVisibility(View.VISIBLE);
            viewHolder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        final ImageView profileView=isMe ? viewHolder.imageRight : viewHolder.imageLeft;
        Picasso.with(getContext()).load(getProfileGravatar(message.getUserId())).into(profileView);
        viewHolder.body.setText(message.getBody());
        return convertView;
    }

    private static String getProfileGravatar(final String userId)
    {
        String hex="";
        try
        {
            final MessageDigest digest=MessageDigest.getInstance("MD5");
            final byte[] hash=digest.digest(userId.getBytes());
            final BigInteger bigInteger=new BigInteger(hash);
            hex=bigInteger.abs().toString(16);
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "https://www.gravatar.com/avatar/"+hex+"?d=identicon";
    }

    class ViewHolder
    {
        public ImageView imageLeft, imageRight;
        public TextView body;
    }
}


