
package com.peter.common.net;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.peter.common.utils.LogUtils;
import com.peter.common.utils.NetUtils;
import com.peter.common.utils.SharedPreferenceUtils;
import com.peter.common.utils.SignUtils;
import com.peter.common.utils.log.ILog;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * http类，异步方法，使用了android-async-http类库。请同时阅读 张云飞
 *
 * @author wqr TODO 重构
 * @version V1.0
 * @date 2015年6月1日 上午10:19:49
 */
public class HttpUtil {
    private static final String TAG = HttpUtil.class.getSimpleName();
    public static final ILog logger = LogUtils.getLogger(TAG);
    protected static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象

    static {
        client.setTimeout(Constants.HTTP_TIME_OUT); // 设置链接超时，如果不设置，默认为10s
    }

    private static void dealCommonParams(Context context, Map<String, String> params) {
        params.put("uid", (String) SharedPreferenceUtils.get(context, Constants.KEY_UID, ""));
        params.put("ts", System.currentTimeMillis() + "");
        params.put("sid", (String) SharedPreferenceUtils.get(context, Constants.KEY_SID, ""));
        params.put("sign_type", "MD5");
        params.put("sn", "scanner");
        String sign = SignUtils.signByMd5(params, Constants.SIGN_MD5_KEY_STRING);
        params.put("sign", sign);
    }

    /**
     * @param @param urlString
     * @param @param res 设定文件
     * @return void 返回类型
     * @throws
     * @Title: post
     * @Description: 用一个完整url获取一个string对象
     */
    public static RequestHandle post(final Activity act, final String url,
                                     Map<String, String> params, ResponseHandlerInterface responseHandler) {
        return post(client, act, url, params, responseHandler);
    }

    /**
     * 文件上传
     *
     * @param act
     * @param url
     * @param fileParams
     * @param params
     * @param responseHandler
     */
    public static RequestHandle upload(Activity act, String url, Map<String, File> fileParams, Map<String, String> params, ResponseHandlerInterface responseHandler) {
        //设置文件上传参数
        RequestParams requestParams = initUploadParams(act, fileParams, params);
        printLog(url, params);
        RequestHandle requestHandle = client.post(url, requestParams, responseHandler);
        if (responseHandler instanceof MyJsonResponseHandler) {
            MyJsonResponseHandler myJsonResponseHandler = (MyJsonResponseHandler) responseHandler;
            myJsonResponseHandler.setOnDialogCancelListener(new OnCancelListenerDelegate(requestHandle));
        }
        return requestHandle;
    }

    /**
     * 封装需要上传的对象zZ
     *
     * @param context
     * @param fileParams
     * @param params
     * @return
     */
    private static RequestParams initUploadParams(Context context, Map<String, File> fileParams,
                                                  Map<String, String> params) {
        if (null == params) {
            params = new HashMap<String, String>();
        }

        /**
         * 时间戳
         */
        params.put("ts", System.currentTimeMillis() + "");

        /**
         * ???
         */
        params.put("sid", (String) SharedPreferenceUtils.get(context, Constants.KEY_SID, ""));

        /**
         * 用户ID, 用来唯一标识用户信息
         */
        params.put("sid", (String) SharedPreferenceUtils.get(context, Constants.KEY_UID, ""));

        RequestParams requestParams = new RequestParams(params);
        try {

            Iterator<String> fileKeys = null == fileParams ? null : fileParams.keySet().iterator();

            /**
             * 添加需要上传的文件
             */
            while (null != fileKeys && fileKeys.hasNext()) {
                String key = fileKeys.next();
                requestParams.put(key, fileParams.get(key), "application/octet-stream");
            }
            return requestParams;
        } catch (FileNotFoundException e) {
            logger.debug("upload error " + e);

        }

        return null;
    }

    /**
     * @param @param urlString
     * @param @param res 设定文件
     * @return void 返回类型
     * @throws
     * @Title: post
     * @Description: 用一个完整url获取一个string对象
     */
    public static RequestHandle post(AsyncHttpClient client1, final Activity context, final String url,
                                     Map<String, String> params, ResponseHandlerInterface responseHandler) {

        if (!NetUtils.isConnected(context)) {
            alert_Not_Network_OnMainThread(context);
            return null;
        }

        if (params == null) {
            params = new HashMap<String, String>();
        }
        // 公共参数处理
        dealCommonParams(context, params);

        // 日志输出
        printLog(url, params);

        // showProgress(progressDialog);
        RequestParams rp = new RequestParams(params);

        RequestHandle requestHandle = client1.post(url, rp, responseHandler);
        if (responseHandler instanceof MyJsonResponseHandler) {
            MyJsonResponseHandler myJsonResponseHandler = (MyJsonResponseHandler) responseHandler;
            myJsonResponseHandler.setOnDialogCancelListener(new OnCancelListenerDelegate(requestHandle));
        }
        return requestHandle;
    }

    private static class OnCancelListenerDelegate implements DialogInterface.OnCancelListener {
        WeakReference<RequestHandle> requestHandleWrapper;

        public OnCancelListenerDelegate(RequestHandle requestHandle) {
            requestHandleWrapper = new WeakReference<RequestHandle>(requestHandle);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            if (requestHandleWrapper != null && requestHandleWrapper.get() != null) {
                requestHandleWrapper.get().cancel(true);
            }
        }
    }

    protected static void alert_Not_Network_OnMainThread(final Activity act) {
        if (act == null || act.isFinishing())
            return;
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (act == null || act.isFinishing())
                    return;
                Toast.makeText(act, "网络连接异常", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * @param url
     * @param params
     */
    private static void printLog(String url, Map<String, String> params) {
        logger.info("【准备发送http请求：】" + url);
        Iterator it = params.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("【准备请求的参数：】\n");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            sb.append("key = " + key + ", value = " + value + "\n");
        }
        logger.info(sb.toString());
    }
}
