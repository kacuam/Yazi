package com.example.yazi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yazi.bean.Message;
import com.example.yazi.util.MessagesListAdapter;
import com.example.yazi.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText inputMsg;

    private MessagesListAdapter adapter;
    private List<Message> listMessages;
    private ListView listViewMessages;

    private Utils utils;

    private String name = "people";

    private static final String TAG_SELF = "self", TAG_NEW = "new",TAG_MESSAGE = "message", TAG_EXIT = "exit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnSend = (Button) findViewById(R.id.btn_send);
        inputMsg = (EditText) findViewById(R.id.input_msg);
        listViewMessages = (ListView) findViewById(R.id.lv_msg);

        utils = new Utils(getApplicationContext());

        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //sendMessageToServer(utils.getSendMessageJSON(inputMsg.getText().toString()));
                inputMsg.setText("");
            }
        });

        listMessages = new ArrayList<Message>();

        adapter = new MessagesListAdapter(this, listMessages);
        listViewMessages.setAdapter(adapter);

        //创建sockets客户端

        //创建服务端接受消息

        //发送消息
        //sendMessageToServer(inputMsg.getText());

    }

    private void sendMessageToServer(String message) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    // 解析从服务端收到的json消息
    private void parseMessage(final String msg) {

        try {
            JSONObject jObj = new JSONObject(msg);


            String flag = jObj.getString("flag");

            //如果是self，json中包含sessionId信息
            if (flag.equalsIgnoreCase(TAG_SELF)) {
                String sessionId = jObj.getString("sessionId");
                utils.storeSessionId(sessionId);
            } else if (flag.equalsIgnoreCase(TAG_NEW)) {
                String name = jObj.getString("name");
                String message = jObj.getString("message");
                String onlineCount = jObj.getString("onlineCount");

                showToast(name + message + ". Currently " + onlineCount+ " people online!");
            } else if (flag.equalsIgnoreCase(TAG_MESSAGE)) {
                String fromName = name;
                String message = jObj.getString("message");
                String sessionId = jObj.getString("sessionId");
                boolean isSelf = true;
                if (!sessionId.equals(utils.getSessionId())) {
                    fromName = jObj.getString("name");
                    isSelf = false;
                }

                Message m = new Message(fromName, message, isSelf);

                // 把消息加入arraylist
                appendMessage(m);

            } else if (flag.equalsIgnoreCase(TAG_EXIT)) {
                String name = jObj.getString("name");
                String message = jObj.getString("message");

                showToast(name + message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //把消息放到listView里
    private void appendMessage(final Message message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                listMessages.add(message);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showToast(final String message) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
            }
        });
    }

}
