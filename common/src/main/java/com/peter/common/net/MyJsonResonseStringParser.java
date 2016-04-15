package com.peter.common.net;

import android.text.TextUtils;

import com.peter.common.utils.LogUtils;
import com.peter.common.utils.log.ILog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by songzhongkun on 15/9/24.
 */
public class MyJsonResonseStringParser {
    private static final String TAG = MyJsonResonseStringParser.class.getSimpleName();
    public static final ILog logger = LogUtils.getLogger(TAG);

    /**
     * @param @param  responseString
     * @param @return
     * @param @throws MyJsonRootEntityParseException
     * @return String
     * @throws JSONException
     * @throws
     * @Description: 从服务端响应的字符串中 解析出 body部分的字符串，以再次解析使用（比如解析成bean）,遇到响应码
     * code！=0时会抛出异常
     */
    public static String parseBodyStringAndThrows(String responseString)
            throws MyJsonRootEntityParseException, JSONException {
        MyJsonRootEntity entity = parse(responseString);
        int code = entity.code;
        if (code != 0) {
            throw new MyJsonRootEntityParseException(code, entity.msg);
        }
        return entity.body;
    }

    /**
     * @param @param  responseString
     * @param @return
     * @param @throws MyJsonRootEntityParseException
     * @param @throws JSONException
     * @return MyJsonRootEntity
     * @throws
     * @Description: , 遇到响应码 code！=0时会抛出异常
     */
    public static MyJsonRootEntity parseAndThrow(String responseString)
            throws MyJsonRootEntityParseException, JSONException {
        MyJsonRootEntity entity = parse(responseString);
        int code = entity.code;
        if (code != 0) {
            throw new MyJsonRootEntityParseException(code, entity.msg);
        }
        return entity;
    }

    public static MyJsonRootEntity parseNotThrow(String responseString) {
        MyJsonRootEntity entity = null;
        try {
            entity = parse(responseString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }


    /**
     * @param @param  responseString
     * @param @return
     * @param @throws Exception
     * @return String
     * @throws
     * @Description: 从服务端响应的字符串中 解析出 body部分的字符串，以再次解析使用（比如解析成bean）
     */
    @SuppressWarnings("unused")
    private static String parseBodyString(String responseString) throws Exception {
        MyJsonRootEntity entity = parse(responseString);
        return entity.body;
    }

    /**
     * @param @param  responseString
     * @param @return
     * @param @throws Exception
     * @return MyJsonRootEntity
     * @throws JSONException
     * @Description: 解析成实体
     */
    private static MyJsonRootEntity parse(String responseString) throws JSONException {
        if (responseString == null)
            return null;
        logger.info("【准备解析：】" + responseString);

        JSONObject object = new JSONObject(responseString);
        String code = object.getString("code");
        String msg = object.getString("msg");
        String body = object.getString("body");

        MyJsonRootEntity entity = new MyJsonRootEntity();
        if (TextUtils.isEmpty(code))
            throw new JSONException("远程服务意外的返回内容：code = 空");
        entity.code = Integer.parseInt(code);
        entity.msg = msg == null ? "" : msg;
        entity.body = body == null ? "" : body;
        return entity;
    }

    /**
     * @param @param  responseString
     * @param @return
     * @param @throws Exception
     * @return int
     * @throws
     * @Description: 从服务端响应的字符串中 解析出 code
     */
    public static int parseCode(String responseString) throws Exception {
        MyJsonRootEntity entity = parse(responseString);
        return entity.code;
    }

    /**
     * 标准的 响应的json的结构
     *
     * @author
     * @Description:
     * @date 2015年4月1日 下午5:12:53
     */
    public static class MyJsonRootEntity {
        public int code;
        public String msg;
        public String body;
    }
}
