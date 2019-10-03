package com.example.yazi.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yazi.R;
import com.example.yazi.bean.Message;

import java.util.List;

//判断是自己的消息还是别人的消息
public class MessagesListAdapter extends BaseAdapter {

    private Context context;
    private List<Message> messagesItems;

    public MessagesListAdapter(Context context, List<Message> messagesItems) {
        this.context = context;
        this.messagesItems = messagesItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = messagesItems.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (messagesItems.get(position).isSelf()){
            convertView = inflater.inflate(R.layout.right,null);
        } else {
            convertView = inflater.inflate(R.layout.left,null);
        }

        TextView from = (TextView) convertView.findViewById(R.id.msg_from);
        TextView msg = (TextView) convertView.findViewById(R.id.msg);

        from.setText(message.getFromName());
        msg.setText(message.getMessage());

        return convertView;
    }
}
