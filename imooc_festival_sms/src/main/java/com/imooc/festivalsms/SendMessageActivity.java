package com.imooc.festivalsms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.festivalsms.bean.Festival;
import com.imooc.festivalsms.bean.FestivalLab;
import com.imooc.festivalsms.bean.Msg;
import com.imooc.festivalsms.bean.SendedMsg;
import com.imooc.festivalsms.biz.SmsBiz;
import com.imooc.festivalsms.view.FlowLayout;

import java.util.HashMap;
import java.util.HashSet;

public class SendMessageActivity extends AppCompatActivity
{

    public static final String KEY_FESTIVAL_ID = "festivalID";
    public static final String KEY_MSG_ID = "msgId";


    private int mFestivalId;
    private int mMsgId;

    private Festival mFestival;
    private Msg mMsg;

    private EditText mEdMsg;
    private Button mBtnAdd;
    private FlowLayout mFlContacts;
    private FloatingActionButton mFabSend;
    private View mLayoutLoading;
    public static final int CODE_REQUEST = 1;

    private HashSet<String> mContactsNames = new HashSet<String>();
    private HashSet<String> mContactsNums = new HashSet<String>();
    private String contactNumber;

    private LayoutInflater mInflater;

    public static final String ACTION_SEND_MESSAGE = "ACTION_SEND_MSG";
    public static final String ACTION_DELIVER_MESSAGE = "ACTION_DELIVER_MSG";

    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverBroadcastReceiver;

    private SmsBiz mSmsBiz;

    private int mMsgSendCount = 0;

    private int mTotalCount;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        mInflater = LayoutInflater.from(this);
        mSmsBiz = new SmsBiz(this);
        initDatas();
        initView();
        initEvent();

        initRecivers();
    }

    private void initRecivers()
    {

        Intent sendIntent = new Intent(ACTION_SEND_MESSAGE);
        mSendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);

        Intent deliverIntent = new Intent(ACTION_DELIVER_MESSAGE);
        mDeliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);


        registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                mMsgSendCount++;
                if (getResultCode() == RESULT_OK)
                {
                    Log.e("Tag", "短信发送成功" + (mMsgSendCount + "/" + mTotalCount));
                } else
                {
                    Log.e("Tag", "短信发送失败");
                }

                Toast.makeText(SendMessageActivity.this, (mMsgSendCount + "/" + mTotalCount) + "短信发送成功", Toast.LENGTH_SHORT).show();

                if (mMsgSendCount == mTotalCount)
                {
                    finish();
                }
            }
        }, new IntentFilter(ACTION_SEND_MESSAGE));


        registerReceiver(mDeliverBroadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Log.e("Tag", "联系人已经成功接受到短信");
            }
        }, new IntentFilter(ACTION_DELIVER_MESSAGE));


    }

    private void initEvent()
    {
        mBtnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQUEST);
            }
        });

        mFabSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String mMsgContent = mEdMsg.getText().toString();

                if (mContactsNames.size() == 0)
                {
                    Toast.makeText(SendMessageActivity.this, "请先选择联系人", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mMsgContent))
                {
                    Toast.makeText(SendMessageActivity.this, "短信内容为空！", Toast.LENGTH_SHORT).show();
                    return;
                }


                mLayoutLoading.setVisibility(View.VISIBLE);
                mTotalCount = mSmsBiz.sendMsg(mContactsNames, buildSendMsg(mMsgContent), mSendPi, mDeliverPi);
                mMsgSendCount = 0;
            }
        });
    }

    private SendedMsg buildSendMsg(String mMsgContent)
    {

        SendedMsg sendedMsg = new SendedMsg();
        sendedMsg.setMsg(mMsgContent);
        sendedMsg.setFestivalName(mFestival.getName());

        String names = "";
        for (String name : mContactsNames)
        {
            names += name + ":";
        }
        String numbers = "";
        for (String number : mContactsNums)
        {
            numbers += number + ":";
        }
        sendedMsg.setNames(names.substring(0, names.length() - 1));
        sendedMsg.setNumbers(numbers.substring(0, numbers.length() - 1));

        return sendedMsg;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                Uri contactUri = data.getData();

                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number = getContactNumber(cursor);

                if (!TextUtils.isEmpty(number))
                {
                    mContactsNums.add(number);
                    mContactsNames.add(contactName);
                    addTag(contactName);
                }

            }
        }
    }

    private void addTag(String contactName)
    {

        TextView view = (TextView) mInflater.inflate(R.layout.tag, mFlContacts, false);

        view.setText(contactName);

        mFlContacts.addView(view);

    }

    private void initDatas()
    {
        mFestivalId = getIntent().getIntExtra(KEY_FESTIVAL_ID, -1);
        mMsgId = getIntent().getIntExtra(KEY_MSG_ID, -1);

        mFestival = FestivalLab.getmInstance().getFestivalById(mFestivalId);
        setTitle(mFestival.getName());
    }

    private void initView()
    {

        mEdMsg = (EditText) findViewById(R.id.id_et_content);
        mBtnAdd = (Button) findViewById(R.id.id_btn_add);
        mFlContacts = (FlowLayout) findViewById(R.id.id_fl_contacts);
        mFabSend = (FloatingActionButton) findViewById(R.id.id_fab_send);
        mLayoutLoading = findViewById(R.id.id_layout_loading);

        mLayoutLoading.setVisibility(View.GONE);

        if (mMsgId != -1)
        {
            mMsg = FestivalLab.getmInstance().getMsgsById(mMsgId);
            mEdMsg.setText(mMsg.getContent());
        }

    }

    public static void toActivity(Context context, int festivalID, int msgId)
    {

        Intent intent = new Intent(context, SendMessageActivity.class);

        intent.putExtra(KEY_FESTIVAL_ID, festivalID);
        intent.putExtra(KEY_MSG_ID, msgId);

        context.startActivity(intent);

    }

    public String getContactNumber(Cursor cursor)
    {

        String number = null;
        int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

        if (numberCount > 0)
        {
            int contactsId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactsId, null, null);

            phoneCursor.moveToFirst();
            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }

        cursor.close();

        return number;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverBroadcastReceiver);
    }
}
