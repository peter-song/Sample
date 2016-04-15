package com.peter.demo.activity.hotel;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.peter.common.net.MyJsonResonseStringParser;
import com.peter.common.net.MyJsonResponseHandler;
import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;

import butterknife.Bind;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * 注册页
 * Created by songzhongkun on 15/11/27 14:43.
 */
public class RegisterFragment extends BackHeaderFragment {

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.et_email)
    EditText et_email;

    private HotelWebSvc hotelWebSvc;

    @Override
    public int layoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initUI() {
        hotelWebSvc = new HotelWebSvc();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initHeader() {
        super.initHeader();
        getHeader().setTitle("注册");
    }

    @OnClick({R.id.btn_reset, R.id.btn_ok})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                et_username.setText("");
                et_password.setText("");
                et_phone.setText("");
                et_email.setText("");
                break;
            case R.id.btn_ok:
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)) {
                    alert("不能为空");
                } else {
                    register(username, password, phone, email);
                }
                break;
        }
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param phone    电话
     * @param email    邮箱
     */
    private void register(String username, String password, String phone, String email) {
        hotelWebSvc.register(mContext, username, password, phone, email, new MyJsonResponseHandler<MyJsonResonseStringParser.MyJsonRootEntity>(mContext, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, MyJsonResonseStringParser.MyJsonRootEntity response) {
                if (response.code == 0) {
                    alert("注册成功, 请登录");
                    closeTopFragment();
                }
            }

            @Override
            protected MyJsonResonseStringParser.MyJsonRootEntity parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return MyJsonResonseStringParser.parseNotThrow(rawJsonData);
            }
        });
    }
}
