package com.peter.demo.activity.hotel;

import android.app.Activity;

import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;
import com.peter.common.net.BaseWebSvc;
import com.peter.common.net.HttpUtil;
import com.peter.common.net.MyRequestParam;
import com.peter.demo.net.Apis;

/**
 *
 * Created by songzhongkun on 15/11/5 12:17.
 */
public class HotelWebSvc extends BaseWebSvc {
    /**
     * 登录
     *
     * @param context    上下文
     * @param login_type 登录类型
     * @param phone      用户名（电话）
     * @param password   密码
     */
    public void requestLogin(Activity context, String login_type, String phone, String password, ResponseHandlerInterface responseHandler) {
        MyRequestParam params = MyRequestParam.create()
                .with("login_type", login_type)
                .with("phone", phone)
                .with("password", password)
                .end();

        HttpUtil.post(context, Apis.LOGIN, params, responseHandler);
    }

    /**
     * 登录
     *
     * @param context         上下文
     * @param name            用户名
     * @param pwd             密码
     * @param responseHandler 回调
     */
    public void requestLogin2(Activity context, String name, String pwd, ResponseHandlerInterface responseHandler) {
        MyRequestParam params = MyRequestParam.create()
                .with("name", name)
                .with("pwd", pwd)
                .end();

        HttpUtil.post(context, Apis.LOGIN2, params, responseHandler);
    }

    /**
     * 注册
     *
     * @param context
     * @param name
     * @param pwd
     * @param phone
     * @param email
     * @param responseHandler
     * @return
     */
    public RequestHandle register(Activity context, String name, String pwd, String phone, String email, ResponseHandlerInterface responseHandler) {
        MyRequestParam params = MyRequestParam.create()
                .with("name", name)
                .with("pwd", pwd)
                .with("phone", phone)
                .with("email", email)
                .end();

        return HttpUtil.post(context, Apis.REGISTER, params, responseHandler);
    }
}
