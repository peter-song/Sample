package com.peter.demo.activity.hotel;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.peter.common.net.Constants;
import com.peter.common.net.MyJsonResonseStringParser;
import com.peter.common.net.MyJsonResponseHandler;
import com.peter.common.utils.SharedPreferenceUtils;
import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;
import com.peter.demo.model.hotel.UserInfo;

import butterknife.Bind;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * 登录页
 * Created by songzhongkun on 15/11/5 11:14.
 */
public class LoginFragment extends BackHeaderFragment {
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    private HotelWebSvc hotelWebSvc;

    @Override
    public int layoutId() {
        return R.layout.fragment_hotel_login;
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
        getHeader().setTitle("登录");
    }

    @Override
    public boolean onFragmentBackPressed() {
        boolean isLogin = (boolean) SharedPreferenceUtils.get(mContext, Constants.IS_LOGIN, false);
        if (!isLogin) {
            return true;
        }

        return super.onFragmentBackPressed();
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    alert("用户名或密码不能为空");
                } else {
                    login2(username, password);
                }
                break;
            case R.id.btn_register:
                openNewFragment(new RegisterFragment(), true);
                break;
        }
    }

    private void login(final String username, String password) {
        hotelWebSvc.requestLogin(mContext, "phone", username, password, new MyJsonResponseHandler<UserInfo>(mContext, true) {
            @Override
            public void onSuccess(int i, Header[] headers, String s, UserInfo userInfo) {
                getApp().getLogin().isLogin = true;
                onLoginOK(userInfo);
                closeTopFragment();
            }

            @Override
            protected UserInfo parseResponse(String s, boolean b) throws Throwable {
                return getGson().fromJson(MyJsonResonseStringParser.parseBodyStringAndThrows(s), new TypeToken<UserInfo>() {
                }.getType());
            }
        });
    }

    private void login2(String name, String pwd) {
        hotelWebSvc.requestLogin2(mContext, name, pwd, new MyJsonResponseHandler<MyJsonResonseStringParser.MyJsonRootEntity>(mContext, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, MyJsonResonseStringParser.MyJsonRootEntity response) {
                if (response.code == 0) {
                    getApp().getLogin().isLogin = true;
                    SharedPreferenceUtils.put(mContext, Constants.IS_LOGIN, true);
                    closeTopFragment();
                }
            }

            @Override
            protected MyJsonResonseStringParser.MyJsonRootEntity parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return MyJsonResonseStringParser.parseAndThrow(rawJsonData);
            }
        });
    }

    private void onLoginOK(UserInfo userInfo) {
        SharedPreferenceUtils.put(mContext, Constants.KEY_SID, userInfo.session.sid);
        SharedPreferenceUtils.put(mContext, Constants.KEY_UID, userInfo.id);
        SharedPreferenceUtils.put(mContext, Constants.IS_LOGIN, true);
        getApp().getLogin().setUserinfo(userInfo);
    }

}
