package com.imooc.festivalsms.biz;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import com.imooc.festivalsms.bean.SendedMsg;
import com.imooc.festivalsms.db.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/12/1.
 */
public class SmsBiz {

    private Context context;

    public SmsBiz(Context context) {
        this.context = context;
    }

    public int sendMsg(String number, String msg, PendingIntent sendPi, PendingIntent deliverPi) {

        SmsManager smsManager = SmsManager.getDefault();

        ArrayList<String> contents = smsManager.divideMessage(msg);
        for (String content : contents) {
            smsManager.sendTextMessage(number, null, content, sendPi, deliverPi);
        }

        return contents.size();
    }

    public int sendMsg(Set<String> numbers, SendedMsg msg, PendingIntent sendPi, PendingIntent deliverPi) {

        save(msg);
        int result = 0;
        for (String number : numbers) {
            int count = sendMsg(number, msg.getMsg(), sendPi, deliverPi);
            result += count;
        }

        return result;
    }


    private void save(SendedMsg sendedMsg) {

        sendedMsg.setDate(new Date());

        ContentValues values = new ContentValues();
        values.put(SendedMsg.COLUMN_DATE, sendedMsg.getDate().getTime());
        values.put(SendedMsg.COLUMN_FES_NAME, sendedMsg.getFestivalName());
        values.put(SendedMsg.COLUMN_MSG, sendedMsg.getMsg());
        values.put(SendedMsg.COLUMN_NAMES, sendedMsg.getNames());
        values.put(SendedMsg.COLUMN_NUMBERS, sendedMsg.getNumbers());

        context.getContentResolver().insert(SmsProvider.URI_SMS_ALL, values);
        System.out.println("succsess");

    }
}
