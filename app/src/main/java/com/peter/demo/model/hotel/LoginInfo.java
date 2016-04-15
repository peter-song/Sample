package com.peter.demo.model.hotel;

/**
 * Created by songzhongkun on 15/11/5 11:42.
 */
public class LoginInfo {
    public boolean isLogin;

    private UserInfo userinfo;

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }
}
