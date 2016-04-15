package com.peter.demo.activity.sms;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peter.common.utils.SharedPreferenceUtils;
import com.peter.demo.R;
import com.peter.demo.receiver.SmsReceiver;
import com.peter.demo.base.BaseFragment;

import java.util.List;

/**
 * Created by songzhongkun on 16/2/24 16:32.
 */
public class SmsFragment extends BaseFragment implements View.OnClickListener {

    private Button btnRegister, btnUnregister, btnSend, btnCall;
    private SmsReceiver smsReceiver;
    private EditText etMobile, etContent, etToMobile;

    @Override
    public int layoutId() {
        return R.layout.fragment_sms;
    }

    @Override
    public void initUI() {
        // 获取发送按钮
        btnSend = (Button) findViewById(R.id.send);
        btnSend.setOnClickListener(this);

        // 获取呼叫按钮
        btnCall = (Button) findViewById(R.id.call);
        btnCall.setOnClickListener(this);

        // 注册监听广播
        btnRegister = (Button) findViewById(R.id.register);
        btnRegister.setOnClickListener(this);

        // 取消注册监听广播
        btnUnregister = (Button) findViewById(R.id.unregister);
        btnUnregister.setOnClickListener(this);

        // 获取手机号文本框
        etMobile = (EditText) findViewById(R.id.mobile);
        // 获取短信内容文本框
        etContent = (EditText) findViewById(R.id.content);
        // 转发手机号文本框
        etToMobile = (EditText) findViewById(R.id.et_toMobile);

        String toMobile = (String) SharedPreferenceUtils.get(mContext, "phone", "");
        etToMobile.setText(toMobile);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initHeader() {
        getHeader().setTitle("短信转发");
        getHeader().hideLeftAndRight();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                String toMobile = etToMobile.getText().toString().trim();
                SharedPreferenceUtils.put(mContext, "phone", toMobile);
                smsReceiver = new SmsReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
                mContext.registerReceiver(smsReceiver, intentFilter);
                Toast.makeText(mContext, R.string.registrSuccess, Toast.LENGTH_SHORT).show();
                break;
            case R.id.unregister:
                if (smsReceiver != null) {
                    mContext.unregisterReceiver(smsReceiver);
                }
                Toast.makeText(mContext, R.string.unRegisterSuccess, Toast.LENGTH_SHORT).show();
                break;
            case R.id.send:
                // 获取手机号
                String mobile = etMobile.getText().toString();
                // 获取短信内容
                String content = etContent.getText().toString();
                if (mobile.isEmpty() || content.isEmpty()) {
                    Toast.makeText(mContext, R.string.notNull, Toast.LENGTH_LONG).show();
                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    List<String> texts = smsManager.divideMessage(content);
                    for (String text : texts) {
                        smsManager.sendTextMessage(mobile, null, text, null, null);
                    }
                    // 添加一条发送结果提示
                    Toast.makeText(mContext, R.string.success, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.call:
                // 获取手机号
                mobile = etMobile.getText().toString();
                if (mobile.isEmpty()) {
                    Toast.makeText(mContext, R.string.phoneNotNull, Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
                    startActivity(intent);
                }
                break;
        }
    }
}
