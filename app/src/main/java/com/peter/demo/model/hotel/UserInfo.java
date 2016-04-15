package com.peter.demo.model.hotel;


import java.io.Serializable;

/**
 * 用户信息实体
 *
 * @author yinshangtai
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public String avatar;
    public Garden garden;
    public String gender;
    public Integer id;
    /**
     * 登录名
     */
    public String name;
    public String nick;
    /**
     * 当前用户手机号
     */
    public String phone;
    public String role_mask;
    public Session session;
    public int status;

    public class Session implements Serializable {
        public String sid;
    }

    public class Garden implements Serializable {
        /**
         * @Fields serialVersionUID : TODO
         */
        private static final long serialVersionUID = 8207846183824922437L;
        public String address;
        public int type;
        public int id;
        public String name;
    }

    @Override
    public String toString() {
        return "UserInfo [avatar=" + avatar + ", garden=" + garden + ", gender=" + gender + ", id="
                + id + ", name=" + name + ", nick=" + nick + ", phone=" + phone + ", role_mask="
                + role_mask + ", session=" + session + ", status="
                + status + "]";
    }
}
