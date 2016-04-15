package com.peter.common.net;

/**
 * Created by songzhongkun on 15/9/24.
 */
public class MyJsonRootEntityParseException extends Exception {
    private static final long serialVersionUID = -8642978599404639923L;
    int code;
    String msg;

    public MyJsonRootEntityParseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public MyJsonRootEntityParseException(int code, String msg, Throwable throwable) {
        super(msg, throwable);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
