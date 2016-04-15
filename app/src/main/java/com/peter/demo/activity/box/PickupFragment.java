package com.peter.demo.activity.box;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;
import com.peter.demo.config.Constants;
import com.peter.demo.utils.HeaderHelper;

import java.lang.reflect.Method;


/**
 * @author peter
 * @ClassName: PickupAct
 * @date 2015-1-7 下午8:10:18
 */
public class PickupFragment extends BackHeaderFragment implements TextWatcher, OnTouchListener, OnLongClickListener, View.OnClickListener {

    private RelativeLayout rlGoHome;
    private Button btnNum0, btnNum1, btnNum2, btnNum3, btnNum4, btnNum5, btnNum6, btnNum7, btnNum8, btnNum9, btnBackspace;
    private EditText etPwd1, etPwd2, etPwd3, etPwd4, etPwd5, etPwd6;

    @Override
    public int layoutId() {
        return R.layout.fragment_pickup;
    }

    @Override
    public void initUI() {
        // 播放语音
        getApp().getSoundPool().play(getApp().getSoundMap().get(Constants.Voice.INPUT_PASSWORD), 1, 1, 0, 0, 1);

        // 获取控件对象
        rlGoHome = (RelativeLayout) findViewById(R.id.rl_goHome);
        btnNum0 = (Button) findViewById(R.id.btn_num0);
        btnNum1 = (Button) findViewById(R.id.btn_num1);
        btnNum2 = (Button) findViewById(R.id.btn_num2);
        btnNum3 = (Button) findViewById(R.id.btn_num3);
        btnNum4 = (Button) findViewById(R.id.btn_num4);
        btnNum5 = (Button) findViewById(R.id.btn_num5);
        btnNum6 = (Button) findViewById(R.id.btn_num6);
        btnNum7 = (Button) findViewById(R.id.btn_num7);
        btnNum8 = (Button) findViewById(R.id.btn_num8);
        btnNum9 = (Button) findViewById(R.id.btn_num9);
        btnBackspace = (Button) findViewById(R.id.btn_backspace);
        etPwd1 = (EditText) findViewById(R.id.et_pwd1);
        etPwd2 = (EditText) findViewById(R.id.et_pwd2);
        etPwd3 = (EditText) findViewById(R.id.et_pwd3);
        etPwd4 = (EditText) findViewById(R.id.et_pwd4);
        etPwd5 = (EditText) findViewById(R.id.et_pwd5);
        etPwd6 = (EditText) findViewById(R.id.et_pwd6);

        // 为对象设置监听事件
        rlGoHome.setOnClickListener(this);
        btnNum0.setOnClickListener(this);
        btnNum1.setOnClickListener(this);
        btnNum2.setOnClickListener(this);
        btnNum3.setOnClickListener(this);
        btnNum4.setOnClickListener(this);
        btnNum5.setOnClickListener(this);
        btnNum6.setOnClickListener(this);
        btnNum7.setOnClickListener(this);
        btnNum8.setOnClickListener(this);
        btnNum9.setOnClickListener(this);
        btnBackspace.setOnClickListener(this);
        btnBackspace.setOnTouchListener(this);
        btnBackspace.setOnLongClickListener(this);
        etPwd1.addTextChangedListener(this);
        etPwd2.addTextChangedListener(this);
        etPwd3.addTextChangedListener(this);
        etPwd4.addTextChangedListener(this);
        etPwd5.addTextChangedListener(this);
        etPwd6.addTextChangedListener(this);

        // 初始化键盘
        initKeyBoard();
    }

    @Override
    public void initData() {
        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        TextView tvTime = (TextView) findViewById(R.id.tv_time);
        // 顶部显示当前日期
        HeaderHelper.updateCurrentDateTime(mContext, tvDate, tvTime);
    }

    @Override
    public void initHeader() {
        getHeader().hide();
    }

    /**
     * 初始化键盘，不显示软键盘
     */
    private void initKeyBoard() {
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(etPwd1, false);
            setShowSoftInputOnFocus.invoke(etPwd2, false);
            setShowSoftInputOnFocus.invoke(etPwd3, false);
            setShowSoftInputOnFocus.invoke(etPwd4, false);
            setShowSoftInputOnFocus.invoke(etPwd5, false);
            setShowSoftInputOnFocus.invoke(etPwd6, false);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_goHome:// 返回主页
                returnTOHomePage();
                break;
            case R.id.btn_num0:
                inputPwd(0);
                break;
            case R.id.btn_num1:
                inputPwd(1);
                break;
            case R.id.btn_num2:
                inputPwd(2);
                break;
            case R.id.btn_num3:
                inputPwd(3);
                break;
            case R.id.btn_num4:
                inputPwd(4);
                break;
            case R.id.btn_num5:
                inputPwd(5);
                break;
            case R.id.btn_num6:
                inputPwd(6);
                break;
            case R.id.btn_num7:
                inputPwd(7);
                break;
            case R.id.btn_num8:
                inputPwd(8);
                break;
            case R.id.btn_num9:
                inputPwd(9);
                break;
            case R.id.btn_backspace:// 删除
                deletePwd();
                break;
        }
    }

    /**
     * 输入密码
     */
    public void inputPwd(int num) {
        if (etPwd1.hasFocus()) {
            etPwd1.setText(String.valueOf(num));
            etPwd2.setFocusable(true);
            etPwd2.requestFocus();
            Selection.setSelection(etPwd2.getText(), etPwd2.length());
        } else if (etPwd2.hasFocus()) {
            etPwd2.setText(String.valueOf(num));
            etPwd3.setFocusable(true);
            etPwd3.requestFocus();
            Selection.setSelection(etPwd3.getText(), etPwd3.length());
        } else if (etPwd3.hasFocus()) {
            etPwd3.setText(String.valueOf(num));
            etPwd4.setFocusable(true);
            etPwd4.requestFocus();
            Selection.setSelection(etPwd4.getText(), etPwd4.length());
        } else if (etPwd4.hasFocus()) {
            etPwd4.setText(String.valueOf(num));
            etPwd5.setFocusable(true);
            etPwd5.requestFocus();
            Selection.setSelection(etPwd5.getText(), etPwd5.length());
        } else if (etPwd5.hasFocus()) {
            etPwd5.setText(String.valueOf(num));
            etPwd6.setFocusable(true);
            etPwd6.requestFocus();
            Selection.setSelection(etPwd6.getText(), etPwd6.length());
        } else if (etPwd6.hasFocus()) {
            etPwd6.setText(String.valueOf(num));
            Selection.setSelection(etPwd6.getText(), etPwd6.length());
        }
        logger.info("------onClick(), Click on num" + num + " Button");
    }

    /**
     * 删除取件密码
     */
    public void deletePwd() {
        if (etPwd6.hasFocus()) {
            if (!"".equals(etPwd6.getText().toString().trim())) {
                etPwd6.setText("");
            } else {
                etPwd5.setFocusable(true);
                etPwd5.requestFocus();
            }
            Selection.setSelection(etPwd5.getText(), etPwd5.length());
        } else if (etPwd5.hasFocus()) {
            if (!"".equals(etPwd5.getText().toString().trim())) {
                etPwd5.setText("");
            } else {
                etPwd4.setFocusable(true);
                etPwd4.requestFocus();
            }
            Selection.setSelection(etPwd4.getText(), etPwd4.length());
        } else if (etPwd4.hasFocus()) {
            if (!"".equals(etPwd4.getText().toString().trim())) {
                etPwd4.setText("");
            } else {
                etPwd3.setFocusable(true);
                etPwd3.requestFocus();
            }
            Selection.setSelection(etPwd3.getText(), etPwd3.length());
        } else if (etPwd3.hasFocus()) {
            if (!"".equals(etPwd3.getText().toString().trim())) {
                etPwd3.setText("");
            } else {
                etPwd2.setFocusable(true);
                etPwd2.requestFocus();
            }
            Selection.setSelection(etPwd2.getText(), etPwd2.length());
        } else if (etPwd2.hasFocus()) {
            if (!"".equals(etPwd2.getText().toString().trim())) {
                etPwd2.setText("");
            } else {
                etPwd1.setFocusable(true);
                etPwd1.requestFocus();
            }
            Selection.setSelection(etPwd1.getText(), etPwd1.length());
        } else if (etPwd1.hasFocus()) {
            if (!"".equals(etPwd1.getText().toString().trim())) {
                etPwd1.setText("");
            }
        }
    }

    /**
     * 开箱
     */
    public void openBox() {
        if (!"".equals(etPwd1.getText().toString().trim()) && !"".equals(etPwd2.getText().toString().trim()) && !"".equals(etPwd3.getText().toString().trim())
                && !"".equals(etPwd4.getText().toString().trim()) && !"".equals(etPwd5.getText().toString().trim())
                && !"".equals(etPwd6.getText().toString().trim())) {
            boolean pwdIsRight = false;
            // 密码正确
            if (pwdIsRight) {
                alert("open box success");
                logger.info("------OpenBox, open box sccuess");
            } else {
                alert("open box faild");
                logger.info("------OpenBox, open box fail");
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        openBox();
        logger.info("---open box---");
    }

    private Runnable runnable;// 用于长按连续删除密码

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            runnable = null;
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        final Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                deletePwd();
                if (runnable != null) {
                    handler.postDelayed(this, 200);
                }
            }
        };
        handler.postDelayed(runnable, 10);
        return false;
    }

    public void returnTOHomePage() {
//        // 提示是否返回主页
        new AlertDialog.Builder(mContext).setTitle(R.string.operation_hint).setCancelable(false).setMessage(R.string.return_hine)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeTopFragment();
                        logger.info("------onClick(), Click on GoHome Button");
                    }
                }).setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onFragmentBackPressed() {
        closeTopFragment();
        return true;
    }

}
